package org.danji.transaction.converter;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.transaction.Transaction;
import org.danji.transaction.domain.TransactionVO;
import org.danji.transaction.enums.Direction;
import org.danji.transaction.enums.Type;
import org.springframework.stereotype.Component;

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
                .comment("")
                .build();
    }
}
