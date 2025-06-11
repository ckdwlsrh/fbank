package com.fbankteam.fbank.dao;

import com.fbankteam.fbank.domain.CardType;
import com.fbankteam.fbank.domain.CardVo;
import com.fbankteam.fbank.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CardDaoImpl implements CardDao {

    // 카드 목록 (simple)
    @Override
    public List<CardVo> getCardList(int userInfoId) {
        List<CardVo> cardList = new ArrayList<>();
        String sql = "SELECT * FROM card WHERE user_id=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userInfoId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                cardList.add(mapResultSetToCardVo(rs)); // ☑ map 메소드로 변환
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cardList;
    }

    // 카드 추가
    @Override
    public boolean addCard(CardVo card) {
        String sql = "INSERT INTO card " +
                "(user_info_id, account_id, card_name, card_password, card_type, card_number, expiration_date, cvc_hash, monthly_limit, billing_day) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, card.getUserInfoId());
            pstmt.setInt(2, card.getAccountId());
            pstmt.setString(3, "");
            pstmt.setString(4, card.getCardPassword());
            pstmt.setString(5, card.getCardType().name());
            pstmt.setString(6, card.getCardNumber());
            pstmt.setString(7, "");
            pstmt.setString(8, card.getCvcHash());
            pstmt.setInt(9, card.getMonthlyLimit());
            pstmt.setInt(10, card.getBillingDay());

            return pstmt.executeUpdate() > 0; // ☑ 성공 여부 반환
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 카드 상세 정보
    @Override
    public Optional<CardVo> getCard(int cardId) {
        String sql = "SELECT * FROM card WHERE id=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, cardId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToCardVo(rs)); // ☑ Optional 반환
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    // 카드 해지
    @Override
    public boolean deleteCard(int cardId) {
        String sql = "DELETE FROM card WHERE id=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, cardId);
            return pstmt.executeUpdate() > 0; // ☑ 성공 여부 반환
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 카드 비번 변경
    @Override
    public boolean updateCardPassword(int cardId, String newPassword) {
        String sql = "UPDATE card SET card_password=? WHERE id=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newPassword);
            pstmt.setInt(2, cardId);
            return pstmt.executeUpdate() > 0; // ☑ 성공 여부 반환
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 카드 타입 변경
    @Override
    public boolean updateCardStatus(int cardId, String cardType) {
        String sql = "UPDATE card SET card_type=? WHERE id=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cardType);
            pstmt.setInt(2, cardId);
            return pstmt.executeUpdate() > 0; // ☑ 성공 여부 반환
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // ☑ 카드 ResultSet → CardVo 변환 메소드 추가
    private CardVo mapResultSetToCardVo(ResultSet rs) throws SQLException {
        CardVo card = new CardVo();
        card.setId(rs.getInt("id"));
        card.setUserInfoId(rs.getInt("user_info_id"));
        card.setAccountId(rs.getInt("account_id"));
        card.setCardPassword(rs.getString("card_password"));
        card.setCardType(CardType.valueOf(rs.getString("card_type")));
        card.setCardNumber(rs.getString("card_number"));
        card.setCvcHash(rs.getString("cvc_hash"));
        card.setMonthlyLimit(rs.getInt("monthly_limit"));
        card.setBillingDay(rs.getInt("billing_day"));
        return card;
    }
}
