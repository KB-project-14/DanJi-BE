package org.danji.wallet.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.danji.common.utils.AuthUtils;
import org.danji.global.error.ErrorCode;
import org.danji.localCurrency.exception.LocalCurrencyException;
import org.danji.wallet.domain.WalletVO;
import org.danji.wallet.dto.*;
import org.danji.wallet.enums.WalletType;
import org.danji.wallet.exception.WalletException;
import org.danji.wallet.mapper.WalletMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class WalletServiceImpl implements WalletService {

    private final WalletMapper walletMapper;


    @Override
    public WalletDTO createWallet(WalletCreateDTO walletCreateDTO) {

        UUID memberId = AuthUtils.getMemberId();

        // 유저의 지역화폐 존재 여부 체크
        WalletVO existingWallet = walletMapper
                .findByMemberIdAndLocalCurrencyId(memberId, walletCreateDTO.getLocalCurrencyId());

        if (existingWallet != null) {
            throw new LocalCurrencyException(ErrorCode.DUPLICATED_LOCAL_CURRENCY_WALLET);
        }

        int displayOrder = 1;
        if (walletCreateDTO.getWalletType() != WalletType.CASH) {
            displayOrder = walletMapper.findMaxDisplayOrderByMemberId(memberId) + 1;
        }

        UUID walletId = UUID.randomUUID();

        WalletVO createVo = WalletVO.builder()
                .walletId(walletId)
                .memberId(memberId)
                .localCurrencyId(walletCreateDTO.getLocalCurrencyId())
                .walletType(walletCreateDTO.getWalletType())
                .balance(0)
                .displayOrder(displayOrder)
                .build();

        walletMapper.create(createVo);

        return WalletDTO.of(walletMapper.findById(walletId));
    }


    @Override
    public WalletDetailDTO getWallet(UUID walletId) {
        WalletVO vo = validateWallet(walletId);
        return walletMapper.selectWalletDetailByWalletId(vo.getWalletId());
    }


    @Override
    public List<WalletDTO> getWalletList(WalletFilterDTO filterDTO) {

        UUID memberId = AuthUtils.getMemberId();

        filterDTO.setMemberId(memberId);

        List<WalletVO> walletList = walletMapper.findByFilter(filterDTO);

        return walletList.stream().map(WalletDTO::of).toList();
    }

    @Override
    @Transactional
    public List<WalletDTO> updateWalletOrder(List<WalletOrderUpdateDTO> walletOrderList) {

        UUID memberId = AuthUtils.getMemberId();

        List<UUID> walletIds = walletOrderList.stream()
                .map(WalletOrderUpdateDTO::getWalletId).toList();

        WalletFilterDTO filter = WalletFilterDTO.builder()
                .memberId(memberId)
                .walletType(WalletType.LOCAL)
                .build();

        List<UUID> myWalletIds = walletMapper.findByFilter(filter).stream()
                .map(WalletVO::getWalletId).toList();

        if (!new HashSet<>(myWalletIds).containsAll(walletIds)) {
            throw new WalletException(ErrorCode.UNAUTHORIZED_WALLET_ACCESS);
        }

        walletMapper.bulkUpdateDisplayOrder(walletOrderList);

        return walletMapper.findByFilter(filter).stream()
                .map(WalletDTO::of).toList();
    }

    @Override
    @Transactional
    public void deleteWallet(UUID walletId) {
        WalletVO wallet = validateWallet(walletId);

        if (wallet.getBalance() != 0) {
            throw new WalletException(ErrorCode.CAN_NOT_DELETE_WALLET_BALANCE_NOT_EMPTY);
        }

        walletMapper.delete(walletId);

        walletMapper.reorderDisplayOrder(wallet.getMemberId());
    }


    private WalletVO validateWallet(UUID walletId) {
        UUID memberId = AuthUtils.getMemberId();

        WalletVO wallet = walletMapper.findById(walletId);
        if (wallet == null) {
            throw new WalletException(ErrorCode.WALLET_NOT_FOUND);
        }
        if (!wallet.getMemberId().equals(memberId)) {
            throw new WalletException(ErrorCode.UNAUTHORIZED_WALLET_ACCESS);
        }
        return wallet;
    }
}
