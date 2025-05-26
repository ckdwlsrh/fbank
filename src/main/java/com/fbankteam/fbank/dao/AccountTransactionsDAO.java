package com.fbankteam.fbank.dao;

import com.fbankteam.fbank.domain.AccountTransactionsVO;
import com.fbankteam.fbank.domain.BankAccountVO;

import java.time.LocalDateTime;
import java.util.List;

public interface AccountTransactionsDAO {
    //최근 거래 내역 5개

    List<BankAccountVO> findAccountTransactionsByUserIdOrderByDateDescLimit5(int userInfoId);

    // 특정 기간 거래 내역
    List<AccountTransactionsVO> findAccountTransactionsByAccountIdAndDate(int accountId, LocalDateTime start, LocalDateTime end);

    //거래내역 추가
    int insertAccountTranscation(AccountTransactionsVO accountTransactionsVO);

}