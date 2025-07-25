package org.danji.transaction.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.danji.transaction.converter.TransactionConverter;
import org.danji.transaction.dto.request.TransferDTO;
import org.danji.transaction.dto.response.TransactionDTO;
import org.danji.wallet.domain.WalletVO;
import org.danji.wallet.mapper.WalletMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("CONVERT")
@Log4j2
public class ConvertProcessor implements TransferProcessor {

    private final TransferProcessor rechargeProcessor;
    private final TransferProcessor refundProcessor;
    private final WalletMapper walletMapper;
    private final TransactionConverter transactionConverter;

    public ConvertProcessor(
            @Qualifier("CHARGE") TransferProcessor rechargeProcessor,
            @Qualifier("REFUND") TransferProcessor refundProcessor,
            WalletMapper walletMapper,
            TransactionConverter transactionConverter
    ) {
        this.rechargeProcessor = rechargeProcessor;
        this.refundProcessor = refundProcessor;
        this.walletMapper = walletMapper;
        this.transactionConverter = transactionConverter;
    }

    @Transactional
    @Override
    public List<TransactionDTO> process(TransferDTO transferDTO) {
        //tranferDTO 에는 지역화폐 -> 지역화폐 전환 기록만 넘어올것으로 예상.
        // 해당 지역화폐 지갑에서 member_id를 찾아서, mainwallet id를 찾은 후
        // 새로운 transferDTO를 만들어서 refundProcessor에 넘겨야함
        // transferDTO 에서 전달 받은 가격, from_wallet_id 랑, boolean 값을 false로 만든 transferDTO
        WalletVO fromLocalCurrencyWalletVO = walletMapper.findById(transferDTO.getFromWalletId());
        //일단 현재는 로그인 구현이 안되었기 때문에, 이런식으로 번잡하게 찾아서 구현해놓음
        UUID memberId = fromLocalCurrencyWalletVO.getMemberId();

        WalletVO mainWalletVO = walletMapper.findByMemberId(memberId);
        TransferDTO refundDTO = transactionConverter.toTransferDTO(transferDTO.getFromWalletId(), mainWalletVO.getWalletId()
        , transferDTO.getType(), transferDTO.getAmount(), false);

        List<TransactionDTO> refundList = refundProcessor.process(refundDTO);

        TransferDTO rechargeDTO = transactionConverter.toTransferDTO(mainWalletVO.getWalletId(), transferDTO.getToWalletId(),
                transferDTO.getType(), transferDTO.getAmount(), false);

        List<TransactionDTO> rechargeList = rechargeProcessor.process(rechargeDTO);

        List<TransactionDTO> convertList = new ArrayList<>();
        convertList.addAll(refundList);
        convertList.addAll(rechargeList);

        return convertList;
    }
}
