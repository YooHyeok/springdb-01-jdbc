package springdb.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
public class UnCheckedAppTest {

    @Test
    @DisplayName("UnChecked Exception 테스트 - SQLException, ConnectException")
    void unchecked() {
        Controller controller = new Controller();
        assertThatThrownBy(() -> controller.request())
//                .isInstanceOf(Exception.class);
//                .isInstanceOf(RuntimeConnectException.class); // RuntimeSQLException이 먼저 터지므로, 검증실패된다.
                .isInstanceOf(RuntimeSQLException.class);
    }

    static class Controller {
        Service service = new Service();
        public void request() {
            service.logic();
        }
    }

    static class Service {
        Repository repository =new Repository();
        NetworkClient networkClient = new NetworkClient();

        public void logic() {
            repository.call();
            networkClient.call();
        }
    }
    static class NetworkClient {
        public void call() {
            try {
                run();
            } catch (Exception e) {
                throw new RuntimeConnectException(e);
            }
        }
        public void run () throws ConnectException {
            throw new ConnectException("SqlEx");
        }
    }
    static class Repository {
        public void call() {
            try {
                runSQL();
            } catch (SQLException e) {
                throw new RuntimeSQLException(e);
            }
        }
        public void runSQL () throws SQLException {
            throw new SQLException("SqlEx");
        }
    }

    static class RuntimeConnectException extends RuntimeException {
        public RuntimeConnectException(Throwable cause) {
            super(cause);
        }
    }

    static class RuntimeSQLException extends RuntimeException {
        public RuntimeSQLException(Throwable cause) {
            super(cause);
        }
    }

}
