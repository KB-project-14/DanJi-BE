package org.danji.transaction.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.danji.transaction.validator.WalletValidator.checkOwnership;

@Service("PAYMENT")
@RequiredArgsConstructor
@Log4j2
public class PaymentProcessor implements TransferProcessor<PaymentDTO> {

    private final WalletMapper walletMapper;
    private final TransactionMapper transactionMapper;
    private final TransactionConverter transactionConverter;
    private final AvailableMerchantMapper availableMerchantMapper;

    @Transactional
    @Override
    public List<TransactionDTO> process(PaymentDTO paymentDTO) {

        //임시 userId
        UUID userId = UUID.randomUUID();

        AvailableMerchantVO availableMerchantVO = availableMerchantMapper.findById(paymentDTO.getAvailableMerchantId());
        if (availableMerchantVO == null) {
            throw new AvailableMerchantException(ErrorCode.AVAILABLE_MERCHANT_NOT_FOUND);
        }

        if (paymentDTO.getType() == PaymentType.LOCAL_CURRENCY) {
            WalletVO LocalCurrencyWalletVO = walletMapper.findById(paymentDTO.getLocalWalletId());
            if (LocalCurrencyWalletVO == null) {
                throw new WalletException(ErrorCode.WALLET_NOT_FOUND);
            }

            List<WalletVO> localWalletByUserIdVO = walletMapper.findLocalWalletByMemberId(userId);
            if (!checkOwnership(localWalletByUserIdVO, LocalCurrencyWalletVO)) {
                throw new WalletException(ErrorCode.NOT_OWNED_LOCAL_WALLET);
            }

            if (LocalCurrencyWalletVO.getBalance() < paymentDTO.getAmount()) {
                throw new WalletException(ErrorCode.WALLET_BALANCE_NOT_ENOUGH);
            }

            walletMapper.updateWalletBalance(LocalCurrencyWalletVO.getWalletId(), - paymentDTO.getAmount());

            TransactionVO LocalTx = transactionConverter.toTransactionVO(
                    UUID.randomUUID(),
                    LocalCurrencyWalletVO.getWalletId(),
                    null,
                    LocalCurrencyWalletVO.getBalance() - paymentDTO.getAmount(),
                    LocalCurrencyWalletVO.getBalance(),
                    paymentDTO.getAmount(),
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
                    paymentDTO.getAmount(),
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
        } else if (paymentDTO.getType() == PaymentType.GENERAL) {
           WalletVO mainWalletVO = walletMapper.findByMemberId(userId);
           if (mainWalletVO == null) {
               throw new WalletException(ErrorCode.WALLET_NOT_FOUND);
           }

           if (mainWalletVO.getBalance() < paymentDTO.getAmount()) {
               throw new WalletException(ErrorCode.WALLET_BALANCE_NOT_ENOUGH);
           }

            walletMapper.updateWalletBalance(mainWalletVO.getWalletId(), - paymentDTO.getAmount());

            TransactionVO mainTx = transactionConverter.toTransactionVO(
                    UUID.randomUUID(),
                    mainWalletVO.getWalletId(),
                    null,
                    mainWalletVO.getBalance() - paymentDTO.getAmount(),
                    mainWalletVO.getBalance(),
                    paymentDTO.getAmount(),
                    Direction.EXPENSE,
                    Type.PAYMENT,
                    availableMerchantVO.getName(),
                    mainWalletVO.getWalletId()
            );

            int successLocalTxCount = transactionMapper.insert(mainTx);
            if (successLocalTxCount != 1) {
                throw new TransactionException(ErrorCode.TRANSACTION_SAVE_FAILED);
            }

            TransactionVO merchantTx = transactionConverter.toTransactionVO(
                    UUID.randomUUID(),
                    mainWalletVO.getWalletId(),
                    null,
                    null,
                    null,
                    paymentDTO.getAmount(),
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
                    transactionConverter.toTransactionDTO(mainTx),
                    transactionConverter.toTransactionDTO(merchantTx)
            );
        }
        return Collections.emptyList();
    }
}
