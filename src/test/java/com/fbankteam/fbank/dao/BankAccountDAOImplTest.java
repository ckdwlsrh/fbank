package com.fbankteam.fbank.dao;

import com.fbankteam.fbank.domain.AccountType;
import com.fbankteam.fbank.domain.BankAccountVO;
import com.fbankteam.fbank.util.JDBCUtil;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountDAOImplTest {

    static BankAccountDAOImpl dao;

    @BeforeAll
    static void setUp() {
        dao = new BankAccountDAOImpl(JDBCUtil.getConnection());
    }

    @AfterAll
    static void tearDown() {
        JDBCUtil.close();
    }

    @Test
    @Order(1)
    @DisplayName("계좌 등록 테스트")
    void insertBankAccount() {
        BankAccountVO account = BankAccountVO.builder()
                .userInfoId(1)
                .accountNumber("999-8888-7779")
                .accountPassword("abcd1234")
                .balance(10000)
                .accountType(AccountType.SAVINGS)
                .createAt(LocalDateTime.now())
                .isFavorite(false)
                .build();

        int result = dao.insertBankAccount(account);

        assertEquals(1, result);
    }

    @Test
    @Order(2)
    @DisplayName("계좌번호로 userInfoId 조회 테스트")
    void findUserInfoIdByAccountNumber() {
        String accountNumber = "123-456-789";
        int userInfoId = dao.findUserInfoIdByAccountNumber(accountNumber);

        assertEquals(1, userInfoId);
    }

    @Test
    @Order(3)
    @DisplayName("즐겨찾기 토글 테스트")
    void toggleFavorite() {
        // 테스트용 계좌 생성
        BankAccountVO account = BankAccountVO.builder()
                .userInfoId(2)
                .accountNumber("555-1111-2223")
                .accountPassword("pass123")
                .balance(2000)
                .accountType(AccountType.SAVINGS)
                .createAt(LocalDateTime.now())
                .isFavorite(false)
                .build();
        dao.insertBankAccount(account);

        // 정확한 accountId 조회 (계좌번호로)
        int accountId = dao.findAccountsByUserInfoId(2).stream()
                .filter(acc -> "555-1111-2223".equals(acc.getAccountNumber()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("계좌를 찾을 수 없습니다."))
                .getId();

        // 즐겨찾기 토글
        int updateCount = dao.toggleFavorite(accountId, 2);
        assertEquals(1, updateCount);

        // 변경 확인
        BankAccountVO updated = dao.findAccountsByUserInfoId(2).stream()
                .filter(acc -> acc.getId() == accountId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("업데이트된 계좌를 찾을 수 없습니다."));

        assertTrue(updated.getIsFavorite());
    }


    @Test
    @Order(4)
    @DisplayName("전체 계좌 조회 테스트")
    void findAccountsByUserInfoId() {
        List<BankAccountVO> list = dao.findAccountsByUserInfoId(1);
        assertNotNull(list);
        list.forEach(System.out::println);
    }

    @Test
    @Order(5)
    @DisplayName("최근 5개 계좌 조회 테스트")
    void findTop5AccountsByUserInfoId() {
        List<BankAccountVO> list = dao.findTop5AccountsByUserInfoId(1);
        assertTrue(list.size() <= 5);
        list.forEach(System.out::println);
    }

    @Test
    @Order(6)
    @DisplayName("즐겨찾기 계좌 조회 테스트")
    void findFavoriteAccountsByUserInfoId() {
        List<BankAccountVO> list = dao.findFavoriteAccountsByUserInfoId(2);
        list.forEach(System.out::println);
    }
}
