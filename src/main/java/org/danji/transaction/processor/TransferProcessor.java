package org.danji.transaction.processor;

import org.danji.transaction.dto.response.TransactionDTO;

import java.util.List;

public interface TransferProcessor<T> {
    List<TransactionDTO> process(T dto);
}
