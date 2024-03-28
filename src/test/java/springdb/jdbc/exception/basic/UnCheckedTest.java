package springdb.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class UnCheckedTest {

    @Test
    @DisplayName("UnChecked Try~Catch")
    void unchecked_catch() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    @DisplayName("UnChecked Throw")
    void unchecked_throw() {
        Service service = new Service();
//        service.callThrow(); // 명식으로 throws 처리했기 때문에 테스트가 실패됨
        try {
            service.callThrow();
        } catch (MyUncheckedException e) {
            log.info("예외 처리, message={}", e.getMessage(), e);
        }

        /* 혹은 성공으로 처리 */
        Assertions.assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf(MyUncheckedException.class);
    }

    /**
     * RuntimeException을 상속받은 예외는 언체크 예외가 된다.
     */
    static class MyUncheckedException extends RuntimeException {
        public MyUncheckedException(String message) {
            super(message);
        }
    }


    /**
     * Unchecked 예외는 예외를 잡거나 던지지 않아도 된다.
     * 예외를 잡지 않으면 자동으로 밖으로 던진다.
     */
    static class Service {
        Repository repository = new Repository();

        /**
         * 필요한 경우 예외를 잡아서 처리하면 된다.
         */
        public void callCatch() {
            try {
                repository.call();
            } catch (MyUncheckedException e) {
                log.info("예외 처리, message={}", e.getMessage(), e);
            }
        }

        /**
         * 예외를 잡지 않아도 자동으로 상위 호출부로 넘어간다.
         * 체크 예외와 다르게 throws 예외 선언을 하지 않아도 된다.
         */
        public void callThrow() {
            repository.call();
        }
    }

    static class Repository {
        public void call() {
            throw new MyUncheckedException("Ex");
        }
    }


}
