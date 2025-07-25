package org.danji.transaction.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.danji.transaction.dto.request.TransferDTO;
import org.danji.transaction.dto.response.TransactionDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("CONVERT")
@RequiredArgsConstructor
@Log4j2
public class ConvertProcessor implements TransferProcessor {

    private final RechargeProcessor rechargeProcessor;
    private final RefundProcessor refundProcessor;
    @Override
    public List<TransactionDTO> process(TransferDTO transferDTO) {
        //tranferDTO 에는 지역화폐 -> 지역화폐 전환 기록만 넘어올거임.
        // 해당 지역화폐 지갑에서 member_id를 찾아서, mainwallet id를 찾은 후
        // 새로운 transferDTO를 만들어서 refundProcessor에 넘겨야함
        // transferDTO 에서 전달 받은 가격, from_wallet_id 랑, boolean 값을 false로 만든 transferDTO
        refundProcessor.process(transferDTO);
        return List.of();
    }
}
