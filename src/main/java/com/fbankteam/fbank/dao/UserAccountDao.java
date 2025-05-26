package com.fbankteam.fbank.dao;

import com.fbankteam.fbank.domain.UserAccountVO;
import java.util.List;
import java.util.Optional;

public interface UserAccountDao {
    int create(UserAccountVO account);
    List<UserAccountVO> getList();
    Optional<UserAccountVO> get(int id);
    int update(UserAccountVO account);
    int delete(int id);
}