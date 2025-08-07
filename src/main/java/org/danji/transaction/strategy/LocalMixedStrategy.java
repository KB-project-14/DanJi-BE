package org.danji.transaction.strategy;

import lombok.RequiredArgsConstructor;
import org.danji.availableMerchant.domain.AvailableMerchantVO;
import org.danji.availableMerchant.exception.AvailableMerchantException;
import org.danji.availableMerchant.mapper.AvailableMerchantMapper;
import org.danji.badge.domain.BadgeVO;
import org.danji.badge.dto.BadgeFilterDTO;
import org.danji.badge.enums.BadgeType;
import org.danji.badge.exception.BadgeException;
import org.danji.badge.mapper.BadgeMapper;
import org.danji.global.error.ErrorCode;
import org.danji.localCurrency.domain.LocalCurrencyVO;
import org.danji.localCurrency.exception.LocalCurrencyException;
import org.danji.localCurrency.mapper.LocalCurrencyMapper;
import org.danji.memberBadge.dto.MemberBadgeCreateDTO;
import org.danji.memberBadge.enums.BadgeGrade;
import org.danji.memberBadge.service.MemberBadgeService;
import org.danji.transaction.converter.TransactionConverter;
import org.danji.transaction.domain.TransactionVO;
import org.danji.transaction.dto.request.PaymentDTO;
import org.danji.transaction.dto.response.TransactionDTO;
import org.danji.transaction.enums.Direction;
import org.danji.transaction.enums.PaymentType;
import org.danji.transaction.enums.Type;
import org.danji.transaction.exception.TransactionException;
import org.danji.transaction.mapper.TransactionMapper;
import org.danji.wallet.domain.WalletVO;
import org.danji.wallet.dto.WalletFilterDTO;
import org.danji.wallet.enums.WalletType;
import org.danji.wallet.exception.WalletException;
import org.danji.wallet.mapper.WalletMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.danji.transaction.validator.WalletValidator.checkOwnership;

@Component
@RequiredArgsConstructor
public class LocalMixedStrategy implements PaymentStrategy {

    private static final int bronze_criteria = 50000;
    private static final int silver_criteria = 100000;
    private static final int gold_criteria = 200000;

    private final WalletMapper walletMapper;
    private final TransactionConverter transactionConverter;
    private final AvailableMerchantMapper availableMerchantMapper;
    private final TransactionMapper transactionMapper;
    private final MemberBadgeService memberBadgeService;
    private final BadgeMapper badgeMapper;
    private final LocalCurrencyMapper localCurrencyMapper;

    @Override
    public boolean supports(PaymentDTO paymentDTO) {

        return paymentDTO.getType() == PaymentType.LOCAL_CURRENCY &&
                paymentDTO.getInputAmount() < paymentDTO.getMerchantAmount();
    }

    @Override
    @Transactional
    public List<TransactionDTO> process(PaymentDTO paymentDTO, UUID userId) {


        AvailableMerchantVO availableMerchantVO = availableMerchantMapper.findById(paymentDTO.getAvailableMerchantId());
        if (availableMerchantVO == null) {
            throw new AvailableMerchantException(ErrorCode.AVAILABLE_MERCHANT_NOT_FOUND);
        }


        WalletVO LocalCurrencyWalletVO = walletMapper.findById(paymentDTO.getLocalWalletId());
        if (LocalCurrencyWalletVO == null) {
            throw new WalletException(ErrorCode.WALLET_NOT_FOUND);
        }
        LocalCurrencyVO localCurrencyVO = localCurrencyMapper.findById(LocalCurrencyWalletVO.getLocalCurrencyId());
        if (localCurrencyVO == null) {
            throw new LocalCurrencyException(ErrorCode.LOCAL_CURRENCY_NOT_FOUND);
        }
        WalletFilterDTO walletFilterDTO = WalletFilterDTO.builder().memberId(userId).walletType(WalletType.LOCAL).build();
        List<WalletVO> localWalletByUserIdVO = walletMapper.findByFilter(walletFilterDTO);
        if (!checkOwnership(localWalletByUserIdVO, LocalCurrencyWalletVO)) {
            throw new WalletException(ErrorCode.UNAUTHORIZED_WALLET_ACCESS);
        }

        if (paymentDTO.getInputAmount() > LocalCurrencyWalletVO.getBalance()) {
            throw new WalletException(ErrorCode.WALLET_BALANCE_NOT_ENOUGH);
        }

        walletMapper.updateWalletBalance(paymentDTO.getLocalWalletId(), -paymentDTO.getInputAmount());
        walletMapper.updateWalletTotalPayment(paymentDTO.getLocalWalletId(), paymentDTO.getInputAmount());

        int paymentAmount = LocalCurrencyWalletVO.getTotalPayment() + paymentDTO.getInputAmount();

        if (paymentAmount >= bronze_criteria && paymentAmount < silver_criteria){
            BadgeFilterDTO badgeFilterDTO = BadgeFilterDTO.builder().badgeType(BadgeType.NORMAL)
                            .regionId(localCurrencyVO.getRegionId())
                                    .build();
            List<BadgeVO> byFilter = badgeMapper.findByFilter(badgeFilterDTO);

            if (memberBadgeService.validateMemberBadge(userId, byFilter.get(0).getBadgeId(), BadgeGrade.BRONZE)){
                MemberBadgeCreateDTO memberBadgeCreateDTO = MemberBadgeCreateDTO.builder()
                        .badgeId(byFilter.get(0).getBadgeId())
                        .badgeGrade(BadgeGrade.BRONZE)
                        .memberId(userId)
                        .build();
                memberBadgeService.createMemberBadge(memberBadgeCreateDTO);
            }
        }
        else if (paymentAmount >= silver_criteria && paymentAmount < gold_criteria){
            BadgeFilterDTO badgeFilterDTO = BadgeFilterDTO.builder().badgeType(BadgeType.NORMAL)
                    .regionId(localCurrencyVO.getRegionId())
                    .build();
            List<BadgeVO> byFilter = badgeMapper.findByFilter(badgeFilterDTO);

            if (memberBadgeService.validateMemberBadge(userId, byFilter.get(0).getBadgeId(), BadgeGrade.SILVER)){
                MemberBadgeCreateDTO memberBadgeCreateDTO = MemberBadgeCreateDTO.builder()
                        .badgeId(byFilter.get(0).getBadgeId())
                        .badgeGrade(BadgeGrade.SILVER)
                        .memberId(userId)
                        .build();
                memberBadgeService.createMemberBadge(memberBadgeCreateDTO);
            }

        }
        else if (paymentAmount >= gold_criteria){
            BadgeFilterDTO badgeFilterDTO = BadgeFilterDTO.builder().badgeType(BadgeType.NORMAL)
                    .regionId(localCurrencyVO.getRegionId())
                    .build();
            List<BadgeVO> byFilter = badgeMapper.findByFilter(badgeFilterDTO);

            if (memberBadgeService.validateMemberBadge(userId, byFilter.get(0).getBadgeId(), BadgeGrade.GOLD)){
                MemberBadgeCreateDTO memberBadgeCreateDTO = MemberBadgeCreateDTO.builder()
                        .badgeId(byFilter.get(0).getBadgeId())
                        .badgeGrade(BadgeGrade.GOLD)
                        .memberId(userId)
                        .build();
                memberBadgeService.createMemberBadge(memberBadgeCreateDTO);
            }
        }
        TransactionVO localTx = transactionConverter.toTransactionVO(
                UUID.randomUUID(), paymentDTO.getLocalWalletId(), null,
                LocalCurrencyWalletVO.getBalance(), LocalCurrencyWalletVO.getBalance() - paymentDTO.getInputAmount(),
                paymentDTO.getInputAmount(), Direction.EXPENSE, Type.PAYMENT, availableMerchantVO.getName(), paymentDTO.getLocalWalletId());
        int successLocalWalletCount = transactionMapper.insert(localTx);

        if (successLocalWalletCount != 1) {
            throw new TransactionException(ErrorCode.TRANSACTION_SAVE_FAILED);
        }

        TransactionVO merchantTx = transactionConverter.toTransactionVO(
                UUID.randomUUID(), paymentDTO.getLocalWalletId(), null,
                null, null,
                paymentDTO.getInputAmount(), Direction.INCOME, Type.PAYMENT, availableMerchantVO.getName(), null);
        int successMerchantTxCount = transactionMapper.insert(merchantTx);

        if (successMerchantTxCount != 1) {
            throw new TransactionException(ErrorCode.TRANSACTION_SAVE_FAILED);
        }

        //현금 지갑에서 결제해야할 금액
        int leftBalance = paymentDTO.getMerchantAmount() - paymentDTO.getInputAmount();
        WalletVO mainWalletVO = walletMapper.findByMemberId(userId);
        if (mainWalletVO == null) {
            throw new WalletException(ErrorCode.WALLET_NOT_FOUND);
        }
        if (mainWalletVO.getBalance() < leftBalance) {
            throw new WalletException(ErrorCode.WALLET_BALANCE_NOT_ENOUGH);
        }

        walletMapper.updateWalletBalance(mainWalletVO.getWalletId(), -leftBalance);

        TransactionVO mainTx = transactionConverter.toTransactionVO(
                UUID.randomUUID(), mainWalletVO.getWalletId(), null,
                mainWalletVO.getBalance(), mainWalletVO.getBalance() - leftBalance,
                leftBalance, Direction.EXPENSE, Type.PAYMENT, availableMerchantVO.getName(), mainWalletVO.getWalletId());
        int successMainWalletCount = transactionMapper.insert(mainTx);

        if (successMainWalletCount != 1) {
            throw new TransactionException(ErrorCode.TRANSACTION_SAVE_FAILED);
        }

        TransactionVO merchantTwoTx = transactionConverter.toTransactionVO(
                UUID.randomUUID(), paymentDTO.getLocalWalletId(), null,
                null, null,
                paymentDTO.getInputAmount(), Direction.INCOME, Type.PAYMENT, availableMerchantVO.getName(), null);
        int successMerchantTwoTxCount = transactionMapper.insert(merchantTwoTx);

        if (successMerchantTwoTxCount != 1) {
            throw new TransactionException(ErrorCode.TRANSACTION_SAVE_FAILED);
        }

        return List.of(
                transactionConverter.toTransactionDTO(localTx),
                transactionConverter.toTransactionDTO(merchantTx),
                transactionConverter.toTransactionDTO(mainTx),
                transactionConverter.toTransactionDTO(merchantTwoTx)
        );
    }
}
