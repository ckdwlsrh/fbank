package com.fbankteam.fbank.dao;

import com.fbankteam.fbank.domain.CardTransactionsVO;
import com.fbankteam.fbank.domain.CardType;
import com.fbankteam.fbank.domain.CardVo;
import com.fbankteam.fbank.util.JDBCUtil;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CardTransactionsDAOImpl implements CardTransactionsDAO {
    static Connection conn=JDBCUtil.getConnection();
    @Override
    public List<CardVo> findCardTransactionsByCardIdOrderByDateDescLimit5(int userInfoId){
        String sql= """
                SELECT a.id as card_id, a.card_number as card_number, a.card_type as card_type, b.id as id, b.amount as amount, b.transaction_date as transaction_date
                FROM card a JOIN card_transactions b ON a.id = b.card_id 
                WHERE a.user_info_id = ? 
                ORDER BY b.transaction_date DESC LIMIT 5
                """;
        List<CardVo> cardVOList = new ArrayList<>();
        try(PreparedStatement pstmt=conn.prepareStatement(sql)){
            pstmt.setInt(1, userInfoId);
            try(ResultSet rs=pstmt.executeQuery()){
                while(rs.next()){
                    CardTransactionsVO cardTransaction = map(rs);
                    CardVo cardVO= CardVo.builder()
                            .id(rs.getInt("card_id"))
                            .cardNumber(rs.getString("card_number"))
                            .cardType(CardType.valueOf(rs.getString("card_type")))
                            .cardTransaction(cardTransaction).build();
                    cardVOList.add(cardVO);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cardVOList;
    }
    
    @Override
    public int getMontlyTotalAmount(int cardID) {
        String sql = "SELECT SUM(amount) FROM card_transactions WHERE card_id = ? and transaction_date BETWEEN ? and ?";
        LocalDateTime start = LocalDate.now().withDayOfMonth(1).atTime(0, 0);
        LocalDateTime end = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).atTime(23, 59, 59);
        int sum=0;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cardID);
            pstmt.setTimestamp(2, Timestamp.valueOf(start));
            pstmt.setTimestamp(3, Timestamp.valueOf(end));
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    sum+= rs.getInt(1);

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sum;
    }

    private CardTransactionsVO map(ResultSet rs) throws SQLException {
        return CardTransactionsVO.builder()
                .id(rs.getInt("id"))
                .card_id(rs.getInt("card_id"))
                .amount(rs.getInt("amount"))
                .transaction_date(rs.getTimestamp("transaction_date").toLocalDateTime())
                .build();

    }
    @Override
    public List<CardTransactionsVO> findTop10RecentCardTransactions(int cardId) {
        List<CardTransactionsVO> list = new ArrayList<>();
        String sql="SELECT * FROM card_transactions WHERE card_id = ? ORDER BY transaction_date DESC LIMIT 10";
        try(PreparedStatement pstmt= conn.prepareStatement(sql)){
            pstmt.setInt(1, cardId);
            try(ResultSet rs=pstmt.executeQuery()){
                while(rs.next()){
                    list.add(map(rs));
                }
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<CardTransactionsVO> findCardTransactionsByCardIdAndPeriod(int cardId, LocalDateTime start, LocalDateTime end) {
        List<CardTransactionsVO> list = new ArrayList<>();
        String sql="SELECT * FROM card_transactions WHERE card_id = ? and transaction_date BETWEEN ? and ?";
        try(PreparedStatement pstmt=conn.prepareStatement(sql)){
            pstmt.setInt(1, cardId);
            pstmt.setTimestamp(2, Timestamp.valueOf(start));
            pstmt.setTimestamp(3, Timestamp.valueOf(end));
            try(ResultSet rs=pstmt.executeQuery()){
                while(rs.next()){
                    list.add(map(rs));
                }
            }
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public int insertCardTransaction(CardTransactionsVO cardTransactionsVO) {
        String sql="INSERT INTO card_transactions (card_id, amount, transaction_date) VALUES (?, ?, ?)";
        try(PreparedStatement pstmt=conn.prepareStatement(sql)){
            pstmt.setInt(1, cardTransactionsVO.getCard_id());
            pstmt.setInt(2, cardTransactionsVO.getAmount());
            pstmt.setTimestamp(3, Timestamp.valueOf(cardTransactionsVO.getTransaction_date()));

            int count=pstmt.executeUpdate();

            return count;
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}

