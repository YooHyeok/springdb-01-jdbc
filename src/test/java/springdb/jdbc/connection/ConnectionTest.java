package springdb.jdbc.connection;

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

    private void useDataSource(DataSource dataSource) throws SQLException {
        Connection con1 = dataSource.getConnection();
        Connection con2 = dataSource.getConnection();
        log.info("connection={}, class={}", con1, con1.getClass()); // conn0
        log.info("connection={}, class={}", con2, con2.getClass()); // conn1
    }

}