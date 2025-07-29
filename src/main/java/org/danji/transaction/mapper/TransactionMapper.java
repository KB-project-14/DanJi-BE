package org.danji.transaction.mapper;

import org.apache.ibatis.annotations.Param;
import org.danji.transaction.domain.TransactionVO;
import org.danji.transaction.dto.request.TransactionFilterDTO;
import org.danji.transaction.dto.response.TransactionDTO;

import java.util.List;
import java.util.UUID;

public interface TransactionMapper {
    int insert(TransactionVO transactionVO);

    List<TransactionVO> findByFilter(TransactionFilterDTO transactionFilterDTO);
}
