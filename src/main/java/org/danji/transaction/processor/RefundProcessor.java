package org.danji.transaction.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.danji.cashback.mapper.CashbackMapper;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service("REFUND")
@RequiredArgsConstructor
@Log4j2
public class RefundProcessor implements TransferProcessor {

    private static final double RECHARGE_FEE_RATE = 0.01;

    private final WalletMapper walletMapper;
    private final LocalCurrencyMapper localCurrencyMapper;
    private final CashbackMapper cashbackMapper;
    private final TransactionConverter transactionConverter;
    private final TransactionMapper transactionMapper;

    @Override
    @Transactional
    public List<TransactionDTO> process(TransferDTO transferDTO) {
        // TODO Authentication 객체에서 userID 정보 꺼내는 코드 작성
        //-------------------------------------------
        //UUID userId = UUID.randomUUID();

        // transferDto의 from_wallet_id 필드로 지역화폐 지갑 찾기
        WalletVO LocalCurrencyWalletVO = walletMapper.getWalletByUUId(transferDTO.getFromWalletId()).orElseThrow(() -> new WalletException(ErrorCode.WALLET_NOT_FOUND));

        //transferDto의 to_wallet_id 필드로 메인지갑 찾기
        WalletVO mainWalletVO = walletMapper.getWalletByUUId(transferDTO.getToWalletId()).orElseThrow(() -> new WalletException(ErrorCode.WALLET_NOT_FOUND));

        // 해당 지갑의 LocalCurrencyId로 지역화폐 찾기
        LocalCurrencyVO localCurrencyVO = localCurrencyMapper.findById(LocalCurrencyWalletVO.getLocalCurrencyId());
        if (localCurrencyVO == null) {
            throw new LocalCurrencyException(ErrorCode.LOCAL_CURRENCY_NOT_FOUND);
        }

        //인센티브 시, 요청금액이 지역화폐 지갑의 잔액(잔액 - 잔액 * 인센티브 퍼센트) + 수수료 적용(1%) 보다 작다면 예외 처리
        if (LocalCurrencyWalletVO.getBalance() * ((100 - localCurrencyVO.getPercentage()) / 100.0) + transferDTO.getAmount() * RECHARGE_FEE_RATE < transferDTO.getAmount()) {
            throw new WalletException(ErrorCode.WALLET_BALANCE_NOT_ENOUGH);
        }
        //캐시백 시, 캐시백 테이블에서 walletId 조건 걸어서 모든 amount 합산 -> 이것을 잔액에서 빼기
        // 요청금액이 지역화폐 지갑의 잔액(잔액 - 모든 amount) + 수수료 적용(1%) 보다 작다면 예외 처리
        int amount = cashbackMapper.sumAmountByWalletId(LocalCurrencyWalletVO.getWalletId());
        if (transferDTO.getAmount() < LocalCurrencyWalletVO.getBalance() - amount + transferDTO.getAmount() * RECHARGE_FEE_RATE){
            throw new WalletException(ErrorCode.WALLET_BALANCE_NOT_ENOUGH);
        }
        /**
        요청할 수 있는 최대의 amount 값을 받도록 예외처리를 해놓았기 때문에 잔액 업데이트 할때, transferDto.getAmount( )를 그대로 사용해도 됨.
         근데 프론트 쪽에서, 요청 금액 이상의 요청이 들어왔을때 (수수료 포함, 인센티브 비율 만큼 돈 제거 등의 문구를 띄워줘야할것)
         **/
        //인센티브인 경우
        if (localCurrencyVO.getBenefitType() == BenefitType.BONUS_CHARGE) {
            walletMapper.updateWalletBalance(transferDTO.getFromWalletId(), -transferDTO.getAmount());
            walletMapper.updateWalletBalance(transferDTO.getToWalletId(), (int) (transferDTO.getAmount() - transferDTO.getAmount() * RECHARGE_FEE_RATE));
        }
        //캐시백인 경우
        else if (localCurrencyVO.getBenefitType() == BenefitType.CASHBACK) {
            // 요청 금액 업데이트 시키기
            walletMapper.updateWalletBalance(transferDTO.getFromWalletId(), -transferDTO.getAmount());
            walletMapper.updateWalletBalance(transferDTO.getToWalletId(), (int) (transferDTO.getAmount() - transferDTO.getAmount() * RECHARGE_FEE_RATE));
        }

        //transaction 테이블에 복식 부기
        //메인지갑 기준
        TransactionVO mainTx = transactionConverter.toTransactionVO(
                UUID.randomUUID(), transferDTO.getFromWalletId(), transferDTO.getToWalletId(),
                mainWalletVO.getBalance(), mainWalletVO.getBalance() + (int) (transferDTO.getAmount() - transferDTO.getAmount() * RECHARGE_FEE_RATE),
                (int) (transferDTO.getAmount() - transferDTO.getAmount() * RECHARGE_FEE_RATE), Direction.INCOME, Type.REFUND, "지역화폐 지갑 -> 메인지갑(입금)", mainWalletVO.getWalletId());
        int successMainWalletCount = transactionMapper.insert(mainTx);

        if (successMainWalletCount != 1) {
            throw new TransactionException(ErrorCode.TRANSACTION_SAVE_FAILED_MAIN);
        }

        //지역화폐 기준
        TransactionVO localTx = transactionConverter.toTransactionVO(
                UUID.randomUUID(), transferDTO.getFromWalletId(), transferDTO.getToWalletId(),
                LocalCurrencyWalletVO.getBalance(), LocalCurrencyWalletVO.getBalance() - transferDTO.getAmount(),
                transferDTO.getAmount(), Direction.EXPENSE, Type.REFUND, "지역화폐 지갑(출금) -> 메인지갑", LocalCurrencyWalletVO.getWalletId());
        int successLocalWalletCount = transactionMapper.insert(localTx);

        if (successLocalWalletCount != 1) {
            throw new TransactionException(ErrorCode.TRANSACTION_SAVE_FAILED_LOCAL);
        }
        return List.of(
                transactionConverter.toTransactionDTO(mainTx),
                transactionConverter.toTransactionDTO(localTx)
        );
    }
}
