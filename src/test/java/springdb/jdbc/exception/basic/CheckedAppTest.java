package springdb.jdbc.exception.basic;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CheckedAppTest {

    @Test
    @DisplayName("Checked Exception 테스트 - SQLException, ConnectException")
    void checked() {
        Controller controller = new Controller();
        assertThatThrownBy(() -> controller.request())
                .isInstanceOf(Exception.class);
    }

    static class Controller {
        Service service = new Service();
        public void request() throws SQLException, ConnectException {
            service.logic();
        }
    }

    static class Service {
        Repository repository =new Repository();
        NetworkClient networkClient = new NetworkClient();

        public void logic() throws SQLException, ConnectException {
            repository.call();
            networkClient.call();
        }
    }
    static class NetworkClient {
        public void call() throws ConnectException {
            throw new ConnectException("ConEx");
        }
    }
    static class Repository {
        public void call() throws SQLException {
            throw new SQLException("SqlEx");
        }
    }
}
