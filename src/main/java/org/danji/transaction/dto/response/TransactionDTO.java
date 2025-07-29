package org.danji.transaction.dto.response;


import lombok.*;
import lombok.experimental.SuperBuilder;
import org.danji.global.dto.BaseDTO;
import org.danji.transaction.enums.Direction;
import org.danji.transaction.enums.Type;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TransactionDTO{
    private UUID transactionId;
    private UUID fromWalletId;
    private UUID toWalletId;
    private Integer beforeBalance;
    private Integer amount;
    private Integer afterBalance;
    private Direction direction;
    private Type type;
    private String comment;
    private UUID ownerWalletId;
}
