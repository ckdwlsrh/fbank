package com.fbankteam.fbank.dao;

import com.fbankteam.fbank.domain.CardTransactionsVO;
import com.fbankteam.fbank.domain.CardVo;

import java.time.LocalDateTime;
import java.util.List;

public interface CardTransactionsDAO {

    //최근 카드 거래내역 5개-카드 정보 포함
    List<CardVo> findCardTransactionsByCardIdOrderByDateDescLimit5(int userInfoId);
    
    //이번달 카드 총 이용 금액
    int getMontlyTotalAmount(int cardID);

    //이용 내역 최근 10개
    List<CardTransactionsVO> findTop10RecentCardTransactions(int cardId);

    //날짜사이의 이용내역
    List<CardTransactionsVO> findCardTransactionsByCardIdAndPeriod(int cardId, LocalDateTime start, LocalDateTime end);

    //카드내역 추가
    int insertCardTransaction(CardTransactionsVO cardTransactionsVO);

}

