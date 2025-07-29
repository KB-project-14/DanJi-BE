package org.danji.transaction.strategy;

import lombok.RequiredArgsConstructor;
import org.danji.availableMerchant.domain.AvailableMerchantVO;
import org.danji.availableMerchant.exception.AvailableMerchantException;
import org.danji.availableMerchant.mapper.AvailableMerchantMapper;
import org.danji.global.error.ErrorCode;
import org.danji.transaction.converter.TransactionConverter;
import org.danji.transaction.domain.TransactionVO;
import org.danji.transaction.dto.request.PaymentDTO;
import org.danji.transaction.dto.response.TransactionDTO;
import org.danji.transaction.enums.Direction;
import org.danji.transaction.enums.PaymentType;
import org.danji.transaction.enums.Type;
import org.danji.transaction.exception.TransactionException;
import org.danji.transaction.mapper.TransactionMapper;
import org.danji.wallet.domain.WalletVO;
import org.danji.wallet.exception.WalletException;
import org.danji.wallet.mapper.WalletMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.danji.transaction.validator.WalletValidator.checkOwnership;

@Component
@RequiredArgsConstructor
public class LocalStrategy implements PaymentStrategy {

    private final AvailableMerchantMapper availableMerchantMapper;
    private final WalletMapper walletMapper;
    private final TransactionConverter transactionConverter;
    private final TransactionMapper transactionMapper;

    @Override
    public boolean supports(PaymentDTO paymentDTO) {

        return paymentDTO.getType() == PaymentType.LOCAL_CURRENCY &&
                paymentDTO.getInputAmount().equals(paymentDTO.getMerchantAmount());
    }

    @Override
    @Transactional
    public List<TransactionDTO> process(PaymentDTO paymentDTO) {

        //테스트용 userId
        UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000000");

        AvailableMerchantVO availableMerchantVO = availableMerchantMapper.findById(paymentDTO.getAvailableMerchantId());
        if (availableMerchantVO == null) {
            throw new AvailableMerchantException(ErrorCode.AVAILABLE_MERCHANT_NOT_FOUND);
        }


        WalletVO LocalCurrencyWalletVO = walletMapper.findById(paymentDTO.getLocalWalletId());
        if (LocalCurrencyWalletVO == null) {
            throw new WalletException(ErrorCode.WALLET_NOT_FOUND);
        }

        List<WalletVO> localWalletByUserIdVO = walletMapper.findLocalWalletByMemberId(userId);
        if (!checkOwnership(localWalletByUserIdVO, LocalCurrencyWalletVO)) {
            throw new WalletException(ErrorCode.UNAUTHORIZED_WALLET_ACCESS);
        }

        if (LocalCurrencyWalletVO.getBalance() < paymentDTO.getMerchantAmount()) {
            throw new WalletException(ErrorCode.WALLET_BALANCE_NOT_ENOUGH);
        }

        walletMapper.updateWalletBalance(LocalCurrencyWalletVO.getWalletId(), -paymentDTO.getMerchantAmount());

        TransactionVO LocalTx = transactionConverter.toTransactionVO(
                UUID.randomUUID(),
                LocalCurrencyWalletVO.getWalletId(),
                null,
                LocalCurrencyWalletVO.getBalance(),
                LocalCurrencyWalletVO.getBalance() - paymentDTO.getMerchantAmount(),
                paymentDTO.getMerchantAmount(),
                Direction.EXPENSE,
                Type.PAYMENT,
                availableMerchantVO.getName(),
                LocalCurrencyWalletVO.getWalletId()
        );

        int successLocalTxCount = transactionMapper.insert(LocalTx);
        if (successLocalTxCount != 1) {
            throw new TransactionException(ErrorCode.TRANSACTION_SAVE_FAILED);
        }

        TransactionVO merchantTx = transactionConverter.toTransactionVO(
                UUID.randomUUID(),
                LocalCurrencyWalletVO.getWalletId(),
                null,
                null,
                null,
                paymentDTO.getMerchantAmount(),
                Direction.INCOME,
                Type.PAYMENT,
                availableMerchantVO.getName(),
                null
        );

        int successMerchantTxCount = transactionMapper.insert(merchantTx);
        if (successMerchantTxCount != 1) {
            throw new TransactionException(ErrorCode.TRANSACTION_SAVE_FAILED);
        }

        return List.of(
                transactionConverter.toTransactionDTO(LocalTx),
                transactionConverter.toTransactionDTO(merchantTx)
        );
    }


}