package springdb.jdbc.repository;

import lombok.extern.slf4j.Slf4j;
import springdb.jdbc.connection.DBConnectionUtil;
import springdb.jdbc.domain.Member;

import java.io.*;
import java.sql.*;
import java.time.Duration;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * JDBC를 통해 회원 객체를 데이터베이스에 저장하는 Repository 클래스
 * JDBC - DriverManager를 사용한다.
 */
@Slf4j
public class MemberRepositoryV0 {
    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values(?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null; // Statement를 상속받아 파라미터를 바인딩할 수 있게 도와준다.

        /**
         * PreparedStatement는 SQL Injection을 예방할 수 있다.
         * 예를들어 String sql = "insert into member(member_id, money) values ("+ memberId +", "+ money +");
         * 와 같은 쿼리가 구성되어 있고 memberId에 대한 파라미터가 String memberId = "select * from ...";
         * 와 같은 문자열이 전달된다고 할때, 문자열 결합에서는 해당 문자열이 SQL로 치환된다.
         * 자칫 잘못하면 프로그램 로직이 들어올 수도 있다..
         * 하지만 파라미터 바인딩시에는 해당 문자열이 단순히 데이터로 취급되어버리기 때문에 안전해진다.
         */
        try { // prepareStatement() 메소드에서 java.sql.SQLException리는 CheckedException을 Throw한다.
            con = getConnection(); // 커넥션 연결 후 커넥션 객체 반환
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId()); // prepareStatement()에 인수로 전달한 SQL문 문자열에 파라미터 바인딩한다.
            pstmt.setInt(2, member.getMoney()); // 첫번째 인수는 물음표의 순서를, 두번째 인수는 바안딩할 값으로 지정한다.
            int count = pstmt.executeUpdate();// 실제 쿼리 실행 후 영향받은 row 수 반환 - Statement를 통해 준비된 SQL을 커넥션을 통해 실제 데이터베이스에 전달
            return member;
        } catch (SQLException e) {
            log.error("db error", e);
            throw e; // save() 메소드 호출부로 예외를 던진다.
        } finally {
            /**
             * 시작의 역순으로 각 리소스를 close()한다.
             * 외부 리소스 즉, 실제 TCP, IP 커넥션을 통해 리소스를 사용한다.
             * 이것을 닫지 않으면 계속 연결이 유지되는데 이를 리소스 누수라고 하며,
             * 결과적으로 커넥션 부족에 대한 장애 발생으로 이어질 수 있다.
             */
//            pstmt.close();
//            con.close();
            close(con, pstmt, null);

        }
    }

    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Member member = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);
            rs = pstmt.executeQuery();
            if (rs.next()) { // ResultSet 커서는 처음에는 아무것도 가리키지 않으므로 if 혹은 while로 조회시 무조건 한번이상 next()로 커서를 넘긴다.
                member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("member not found memberId = " + memberId);
            }
        } catch (Exception e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, pstmt, rs);
        }

    }


    /**
     * 리소스를 close() 한다.
     *
     * 여러 리소스를 닫기 위해 사용한다.
     * 여러 리소르객체를 각각 따로 try-catch를 통해 닫아주므로 만약 중간에 clsoe에 대한 예외가 발생하더라도
     * 해당 예외가 끝난 뒤 다음 close에 대한 작업을 그대로 실행한다.
     * @param con
     * @param stmt: [Statement] - PrepareStatement의 부모 인터페이스로 SQL에 파라미터 바인딩을할 수 없다. (문자열 결합, formating등을 활용해야 한다.)
     * @param rs
     */
    private void close(Connection con, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.error("error", e);
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                log.error("error", e);
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }
    }


    /**
     * 데이터베이스에 대한 커넥션을 맺고 커넥션을 반환한다. <br/>
     * DBConnectionUtil 클래스의 getConnection()을 호출한다. <br/>
     * DriverManager의 getConnection()에 의해 일치하는 데이터베이스에 대한 커넥션을 맺고 커넥션을 반환한다.
     * @return
     */
    private Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }
}
