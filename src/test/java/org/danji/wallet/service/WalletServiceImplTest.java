package org.danji.wallet.service;

import lombok.extern.log4j.Log4j2;
import org.danji.global.config.RootConfig;
import org.danji.wallet.dto.WalletDTO;
import org.danji.wallet.enums.WalletType;
import org.danji.wallet.mapper.WalletMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RootConfig.class})
@Log4j2
@Transactional
class WalletServiceImplTest {

    @Autowired
    private WalletService walletService;

    @Autowired
    private WalletMapper walletMapper;

    @Test
    @Disabled
    void createWallet() {
        // given
        WalletDTO createDTO = WalletDTO.builder()
                .localCurrencyId(UUID.randomUUID())
                .walletType(WalletType.CASH)
                .balance(0)
                .displayOrder(1)
                .build();

        // when
        WalletDTO result = walletService.createWallet(createDTO);

        // then
        WalletDTO checkWallet = WalletDTO.of(walletMapper.getWalletByUUId(result.getWalletId()));
        assertEquals(checkWallet, result);
    }
}