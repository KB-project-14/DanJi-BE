package org.danji.transaction.service;

import org.danji.transaction.dto.request.TransferDTO;
import org.danji.transaction.dto.response.TransactionDTO;

import java.util.List;

public interface TransactionService {

    List<TransactionDTO> handleTransfer(TransferDTO transferDTO);

}
