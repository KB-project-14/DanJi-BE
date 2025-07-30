package org.danji.transaction.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.danji.availableMerchant.domain.AvailableMerchantVO;
import org.danji.availableMerchant.exception.AvailableMerchantException;
import org.danji.availableMerchant.mapper.AvailableMerchantMapper;
import org.danji.common.utils.AuthUtils;
import org.danji.global.error.ErrorCode;
import org.danji.member.domain.MemberVO;
import org.danji.member.mapper.MemberMapper;
import org.danji.transaction.converter.TransactionConverter;
import org.danji.transaction.domain.TransactionVO;
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
    private final MemberMapper memberMapper;

    private final List<PaymentStrategy> strategies;


    @Override
    public List<TransactionDTO> process(PaymentDTO paymentDTO) {

        //결제 비밀번호 확인 로직
        UUID memberId = AuthUtils.getMemberId();
        MemberVO memberVO = memberMapper.findById(memberId);
        if (!paymentDTO.getWalletPin().equals(memberVO.getPaymentPin())){
            throw new WalletException(ErrorCode.INVALID_PAYMENT_PASSWORD);
        }

        if (paymentDTO.getType() == PaymentType.GENERAL) {
            return processGeneral(paymentDTO);
        }

        // 지역화폐는 strategy를 통해 분기
        return strategies.stream()
                .filter(strategy -> strategy.supports(paymentDTO))
                .findFirst()
                .orElseThrow(() -> new WalletException(ErrorCode.STRATEGY_NOT_FOUND))
                .process(paymentDTO);

    }

    private List<TransactionDTO> processGeneral(PaymentDTO paymentDTO) {
        // 일반 결제 처리 로직 (예: 메인지갑 차감)

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
