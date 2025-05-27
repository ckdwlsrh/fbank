package com.fbankteam.fbank.dao;

import com.fbankteam.fbank.domain.UserAccountVO;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserAccountDaoImpl implements UserAccountDao {

    private final Connection conn;

    private UserAccountVO map(ResultSet rs) throws SQLException {
        return UserAccountVO.builder()
                .id(rs.getInt("id"))
                .userInfoId(rs.getInt("user_info_id"))
                .loginId(rs.getString("login_id"))
                .loginPassword(rs.getString("login_password"))
                .build();
    }

    @Override
    public int create(UserAccountVO vo) {
        String sql = "INSERT INTO user_account (user_info_id, login_id, login_password) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, vo.getUserInfoId());
            stmt.setString(2, vo.getLoginId());
            stmt.setString(3, vo.getLoginPassword());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserAccountVO> getList() {
        String sql = "SELECT * FROM user_account";
        List<UserAccountVO> list = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Optional<UserAccountVO> get(int id) {
        String sql = "SELECT * FROM user_account WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return Optional.of(map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public int update(UserAccountVO vo) {
        String sql = "UPDATE user_account SET login_id = ?, login_password = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, vo.getLoginId());
            stmt.setString(2, vo.getLoginPassword());
            stmt.setInt(3, vo.getId());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM user_account WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
