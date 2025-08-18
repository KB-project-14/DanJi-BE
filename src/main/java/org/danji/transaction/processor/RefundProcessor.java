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
    private final TransactionConverter transactionConverter;
    private final TransactionMapper transactionMapper;

    @Override
    @Transactional
    public List<TransactionDTO> process(TransferDTO transferDTO) {

        UUID userId = AuthUtils.getMemberId();

        WalletVO LocalCurrencyWalletVO = walletMapper.findById(transferDTO.getFromWalletId());
        if (LocalCurrencyWalletVO == null) {
            throw new WalletException(ErrorCode.WALLET_NOT_FOUND);
        }
        WalletFilterDTO walletFilterDTO = WalletFilterDTO.builder().memberId(userId).walletType(WalletType.LOCAL).build();
        List<WalletVO> localWalletByUserIdVO = walletMapper.findByFilter(walletFilterDTO);

        if (!checkOwnership(localWalletByUserIdVO, LocalCurrencyWalletVO)) {
            throw new WalletException(ErrorCode.UNAUTHORIZED_WALLET_ACCESS);
        }

        WalletVO mainWalletVO = walletMapper.findById(transferDTO.getToWalletId());
        if (mainWalletVO == null) {
            throw new WalletException(ErrorCode.WALLET_NOT_FOUND);
        }

        WalletVO mainWalletByUserIdVO = walletMapper.findByMemberId(userId);
        if (!mainWalletByUserIdVO.getWalletId().equals(mainWalletVO.getWalletId())) {
            throw new WalletException(ErrorCode.UNAUTHORIZED_WALLET_ACCESS);
        }

        LocalCurrencyVO localCurrencyVO = localCurrencyMapper.findById(LocalCurrencyWalletVO.getLocalCurrencyId());
        if (localCurrencyVO == null) {
            throw new LocalCurrencyException(ErrorCode.LOCAL_CURRENCY_NOT_FOUND);
        }

        if (localCurrencyVO.getBenefitType() == BenefitType.INCENTIVE) {
            if (transferDTO.getAmount() > LocalCurrencyWalletVO.getBalance()) {
                throw new WalletException(ErrorCode.WALLET_BALANCE_NOT_ENOUGH);
            }
        }


        if (localCurrencyVO.getBenefitType() == BenefitType.INCENTIVE) {
            double rawValue = transferDTO.getAmount() * (100.0 / (100.0 + localCurrencyVO.getPercentage()));

            walletMapper.updateWalletBalance(transferDTO.getFromWalletId(), -transferDTO.getAmount());
            walletMapper.updateWalletBalance(transferDTO.getToWalletId(), (int) Math.round(rawValue));
        } else {
            int requestAmount = transferDTO.getAmount();
            if (requestAmount > LocalCurrencyWalletVO.getBalance()) {
                throw new WalletException(ErrorCode.WALLET_BALANCE_NOT_ENOUGH);
            }
            walletMapper.updateWalletBalance(transferDTO.getFromWalletId(), -requestAmount);
            walletMapper.updateWalletBalance(transferDTO.getToWalletId(), requestAmount);
        }

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


        TransactionVO localTx = null;


        localTx = transactionConverter.toTransactionVO(
                UUID.randomUUID(), transferDTO.getFromWalletId(), transferDTO.getToWalletId(),
                LocalCurrencyWalletVO.getBalance(), LocalCurrencyWalletVO.getBalance() - transferDTO.getAmount(),
                transferDTO.getAmount(), Direction.EXPENSE, transferDTO.getType(), "환불", LocalCurrencyWalletVO.getWalletId());
        int successLocalWalletCount = transactionMapper.insert(localTx);

        if (successLocalWalletCount != 1) {
            throw new TransactionException(ErrorCode.TRANSACTION_SAVE_FAILED);
        }


        return List.of(
                transactionConverter.toTransactionDTO(mainTx),
                transactionConverter.toTransactionDTO(localTx)
        );


    }
}
