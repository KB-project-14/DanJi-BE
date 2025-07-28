package org.danji.transaction.strategy;

import lombok.RequiredArgsConstructor;
import org.danji.global.error.ErrorCode;
import org.danji.transaction.dto.request.PaymentDTO;
import org.danji.transaction.dto.response.TransactionDTO;
import org.danji.transaction.enums.PaymentType;
import org.danji.wallet.exception.WalletException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LocalOverPaidStrategy implements PaymentStrategy{
    @Override
    public boolean supports(PaymentDTO dto) {
        return dto.getType() == PaymentType.LOCAL_CURRENCY &&
                dto.getInputAmount() > dto.getMerchantAmount();
    }

    @Override
    @Transactional
    public List<TransactionDTO> process(PaymentDTO dto) {
        throw new WalletException(ErrorCode.WALLET_BALANCE_NOT_ENOUGH);
    }
}
