package org.danji.transaction.service;

import org.danji.transaction.dto.request.PaymentDTO;
import org.danji.transaction.dto.request.TransactionFilterDTO;
import org.danji.transaction.dto.request.TransferDTO;
import org.danji.transaction.dto.response.TransactionAggregateDTO;
import org.danji.transaction.dto.response.TransactionDTO;

import java.util.List;

public interface TransactionService {

    List<TransactionDTO> handleTransfer(TransferDTO transferDTO);

    List<TransactionDTO> handlePayment(PaymentDTO paymentDTO);

    TransactionAggregateDTO getTransactionAggregate(TransactionFilterDTO transactionFilterDTO);
}
