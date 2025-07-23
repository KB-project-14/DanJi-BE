package org.danji.transaction.processor;

import org.danji.transaction.dto.request.TransferDTO;
import org.danji.transaction.dto.response.TransactionDTO;

import java.util.List;

public interface TransferProcessor {
    List<TransactionDTO> process(TransferDTO dto);
}
