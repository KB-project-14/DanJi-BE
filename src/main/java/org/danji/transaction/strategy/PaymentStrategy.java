package org.danji.transaction.strategy;

import org.danji.transaction.dto.request.PaymentDTO;
import org.danji.transaction.dto.response.TransactionDTO;

import java.util.List;
import java.util.UUID;

public interface PaymentStrategy {
    boolean supports(PaymentDTO dto);

    List<TransactionDTO> process(PaymentDTO dto, UUID userId);
}
