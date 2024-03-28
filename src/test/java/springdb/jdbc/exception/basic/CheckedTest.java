package springdb.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class CheckedTest {

    @Test
    @DisplayName("Checked Try~Catch")
    void checked_catch() {
        //given
        Service service = new Service();
        service.callCatch();
    }

    @Test
    @DisplayName("Checked Throw")
    void checked_throw() {
        //given
        Service service = new Service();
        try {
            service.callThrow();
        } catch (MyCheckedException e) {
            log.info("예외 처리, message={}", e.getMessage(), e);
        }

        /* 혹은 성공으로 처리 */
        Assertions.assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf(MyCheckedException.class);
    }


    /**
     * Exception을 상속받은 예외는 Check 예외가 된다.
     */
    static class MyCheckedException extends Exception {
        public MyCheckedException(String message) {
            super(message);
        }
    }

    /**
     * Checked 예외는 예외를 잡아서 처리하거나, 던지거나 둘중 하나를 필수로 선택해야한다.
     */
    static class Service {
        Repository repository = new Repository();

        /**
         * 체크 예외를 잡아서 처리하는 코드
         * Repository의 call 메소드는 throws를 사용하여 호출부로 MyCheckedException을 던졌으므로
         * 호출부에서는 해당 Exception을 잡거나 다시 던져야한다.
         */
        public void callCatch() {
            try {
                repository.call();
            } catch (MyCheckedException e) {
                // Exception 처리 로직
                log.info("예외 처리, message={}", e.getMessage(), e); //StackTrace를 출력하려면 마지막 파라미터로 넣어준다.
            }
        }

        /**
         * 체크 예외를 밖으로 던지는 코드
         * 체크 예외는 예외를 잡지 않고 밖으로 던지려면 throws 예외를 메소드에 필수로 선언해야 한다.
         * @throws MyCheckedException
         */
        public void callThrow() throws MyCheckedException {
            repository.call();
        }
    }

    static class Repository {
        /**
         * Checked Exception의 경우 Try~Catch로 예외를 잡거나 메소드가 호출된곳으로 던져야한다.
         * 만약 두 작업 모두 하지 않는다면 컴파일러가 관여해서 해당 메소드를 잡거나, 호출된곳으로 던지게끔 유도한다.
         * 메소드가 호출된곳으로 던지기 위해서는 해당 메소드의 우측에 throws로 발생한 exception과 동일한 클래스를 선언해준다.
         * @throws MyCheckedException
         */
        public void call() throws MyCheckedException { //
            throw new MyCheckedException("Ex");
        }
    }
}
