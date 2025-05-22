package com.fbankteam.fbank.dao;

import com.fbankteam.fbank.domain.AccountTransactionsVO;

import java.time.LocalDateTime;
import java.util.List;

public interface AccountTransactionsDAO {
    //최근 거래 내역 5개

    List<AccountTransactionsVO> findAccountTransactionsByIdOrderByDateDescLimit5(int id);

    // 특정 기간 거래 내역
    List<AccountTransactionsVO> findAccountTransactionsByIdAndDate(int id, LocalDateTime start, LocalDateTime end);

    //거래내역 추가
    int insertAccountTranscation(AccountTransactionsVO accountTransactionsVO);

}