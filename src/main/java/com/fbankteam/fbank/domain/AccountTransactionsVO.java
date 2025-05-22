package com.fbankteam.fbank.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransactionsVO {
    private Integer id;
    private Integer accountId;
    private TransactionType transactionType;
    private LocalDateTime transactionDate;
    private Integer amount;
    private String toAccountNumber;
    private String receiverBankName;
    private String receiverName;

    //TODO: 조인을 위한 1:N 리스트 필요
}
