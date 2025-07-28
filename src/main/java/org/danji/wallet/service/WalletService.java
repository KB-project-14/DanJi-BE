package org.danji.wallet.service;

import org.danji.wallet.dto.WalletCreateDTO;
import org.danji.wallet.dto.WalletDTO;
import org.danji.wallet.dto.WalletFilterDTO;
import org.danji.wallet.dto.WalletOrderUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface WalletService {

    WalletDTO createWallet(WalletCreateDTO walletCreateDTO);

    WalletDTO getWallet(UUID walletId);

    List<WalletDTO> getWalletList(WalletFilterDTO filterDTO);

    List<WalletDTO> updateWalletOrder(List<WalletOrderUpdateDTO> walletOrderList);

    void deleteWallet(UUID walletId);
}
