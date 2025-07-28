package org.danji.wallet.mapper;

import org.apache.ibatis.annotations.Param;
import org.danji.wallet.domain.WalletVO;
import org.danji.wallet.dto.WalletFilterDTO;
import org.danji.wallet.dto.WalletOrderUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface WalletMapper {

    WalletVO findById(UUID walletId);

    void updateWalletBalance(@Param("walletId") UUID walletId, @Param("amount") int amount);

    WalletVO findByMemberIdAndLocalCurrencyId(@Param("memberId") UUID memberId, @Param("localCurrencyId") UUID localCurrencyId);

    void create(WalletVO vo);

    List<WalletVO> findByFilter(WalletFilterDTO filterDTO);

    WalletVO findByMemberId(UUID memberId);


    void bulkUpdateDisplayOrder(List<WalletOrderUpdateDTO> dtoList);

    void delete(UUID walletId);

    void reorderDisplayOrder(UUID memberId);




    WalletVO findByMemberId(UUID memberId);


    List<WalletVO> findLocalWalletByMemberId(UUID memberId);


    int findMaxDisplayOrderByMemberId(UUID memberId);

    List<WalletVO> findLocalWalletByMemberId(UUID memberId);

    int findMaxDisplayOrderByMemberId(UUID memberId);

}
