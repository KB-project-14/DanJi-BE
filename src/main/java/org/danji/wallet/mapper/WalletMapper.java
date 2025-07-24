package org.danji.wallet.mapper;

import org.apache.ibatis.annotations.Param;
import org.danji.localCurrency.domain.LocalCurrencyVO;
import org.danji.localCurrency.enums.BenefitType;
import org.danji.wallet.domain.WalletVO;
import org.springframework.security.core.parameters.P;

import javax.validation.constraints.Min;
import java.util.Optional;
import java.util.UUID;

public interface WalletMapper {

    WalletVO getWalletByUUId(UUID walletId);

    void updateWalletBalance(@Param("walletId") UUID walletId, @Param("amount") int amount);

}
