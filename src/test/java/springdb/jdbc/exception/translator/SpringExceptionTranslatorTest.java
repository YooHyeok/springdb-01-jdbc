package springdb.jdbc.exception.translator;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import springdb.jdbc.connection.ConnectionConst;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static springdb.jdbc.connection.ConnectionConst.*;

@Slf4j
public class SpringExceptionTranslatorTest {

    DataSource dataSource;
    @BeforeEach
    void init() {
        dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
    }

    @Test
    @DisplayName("")
    void sqlExceptionErrorCode() {
        String sql = "select bad grammer"; // 잘못된 SQL

        try {
            Connection con = dataSource.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.executeQuery();
        } catch (SQLException e) {
            Assertions.assertThat(e.getErrorCode()).isEqualTo(42122); // h2의 오류코드
            int errorCode = e.getErrorCode();
            log.info("errorCode = {}", errorCode);
            log.info("error", e);
        }

    }

    @Test
    @DisplayName("Spring 예외 변환기 테스트")
    void exceptionTraslator() {
        String sql = "select bad grammer"; // 잘못된 SQL

        try {
            Connection con = dataSource.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.executeQuery();
        } catch (SQLException e) {
            SQLErrorCodeSQLExceptionTranslator exceptionTranslator = new SQLErrorCodeSQLExceptionTranslator(dataSource);

            DataAccessException resultException = exceptionTranslator.translate("작업명", sql, e); //BadGrammarException로 변환해준다.
            log.info("resultException", resultException);
            Assertions.assertThat(resultException.getClass()).isEqualTo(BadSqlGrammarException.class);
        }

    }

}
