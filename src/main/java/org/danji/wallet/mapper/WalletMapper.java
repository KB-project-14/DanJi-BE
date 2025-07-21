package org.danji.wallet.mapper;

import org.danji.localCurrency.domain.LocalCurrencyVO;
import org.danji.localCurrency.enums.BenefitType;
import org.danji.wallet.domain.WalletVO;

import javax.validation.constraints.Min;
import java.util.Optional;
import java.util.UUID;

public interface WalletMapper {
    Optional<WalletVO> getCashWalletByUserId(UUID userId);

    Optional<WalletVO> getWalletByUserId(UUID walletId);

    void updateWalletBalance(UUID walletId, UUID walletId1, int amount);

    void increaseBalance(UUID walletId, int amount);
}
