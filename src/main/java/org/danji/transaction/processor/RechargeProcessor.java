package org.danji.transaction.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.danji.global.error.ErrorCode;
import org.danji.localCurrency.domain.LocalCurrencyVO;
import org.danji.localCurrency.enums.BenefitType;
import org.danji.localCurrency.exception.LocalCurrencyException;
import org.danji.localCurrency.mapper.LocalCurrencyMapper;
import org.danji.transaction.converter.TransactionConverter;
import org.danji.transaction.domain.TransactionVO;
import org.danji.transaction.dto.request.TransferDTO;
import org.danji.transaction.dto.response.TransactionDTO;
import org.danji.transaction.enums.Direction;
import org.danji.transaction.enums.Type;
import org.danji.transaction.exception.TransactionException;
import org.danji.transaction.mapper.TransactionMapper;
import org.danji.wallet.domain.WalletVO;
import org.danji.wallet.exception.WalletException;
import org.danji.wallet.mapper.WalletMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service("RECHARGE")
@RequiredArgsConstructor
@Log4j2
public class RechargeProcessor implements TransferProcessor {

    private static final double RECHARGE_FEE_RATE = 0.01;

    private final WalletMapper walletMapper;
    private final LocalCurrencyMapper localCurrencyMapper;
    private final TransactionMapper transactionMapper;
    private final TransactionConverter transactionConverter;

    @Transactional
    @Override
    public List<TransactionDTO> process(TransferDTO transferDTO) {
        // TODO Authentication 객체에서 userID 정보 꺼내는 코드 작성
        //-------------------------------------------
        UUID userId = UUID.randomUUID();
        // transferDTO의 fromWalletId 로 메인지갑 불러오기
        WalletVO mainWalletVO = walletMapper.getWalletByUUId(transferDTO.getFromWalletId());
        if (mainWalletVO == null) {
            throw new WalletException(ErrorCode.WALLET_NOT_FOUND);
        }
        // 요청금액보다 메인 지갑의 잔액이 작다면 예외 터뜨리기
        // 수수료 1% 도 감안해서 계산
        if (mainWalletVO.getBalance() + mainWalletVO.getBalance() * RECHARGE_FEE_RATE < transferDTO.getAmount()) {
            throw new WalletException(ErrorCode.WALLET_BALANCE_NOT_ENOUGH);
        }
        WalletVO LocalCurrencyWalletVO = walletMapper.getWalletByUUId(transferDTO.getToWalletId());
        if (LocalCurrencyWalletVO == null) {
            throw new WalletException(ErrorCode.WALLET_NOT_FOUND);
        }
        // 해당 지갑의 LocalCurrencyId로 지역화폐 찾기
        LocalCurrencyVO localCurrencyVO = localCurrencyMapper.findById(LocalCurrencyWalletVO.getLocalCurrencyId());
        if (localCurrencyVO == null) {
            throw new LocalCurrencyException(ErrorCode.LOCAL_CURRENCY_NOT_FOUND);
        }

        if (localCurrencyVO.getBenefitType() == BenefitType.BONUS_CHARGE) {
            //요청금액 에 incentive 비율을 합한 금액으로 업데이트
            walletMapper.updateWalletBalance(mainWalletVO.getWalletId(), -transferDTO.getAmount());
            walletMapper.updateWalletBalance(LocalCurrencyWalletVO.getWalletId(), (transferDTO.getAmount() - (int) (transferDTO.getAmount() - transferDTO.getAmount() * RECHARGE_FEE_RATE)) * localCurrencyVO.getPercentage());
        } else if (localCurrencyVO.getBenefitType() == BenefitType.CASHBACK) {
            // 요청 금액 업데이트 시키기
            walletMapper.updateWalletBalance(mainWalletVO.getWalletId(), -transferDTO.getAmount());
            walletMapper.updateWalletBalance(LocalCurrencyWalletVO.getWalletId(), (int) (transferDTO.getAmount() - transferDTO.getAmount() * RECHARGE_FEE_RATE));
            //7일 뒤에 캐시백에 대한 부분 업데이트

            // TODO converter로 구현한거 빌더패턴으로 바꾸기
            // TODO 결제 로직에 빌더 패턴으로 바꿔서 이 코드 넣기
            // cashbackMapper.insert(cashbackConverter.toCashbackVO(
            //  UUID.randomUUID(), LocalCurrencyWalletVO.getWalletId(), transferDTO.getAmount() * localCurrencyVO.getPercentage(), LocalDateTime.now().plusDays(7), CashBackStatus.PENDING));

        }
        //transaction 테이블에 복식 부기
        //메인지갑 기준
        TransactionVO mainTx = transactionConverter.toTransactionVO(
                UUID.randomUUID(), mainWalletVO.getWalletId(), LocalCurrencyWalletVO.getWalletId(),
                mainWalletVO.getBalance(), mainWalletVO.getBalance() - transferDTO.getAmount(),
                transferDTO.getAmount(), Direction.EXPENSE, Type.CHARGE, "메인지갑(출금) -> 지역화폐 지갑", mainWalletVO.getWalletId());
        int successMainWalletCount = transactionMapper.insert(mainTx);

        if (successMainWalletCount != 1) {
            throw new TransactionException(ErrorCode.TRANSACTION_SAVE_FAILED_MAIN);
        }
        //지역화폐 기준
        //인센티브 규정일때는 인센티브 금액까지 포함해서 transaction 테이블에 넣기
        TransactionVO localTx = null;
        if (localCurrencyVO.getBenefitType() == BenefitType.BONUS_CHARGE) {
            localTx = transactionConverter.toTransactionVO(
                    UUID.randomUUID(), LocalCurrencyWalletVO.getWalletId(), mainWalletVO.getWalletId(),
                    LocalCurrencyWalletVO.getBalance(), LocalCurrencyWalletVO.getBalance() + (transferDTO.getAmount() - (int) (transferDTO.getAmount() - transferDTO.getAmount() * RECHARGE_FEE_RATE)) * localCurrencyVO.getPercentage(),
                    (transferDTO.getAmount() - (int) (transferDTO.getAmount() - transferDTO.getAmount() * RECHARGE_FEE_RATE)) * localCurrencyVO.getPercentage(), Direction.INCOME, Type.CHARGE, "메인지갑 -> 지역화폐 지갑(입금)", LocalCurrencyWalletVO.getWalletId());
            int successLocalCurrencyWalletCount = transactionMapper.insert(localTx);
            if (successLocalCurrencyWalletCount != 1) {
                throw new TransactionException(ErrorCode.TRANSACTION_SAVE_FAILED_LOCAL);
            }
        }
        //캐시백 규정일때는 캐시백 포함하지 않은 금액을 transaction 테이블에 넣어주고, 캐시백이 발생될때 또 transaction 테이블에 넣어주기
        else if (localCurrencyVO.getBenefitType() == BenefitType.CASHBACK) {
            localTx = transactionConverter.toTransactionVO(
                    UUID.randomUUID(), LocalCurrencyWalletVO.getWalletId(), mainWalletVO.getWalletId(),
                    LocalCurrencyWalletVO.getBalance(), (LocalCurrencyWalletVO.getBalance() + (int) (transferDTO.getAmount() - transferDTO.getAmount() * RECHARGE_FEE_RATE)),
                    (int) (transferDTO.getAmount() - transferDTO.getAmount() * RECHARGE_FEE_RATE), Direction.INCOME, Type.CHARGE, "메인지갑 -> 지역화폐 지갑(입금)", LocalCurrencyWalletVO.getWalletId());
            int successLocalCurrencyWalletCount = transactionMapper.insert(localTx);
            if (successLocalCurrencyWalletCount != 1) {
                throw new TransactionException(ErrorCode.TRANSACTION_SAVE_FAILED_LOCAL);
            }
        }

        return List.of(
                transactionConverter.toTransactionDTO(mainTx),
                transactionConverter.toTransactionDTO(localTx)
        );
    }
}
