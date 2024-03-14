package springdb.jdbc.connection;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static springdb.jdbc.connection.ConnectionConst.*;

@Slf4j
public class DBConnectionUtil {
    /**
     * 데이터베이스 Connection 반환 함수
     * DriverManager의 getConeection() 메소드를 통해 DB(H2)의 URL, USERNAME, PASSWORD 정보들을 넘겨
     * jdbc 표준 커넥션 인터페이스인 java.sql의 Connection 객체를 반환한다.
     * 해당 Connection객체는 URL 정보인 jdbc:h2에 의한 org.h2.jdbc.JdbcConnection 구현체이다.
     * @return Connection - JDBC 표준 인터페이스가 제공하는 Connection (h2.jdbc.JdbcConnection)
     */
    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            log.info("get connection={}, class={}", connection, connection.getClass());
            // get connection=conn0: url=jdbc:h2:tcp://localhost/~/jdbc user=SA, class=class org.h2.jdbc.JdbcConnection
            return connection;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {

        }
    }
}
