package org.danji.transaction.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
import org.danji.wallet.enums.WalletType;
import org.danji.wallet.exception.WalletException;
import org.danji.wallet.mapper.WalletMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;



@Service("CHARGE")
@RequiredArgsConstructor
@Log4j2
public class RechargeProcessor implements TransferProcessor<TransferDTO> {

    private static final double RECHARGE_FEE_RATE = 0.00;

    private final WalletMapper walletMapper;
    private final LocalCurrencyMapper localCurrencyMapper;
    private final TransactionMapper transactionMapper;
    private final TransactionConverter transactionConverter;

    @Transactional
    @Override
    public List<TransactionDTO> process(TransferDTO transferDTO) {

        UUID userId = AuthUtils.getMemberId();

        WalletVO mainWalletVO = walletMapper.findById(transferDTO.getFromWalletId());
        System.out.println(mainWalletVO);
        if (mainWalletVO == null) {
            throw new WalletException(ErrorCode.WALLET_NOT_FOUND);
        }
        if (!userId.equals(mainWalletVO.getMemberId())) {
            throw new WalletException(ErrorCode.UNAUTHORIZED_WALLET_ACCESS);
        }

        WalletVO localCurrencyWalletVO = walletMapper.findById(transferDTO.getToWalletId());
        if (localCurrencyWalletVO == null) {
            throw new WalletException(ErrorCode.WALLET_NOT_FOUND);
        }
        if (!userId.equals(localCurrencyWalletVO.getMemberId())) {
            throw new WalletException(ErrorCode.UNAUTHORIZED_WALLET_ACCESS);
        }
        if (localCurrencyWalletVO.getWalletType() != WalletType.LOCAL) {
            throw new WalletException(ErrorCode.UNAUTHORIZED_WALLET_ACCESS);
        }

        LocalCurrencyVO localCurrencyVO = localCurrencyMapper.findById(localCurrencyWalletVO.getLocalCurrencyId());
        if (localCurrencyVO == null) {
            throw new LocalCurrencyException(ErrorCode.LOCAL_CURRENCY_NOT_FOUND);
        }

        int baseAmount = transferDTO.getAmount();
        int percentage = (localCurrencyVO.getBenefitType() == BenefitType.INCENTIVE && localCurrencyVO.getPercentage() != null)
                ? localCurrencyVO.getPercentage()
                : 0;
        int maxChargeAmount = localCurrencyVO.getMaximum();
        int monthValue = LocalDateTime.now().getMonthValue();
        int totalChargeAmountByMonth = transactionMapper.findTotalChargeAmountByMonth(localCurrencyWalletVO.getWalletId(), monthValue);
        int exactAmount = (localCurrencyVO.getBenefitType() == BenefitType.INCENTIVE && percentage > 0)
                ? (int) Math.round(totalChargeAmountByMonth * (100.0 / (100.0 + percentage)))
                : totalChargeAmountByMonth;
        if (baseAmount > maxChargeAmount - exactAmount){
            throw new LocalCurrencyException(ErrorCode.LOCAL_WALLET_EXCEEDS_MONTHLY_MAX);
        }

        if (transferDTO.isTransactionLogging()) {
            if (mainWalletVO.getBalance() < transferDTO.getAmount() + transferDTO.getAmount() * RECHARGE_FEE_RATE) {
              throw new WalletException(ErrorCode.WALLET_BALANCE_NOT_ENOUGH);
            }
        }

        int feeAmount = transferDTO.isTransactionLogging() ? (int)(transferDTO.getAmount() * RECHARGE_FEE_RATE) : 0;
        int totalAmount = baseAmount + feeAmount;
        int bonus = (percentage > 0) ? (int) Math.round(baseAmount * (percentage / 100.0)) : 0;
        int creditedAmount = baseAmount + bonus;

        int deducted = walletMapper.updateWalletBalance(mainWalletVO.getWalletId(), -totalAmount);
            if (deducted != 1) {
                    throw new IllegalStateException("메인 지갑 차감 실패: walletId=" + mainWalletVO.getWalletId());
            }
        int credited = walletMapper.updateWalletBalance(localCurrencyWalletVO.getWalletId(), creditedAmount);
            if (credited != 1) {
                    throw new IllegalStateException("지역화폐 지갑 입금 실패: walletId=" + localCurrencyWalletVO.getWalletId());
            }


        TransactionVO mainTx = transactionConverter.toTransactionVO(
                UUID.randomUUID(),
                mainWalletVO.getWalletId(),
                localCurrencyWalletVO.getWalletId(),
                mainWalletVO.getBalance(),
                mainWalletVO.getBalance() - totalAmount,
                totalAmount,
                Direction.EXPENSE,
                transferDTO.getType(),
                "충전",
                mainWalletVO.getWalletId()
        );

        int successMainWalletCount = transactionMapper.insert(mainTx);
        if (successMainWalletCount != 1) {
            throw new TransactionException(ErrorCode.TRANSACTION_SAVE_FAILED);
        }

        TransactionVO localTx = transactionConverter.toTransactionVO(
                UUID.randomUUID(),
                localCurrencyWalletVO.getWalletId(),
                mainWalletVO.getWalletId(),
                localCurrencyWalletVO.getBalance(),
                localCurrencyWalletVO.getBalance() + creditedAmount,
                creditedAmount,
                Direction.INCOME,
                transferDTO.getType(),
                "충전",
                localCurrencyWalletVO.getWalletId()
        );
        int successLocalCurrencyWalletCount = transactionMapper.insert(localTx);
        if (successLocalCurrencyWalletCount != 1) {
            throw new TransactionException(ErrorCode.TRANSACTION_SAVE_FAILED);
        }

        return List.of(
                transactionConverter.toTransactionDTO(mainTx),
                transactionConverter.toTransactionDTO(localTx)
        );
    }
}
