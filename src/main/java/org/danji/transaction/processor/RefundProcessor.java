package org.danji.transaction.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.danji.cashback.mapper.CashbackMapper;
import org.danji.common.utils.AuthUtils;
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
import org.danji.transaction.exception.TransactionException;
import org.danji.transaction.mapper.TransactionMapper;
import org.danji.wallet.domain.WalletVO;
import org.danji.wallet.dto.WalletFilterDTO;
import org.danji.wallet.enums.WalletType;
import org.danji.wallet.exception.WalletException;
import org.danji.wallet.mapper.WalletMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.danji.transaction.validator.WalletValidator.checkOwnership;

@Service("REFUND")
@RequiredArgsConstructor
@Log4j2
public class RefundProcessor implements TransferProcessor<TransferDTO> {

    private static final double RECHARGE_FEE_RATE = 0.00;

    private final WalletMapper walletMapper;
    private final LocalCurrencyMapper localCurrencyMapper;
    private final CashbackMapper cashbackMapper;
    private final TransactionConverter transactionConverter;
    private final TransactionMapper transactionMapper;

    @Override
    @Transactional
    public List<TransactionDTO> process(TransferDTO transferDTO) {

        UUID userId = AuthUtils.getMemberId();
        // transferDto의 from_wallet_id 필드로 지역화폐 지갑 찾기
        WalletVO LocalCurrencyWalletVO = walletMapper.findById(transferDTO.getFromWalletId());
        if (LocalCurrencyWalletVO == null) {
            throw new WalletException(ErrorCode.WALLET_NOT_FOUND);
        }
        WalletFilterDTO walletFilterDTO = WalletFilterDTO.builder().memberId(userId).walletType(WalletType.LOCAL).build();
        List<WalletVO> localWalletByUserIdVO = walletMapper.findByFilter(walletFilterDTO);

        if (!checkOwnership(localWalletByUserIdVO, LocalCurrencyWalletVO)){
            throw new WalletException(ErrorCode.UNAUTHORIZED_WALLET_ACCESS);
        }
        //transferDto의 to_wallet_id 필드로 메인지갑 찾기
        WalletVO mainWalletVO = walletMapper.findById(transferDTO.getToWalletId());
        if (mainWalletVO == null) {
            throw new WalletException(ErrorCode.WALLET_NOT_FOUND);
        }

        WalletVO mainWalletByUserIdVO = walletMapper.findByMemberId(userId);
        if (!mainWalletByUserIdVO.getWalletId().equals(mainWalletVO.getWalletId())) {
            throw new WalletException(ErrorCode.UNAUTHORIZED_WALLET_ACCESS);
        }

        // 해당 지갑의 LocalCurrencyId로 지역화폐 찾기
        LocalCurrencyVO localCurrencyVO = localCurrencyMapper.findById(LocalCurrencyWalletVO.getLocalCurrencyId());
        if (localCurrencyVO == null) {
            throw new LocalCurrencyException(ErrorCode.LOCAL_CURRENCY_NOT_FOUND);
        }

        //int amount = cashbackMapper.sumAmountByWalletId(LocalCurrencyWalletVO.getWalletId());
        //인센티브 시, 요청금액이 지역화폐 지갑의 잔액(잔액 - 잔액 * 인센티브 퍼센트) + 수수료 적용(1%) 보다 작다면 예외 처리
        if (localCurrencyVO.getBenefitType() == BenefitType.INCENTIVE) {
            //double rawValue = LocalCurrencyWalletVO.getBalance() * (100.0 / (100 + localCurrencyVO.getPercentage()));
            if (transferDTO.getAmount() > LocalCurrencyWalletVO.getBalance()) {
                throw new WalletException(ErrorCode.WALLET_BALANCE_NOT_ENOUGH);
            }
        }
        //캐시백 시, 캐시백 테이블에서 walletId 조건 걸어서 모든 amount 합산 -> 이것을 잔액에서 빼기
        // 요청금액이 지역화폐 지갑의 잔액(잔액 - 모든 amount) + 수수료 적용(1%) 보다 작다면 예외 처리
//        else if (localCurrencyVO.getBenefitType() == BenefitType.CASHBACK) {
//            if (transferDTO.getAmount() < LocalCurrencyWalletVO.getBalance() - amount + transferDTO.getAmount() * RECHARGE_FEE_RATE) {
//                throw new WalletException(ErrorCode.WALLET_BALANCE_NOT_ENOUGH);
//            }
//        }
        /**
         요청할 수 있는 최대의 amount 값을 받도록 예외처리를 해놓았기 때문에 잔액 업데이트 할때, transferDto.getAmount( )를 그대로 사용해도 됨.
         근데 프론트 쪽에서, 요청 금액 이상의 요청이 들어왔을때 (수수료 포함, 인센티브 비율 만큼 돈 제거 등의 문구를 띄워줘야할것)
         **/
        //인센티브인 경우
        if (localCurrencyVO.getBenefitType() == BenefitType.INCENTIVE) {
            double rawValue = transferDTO.getAmount() * (100.0 / (100.0 + localCurrencyVO.getPercentage()));
            //-((int) Math.round(rawValue) + (int) (transferDTO.getAmount() * RECHARGE_FEE_RATE))
            walletMapper.updateWalletBalance(transferDTO.getFromWalletId(), -transferDTO.getAmount());
            walletMapper.updateWalletBalance(transferDTO.getToWalletId(), (int) Math.round(rawValue));
        }
        //캐시백인 경우
//        else if (localCurrencyVO.getBenefitType() == BenefitType.CASHBACK) {
//            // 요청 금액 업데이트 시키기
//            walletMapper.updateWalletBalance(transferDTO.getFromWalletId(), -(transferDTO.getAmount() + amount + (int) (transferDTO.getAmount() * RECHARGE_FEE_RATE)));
//            walletMapper.updateWalletBalance(transferDTO.getToWalletId(), transferDTO.getAmount());
//        }
        //인센티브도 캐시백도 아닌 경우 원금 그대로 환불
        else {
            int requestAmount = transferDTO.getAmount();
            if (requestAmount > LocalCurrencyWalletVO.getBalance()) {
                        throw new WalletException(ErrorCode.WALLET_BALANCE_NOT_ENOUGH);
            }
            walletMapper.updateWalletBalance(transferDTO.getFromWalletId(), -requestAmount);
            walletMapper.updateWalletBalance(transferDTO.getToWalletId(), requestAmount);
        }

        //transaction 테이블에 복식 부기
        //메인지갑 기준
        //인센티브일 때만 원금으로 역산, 아니면 그대로
        double rawValue = (localCurrencyVO.getBenefitType() == BenefitType.INCENTIVE && localCurrencyVO.getPercentage() != null)
            ? transferDTO.getAmount() * (100.0 / (100.0 + localCurrencyVO.getPercentage()))
                : (double) transferDTO.getAmount();
        TransactionVO mainTx = transactionConverter.toTransactionVO(
                UUID.randomUUID(), transferDTO.getFromWalletId(), transferDTO.getToWalletId(),
                mainWalletVO.getBalance(), mainWalletVO.getBalance() + (int) Math.round(rawValue),
                (int) Math.round(rawValue), Direction.INCOME, transferDTO.getType(), "환불", mainWalletVO.getWalletId());
        int successMainWalletCount = transactionMapper.insert(mainTx);

        if (successMainWalletCount != 1) {
            throw new TransactionException(ErrorCode.TRANSACTION_SAVE_FAILED);
        }

        //지역화폐 기준
        TransactionVO localTx = null;
        //지금은 항상 실행되어야 해서 incentive일 때만 생성하는 조건문 주석 처리
//        if (localCurrencyVO.getBenefitType() == BenefitType.INCENTIVE) {

            localTx = transactionConverter.toTransactionVO(
                    UUID.randomUUID(), transferDTO.getFromWalletId(), transferDTO.getToWalletId(),
                    LocalCurrencyWalletVO.getBalance(), LocalCurrencyWalletVO.getBalance() - transferDTO.getAmount(),
                    transferDTO.getAmount(), Direction.EXPENSE, transferDTO.getType(), "환불", LocalCurrencyWalletVO.getWalletId());
            int successLocalWalletCount = transactionMapper.insert(localTx);

            if (successLocalWalletCount != 1) {
                throw new TransactionException(ErrorCode.TRANSACTION_SAVE_FAILED);
            }

//        } else if (localCurrencyVO.getBenefitType() == BenefitType.CASHBACK) {
//            localTx = transactionConverter.toTransactionVO(
//                    UUID.randomUUID(), transferDTO.getFromWalletId(), transferDTO.getToWalletId(),
//                    LocalCurrencyWalletVO.getBalance() + (transferDTO.getAmount() + amount + (int) (transferDTO.getAmount() * RECHARGE_FEE_RATE)), LocalCurrencyWalletVO.getBalance(),
//                    (transferDTO.getAmount() + amount + (int) (transferDTO.getAmount() * RECHARGE_FEE_RATE)), Direction.EXPENSE, transferDTO.getType(), "환불", LocalCurrencyWalletVO.getWalletId());
//            int successLocalWalletCount = transactionMapper.insert(localTx);
//
//            if (successLocalWalletCount != 1) {
//                throw new TransactionException(ErrorCode.TRANSACTION_SAVE_FAILED_LOCAL);
//            }
//        }


        return List.of(
                transactionConverter.toTransactionDTO(mainTx),
                transactionConverter.toTransactionDTO(localTx)
        );


    }
}
