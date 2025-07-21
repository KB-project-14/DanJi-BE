package org.danji.transaction.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.danji.transaction.enums.Direction;
import org.danji.transaction.enums.Type;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TransactionVO {
    private Long transactionId;
    private Long fromWalletId;
    private Long toWalletId;
    private Integer beforeBalance;
    private Integer afterBalance;
    private Integer amount;
    private Direction direction;
    private Type type;
    private String comment;

}
