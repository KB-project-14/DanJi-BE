package org.danji.transaction.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TransactionAggregateDTO {

    private Integer aggregateCharge;
    private Integer aggregateIncentive;

    private List<TransactionDTO> transactions;
}
