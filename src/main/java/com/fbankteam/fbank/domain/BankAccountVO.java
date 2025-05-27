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
public class BankAccountVO {
    private Integer id;
    private Integer userInfoId;
    private String accountNumber;
    private String accountPassword;
    private Integer balance;
    private AccountType accountType; //'SAVINGS' or 'INSTALLMENT'
    private LocalDateTime createAt;
    private boolean isFavorite;

    // 1:N긴 하지만 5건 조인은 1:1이 더 편함
    private AccountTransactionsVO transaction;
}
