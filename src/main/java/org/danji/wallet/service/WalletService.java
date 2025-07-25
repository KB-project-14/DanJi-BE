package org.danji.wallet.service;

import org.danji.wallet.domain.WalletVO;
import org.danji.wallet.dto.WalletDTO;
import org.danji.wallet.dto.WalletFilterDTO;

import java.util.List;
import java.util.UUID;

public interface WalletService {

    WalletDTO createWallet(WalletDTO walletDTO);

    WalletDTO getWallet(UUID walletId);

    List<WalletDTO> getWalletList(WalletFilterDTO filterDTO);
}
