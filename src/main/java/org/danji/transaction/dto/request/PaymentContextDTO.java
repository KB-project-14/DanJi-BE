package org.danji.transaction.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PaymentContextDTO {
    UUID walletId; Integer balance; Integer totalPayment;
    Long regionId; String merchantName;
    boolean walletExists; boolean merchantExists; boolean localCurrencyExists; boolean authorized;
}