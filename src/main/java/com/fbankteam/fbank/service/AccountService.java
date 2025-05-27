package com.fbankteam.fbank.service;

import com.fbankteam.fbank.dao.AccountTransactionsDAO;
import com.fbankteam.fbank.dao.AccountTranscationsDAOImpl;
import com.fbankteam.fbank.dao.BankAccountDAO;
import com.fbankteam.fbank.domain.AccountTransactionsVO;
import com.fbankteam.fbank.domain.BankAccountVO;
import com.fbankteam.fbank.domain.TransactionType;
import com.fbankteam.fbank.util.JDBCUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class AccountService {
    private final AccountTransactionsDAO accountTranscationsDAO = new AccountTranscationsDAOImpl(JDBCUtil.getConnection());
    //TODO: impl연결 필요
    //private final BankAccountDAO bankAccountDAO = new BankAccountDAO();

    //1. 계좌 개설
    public boolean create(int userId) {
        return false;
    }
    //2. 계좌 거래 내역 추가 및 계좌 잔액 수정
    public boolean createTransaction(int accountId, int amount, TransactionType transactionType, String receiverName, String receiverBankName, String toAccountNumber) {
        Connection conn = null;
        try {
            conn = JDBCUtil.getConnection();
            conn.setAutoCommit(false);
            AccountTransactionsVO data = AccountTransactionsVO
                    .builder()
                    .accountId(accountId)
                    .transactionType(transactionType)
                    .transactionDate(LocalDateTime.now())
                    .amount(amount)
                    .receiverName(receiverName)
                    .receiverBankName(receiverBankName)
                    .toAccountNumber(toAccountNumber)
                    .build();


            int inserted = accountTranscationsDAO.insertAccountTranscation(data);
            //TODO : 잔액변경
            //int updated = bankAccountDAO.updateBalance(accountId, amount);

            if (inserted == 1 ){//&& updated == 1) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }

        }
        catch(SQLException e) {
            try {
                if (conn != null) conn.rollback(); // 예외 발생 시 롤백
            } catch (SQLException se) {
                se.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
        finally {
            try {
                if (conn != null) conn.setAutoCommit(true); // 원상 복구
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //3. 최근 5개 계좌 조회
    public List<BankAccountVO> getTransactionsTop5(int userInfoId) {
        return accountTranscationsDAO.findAccountTransactionsByUserIdOrderByDateDescLimit5(userInfoId);
    }
    //TODO : 4. 내 계좌 가져오기
//    public List<BankAccountVO> getBankAccounts(int userInfoId) {
//        return ;
//    }

    //TODO : 5. 내 계좌 즐겨찾기 및 잔액 순으로 가져오기
//    public List<BankAccountVO> getBankAccounts(int userInfoId) {
//
//    }

    //6. 계좌id로 거래이력 1달치 가져오기
    public List<AccountTransactionsVO> getTransactionsOfPastMonth(int accountId) {
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = end.minusMonths(1);
        return accountTranscationsDAO.findAccountTransactionsByAccountIdAndDate(accountId, start, end);
    }

    //TODO: 7. 즐겨찾기 업데이트
//    public boolean updateFavorites(int accountId) {
//
//    }

    //8. 거래이력 start, end로 찾기
    public List<AccountTransactionsVO> getTransactionsByDate(int accountId, LocalDateTime start, LocalDateTime end) {
        return accountTranscationsDAO.findAccountTransactionsByAccountIdAndDate(accountId, start, end);
    }

    //TODO : 9.계좌가 존재하는지 확인
//    public boolean isValidBankAccountNumber(String accountNumber) {
//
//    }
}
