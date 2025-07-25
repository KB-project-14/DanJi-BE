package org.danji.wallet.mapper;

import org.apache.ibatis.annotations.Param;
import org.danji.wallet.domain.WalletVO;

import java.util.UUID;

public interface WalletMapper {

    WalletVO getWalletByUUId(UUID walletId);

    void updateWalletBalance(@Param("walletId") UUID walletId, @Param("amount") int amount);

    WalletVO findByMemberIdAndLocalCurrencyId(@Param("memberId") UUID memberId, @Param("localCurrencyId") UUID localCurrencyId);

    void create(WalletVO vo);

}
