package com.fbankteam.fbank.dao;

import com.fbankteam.fbank.domain.UserInfoVO;
import com.fbankteam.fbank.util.JDBCUtil;
import org.junit.jupiter.api.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserInfoDaoImplTest {

    static UserInfoDaoImpl dao;
    static int createdId; // 생성된 데이터 id 저장용

    @BeforeAll
    static void setUp() throws SQLException {
        // 기존 테스트 유저 삭제 (중복 방지)
        String deleteSql = "DELETE FROM user_info WHERE rrn = ? OR user_name = ?";
        try (PreparedStatement stmt = JDBCUtil.getConnection().prepareStatement(deleteSql)) {
            stmt.setString(1, "990101-1234567");
            stmt.setString(2, "테스트유저");
            stmt.executeUpdate();
        }

        dao = new UserInfoDaoImpl(JDBCUtil.getConnection());
    }

    @AfterAll
    static void tearDown() {
        JDBCUtil.close();
    }

    @Test
    @Order(1)
    @DisplayName("회원 정보 등록")// Create
    void testCreate() {
        UserInfoVO vo = UserInfoVO.builder()
                .userName("테스트유저")
                .rrn("990101-1234567")
                .phoneNumber("010-9999-9999")
                .build();

        int result = dao.create(vo);
        assertEquals(1, result);
    }

    @Test
    @Order(2)
    @DisplayName("회원 정보 전체 조회")// Read
    void testGetList() {
        List<UserInfoVO> list = dao.getList();
        assertFalse(list.isEmpty());
        list.forEach(System.out::println);

        Optional<UserInfoVO> found = list.stream()
                .filter(u -> "테스트유저".equals(u.getUserName()))
                .findFirst();
        assertTrue(found.isPresent());

        createdId = found.get().getId();
    }

    @Test
    @Order(3)
    @DisplayName("회원 정보 단건 조회")// Read
    void testGet() {
        Optional<UserInfoVO> voOpt = dao.get(createdId);
        assertTrue(voOpt.isPresent());
        UserInfoVO vo = voOpt.get();
        assertEquals("테스트유저", vo.getUserName());
        System.out.println(vo);
    }

    @Test
    @Order(4)
    @DisplayName("회원 정보 수정")// Update
    void testUpdate() {
        UserInfoVO vo = UserInfoVO.builder()
                .id(createdId)
                .userName("테스트유저_수정")
                .rrn("990101-1234567")
                .phoneNumber("010-8888-8888")
                .build();

        int result = dao.update(vo);
        assertEquals(1, result);

        Optional<UserInfoVO> updated = dao.get(createdId);
        assertTrue(updated.isPresent());
        assertEquals("테스트유저_수정", updated.get().getUserName());
        assertEquals("010-8888-8888", updated.get().getPhoneNumber());
    }

    @Test
    @Order(5)
    @DisplayName("회원 정보 삭제")// Delete
    void testDelete() {
        int result = dao.delete(createdId);
        assertEquals(1, result);

        Optional<UserInfoVO> deleted = dao.get(createdId);
        assertTrue(deleted.isEmpty());
    }
}
