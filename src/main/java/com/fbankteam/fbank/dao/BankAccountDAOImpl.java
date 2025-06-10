package com.fbankteam.fbank.dao;

import com.fbankteam.fbank.domain.BankAccountVO;
import com.fbankteam.fbank.domain.AccountType;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class BankAccountDAOImpl implements BankAccountDAO{
    private final Connection conn;

    // 1. 계좌 등록
    @Override
    public int insertBankAccount(BankAccountVO bankAccount) {
        String sql = """
                INSERT INTO bank_account (user_info_id, account_number, account_password, balance, account_type,is_favorite
                ) VALUES (?,?,?,?,?,?)
                """;

        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, bankAccount.getUserInfoId());
            pstmt.setString(2, bankAccount.getAccountNumber());
            pstmt.setString(3, bankAccount.getAccountPassword());
            pstmt.setInt(4, bankAccount.getBalance());
            pstmt.setString(5, bankAccount.getAccountType().name());
            pstmt.setBoolean(6, bankAccount.getIsFavorite());

            return pstmt.executeUpdate(); // 성공 시 1 반환
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    // 2. 계좌번호로 회원 정보 ID 조회
    @Override
    public int findUserInfoIdByAccountNumber(String accountNumber){
        String sql = "SELECT user_info_id FROM bank_account WHERE account_number = ?";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return rs.getInt("user_info_id");
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return -1; // 조회 실패 시 -1 반환
    }

    // 3. 최근 5개 계좌 조회 (즐겨찾기 우선, 잔액 내림차순 정렬)
    public List<BankAccountVO> findTop5AccountsByUserInfoId(int userInfoId) {
        String sql = """
                SELECT account_type, account_number, balance
                FROM bank_account
                WHERE user_info_id = ?
                ORDER BY is_favorite DESC, balance DESC
                LIMIT 5
                """;
        List<BankAccountVO> accounts = new ArrayList<>();
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, userInfoId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                //accounts.add(map(rs));

                BankAccountVO account = BankAccountVO.builder()
                        .accountType(AccountType.valueOf(rs.getString("account_type")))
                        .accountNumber(rs.getString("account_number"))
                        .balance(rs.getInt("balance"))
                        .build();

                accounts.add(account);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return accounts;
    }

    // 4. 내 계좌 전체 조회
    @Override
    public List<BankAccountVO> findAccountsByUserInfoId(int userInfoId) {
        String sql = """
                SELECT id, account_number, account_type, balance, create_at, is_favorite
                FROM bank_account
                WHERE user_info_id = ?
                ORDER BY is_favorite DESC, balance DESC             
                """;
        List<BankAccountVO> accounts = new ArrayList<>();
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, userInfoId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                //accounts.add(map(rs));
                BankAccountVO account = BankAccountVO.builder()
                        .id(rs.getInt("id"))
                        .accountNumber(rs.getString("account_number"))
                        .accountType(AccountType.valueOf(rs.getString("account_type")))
                        .balance(rs.getInt("balance"))
                        .createAt(rs.getTimestamp("create_at").toLocalDateTime())
                        .isFavorite(rs.getBoolean("is_favorite"))
                        .build();

                accounts.add(account);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return accounts;
    }

    // 5. 즐겨찾기 계좌 목록 조회
    @Override
    public List<BankAccountVO> findFavoriteAccountsByUserInfoId(int userInfoId) {
        String sql = """
                SELECT id, account_number, is_favorite
                FROM bank_account
                WHERE user_info_id = ?
                ORDER BY is_favorite DESC, balance DESC
                """;
        List<BankAccountVO> accounts = new ArrayList<>();
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, userInfoId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                BankAccountVO account = BankAccountVO.builder()
                        .id(rs.getInt("id"))
                        .accountNumber(rs.getString("account_number"))
                        .isFavorite(rs.getBoolean("is_favorite"))
                        .build();

                accounts.add(account);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return accounts;
    }

    // 6. 즐겨찾기 토글
    @Override
    public int toggleFavorite(int accountId, int userInfoId) {
        String sql = """
                UPDATE bank_account
                SET is_favorite = NOT is_favorite
                WHERE id = ? AND user_info_id = ?
                """;
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, accountId);
            pstmt.setInt(2, userInfoId);
            return pstmt.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
