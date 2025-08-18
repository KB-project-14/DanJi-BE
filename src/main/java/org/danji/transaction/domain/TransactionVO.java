package org.danji.transaction.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.danji.global.domain.BaseVO;
import org.danji.transaction.enums.Direction;
import org.danji.transaction.enums.Type;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class TransactionVO extends BaseVO {
    private UUID transactionId;
    private UUID fromWalletId;
    private UUID toWalletId;
    private Integer beforeBalance;
    private Integer afterBalance;
    private Integer amount;
    private Direction direction;
    private Type type;
    private String comment;
    private UUID ownerWalletId;
}
