package com.fbankteam.fbank.dao;

import com.fbankteam.fbank.domain.UserInfoVO;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserInfoDaoImpl implements UserInfoDao {

    private final Connection conn;

    private UserInfoVO map(ResultSet rs) throws SQLException {
        return UserInfoVO.builder()
                .id(rs.getInt("id"))
                .userName(rs.getString("user_name"))
                .rrn(rs.getString("rrn"))
                .phoneNumber(rs.getString("phone_number"))
                .build();
    }

    @Override
    public int create(UserInfoVO vo) {
        String sql = "INSERT INTO user_info (user_name, rrn, phone_number) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, vo.getUserName());
            stmt.setString(2, vo.getRrn());
            stmt.setString(3, vo.getPhoneNumber());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserInfoVO> getList() {
        String sql = "SELECT * FROM user_info";
        List<UserInfoVO> list = new ArrayList<>();
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
    public Optional<UserInfoVO> get(int id) {
        String sql = "SELECT * FROM user_info WHERE id = ?";
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
    public int update(UserInfoVO vo) {
        String sql = "UPDATE user_info SET user_name = ?, rrn = ?, phone_number = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, vo.getUserName());
            stmt.setString(2, vo.getRrn());
            stmt.setString(3, vo.getPhoneNumber());
            stmt.setInt(4, vo.getId());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM user_info WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
