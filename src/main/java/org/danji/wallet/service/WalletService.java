package org.danji.wallet.service;

import org.danji.wallet.domain.WalletVO;
import org.danji.wallet.dto.WalletDTO;

public interface WalletService {

    WalletDTO createWallet(WalletDTO walletDTO);
}
