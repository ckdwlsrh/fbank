package com.fbankteam.fbank.dao;

import com.fbankteam.fbank.domain.UserAccountVO;
import com.fbankteam.fbank.util.JDBCUtil;
import org.junit.jupiter.api.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserAccountDaoImplTest {

    static UserAccountDaoImpl dao;
    static int createdId; // 생성된 user_account id 저장용

    @BeforeAll
    static void setUp() throws SQLException {
        // 테스트용 user_account 중복 데이터 삭제 (login_id 기준)
        String deleteSql = "DELETE FROM user_account WHERE login_id = ?";
        try (PreparedStatement stmt = JDBCUtil.getConnection().prepareStatement(deleteSql)) {
            stmt.setString(1, "testlogin123");
            stmt.executeUpdate();
        }

        dao = new UserAccountDaoImpl(JDBCUtil.getConnection());
    }

    @AfterAll
    static void tearDown() {
        JDBCUtil.close();
    }

    @Test
    @Order(1)
    @DisplayName("회원 계정 생성")// Create
    void testCreate() {
        // user_info_id는 실제 DB에 있는 값을 넣어야 함
        UserAccountVO vo = UserAccountVO.builder()
                .userInfoId(1)
                .loginId("testlogin123")
                .loginPassword("testpass123")
                .build();

        int result = dao.create(vo);
        assertEquals(1, result);
    }

    @Test
    @Order(2)
    @DisplayName("회원 계정 목록 조회")// Read
    void testGetList() {
        List<UserAccountVO> list = dao.getList();
        assertFalse(list.isEmpty());

        Optional<UserAccountVO> found = list.stream()
                .filter(u -> "testlogin123".equals(u.getLoginId()))
                .findFirst();

        assertTrue(found.isPresent());
        createdId = found.get().getId();
    }

    @Test
    @Order(3)
    @DisplayName("회원 계정 단건 조회")// Read
    void testGet() {
        Optional<UserAccountVO> voOpt = dao.get(createdId);
        assertTrue(voOpt.isPresent());
        UserAccountVO vo = voOpt.get();
        assertEquals("testlogin123", vo.getLoginId());
        System.out.println(vo);
    }

    @Test
    @Order(4)
    @DisplayName("회원 계정 수정")// Update
    void testUpdate() {
        UserAccountVO vo = UserAccountVO.builder()
                .id(createdId)
                .userInfoId(1)
                .loginId("testlogin456")
                .loginPassword("testpass456")
                .build();

        int result = dao.update(vo);
        assertEquals(1, result);

        Optional<UserAccountVO> updated = dao.get(createdId);
        assertTrue(updated.isPresent());
        assertEquals("testlogin456", updated.get().getLoginId());
        assertEquals("testpass456", updated.get().getLoginPassword());
    }

    @Test
    @Order(5)
    @DisplayName("회원 계정 삭제")// Delete
    void testDelete() {
        int result = dao.delete(createdId);
        assertEquals(1, result);

        Optional<UserAccountVO> deleted = dao.get(createdId);
        assertTrue(deleted.isEmpty());
    }
}
