package org.danji.transaction.mapper;

import org.apache.ibatis.annotations.Param;
import org.danji.transaction.domain.TransactionVO;
import org.danji.transaction.dto.request.TransactionFilterDTO;
import java.util.List;
import java.util.UUID;

public interface TransactionMapper {

    int insert(TransactionVO transactionVO);

    int insertMany(List<TransactionVO> transactionVO);

    List<TransactionVO> findByFilter(TransactionFilterDTO transactionFilterDTO);

    int findTotalChargeAmountByMonth(@Param("localWalletId") UUID localWalletId, @Param("month") Integer month);
}
