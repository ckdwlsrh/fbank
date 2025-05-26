package com.fbankteam.fbank.dao;

import com.fbankteam.fbank.dao.CardTransactionsDAOImpl;
import com.fbankteam.fbank.domain.CardTransactionsVO;
import com.fbankteam.fbank.util.JDBCUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

public class CardTransactionsDAOImplTest {

    static CardTransactionsDAOImpl dao=new CardTransactionsDAOImpl();

    @AfterAll
    static void tearDown() {
        JDBCUtil.close();
    }

    @Test
    @DisplayName("이번달 카드 총 이용 금액")
    void getMontlyTotalAmount() {
        int totalAmount=dao.getMontlyTotalAmount(1);
        System.out.println("이번달 카드 총 이용 금액: "+totalAmount);
    }

    @Test
    @DisplayName("이용 내역 최근 10개")
    void findTop10RecentCardTransactions() {
        List<CardTransactionsVO> list=dao.findTop10RecentCardTransactions(1);
        System.out.println("이용내역 최근 10게");
        list.forEach(System.out::println);
    }

    @Test
    @DisplayName("날짜사이의 이용내역")
    void findCardTransactionsByCardIdAndPeriod() {
        List<CardTransactionsVO> list=dao.findCardTransactionsByCardIdAndPeriod(1, LocalDateTime.of(2025, 5,1, 0,0,0), LocalDateTime.of(2025, 5,30, 23,59,59));
        System.out.println("날짜사이의 이용내역");
        list.forEach(System.out::println);
    }

    @Test
    @DisplayName("카드내역 추가")
    void insertCardTransaction() {
        CardTransactionsVO cardTransactionsVO= CardTransactionsVO.builder()
                .card_id(1)
                .amount(10000)
                .transaction_date(LocalDateTime.now())
                .build();
        int count=dao.insertCardTransaction(cardTransactionsVO);
        Assertions.assertEquals(1, count);
    }



}
