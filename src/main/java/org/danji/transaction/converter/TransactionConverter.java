package org.danji.transaction.converter;

import org.danji.transaction.domain.TransactionVO;
import org.danji.transaction.dto.request.TransferDTO;
import org.danji.transaction.dto.response.TransactionDTO;
import org.danji.transaction.enums.Direction;
import org.danji.transaction.enums.Type;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TransactionConverter {

    public TransactionVO toTransactionVO(UUID transactionId, UUID mainWalletId, UUID LocalWalletId,
                                         Integer beforeBalance, Integer afterBalance, Integer amount,
                                         Direction direction, Type type, String comment, UUID ownerWalletId) {
        return TransactionVO.builder()
                .transactionId(transactionId)
                .fromWalletId(mainWalletId)
                .toWalletId(LocalWalletId)
                .beforeBalance(beforeBalance)
                .afterBalance(afterBalance)
                .amount(amount)
                .direction(direction)
                .type(type)
                .comment(comment)
                .ownerWalletId(ownerWalletId)
                .build();
    }

    public TransactionDTO toTransactionDTO(TransactionVO transactionVO){
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
                .ownerWalletId(transactionVO.getOwnerWalletId())
                .build();
    }

    public TransferDTO toTransferDTO(UUID fromWalletId, UUID toWalletId, Type type, Integer amount, Boolean transactionLogging){
       return TransferDTO.builder()
               .fromWalletId(fromWalletId)
               .toWalletId(toWalletId)
               .type(type)
               .amount(amount)
               .transactionLogging(transactionLogging)
               .build();
    }
}
