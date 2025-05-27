package com.fbankteam.fbank.dao;

import com.fbankteam.fbank.domain.CardVo;

import java.util.List;

public interface CardDao {
    // card 목록 조회
    List<CardVo> getCardList(int userInfoId);

    // 카드 추가
    void addCard(CardVo card);

    // 카드 상세 조회
    CardVo getCard(int cardId);

    // 카드 삭제
    void deleteCard(int cardId);


    // 비밀번호 변경
    void updateCardPassword(int cardId, String newPassword);

    // 카드 상태 변경
    void updateCardStatus(int cardId, String cardType);
}
