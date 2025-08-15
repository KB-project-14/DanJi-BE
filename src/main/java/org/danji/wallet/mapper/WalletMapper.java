package org.danji.wallet.mapper;

import org.apache.ibatis.annotations.Param;
import org.danji.transaction.dto.request.PaymentContextDTO;
import org.danji.wallet.domain.WalletVO;
import org.danji.wallet.dto.WalletDetailDTO;
import org.danji.wallet.dto.WalletFilterDTO;
import org.danji.wallet.dto.WalletOrderUpdateDTO;

import javax.validation.constraints.NotNull;
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

    int findMaxDisplayOrderByMemberId(UUID memberId);

    WalletDetailDTO selectWalletDetailByWalletId(UUID walletId);

    void updateWalletTotalPayment(@Param("walletId") UUID walletId, @Param("totalAmount") int totalAmount);

    //내가 가진 지역화폐 리스트 조회
    List<WalletDetailDTO> findWalletListByMemberId(UUID memberId);

    int payAndAccumulate(@Param("walletId")UUID walletId, @Param("memberId")UUID memberId, @Param("inputAmount")Integer inputAmount);

    PaymentContextDTO getPaymentContext(@Param("walletId") UUID localWalletId, @Param("memberId")UUID userId, @Param("merchantId") UUID availableMerchantId);
}
