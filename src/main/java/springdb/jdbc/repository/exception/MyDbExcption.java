package springdb.jdbc.repository.exception;

public class MyDbExcption extends RuntimeException {
    public MyDbExcption() {
        super();
    }

    public MyDbExcption(String message) {
        super(message);
    }

    public MyDbExcption(String message, Throwable cause) {
        super(message, cause);
    }

    public MyDbExcption(Throwable cause) {
        super(cause);
    }
}
