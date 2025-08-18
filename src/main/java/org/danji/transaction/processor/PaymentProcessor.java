package org.danji.transaction.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.danji.availableMerchant.domain.AvailableMerchantVO;
import org.danji.availableMerchant.exception.AvailableMerchantException;
import org.danji.availableMerchant.mapper.AvailableMerchantMapper;
import org.danji.common.utils.AuthUtils;
import org.danji.global.error.ErrorCode;
import org.danji.localCurrency.exception.LocalCurrencyException;
import org.danji.member.service.MemberService;
import org.danji.transaction.converter.TransactionConverter;
import org.danji.transaction.domain.TransactionVO;
import org.danji.transaction.dto.request.PaymentContextDTO;
import org.danji.transaction.dto.request.PaymentDTO;
import org.danji.transaction.dto.response.TransactionDTO;
import org.danji.transaction.enums.Direction;
import org.danji.transaction.enums.PaymentType;
import org.danji.transaction.enums.Type;
import org.danji.transaction.exception.TransactionException;
import org.danji.transaction.mapper.TransactionMapper;
import org.danji.transaction.strategy.PaymentStrategy;
import org.danji.wallet.domain.WalletVO;
import org.danji.wallet.exception.WalletException;
import org.danji.wallet.mapper.WalletMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;



@Service("PAYMENT")
@RequiredArgsConstructor
@Log4j2
public class PaymentProcessor implements TransferProcessor<PaymentDTO> {

    private final WalletMapper walletMapper;
    private final TransactionMapper transactionMapper;
    private final TransactionConverter transactionConverter;
    private final AvailableMerchantMapper availableMerchantMapper;
    private final MemberService memberService;

    private final List<PaymentStrategy> strategies;


    @Override
    public List<TransactionDTO> process(PaymentDTO paymentDTO) {

        UUID userId = AuthUtils.getMemberId();
        if (!memberService.checkPaymentPin(paymentDTO.getWalletPin())) {
            throw new WalletException(ErrorCode.INVALID_PAYMENT_PASSWORD);
        }

        PaymentContextDTO ctx = walletMapper.getPaymentContext(
                paymentDTO.getLocalWalletId(), userId, paymentDTO.getAvailableMerchantId());

        if (!ctx.isMerchantExists()) throw new AvailableMerchantException(ErrorCode.AVAILABLE_MERCHANT_NOT_FOUND);
        if (!ctx.isWalletExists()) throw new WalletException(ErrorCode.WALLET_NOT_FOUND);
        if (!ctx.isLocalCurrencyExists()) throw new LocalCurrencyException(ErrorCode.LOCAL_CURRENCY_NOT_FOUND);
        if (!ctx.isAuthorized()) throw new WalletException(ErrorCode.UNAUTHORIZED_WALLET_ACCESS);

        if (ctx.getBalance() < paymentDTO.getMerchantAmount()) {
            throw new WalletException(ErrorCode.WALLET_BALANCE_NOT_ENOUGH);
        }

        if (paymentDTO.getType() == PaymentType.GENERAL) {
            return processGeneral(paymentDTO);
        }


        return strategies.stream()
                .filter(strategy -> strategy.supports(paymentDTO))
                .findFirst()
                .orElseThrow(() -> new WalletException(ErrorCode.STRATEGY_NOT_FOUND))
                .process(paymentDTO, userId, ctx);


    }

    private List<TransactionDTO> processGeneral(PaymentDTO paymentDTO) {

        UUID userId = AuthUtils.getMemberId();
        WalletVO mainWalletVO = walletMapper.findByMemberId(userId);
        if (mainWalletVO == null) {
            throw new WalletException(ErrorCode.WALLET_NOT_FOUND);
        }

        if (mainWalletVO.getBalance() < paymentDTO.getMerchantAmount()) {
            throw new WalletException(ErrorCode.WALLET_BALANCE_NOT_ENOUGH);
        }

        walletMapper.updateWalletBalance(mainWalletVO.getWalletId(), -paymentDTO.getMerchantAmount());


        AvailableMerchantVO availableMerchantVO = availableMerchantMapper.findById(paymentDTO.getAvailableMerchantId());
        if (availableMerchantVO == null) {
            throw new AvailableMerchantException(ErrorCode.AVAILABLE_MERCHANT_NOT_FOUND);
        }

        TransactionVO mainTx = transactionConverter.toTransactionVO(
                UUID.randomUUID(),
                mainWalletVO.getWalletId(),
                null,
                mainWalletVO.getBalance(),
                mainWalletVO.getBalance() - paymentDTO.getMerchantAmount(),
                paymentDTO.getMerchantAmount(),
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
                transactionConverter.toTransactionDTO(mainTx),
                transactionConverter.toTransactionDTO(merchantTx)
        );

    }
}
