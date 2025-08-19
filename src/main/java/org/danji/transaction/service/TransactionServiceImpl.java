package org.danji.transaction.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.danji.localCurrency.domain.LocalCurrencyVO;
import org.danji.localCurrency.mapper.LocalCurrencyMapper;
import org.danji.transaction.converter.TransactionConverter;
import org.danji.transaction.dto.request.PaymentDTO;
import org.danji.transaction.dto.request.TransactionFilterDTO;
import org.danji.transaction.dto.request.TransferDTO;
import org.danji.transaction.dto.response.TransactionAggregateDTO;
import org.danji.transaction.dto.response.TransactionDTO;
import org.danji.transaction.enums.Type;
import org.danji.transaction.mapper.TransactionMapper;
import org.danji.transaction.processor.TransferProcessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class TransactionServiceImpl implements TransactionService {

    private final Map<String, TransferProcessor> processorMap;
    private final TransactionMapper transactionMapper;
    private final TransactionConverter transactionConverter;
    private final LocalCurrencyMapper localCurrencyMapper;

    @Transactional
    @Override
    public List<TransactionDTO> handleTransfer(TransferDTO transferDTO) {
        TransferProcessor<TransferDTO> processor = processorMap.get(transferDTO.getType().name());

        return processor.process(transferDTO);
    }

    public List<TransactionDTO> handlePayment(PaymentDTO paymentDTO) {
        TransferProcessor<PaymentDTO> processor = processorMap.get("PAYMENT");
        return processor.process(paymentDTO);
    }

    @Override
    public TransactionAggregateDTO getTransactionAggregate(TransactionFilterDTO transactionFilterDTO) {

        List<TransactionDTO> transactionList = fetchTransactions(transactionFilterDTO);

        int totalCharge = calculateTotalWithType(transactionList, Type.CHARGE);
        LocalCurrencyVO localCurrencyVO = localCurrencyMapper.findByWalletId(transactionFilterDTO.getWalletId());

        Integer percentage = 0;

        if (localCurrencyVO != null) {
            percentage = localCurrencyVO.getPercentage();
        }

        return TransactionAggregateDTO.builder()
                .transactions(transactionList)
                .aggregateCharge(totalCharge)
                .aggregateIncentive(totalCharge * percentage / (100 + percentage))
                .build();
    }

    private List<TransactionDTO> fetchTransactions(TransactionFilterDTO filterDto) {
        return transactionMapper
                .findByFilter(filterDto).stream()
                .map(transactionConverter::toTransactionDTO)
                .toList();
    }

    private int calculateTotalWithType(List<TransactionDTO> transactions, Type type) {
        return transactions.stream()
                .filter(dto -> dto.getType() == type)
                .mapToInt(TransactionDTO::getAmount)
                .sum();
    }

}
