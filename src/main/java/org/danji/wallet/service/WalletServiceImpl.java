package org.danji.wallet.service;

import lombok.RequiredArgsConstructor;
import org.danji.common.utils.AuthUtils;
import org.danji.global.error.ErrorCode;
import org.danji.localCurrency.exception.LocalCurrencyException;
import org.danji.wallet.domain.WalletVO;
import org.danji.wallet.dto.WalletDTO;
import org.danji.wallet.dto.WalletFilterDTO;
import org.danji.wallet.exception.WalletException;
import org.danji.wallet.mapper.WalletMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletMapper walletMapper;


    @Override
    public WalletDTO createWallet(WalletDTO walletDTO) {

        // TODO security 완성되면 수정 필요
        // securityContextHolder에서 memberId 꺼내오기
//        UUID memberId = AuthUtils.getMemberId();
        UUID memberId = UUID.randomUUID();

        // 유저의 지역화폐 존재 여부 체크
        WalletVO existingWallet = walletMapper.findByMemberIdAndLocalCurrencyId(memberId, walletDTO.getLocalCurrencyId());
        if (existingWallet != null) {
            throw new LocalCurrencyException(ErrorCode.DUPLICATED_LOCAL_CURRENCY_WALLET);
        }

        UUID walletId = UUID.randomUUID();
        walletDTO.setWalletId(walletId);
        walletDTO.setMemberId(memberId);

        walletMapper.create(walletDTO.toVo());

        return WalletDTO.of(walletMapper.findById(walletId));
    }

    @Override
    public WalletDTO getWallet(UUID walletId) {
        WalletVO vo = walletMapper.findById(walletId);

        if (vo == null) {
            throw new WalletException(ErrorCode.WALLET_NOT_FOUND);
        }

        // TODO security 완성되면 수정 필요
        // securityContextHolder에서 memberId 꺼내오기
        UUID memberId = AuthUtils.getMemberId();

        if (!vo.getMemberId().equals(memberId)) {
            throw new WalletException(ErrorCode.UNAUTHORIZED_WALLET_ACCESS);
        }

        return WalletDTO.of(vo);
    }


    @Override
    public List<WalletDTO> getWalletList(WalletFilterDTO filterDTO) {

        // TODO security 완성되면 수정 필요
        // securityContextHolder에서 memberId 꺼내오기
//        UUID memberId = AuthUtils.getMemberId();
        UUID memberId = UUID.randomUUID();

        filterDTO.setMemberId(memberId);

        List<WalletVO> walletList = walletMapper.findByFilter(filterDTO);

        return walletList.stream().map(WalletDTO::of).toList();
    }

}
