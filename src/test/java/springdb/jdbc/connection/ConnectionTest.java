package springdb.jdbc.connection;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static springdb.jdbc.connection.ConnectionConst.*;

@Slf4j
class ConnectionTest {
    @Test
    @DisplayName("JDBC 커넥션 테스트 - DriverManager")
    void driverManager() throws SQLException {
        Connection con1 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Connection con2 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        log.info("connection={}, class={}", con1, con1.getClass()); // conn0
        log.info("connection={}, class={}", con2, con2.getClass()); // conn1
    }

    @Test
    @DisplayName("JDBC 커넥션 테스트 - DriverManagerDataSource")
    void diverManagerDataSourcethrows() throws SQLException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        useDataSource(dataSource);
    }
    
    @Test
    @DisplayName("Connection Pooling 테스트")
    void dataSourceConnectionPool() throws SQLException, InterruptedException {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("MyPool");

        useDataSource(dataSource);

        /**
         * 커넥션 풀에서 커넥션을 생성하는 작업은 애플리케이션 실행 속도에 영향을 주지 않기 위해 별도의 스레드에서 작동함.
         * 별도의 스레드에서 동작하기 때문에 테스트가 먼저 종료되어 버리므로 Pool에 추가되는 과정이 제대로 진행되지 않음.
         * 따라서 테스트 실행 후 대기시간을 주어야 로그 확인 가능
         */
        Thread.sleep(1000); //대기시간 1초
    }

    private void useDataSource(DataSource dataSource) throws SQLException {
        Connection con1 = dataSource.getConnection();
        Connection con2 = dataSource.getConnection();
/*        Connection con3 = dataSource.getConnection();
        Connection con4 = dataSource.getConnection();
        Connection con5 = dataSource.getConnection();
        Connection con6 = dataSource.getConnection();
        Connection con7 = dataSource.getConnection();
        Connection con8 = dataSource.getConnection();
        Connection con9 = dataSource.getConnection();
        Connection con10 = dataSource.getConnection();
        Connection con11 = dataSource.getConnection();*/
        log.info("connection={}, class={}", con1, con1.getClass()); // conn0
        log.info("connection={}, class={}", con2, con2.getClass()); // conn1
    }

}