package org.danji.transaction.validator;

import org.danji.wallet.domain.WalletVO;

import java.util.List;
import java.util.UUID;

public class WalletValidator {

    public static boolean checkOwnership(List<WalletVO> wallets, WalletVO targetWalletVO) {
        for (WalletVO wallet : wallets) {
            if (wallet.getWalletId().equals(targetWalletVO.getWalletId())) {
                return true;
            }
        }
        return false;
    }
}
