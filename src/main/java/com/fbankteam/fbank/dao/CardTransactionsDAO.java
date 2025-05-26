package com.fbankteam.fbank.dao;

import com.fbankteam.fbank.domain.CardTransactionsVO;

import java.time.LocalDateTime;
import java.util.List;

public interface CardTransactionsDAO {

    //이번달 카드 총 이용 금액
    int getMontlyTotalAmount(int cardID);

    //이용 내역 최근 10개
    List<CardTransactionsVO> findTop10RecentCardTransactions(int cardId);

    //날짜사이의 이용내역
    List<CardTransactionsVO> findCardTransactionsByCardIdAndPeriod(int cardId, LocalDateTime start, LocalDateTime end);

    //카드내역 추가
    int insertCardTransaction(CardTransactionsVO cardTransactionsVO);

}

