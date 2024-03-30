package springdb.jdbc.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import springdb.jdbc.domain.Member;
import springdb.jdbc.repository.exception.MyDbExcption;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

/**
 * 예외 누수 문제 해결
 * 체크 예외를 런타임 예외로 변경
 * MemberRepository 인터페이스 사용
 * throws SQLException 제거
 */
@Slf4j
public class MemberRepositoryV4_1 implements MemberRepository {
    private final DataSource dataSource;

    public MemberRepositoryV4_1(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Member save(Member member) {
        String sql = "insert into member(member_id, money) values(?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            int count = pstmt.executeUpdate();
            return member;
        } catch (SQLException e) {
            throw new MyDbExcption(e);
        } finally {
            close(con, pstmt, null);
        }

    }

    @Override
    public Member findById(String memberId) {
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
            if (rs.next()) {
                member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("member not found memberId = " + memberId);
            }
        } catch (Exception e) {
            throw new MyDbExcption(e);
        } finally {
            close(con, pstmt, rs);
        }

    }

    @Override
    public void update(String memberId, int money) {
        String sql = "update member set money=? where member_id=?";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize={}", resultSize);
        } catch (SQLException e) {
            throw new MyDbExcption(e);
        } finally {
            close(con, pstmt, null);
        }

    }

    @Override
    public void delete(String memberId) {
        String sql = "delete member where member_id = ? ";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize={}", resultSize);
        } catch (SQLException e) {
//            throw new MyDbExcption(e);
        } finally {
            close(con, pstmt, null);
        }
    }

    /**
     * JdbcUtils의 편의 메소드를 활용하여 리소스를 close() 한다.<br/>
     * 트랜잭션 동기화를 사용하기 때문에 Connection은 DataSourceUtils를 통해 닫아준다.<br/>
     * 트랜잭션을 사용하기 위해 동기화 된 커넥션은 닫지않고 그대로 유지하며, 트랜잭션 동기화 매니저가 관리하는 커넥션이 없는경우 해당 커넥션을닫는다.
     */
    private void close(Connection con, Statement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        DataSourceUtils.releaseConnection(con, dataSource);
    }


    /**
     * 트랜잭션 동기화를 사용하기 때문에 DataSourceUtils를 사용하여 Connection획득.<br/>
     * 기본적으로 새로운 커넥션을 생성하며, 트랜잭션 동기화매니저가 관리하는 커넥션이 있으면, 해당 커넥션을 반환한다.
     * @return
     */
    private Connection getConnection() {
        Connection con = DataSourceUtils.getConnection(dataSource);
        log.info("getConnection={}, classes={}", con, con.getClass());
        return con;
    }
}
