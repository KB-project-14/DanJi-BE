package org.danji.transaction.service;

import org.danji.transaction.dto.request.TransferDTO;
import org.danji.transaction.dto.response.TransactionDTO;
import org.danji.transaction.enums.TransferType;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    List<TransactionDTO> handleTransfer(TransferDTO transferDTO);

}
