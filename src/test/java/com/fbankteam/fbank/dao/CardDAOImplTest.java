package com.fbankteam.fbank.dao;

import com.fbankteam.fbank.domain.CardType;
import com.fbankteam.fbank.domain.CardVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CardDAOImplTest {
    CardDao cardDao = new CardDaoImpl();

    @Test
    @DisplayName("내 카드 목록 불러오기(simple)")
    void testGetCardList() {
        List<CardVo> cardList = cardDao.getCardList(1);
        for (CardVo cardVo : cardList) {
            System.out.println(cardVo);
        }
    }

    @Test
    @DisplayName("카드 추가")
    void testAddCard() {
        CardVo card = new CardVo();

        card.setUserInfoId(1); // 실제 존재하는 유저 ID
        card.setAccountId(1); // 실제 존재하는 계좌 ID
        card.setCardPassword("1234");
        card.setCardType(CardType.CREDIT);  // 체크카드면 DEBIT
        card.setCardNumber("1234-5678-9876-5432");
        card.setCvcHash("123");
        card.setMonthlyLimit(200);
        card.setBillingDay(10);

        cardDao.addCard(card);
    }

    @Test
    @DisplayName("카드 상세 정보")
    void testGetCard() {
        CardVo card = cardDao.getCard(1);
        System.out.println(card);
    }

    @Test
    @DisplayName("카드 해지")
    void testDeleteCard() {
        CardVo card = cardDao.getCard(1);
        cardDao.deleteCard(1);
    }

    @Test
    @DisplayName("카드 비번 변경")
    void testUpdateCardPassword() {
        CardVo card = cardDao.getCard(1);
        card.setCardPassword("1234");
        cardDao.updateCardPassword(1, card.getCardPassword());
    }

    @Test
    @DisplayName("카드 타입 변경")
    void testUpdateCardStatus() {
        CardVo card = cardDao.getCard(1);
        cardDao.updateCardStatus(1, card.getCardPassword());
    }

}
