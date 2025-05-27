package com.fbankteam.fbank.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardVo {
    private Integer id;
    private Integer userInfoId;
    private Integer accountId;
    private String cardNumber;
    private CardType cardType;
    private String cardPassword;
    private String cvcHash;
    private Integer monthlyLimit;
    private Integer billingDay;

    private CardTransactionsVO cardTransaction;
}
