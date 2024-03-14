package springdb.jdbc.connection;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class DBConnectionUtilTest {

    @Test
    @DisplayName("")
    void connection() {
        //given
        Connection connection = DBConnectionUtil.getConnection();

        //when & then
        assertThat(connection).isNotNull();
    }
}
