package com.fbankteam.fbank.dao;

import com.fbankteam.fbank.domain.UserInfoVO;
import java.util.List;
import java.util.Optional;

public interface UserInfoDao {
    int create(UserInfoVO info);
    List<UserInfoVO> getList();
    Optional<UserInfoVO> get(int id);
    int update(UserInfoVO info);
    int delete(int id);
}