package com.fbankteam.fbank.dao;

import com.fbankteam.fbank.domain.UserAccountVO;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserAccountDao {
    int create(UserAccountVO account) throws SQLException;
    List<UserAccountVO> getList() throws SQLException;
    Optional<UserAccountVO> get(int id) throws SQLException;
    int update(UserAccountVO account) throws SQLException;
    int delete(int id) throws SQLException;
}
