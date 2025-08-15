package org.danji.transaction.strategy;

import org.danji.availableMerchant.domain.AvailableMerchantVO;
import org.danji.localCurrency.domain.LocalCurrencyVO;
import org.danji.transaction.dto.request.PaymentContextDTO;
import org.danji.transaction.dto.request.PaymentDTO;
import org.danji.transaction.dto.response.TransactionDTO;
import org.danji.wallet.domain.WalletVO;

import java.util.List;
import java.util.UUID;

public interface PaymentStrategy {
    boolean supports(PaymentDTO dto);

    List<TransactionDTO> process(PaymentDTO dto, UUID userId, PaymentContextDTO ctx);
}
