package com.fbankteam.fbank.dao;

import com.fbankteam.fbank.domain.AccountTransactionsVO;
import com.fbankteam.fbank.domain.BankAccountVO;
import com.fbankteam.fbank.domain.TransactionType;
import com.fbankteam.fbank.util.JDBCUtil;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountTranscationsDAOImplTest {

    static AccountTranscationsDAOImpl dao;
    @BeforeAll
    static void setUp() {
        dao = new AccountTranscationsDAOImpl(JDBCUtil.getConnection());
    }

    @AfterAll
    static void tearDown() {
        JDBCUtil.close();
    }

    @Test
    @DisplayName("유저id로 최근 거래내역 5개")
    void findAccountTransactionsByUserIdOrderByDateDescLimit5() {
        List<BankAccountVO> datas = dao.findAccountTransactionsByUserIdOrderByDateDescLimit5(2);
        datas.forEach(System.out::println);
    }

    @Test
    @DisplayName("특정 기간 거래내역 확인")
    void findAccountTransactionsByIdAndDate() {
        List<AccountTransactionsVO> datas = dao.findAccountTransactionsByAccountIdAndDate(2, LocalDateTime.of(2025,5,1,0,0,0), LocalDateTime.of(2025,5,30,0,0,0));
        datas.forEach(System.out::println);
    }

    @Test
    @DisplayName("거래 내역 추가")
    void insertAccountTranscation() {
        AccountTransactionsVO data = AccountTransactionsVO
                .builder()
                .accountId(2)
                .transactionType(TransactionType.TRANSFER)
                .transactionDate(LocalDateTime.now())
                .amount(500000)
                .receiverName("고창진")
                .receiverBankName("카카오뱅크")
                .toAccountNumber("333-3333-3333")
                .build();

        int count = dao.insertAccountTranscation(data);

        Assertions.assertEquals(1, count);
    }
}