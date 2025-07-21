package org.danji.transaction.converter;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.transaction.Transaction;
import org.danji.transaction.domain.TransactionVO;
import org.danji.transaction.dto.response.TransactionDTO;
import org.danji.transaction.enums.Direction;
import org.danji.transaction.enums.Type;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransactionConverter {

    public TransactionVO toTransactionVO(UUID transactionId, UUID fromWalletId, UUID toWalletId, Integer beforeBalance,
                                         Integer afterBalance, Integer amount, Direction direction, Type type, String comment) {
        return TransactionVO.builder()
                .transactionId(transactionId)
                .fromWalletId(fromWalletId)
                .toWalletId(toWalletId)
                .beforeBalance(beforeBalance)
                .afterBalance(afterBalance)
                .amount(amount)
                .direction(direction)
                .type(type)
                .comment(comment)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public TransactionDTO transactionDTO(TransactionVO transactionVO){
        return TransactionDTO.builder()
                .transactionId(transactionVO.getTransactionId())
                .fromWalletId(transactionVO.getFromWalletId())
                .toWalletId(transactionVO.getToWalletId())
                .beforeBalance(transactionVO.getBeforeBalance())
                .afterBalance(transactionVO.getAfterBalance())
                .amount(transactionVO.getAmount())
                .direction(transactionVO.getDirection())
                .type(transactionVO.getType())
                .comment(transactionVO.getComment())
                .createdAt(transactionVO.getCreatedAt())
                .updatedAt(transactionVO.getUpdatedAt())
                .build();
    }
}
