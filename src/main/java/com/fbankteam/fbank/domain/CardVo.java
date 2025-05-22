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
    private int id;
    private int userInfoId;
    private int accountId;
    private String cardNumber;
    private String cardType;
    private String cardPassword;
    private String cvcHash;
    private Integer monthlyLimit;
    private Integer billingDay;
}
