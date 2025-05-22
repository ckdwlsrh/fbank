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
    private int id;
    private int accountId;
    private TransactionType transactionType;
    private LocalDateTime transactionDate;
    private int amount;
    private String toAccountNumber;
    private String receiverBankName;
    private String receiverName;


}
