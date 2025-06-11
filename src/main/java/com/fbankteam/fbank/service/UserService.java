package com.fbankteam.fbank.service;

import com.fbankteam.fbank.dao.UserAccountDao;
import com.fbankteam.fbank.dao.UserInfoDao;
import com.fbankteam.fbank.domain.UserAccountVO;
import com.fbankteam.fbank.domain.UserInfoVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/*필요 기능들
 * 1. 로그인 기능
 * 2. 계좌번호로 user_info_id 조회
 * 3. 회원정보 인증- 이름 주민번호 전화번호 일치 확인
 * 4. 회원정보 등록- 계좌가 없을시 등록 (이후 계좌 등록으로)
 * 5. 아이디 중복 체크
 * 6. 회원 계정 등록(id,password)
 * */
public class UserService {

    private final UserInfoDao userInfoDao;
    private final UserAccountDao userAccountDao;
    private final Connection conn;

    public UserService(Connection conn, UserInfoDao userInfoDao, UserAccountDao userAccountDao) {
        this.conn = conn;
        this.userInfoDao = userInfoDao;
        this.userAccountDao = userAccountDao;
    }

    // 로그인
    public Optional<UserAccountVO> login(String loginId, String password) {
        String sql = "SELECT login_password FROM user_account WHERE login_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, loginId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String dbPassword = rs.getString("login_password");
                    if (dbPassword.equals(password)) {
                        return userAccountDao.getList().stream()
                                .filter(acc -> loginId.equals(acc.getLoginId()))
                                .findFirst();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    // 2.계좌번호로 조회
    public Optional<Integer> findUserInfoIdByAccountNumber(String accountNumber) {
        String sql = "SELECT user_info_id FROM bank_account WHERE account_number = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(rs.getInt("user_info_id"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    // 3. 개인정보 인증
    public Optional<UserInfoVO> authenticateUserInfo(String name, String rrn, String phoneNumber) {
        String sql = "SELECT * FROM user_info WHERE user_name = ? AND rrn = ? AND phone_number = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, rrn);
            stmt.setString(3, phoneNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    UserInfoVO vo = UserInfoVO.builder()
                            .id(rs.getInt("id"))
                            .userName(rs.getString("user_name"))
                            .rrn(rs.getString("rrn"))
                            .phoneNumber(rs.getString("phone_number"))
                            .build();
                    return Optional.of(vo);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    // 4. 신규 회원정보 등록
    public int registerUserInfo(UserInfoVO userInfo) {
        return userInfoDao.create(userInfo);
    }

    // 5. 아이디 중복 체크
    public boolean isLoginIdAvailable(String loginId) {
        String sql = "SELECT COUNT(*) FROM user_account WHERE login_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, loginId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    // 6. 회원 계정 등록
    public int registerUserAccount(UserAccountVO userAccount) {
        return userAccountDao.create(userAccount);
    }
}
