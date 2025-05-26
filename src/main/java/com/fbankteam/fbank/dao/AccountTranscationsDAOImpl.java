package com.fbankteam.fbank.dao;

import com.fbankteam.fbank.domain.AccountTransactionsVO;
import com.fbankteam.fbank.domain.TransactionType;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class AccountTranscationsDAOImpl implements AccountTransactionsDAO{

    private final Connection conn;

    private AccountTransactionsVO map(ResultSet rs) throws SQLException {
        AccountTransactionsVO data = AccountTransactionsVO.builder()
                .id(rs.getInt("id"))
                .accountId(rs.getInt("account_id"))
                .transactionType(TransactionType.valueOf(rs.getString("transaction_type")))
                .transactionDate(rs.getObject("transaction_date", LocalDateTime.class))
                .amount(rs.getInt("amount"))
                .toAccountNumber(rs.getString("to_account_number"))
                .receiverBankName(rs.getString("receiver_bank_name"))
                .receiverName("receiver_name")
                .build();
        return data;
    }

    //TODO: 계좌와 계좌 거래 내역 조인 필요함!
    @Override
    public List<AccountTransactionsVO> findAccountTransactionsByAccountIdOrderByDateDescLimit5(int id) {
        String sql = "";
        try(PreparedStatement p = conn.prepareStatement(sql)) {

        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return List.of();
    }

    @Override
    public List<AccountTransactionsVO> findAccountTransactionsByAccountIdAndDate(int accountId, LocalDateTime start, LocalDateTime end) {
        String sql = "select * from account_transactions " +
                     "where account_id = ? and " +
                     "transaction_date between ? and ?" +
                     "order by transaction_date desc";
        List<AccountTransactionsVO> result = new ArrayList<>();
        try(PreparedStatement p = conn.prepareStatement(sql)) {
            p.setInt(1, accountId);
            p.setObject(2, start);
            p.setObject(3, end);
            try(ResultSet rs = p.executeQuery()) {
                while(rs.next()) {
                    result.add(map(rs));
                }
            }
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int insertAccountTranscation(AccountTransactionsVO accountTransactionsVO) {
        String sql = "INSERT INTO account_transactions" +
                     "(account_id, transaction_type, transaction_date, amount, to_account_number, receiver_bank_name, receiver_name) " +
                     "VALUES(?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement p = conn.prepareStatement(sql)) {
            p.setInt(1, accountTransactionsVO.getAccountId());
            p.setString(2, accountTransactionsVO.getTransactionType().name());
            p.setObject(3, accountTransactionsVO.getTransactionDate());
            p.setInt(4, accountTransactionsVO.getAmount());
            p.setString(5, accountTransactionsVO.getToAccountNumber());
            p.setString(6, accountTransactionsVO.getReceiverBankName());
            p.setString(7, accountTransactionsVO.getReceiverName());

            int count = p.executeUpdate();

            return count;
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
