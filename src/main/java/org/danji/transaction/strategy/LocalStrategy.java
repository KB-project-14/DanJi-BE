package org.danji.transaction.strategy;

import lombok.RequiredArgsConstructor;
import org.danji.badge.domain.BadgeVO;
import org.danji.badge.dto.BadgeFilterDTO;
import org.danji.badge.enums.BadgeType;
import org.danji.badge.mapper.BadgeMapper;
import org.danji.global.error.ErrorCode;
import org.danji.memberBadge.dto.MemberBadgeCreateDTO;
import org.danji.memberBadge.enums.BadgeGrade;
import org.danji.memberBadge.service.MemberBadgeService;
import org.danji.transaction.converter.TransactionConverter;
import org.danji.transaction.domain.TransactionVO;
import org.danji.transaction.dto.request.PaymentContextDTO;
import org.danji.transaction.dto.request.PaymentDTO;
import org.danji.transaction.dto.response.TransactionDTO;
import org.danji.transaction.enums.Direction;
import org.danji.transaction.enums.PaymentType;
import org.danji.transaction.enums.Type;
import org.danji.transaction.exception.TransactionException;
import org.danji.transaction.mapper.TransactionMapper;
import org.danji.wallet.exception.WalletException;
import org.danji.wallet.mapper.WalletMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;



@Component
@RequiredArgsConstructor
public class LocalStrategy implements PaymentStrategy {

    private static final int bronze_criteria = 50000;
    private static final int silver_criteria = 100000;
    private static final int gold_criteria = 200000;
    private final WalletMapper walletMapper;
    private final TransactionConverter transactionConverter;
    private final TransactionMapper transactionMapper;
    private final BadgeMapper badgeMapper;
    private final MemberBadgeService memberBadgeService;

    @Override
    public boolean supports(PaymentDTO paymentDTO) {

        return paymentDTO.getType() == PaymentType.LOCAL_CURRENCY &&
                paymentDTO.getInputAmount().equals(paymentDTO.getMerchantAmount());
    }

    @Override
    @Transactional
    public List<TransactionDTO> process(PaymentDTO paymentDTO, UUID userId, PaymentContextDTO ctx) {
        int updated = walletMapper.payAndAccumulate(ctx.getWalletId(), userId, paymentDTO.getInputAmount());
        if (updated != 1) {
            throw new WalletException(ErrorCode.WALLET_BALANCE_NOT_ENOUGH);
        }

        int paymentAmount = ctx.getTotalPayment() + paymentDTO.getInputAmount();

        BadgeFilterDTO badgeFilterDTO = BadgeFilterDTO.builder().badgeType(BadgeType.NORMAL)
                .regionId(ctx.getRegionId())
                .build();
        List<BadgeVO> byFilter = badgeMapper.findByFilter(badgeFilterDTO);

        BadgeGrade target = null;
        if (paymentAmount >= gold_criteria) {
            target = BadgeGrade.GOLD;
        } else if (paymentAmount >= silver_criteria) {
            target = BadgeGrade.SILVER;
        } else if (paymentAmount >= bronze_criteria) {
            target = BadgeGrade.BRONZE;
        }

        if (target != null) {
            UUID badgeId = byFilter.get(0).getBadgeId();
            if (memberBadgeService.validateMemberBadge(userId, badgeId, target)) {
                MemberBadgeCreateDTO dto = MemberBadgeCreateDTO.builder()
                        .badgeId(badgeId)
                        .badgeGrade(target)
                        .memberId(userId)
                        .build();
                memberBadgeService.createMemberBadge(dto);
            }
        }

        final UUID walletId   = ctx.getWalletId();
        final String comment  = ctx.getMerchantName();
        final int amount      = paymentDTO.getMerchantAmount();
        final int before      = ctx.getBalance();
        final int after       = before - amount;


        TransactionVO expenseTx = transactionConverter.toTransactionVO(
                UUID.randomUUID(), walletId, null,
                before, after, amount,
                Direction.EXPENSE, Type.PAYMENT, comment, walletId
        );
        TransactionVO incomeTx = transactionConverter.toTransactionVO(
                UUID.randomUUID(), walletId, null,
                null, null, amount,
                Direction.INCOME, Type.PAYMENT, comment, null
        );


        int inserted = transactionMapper.insertMany(Arrays.asList(expenseTx, incomeTx));
        if (inserted != 2) throw new TransactionException(ErrorCode.TRANSACTION_SAVE_FAILED);

        return List.of(
                transactionConverter.toTransactionDTO(expenseTx),
                transactionConverter.toTransactionDTO(incomeTx)
        );
    }


}