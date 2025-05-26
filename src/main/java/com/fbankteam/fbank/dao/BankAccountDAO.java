package com.fbankteam.fbank.dao;

import com.fbankteam.fbank.domain.BankAccountVO;

import java.util.List;

public interface BankAccountDAO {
    // 계좌 등록
    int insertBankAccount(BankAccountVO bankAccount);

    // 계좌번호로 회원 정보 id 조회
    int findUserInfoIdByAccountNumber(String accountNumber);

    // 최근 5개 계좌 조회
    List<BankAccountVO> findTop5AccountsByUserInfoId(int userInfoId);

    // 내 계좌 전체 조회
    List<BankAccountVO> findAccountsByUserInfoId(int userInfoId);

    // 즐겨찾기 계좌 목록 조회
    List<BankAccountVO> findFavoriteAccountsByUserInfoId(int userInfoId);

    // 즐겨찾기 토글
    int toggleFavorite(int accountId, int userInfoId);
}
