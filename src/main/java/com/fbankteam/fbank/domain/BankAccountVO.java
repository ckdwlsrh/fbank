package com.fbankteam.fbank.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccountVO {
    private Integer id;
    private Integer userInfoId;
    private String accountNumber;
    private String accountPassword;
    private Integer balance;
    private AccountType accountType; //'SAVINGS' or 'INSTALLMENT'
    private LocalDateTime createAt;
    private Boolean isFavorite;
}
