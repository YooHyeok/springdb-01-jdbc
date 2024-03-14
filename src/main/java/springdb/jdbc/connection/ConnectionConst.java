package springdb.jdbc.connection;

/**
 * 상수 전용 클래스이므로 객체 생성을 방지하기위해 추상클래스로 선언한다.
 */
public abstract class ConnectionConst {
    public static final String URL = "jdbc:h2:tcp://localhost/~/jdbc";
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "";

}

