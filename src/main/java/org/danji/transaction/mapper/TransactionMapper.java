package org.danji.transaction.mapper;

import org.danji.transaction.domain.TransactionVO;
import org.danji.transaction.dto.response.TransactionDTO;

public interface TransactionMapper {
    TransactionDTO insert(TransactionVO transactionVO);
}
