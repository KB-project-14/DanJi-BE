package org.danji.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.danji.global.dto.BaseDTO;
import org.danji.wallet.domain.WalletVO;
import org.danji.wallet.enums.WalletType;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class WalletDTO extends BaseDTO {
    private UUID walletId;
    private UUID memberId;
    private UUID localCurrencyId;
    private WalletType walletType;
    private Integer balance;
    private int displayOrder;
    private String walletPin;

    public static WalletDTO of(WalletVO vo) {
        return vo == null ? null : WalletDTO.builder()
                .walletId(vo.getWalletId())
                .memberId(vo.getMemberId())
                .localCurrencyId(vo.getLocalCurrencyId())
                .walletType(vo.getWalletType())
                .balance(vo.getBalance())
                .displayOrder(vo.getDisplayOrder())
                .createdAt(vo.getCreatedAt())
                .updatedAt(vo.getUpdatedAt())
                .build();
    }

    public WalletVO toVo() {
        return WalletVO.builder()
                .walletId(walletId)
                .memberId(memberId)
                .localCurrencyId(localCurrencyId)
                .walletType(walletType)
                .balance(balance)
                .displayOrder(displayOrder)
                .walletPin(walletPin)
                .build();
    }
}
