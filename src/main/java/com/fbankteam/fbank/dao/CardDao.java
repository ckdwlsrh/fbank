package com.fbankteam.fbank.dao;

import com.fbankteam.fbank.domain.CardVo;

import java.util.List;
import java.util.Optional;

public interface CardDao {
    // card 목록 조회
    List<CardVo> getCardList(int userInfoId);

    // 카드 추가
    boolean addCard(CardVo card);  // void → boolean

    // 카드 상세 조회
    Optional<CardVo> getCard(int cardId);  // CardVo → Optional<CardVo>

    // 카드 삭제
    boolean deleteCard(int cardId);  // void → boolean

    // 비밀번호 변경
    boolean updateCardPassword(int cardId, String newPassword);  // void → boolean

    // 카드 상태 변경
    boolean updateCardStatus(int cardId, String cardType);  // void → boolean
}
