package org.danji.transaction.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.bytebuddy.asm.Advice;
import org.danji.cashback.converter.CashbackConverter;
import org.danji.cashback.domain.CashbackVO;
import org.danji.cashback.enums.CashBackStatus;
import org.danji.cashback.mapper.CashbackMapper;
import org.danji.global.error.ErrorCode;
import org.danji.localCurrency.domain.LocalCurrencyVO;
import org.danji.localCurrency.enums.BenefitType;

import org.danji.localCurrency.exception.LocalCurrencyException;
import org.danji.localCurrency.mapper.LocalCurrencyMapper;
import org.danji.transaction.converter.TransactionConverter;
import org.danji.transaction.domain.TransactionVO;
import org.danji.transaction.dto.request.PaymentDTO;
import org.danji.transaction.dto.request.TransactionFilterDTO;
import org.danji.transaction.dto.request.TransferDTO;
import org.danji.transaction.dto.response.TransactionDTO;
import org.danji.transaction.enums.Direction;
import org.danji.transaction.enums.Type;
import org.danji.transaction.exception.TransactionException;
import org.danji.transaction.mapper.TransactionMapper;
import org.danji.transaction.processor.TransferProcessor;
import org.danji.wallet.domain.WalletVO;
import org.danji.wallet.exception.WalletException;
import org.danji.wallet.mapper.WalletMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class TransactionServiceImpl implements TransactionService {

    private final Map<String, TransferProcessor> processorMap;
    private final TransactionMapper transactionMapper;
    private final TransactionConverter transactionConverter;

    @Transactional
    @Override
    public List<TransactionDTO> handleTransfer(TransferDTO transferDTO){
        TransferProcessor<TransferDTO> processor = processorMap.get(transferDTO.getType().name());

        return processor.process(transferDTO);
    }

    public List<TransactionDTO> handlePayment(PaymentDTO paymentDTO){
        TransferProcessor<PaymentDTO> processor = processorMap.get("PAYMENT");
        return processor.process(paymentDTO);
    }

    @Override
    public List<TransactionDTO> getTransactionsByWalletId(UUID walletId, TransactionFilterDTO transactionFilterDTO) {
        List<TransactionVO> transactionVOList = transactionMapper.findbyWalletId(walletId, transactionFilterDTO);
        return transactionVOList.stream()
                .map(transactionConverter::toTransactionDTO)
                .collect(Collectors.toList());
    }
}
