package org.danji.wallet.service;

import org.danji.wallet.dto.*;

import java.util.List;
import java.util.UUID;

public interface WalletService {

    WalletDTO createWallet(WalletCreateDTO walletCreateDTO);

    WalletDetailDTO getWallet(UUID walletId);

    List<WalletDTO> getWalletList(WalletFilterDTO filterDTO);

    List<WalletDTO> updateWalletOrder(List<WalletOrderUpdateDTO> walletOrderList);

    void deleteWallet(UUID walletId);

    List<WalletDetailDTO> getWalletWithCurrency(WalletFilterDTO filter);
}
