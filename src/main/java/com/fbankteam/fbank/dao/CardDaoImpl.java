package com.fbankteam.fbank.dao;

import com.fbankteam.fbank.domain.CardType;
import com.fbankteam.fbank.domain.CardVo;
import com.fbankteam.fbank.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CardDaoImpl implements CardDao {

    @Override
    // 카드 목록 (simple)
    public List<CardVo> getCardList(int userInfoId) {
        List<CardVo> cardList = new ArrayList<CardVo>();
        Connection conn = JDBCUtil.getConnection();

        String sql = "select * from card where user_id=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userInfoId);
            ResultSet rs = pstmt.executeQuery();

//            card id, type, number 출력
            while (rs.next()) {
                CardVo card = new CardVo();
                card.setId(rs.getInt("id"));
                card.setCardType(CardType.valueOf(rs.getString("card_type")));
                card.setCardNumber(String.valueOf(rs.getString("card_number")));

                cardList.add(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cardList;
    }

    // 카드 추가
    @Override
    public void addCard(CardVo card) {
        String sql = "insert into card " + "(user_info_id, account_id,card_name, card_password, card_type, card_number, expiration_date, cvc_hash, monthly_limit, billing_day)"+"values(?,?,?,?,?,?,?,?,?)";
        Connection conn = JDBCUtil.getConnection();

        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, card.getUserInfoId());
            pstmt.setInt(2, card.getAccountId());
            pstmt.setString(3, "");
            pstmt.setString(4, card.getCardPassword());
            pstmt.setString(5, card.getCardType().name());
            pstmt.setString(6, card.getCardNumber());
            pstmt.setString(7,"");
            pstmt.setString(8, card.getCvcHash());
            pstmt.setInt(9, card.getMonthlyLimit());
            pstmt.setInt(10, card.getBillingDay());

            pstmt.executeUpdate();
            System.out.println("카드 개설이 완료되었습니다!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("카드 개설에 에러가 발생하였습니다");
        }
    }

    // 카드 상세 정보
    @Override
    public CardVo getCard(int cardId) {
        CardVo card = null;
        String sql = "select * from card where id=?";
        Connection conn = JDBCUtil.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cardId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                card = new CardVo();
                card.setId(rs.getInt("id"));
                card.setUserInfoId(rs.getInt("user_info_id"));
                card.setAccountId(rs.getInt("account_id"));
                card.setCardNumber(rs.getString("card_number"));
                card.setCardType(CardType.valueOf(rs.getString("card_type")));
                card.setCardPassword(rs.getString("card_password"));
                card.setCvcHash(rs.getString("cvc_hash"));
                card.setMonthlyLimit(rs.getInt("monthly_limit"));
                card.setBillingDay(rs.getInt("billing_day"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return card;
    }

    // 카드 해지
    @Override
    public void deleteCard(int cardId) {
        String sql = "DELETE FROM card WHERE id = ?";
        Connection conn = JDBCUtil.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cardId);
            int row = pstmt.executeUpdate();

            if (row > 0) {
                System.out.println("카드가 정상적으로 해지되었습니다.");
            } else {
                System.out.println("해지할 카드가 존재하지 않습니다.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 카드 비번 변경
    @Override
    public void updateCardPassword(int cardId, String newPassword) {

    }

    // 카드 타입 변경
    @Override
    public void updateCardStatus(int cardId, String cardType) {
        String sql = "update card set card_password=? where id=?";
        Connection conn = JDBCUtil.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cardType);
            pstmt.setInt(2, cardId);
            int row = pstmt.executeUpdate();
            if (row > 0) {
                System.out.println("비밀번호 변경 성공");
            } else {
                System.out.println("비밀번호 변경 실패");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
