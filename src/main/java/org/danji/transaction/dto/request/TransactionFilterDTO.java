package org.danji.transaction.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.danji.global.enums.SortOrder;
import org.danji.transaction.enums.Direction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TransactionFilterDTO {

    @NotNull(message = "시작날짜 값은 필수입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "종료날짜 값은 필수입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastDate;

    private Direction direction;

    @NotNull(message = "거래내역 정렬 기준 값은 필수입니다.")
    private SortOrder sortOrder;
}
