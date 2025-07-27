package org.danji.transaction.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.danji.transaction.dto.request.PaymentDTO;
import org.danji.transaction.dto.response.TransactionDTO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("PAYMENT")
@RequiredArgsConstructor
@Log4j2
public class PaymentProcessor implements TransferProcessor<PaymentDTO> {
    @Override
    public List<TransactionDTO> process(PaymentDTO dto) {
        return List.of();
    }
}
