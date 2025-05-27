package com.fbankteam.fbank.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CardTransactionsVO {
    private Integer id;
    private Integer card_id;
    private Integer amount;
    private LocalDateTime transaction_date;
}

