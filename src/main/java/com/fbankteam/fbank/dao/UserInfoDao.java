package com.fbankteam.fbank.dao;
import com.fbankteam.fbank.domain.UserInfoVO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public interface UserInfoDao {
    int create(UserInfoVO info) throws SQLException;
    List<UserInfoVO> getList() throws SQLException;
    Optional<UserInfoVO> get(int id) throws SQLException;
    int update(UserInfoVO info) throws SQLException;
    int delete(int id) throws SQLException;


}
