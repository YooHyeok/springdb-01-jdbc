# *Spring Database*

# Part01 - *JDBC*

### `등장 이유 `
일반적으로 애플리케이션을 개발할 때 중요한 데이터는 대부분 데이터 베이스에 보관한다.  
클라이언트, 애플리케이션 서버, 데이터베이스 서버 간의 관계 및 구조로 이루어져 있으며,  
클라이언트(아이폰,안드로이드 / 웹)는 애플리케이션 서버를 통해 데이터베이스에 접근하여 데이터를 저장하거나 조회하게 된다.  

1. `커넥션 연결` : 주로 TCP/IP를 사용하여 애플리케이션 서버로부터 데이터베이스 서버로 커넥션을 연결한다
2. `SQL 전달` : 애플리케이션 서버는 DB가 이해할 수 있는 SQL을 연결된 커넥션을 통해 DB에 전달한다.
3. `결과 응답` : DB는 전달된 SQL을 수행하고 그 결과를 애플리케이션 서버에 응답한다. (애플리케이션 서버에서 응답 결과를 활용)

위 내용은 오래된 방식이다.
각각의 데이터베이스 마다 커넥션을 연결하는 방법과, SQL을 정답하는 방법, 그리고 결과를 응답받는 방법이 모두 다른 문제를 가지고 있었다.  
(참고로 관계형 데이터베이스만 해도 수십개가 있다.)  

예를들어, MySQL과 Oracle에서의 각 방법들이 서로 다르고, 이때 DB를 교체하면 애플리케이션 서버에 있는 로직들을 모두 해당 DB에 맞게 변경해야만 한다.

### `문제점`
1) 다른 종류의 데이터베이스로 변경시 애플리케이션 서버에 개발된 데이터베이스 사용 코드도 함께 변경해야함.
2) 개발자가 각각의 데이터베이스마다 커넥션연결, SQL전달, 결과응답방법을 새로 학습해야함.

이러한 문제를 해결하기 위해 `JDBC`라는 자바 표준 등장

### JDBC 표준 인터페이스
`JDBC(Java Database Connectivity)`  
자바에서 데이터베이스에 접속할 수 있도록 하는 자바 API  
데이터베이스에서 자료를 쿼리하거나 업데이트하는 방법을 제공

### 표준 인터페이스 정의 3가지 기능
* `Connection` - 연결 _(java.sql.Connection)_
* `Statement` - SQL 전달 _(java.sql.Statement)_
* `ResultSet` - 결과 응답 _(java.sql.ResultSet)_

인터페이스만 있다고 해서 기능이 동작하지 않는다.  
각각의 DB 벤더(회사)에서 자신의 DB에 맞도록 표준 인터페이스를 구현한 것을 라이브러리로 제공하며, 이를 JDBC 드라이버라 한다.  
`ex)` MySQL JDBC Driver | Oracle JDBC Driver

### JDBC의 등장으로 2가지 문제 해결
1. 데이터베이스를 다른 종류의 데이터베이스로 변경하면 애플리케이션 서버의 데이터베이스 사용 코드도 함께 변경해야 하는 문제  
   - 애플리케이션 로직이 JDBC 표준 인터페이스에만 의존하게 되어 다른 종류의 데이터베이스로 변경할 경우 JDBC 구현 라이브러리만 변경하면 되고, 애플리케이션 서버의 사용 코드를 그대로 유지할 수 있게 됨
2. 개발자가 각각의 데이터베이스마다 커넥션연결, SQL 전달, 결과응답방법을 새로 학습해야 하는 문제
   - JDBC 표준 인터페이스 사용법만 학습하면 되며, 한번 배워두면 수십개의 데이터베이스에 모두 동일하게 적용 가능 

*`표준화의 한계`*  
각각의 데이터베이스 마다 SQL, 데이터타입 등의 일부 사용법이 다르다.  
(ANSI SQL 표준이 있으나 일반적인 부분만 공통화 되어 한계가 있음)  
대표적으로 실무에서 기본적으로 사용하는 페이징 SQL은 각각의 데이터베이스마다 사용법이 다르다.  
`JPA(Java Persistence API)` 사용시 이런 표준화 한계 극복 또한 어느정도 가능해 진다.

# *JDBC와* *`SQL Mapper`*
애플리케이션에서 JDBC에 SQL을 직접 전달하여 사용하는 방법은 너무 로우레벨로 제공되어 기능이 하나하나 쪼개져있어 사용하기 매우 번잡하고 불편하다.  
그래서 직접 사용하기 보다는 편리하게 사용하는 기술이 있으며 이는 SQL Mapper와 ORM 기술로 나뉜다.  
그중 SQLMapper에는 대표적으로 `JDBC Template`와 `MyBatis`가 있다.  

 - ### 장점

   - JDBC를 편리하게 사용하도록 도와준다
     - SQL 응답 결과를 객체로 편리하게 변환해 준다.
     - JDBC의 반복 코드를 제거해준다.

 - ### 단점
    개발자가 SQL을 직접 작성해야 한다.

**결론**: 실무에서는 JDBC를 직접 사용하는 경우는 없으며 최소한 SQL Mapper의 JDBC Template이나 Mybatis 둘 중 하나를 선택하여 사용하게 된다.  

## ORM 기술 - `JPA`
ORM이란 객체를 관계형 데이터베이스 테이블과 매핑해주는 기술이다.  
개발자는 반복적인 SQL을 직접 작성하지 않고, ORM 기술이 개발자 대신에 SQL을 동적으로 만들어 실행해준다.  
추가로 각각의 데이터베이스 벤더마다 다른 SQL을 사용하는 문제도 중간에서 해결해 준다.  
Java진영에서는 JPA(Java Persistent API - 자바 영속성 API) 가 존재한다.  
JPA는 자바 ORM기술인 영속성에 대한 API 표준명세 이므로 특정 기능을 하는 라이브러리가 아닌 ORM을 사용하기 위한 인터페이스를 모아둔 것이다.  
즉, 자바 애플리케이션에서 관계형 데이터베이스를 ORM기술로 어떻게 사용해야 하는지를 정의하는 방법 중 한가지이다.  
위에서 말했듯, 단순한 명세이기 때문에 구현이 없다.  
따라서 JPA를 구현한 ORM 프레임워크인 `Hibernate`, `EclipseLink`, `DataNucleus` 중 하나를 사용해야 한다.  
그중 대표적으로 가장 많이 사용하는 것이 바로 `Hibernate`이며, 가장 범용적으로 다양한 기능을 제공한다.  

## `SQL Mapper` VS `ORM 기술`
SQL Mapper와 ORM 기술 둘다 각각의 장단점이 있다.  
SQL Mapper는 SQL만 직접 작성하면 나머지 번거로운 작업은 SQL MApper가 대신 해결해 준다.  
따라서 SQL Mapper는 SQL만 작성할 줄 알면 금방 습득하여 사용할 수 있다.  
반면 ORM 기술은 SQL 자체를 작성하지 않아도 되어서 개발 생산성이 매우 높아진다.  
편리한 반면에 쉬운 기술은 아니므로 실무에서 사용하려면 깊이있게 학습해야 한다.

### +) 중요 Tip
ORM 기술도 내부적으로는 모두 JDBC를 사용한다.  
따라서 JDBC를 직접 사용하지는 않더라도, JDBC가 어떻게 동작하는지 기본 원리를 알아두어야 한다.  
그래야 해당 기술들을 더 깊이있게 이해할 수 있고, 무엇보다 문제가 발생했을 때 근본적인 문제를 찾아서 해결할 수 있다.  
**JDBC는 자바 개발자라면 꼭 알아두어야 하는 필수 기본 기술** 이다.


# 데이터 베이스 연결
아래와 같이 테스트 코드를 작성해보도록 한다

* #### [ConnectionConst] class 생성
```java
/**
 * 상수 전용 클래스이므로 객체 생성을 방지하기위해 추상클래스로 선언한다.
 */
public abstract class ConnectionConst {
    public static final String URL = "jdbc:h2:tcp://localhost/~/jdbc";
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "";

}
```

* #### [DBConnectionUtil] class 생성
```java
@Slf4j
public class DBConnectionUtil {
    
    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            log.info("get connection={}, class={}", connection, connection.getClass());
            // get connection=conn0: url=jdbc:h2:tcp://localhost/~/jdbc user=SA, class=class org.h2.jdbc.JdbcConnection
            return connection;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {

        }
    }
}
```
* #### [DBConnectionUtilTest] test class 생성
```java
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
```

DriverManager의 getConnection() 메소드 매개변수로 서버에 설치된 각 벤더별 database 서버에 접속하는 JDBC URL, 사용자이름, 비밀번호를 전달하게 되면  
JDBC 표준 커넥션 인터페이스인 java.sql패키지의 Connection 객체를 반환한다.
해당 Connection객체는 Database 접속 정보를 통해 현재 라이브러리로 등록된 모든 DataBase `Driver`에 각각 connect를 시도한다.
이 Driver는 인터페이스로 각 벤더에 맞는 구현체를 갖게 된다.  
(h2 = org.h2.Driver / MySQL = com.mysql.cj.jdbc.NonRegisteringDriver)

```json
runtimeOnly 'com.h2database:h2'
runtimeOnly 'com.mysql:mysql-connector-j'
```

예를들어 위와같이 h2와 MySQL 라이브러리 의존성을 모두 다운받았다면
두개의 Driver 구현체 클래스를 루프를통해 접근하여 connect를 시도한다.
connect 성공여부에 상관없이 con객체를 반환받게되는데 실패하면 해당 객체는 비어있게 된다.
내부 로직에 의해 con객체가 비어있지 않다면 해당 객체를 반환한다.

위 과정을 거쳐 H2라면 반환받은 Connection 객체는  org.h2.jdbc의 JdbcConnection 구현체가 될것이며,
만약 MySQL이라면 Connection 객체는  com.mysql.cj.jdbc의 JdbcConnection 구현체가 될것이다

내부 코드는 다음과 같다

```java
package java.sql;
public class DriverManager {
    /* 생략 */
    @CallerSensitive
    public static Connection getConnection(String url,
        String user, String password) throws SQLException {
        /*생략*/
        return (getConnection(url, info, Reflection.getCallerClass()));
    }
    /* 생략 */
    private static Connection getConnection(
            String url, java.util.Properties info, Class<?> caller) throws SQLException {
        
        /* 생략 */
        for (DriverInfo aDriver : registeredDrivers) {

            if (isDriverAllowed(aDriver.driver, callerCL)) {
                try {
                    Connection con = aDriver.driver.connect(url, info); // connect() 메소드 는 sql Driver 인터페이스에 정의되어 있다.
                    if (con != null) {
                        return (con);
                    }
                } catch (SQLException ex) {/*생략*/}

            }
            /*생략*/
        }
    }
}
```

내부 static 메소드인 getConnection() 함수에는 아래와 같이 의존성으로 등록된 모든 라이브러리의 Driver에 접근하여 Loop를 돈다.
```java
for (DriverInfo aDriver : registeredDrivers) {

    if (isDriverAllowed(aDriver.driver, callerCL)) {
        try {
            Connection con = aDriver.driver.connect(url, info); // connect() 메소드 는 sql Driver 인터페이스에 정의되어 있다.
            if (con != null) {
                return (con);
            }
        } catch (SQLException ex) {/*생략*/}

    }
    /*생략*/
}
```
바로 위 Loop를 통해 등록된 Driver들에 순차적으로 connect하게 되고  
반환하는 Connection객체가 비어있지 않을경우 연결에 성공하였으므로 해당 Connection 객체를 반환한다.

여기서 호출된 Driver와 Driver의 connect() 메소드는 아래와 같이 각각 각 밴더의 구현체 클래스와 오버라이딩 된 메소드가 된다.

- ### H2 Driver
```java
package org.h2;

public class Driver implements java.sql.Driver, JdbcDriverBackwardsCompat {

    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        if (url == null) {
            throw DbException.getJdbcSQLException(ErrorCode.URL_FORMAT_ERROR_2, null, Constants.URL_FORMAT, null);
        } else if (url.startsWith(Constants.START_URL)) {
            return new JdbcConnection(url, info, null, null, false); // 해당 코드에 의해 JdbcConnection객체 반환
        } else if (url.equals(DEFAULT_URL)) {
            return DEFAULT_CONNECTION.get();
        } else {
            return null;
        }
    }
}

```

```java
return new JdbcConnection(url, info, null, null, false);
```
주석이 달린 부분의 반환코드를 `JdbcConnection`을 반환하게 되는데 해당 객체는  `org.h2.jdbc 패키지의 JdbcConnection` 이다


- ### MySQL Driver
```java
package com.mysql.cj.jdbc;
public class NonRegisteringDriver implements java.sql.Driver {

@Override
    public java.sql.Connection connect(String url, Properties info) throws SQLException {
        try {
            /* 생략 */
            ConnectionUrl conStr = ConnectionUrl.getConnectionUrlInstance(url, info);
            switch (conStr.getType()) {
                case SINGLE_CONNECTION:
                    return com.mysql.cj.jdbc.ConnectionImpl.getInstance(conStr.getMainHost()); // 해당 코드를 통해 JdbcConnection 객체 반환
                // 이때
                /*생략*/
            }

        } catch (UnsupportedConnectionStringException e) {/*생략*/}
        /* 생략 */
    }
}
```

```java
return com.mysql.cj.jdbc.ConnectionImpl.getInstance(conStr.getMainHost());
```
switch문의 위 코드를 통해 `JdbcConnection`을 반환하게 되는데 getInstance의 반환타입은  `com.mysql.cj.jdbc 패키지의 JdbcConnection` 이다.

H2 Driver 혹은 MySQL Driver 에서의 connect() 과정에서 JdbcConnection 객체들을 각각 반환하게 되는 조건은  
넘겨받은 DataBase URL 정보가 각 밴더별 JDBC URL 정보와 일치한다면 해당 객체를 반환하게 될것이다.


# *JDBC 개발*

## `Statement`와`PreparedStatement`
Statement는 일반적으로 JDBC에서 SQL구문을 실행하는 역할을 한다.  
데이터베이스와 연결되어 있는 Connection 객체를 통해 SQL문을 데이터베이스에 전달하여 실행하고, 결과를 리턴받아 주는 객체이다.  
Statement는 4단계의 동작 방식을 가진다.  
parse(구문 분석) -> bind(치환) -> execute(실행) -> patch(추출)
아래는 저장에 대한 간단한 사용법이다.  

### executeUpdate()
- 저장
    ```java
    Connection con = null;
    Statement stmt = null;
    String sql = "INSERT INTO member(member_id, money) VALUES(" + memberId + ", "+ money + )";
    try {
        con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        stmt = con.createStatement(sql);
        stmt.executeUpdate();
    } catch(Exception e) {e.printStackTrace();}
    finallly {
    /* Closeable 구현*/
    }    ```  

- 수정
    ```java
    Connection con = null;
    Statement stmt = null;
    String sql = "UPDATE member SET money = " + money;
    try {
    Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    Statement stmt = con.createStatement(sql);
    stmt.executeUpdate();
    } catch(Exception e) {e.printStackTrace();}
    finallly {
    /* Closeable 구현*/
    }
    ```  
### executeQuery()
- 조회
    ```java
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;
    String sql = "SELECT * FROM member WHERE member_id = " + memberId;
    Member member = null;
    try {
        con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        stmt = con.createStatement(sql);
        rs = stmt.executeUpdate();
    while(rs.next()) {
        member.setMoney(rs.getString())
    } catch(Exception e) {e.printStackTrace();}
    finallly {
    /* Closeable 구현*/
    }
    ```
저장/수정에서는 executeUpdate , 조회에서는 executeQuery를 사용한다.  
executeUpdate는 적용된 레코드 행의 수를 반환한다.  
executeQuery 경우 조회된 레코드를 ResultSet으로 반환하며, 해당 레코드는 iterator와 유사한 문법으로 While반복문을 통해  
커서(행)을 옴기고 행이 존재한다면 데이터를 추출하는 형태로 구현한다.  
최초의 ResultSet의 커서는 데이터를 가리키고 있지 않기 때문에 조건절을 통해 최초로 1회이상 호출해야 데이터조회가 가능하다.  
(만약 단건 조회라면 if문을 사용하여 커서를 체크한다.)  

PreparedStatement는 Statement를 상속하고 있는 인터페이스이다.  
내부적으로 Statement의 4단계 과정 중 첫번째 parse과정의 결과를 캐싱하고, 이후 3가지 단계만 거쳐 SQL문이 실행될 수 있게 한다.  
이는 동일한 쿼리가 연속적으로 발생할 경우 메모리에 캐싱된 구문을 재사용하기 때문에 구문분석에 대한 작업이 생략되므로,  
Statement보다 효율적으로 작동하게 된다.

또한 PreparedStatement는 `SQL Injection`을 예방할 수 있다.   
예를들어 `String sql = "insert into member(member_id, money) values ("+ memberId +", "+ money +");`  
위와 같은 쿼리가 구성되어 있고 memberId에 대한 파라미터가 `String memberId = "select * from ...";`  
와 같은 조회 형태의 SQL 문자열이 전달된다고 할때, 문자열 결합에서는 해당 문자열이 SQL로 치환된다.  
이 경우 자칫 잘못하면 프로그램 로직이 들어올 수도 있다..  
하지만 파라미터 바인딩시에는 해당 문자열이 단순히 데이터로 취급되어버리기 때문에 안전해진다. 

- ## 저장
    ```java
    @Slf4j
    public class MemberRepositoryV0 {
        /* H2 DB 정보 */
        public static final String URL = "jdbc:h2:tcp://localhost/~/jdbc";
        public static final String USERNAME = "sa";
        public static final String PASSWORD = "";
        
        public Member save(Member member) throws SQLException {
            
            try { // prepareStatement() 메소드에서 java.sql.SQLException리는 CheckedException을 Throw한다.
                Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD); // 커넥션 연결 후 커넥션 객체 반환
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, member.getMemberId()); // prepareStatement()에 인수로 전달한 SQL문 문자열에 파라미터 바인딩한다.
                pstmt.setInt(2, member.getMoney()); // 첫번째 인수는 물음표의 순서를, 두번째 인수는 바안딩할 값으로 지정한다.
                int count = pstmt.executeUpdate();// 실제 쿼리 실행 후 영향받은 row 수 반환 - Statement를 통해 준비된 SQL을 커넥션을 통해 실제 데이터베이스에 전달
                return member;
            } catch (SQLException e) {
                log.error("db error", e);
                throw e; // save() 메소드 호출부로 예외를 던진다.
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        log.error("error", e);
                    }
                }
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException e) {
                        log.error("error", e);
                    }
                }
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
    
    ```
    
    ParameterBinding

    ```java
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
        con=DriverManager.getConnection(URL,USERNAME,PASSWORD); // 커넥션 연결 후 커넥션 객체 반환
        pstmt=con.prepareStatement(sql);
        pstmt.setString(1,member.getMemberId()); // prepareStatement()에 인수로 전달한 SQL문 문자열에 파라미터 바인딩한다.
        pstmt.setInt(2,member.getMoney()); // 첫번째 인수는 물음표의 순서를, 두번째 인수는 바안딩할 값으로 지정한다.
        int count=pstmt.executeUpdate();
    }
    ```
     
    먼저 JDBC 커넥션 객체를 얻고, 해당 객체의 prepareStatement() 함수를 통해 sql을 인자로 넘긴다.  
    PrepareStatement 객체의 set메소드를 통해 SQL에 파라미터값을 받을 위치에 지정한 ? 문자에 파라미터 바인딩이 가능하다.  
    setString과 setInt 등을 사용하였는데, 첫번째 인자로는 바인딩 될 ?의 순서를 지정하고, 두번째 인자로는 바인딩 될 값을 지정한다.  
    바인딩이 완료되었다면 PrepareStatement객체의 executeUpdate() 메소드를 통해 실제 쿼리를 실행한다.  
    이때 해당 메소드로부터 반환받는 int 값은 실제 쿼리 실행 후 영향받은 row의 갯수이다.
    조회쿼리는 Statement와 사용법이 거의 동일하다.
    
    ```java
        finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    log.error("error", e);
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    log.error("error", e);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    ```
    열린 Closable 객체들은 fnially구문에서 닫아준다.  
    간혹 책에서 try 구문 안에 JDBC 로직 종료 후 바로 CLoasable에 대한 작업을 하는데, 이는 문제 발생을 야기한다.  
    만약 JDBC 구문이 오류가 나서 catch구문으로 빠지게되면, Closable에 대한 로직은 작동되지 않기 때문이다.  
    또한, finally 안에서도 각 Closable한 객체에 대해 각각 예외처리를 따로 해 줘야한다.  
    중간에 예외가 발생한다면, 해당 Excepion을 처리하고 그 다음에 오는 작업이 수행되지 않기 때문이다.

# Connection Pool
JDBC를 사용하기 위해 DriverManager를 사용하게 되면 데이터베이스 커넥션을 항상 매번 획득하게 된다.  
데이터베이스 커넥션을 획득할 때는 아래와 같은 복잡한 과정을 거친다.
1. 애플리케이션 로직은 DB 드라이버를 통해 커넥션을 조회한다. (`getConnection()`)
2. DB 드라이버는 DB와 `TCP/IP` 커넥션을 연결한다.
   (이 과정에서 3way handshake 같은 `TCP/IP` 연결을 위한 네트워크 동작이 발생한다.)
3. `TCP/IP` 커넥션이 연결 후 DB 드라이버는 ID,PW 같은 기타 부가정보를 DB에 전달한다.
4. DB는 ID, PW를 통해 내부 인증을 완료하고, 내부에 DB 세션을 생성한다.
   (DB Session: 인증된 사용자라는 것과, 내부에서 특정 작업을 하기 위해 생성됨)
5. DB는 커넥션 생성이 완료되었다는 응답을 반환한다.
6. DB 드라이버는 커넥션 객체를 생성하여 클라이언트에 반환한다.

위와같이 커넥션을 새로 생성하는 것은 과정도 복잡하고, 시간도 많이 소모되는 일이다.  
DB는 물론이고 애플리케이션 서버 입장에서도 `TCP/IP` 커넥션을 새로 생성하기 위한 리소스를 매번 사용해야 한다.  
진짜 문제는 고객이 애플리케이션을 사용할 때, SQL을 실행하는 시간 뿐만 아니라 커넥션을 새로 만드는 시간이 추가되기 때문에 결과적으로  
응답 속도에 영향을 준다.  
이는 사용자에게 좋지 않은 경험을 줄 수 있다.  
이런 문제를 한번에 해결하는 아이디어가 바로 커넥션을 미리 생성해두고 사용하는 커넥션 풀이라는 방법이다.  
커넥션 풀은 이름 그대로 커녁션을 관리하는 풀(수영장 풀을 상상하면 된다.)이다.  
애플리케이션을 시작하는 시점에 커넥션 풀은 필요한 만큼 커넥션을 미리 확보한 뒤 풀에 보관한다.  
얼마나 보관할지는 서비스의 특징과 서버 스펙에 따라 다르며, 기본값은 10개이다.  
커넥션 풀에 들어있는 커넥션은 `TCP/IP`로 DB와 커넥션이 연결되어 있는 상태이기 때문에 언제든지 즉시 SQL을 DB에 전달할 수 있다.  

- 애플리케이션 로직에서 DB 드라이버를 통해 새로운 커넥션을 획득하는 것이 아님.
- 커넥션 풀을 통해 이미 생성되어 있는 커넥션을 객체 참조로 가져다 쓰기만 하면 됨.
- 커넥션 풀에 커넥션을 요청하면 커넥션 풀은 자신이 가지고 있는 커넥션 중에 하나를 반환함.

애플리케이션 로직은 커넥션 풀에서 받은 커넥션을 사용해서 SQL을 데이터베이스에 전달하고 그 결과를 받아 처리한다.  
커넥션을 모두 사용하고 나면 이제는 커넥션을 종료하는 것이 아니라, 다음 작업에서 재사용 할 수 있도록 해당 커넥션을 그대로 커넥션 풀에 반환하면 된다.  
이때 주의할 점은 커넥션 종료가 아닌 커넥션이 살아있는 상태로 커넥션 풀에 반환해야 한다는 것이다.  

[결론]
- 적절한 커넥션 풀 숫자는 서비스의 특징과 애플리케이션 서버 스펙, DB 서버 스펙에 따라 다르므로 성능테스트를 통해 정해야 함.
- 커넥션 풀은 서버당 최대 커넥션 수 제한이 가능. 따라서 DB에 무한정 연결이 생성되는 것을 막아 DB를 보호하는 효과도 있음.
- 커넥션 풀을 통해 얻는 이점이 매우 크기 때문에 실무에서는 항상 기본으로 사용한다.
- 커넥션 풀은 개념적으로 단순해서 직접 구현할 수도 있지만, 사용도 편리하고 성능도 뛰어난 오픈소스 커넥션 풀이 많으므로 오픈소스를 사용.
- 대표적인 커넥션 풀 오픈소스 `commons-dbcp2` `tomcat-jdbc pool` `HikariCP` 등이 있다. (춘추전국시대)
- 성능과 사용의 편리함 측면에서는 최근 `HikariCP`를 주로 사용하며, 스프링부트 2.0부터는 기본 커넥션 풀로 제공된다.  
  성능, 사용의 편리함, 안정성 측면에서 이미 검증되었기 때문에 커넥션풀을 사용할 때는 고민없이 `HikariCP`를 사용하면 된다.  
  (실무에서도 레거시 프로젝트가 아닌 이상 대부분 `HikariCP`를 사용함)

# Datasource
커넥션 풀을 사용하게 되면 의존 관계가 DriverManager 에서 HikariCP로 변경되기 때문에 커넥션을 획득하는 애플리케이션 코드도 함께 변경해야한다.    
이런 문제를 해결하기 위해 결국 커넥션을 획득 하는 방법을 추상화 하게 된다.  
자바에서는 이런 문제를 해결하기 위해 `javax.sql.DataSource`라는 인터페이스를 제공한다.  
`DataSource`가 바로 `커넥션을 획득하는 방법을 추상화` 하는 인터페이스 이다.  
핵심 기능은 커넥션 조회 하나이다.(다른 기능도 있으나 중요하지 않음)  
`DBCP2, HikariCP, DriverManager TomcatJDBC` 등 각 구현체들마다 커넥션을 획득하는 방법을 추상화하여 표준화한것이다. 

대부분의 커넥션 풀은 DataSource 인터페이스를 미리 구현해 두었으므로,개발자는 `DBCP, HikariCP` 등의 코드를 직접 의존하는 것이 아니라  
`DataSource` 인터페이스에만 의존하도록 애플리케이션 로직을 작성하면 된다.  
커넥션 풀 구현 기술을 변경하고 싶으면 해당 구현체로 갈아끼우기만 하면 된다.  

`DriverManager`는 `DataSource` 인터페이스를 사용하지 않기 때문에 `DriverManager`는 직접 사용해야 한다.  
따라서 `DriverManager`를 사용하다가 `DataSource` 기반의 커넥션 풀을 사용하도록 변경하면 관련 코드를 다 고쳐야 한다.
이런 문제를 해결하기 위해 스프링은 `DriverManager`도 `DataSource`를 통해서 사용할 수 있도록 `DriverManagerDataSource`라는  
`DataSource`를 구현한 클래스를 제공한다.  

자바는 `DataSource`를 통해 커넥션을 획득하는 방법을 추상화 했으므로, 애플리케이션 로직은 `DataSource` 인터페이스에만 의존하면 된다.  
덕분에 `DirverManagerDataSource`를 통해 `DriverManager`를 사용하다가 커넥션 풀을 사용하도록 코드를 변경해도 애플리케이션 로직은 변경하지 않아도 된다.

 - ## DataSource 테스트 
   - DriverManager
    ```java
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
    }
    ```
    - DriverManagerDataSource
    ```java
    @Slf4j
    class ConnectionTest {
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
    ```
    DriverManager는 커넥션을 획득할 때 마다 `URL, USERNAME, PASSWORD` 같은 파라미터를 계속 전달해야 한다.  
    반면 DataSource를 사용하는 방식은 처음 객체를 생성할 때만 필요한 파라미터를 남겨두고, 커넥션을 획득할 때는 단순히 dataSource.getConnection()만 호출하면 된다.  
    위와같은 차이는 `설정과 사용의 분리`를 한것이다.

### `설정과 사용의 분리`
- 설정 : `DataSource`를 만들고 필요한 속성들을 사용해서 `URL, USERNAME, PASSWORD`같은 부분을 입력하는 것을 말한다.  
  이렇게 설정과 관련된 속성들은 한 곳에 있는 것이 향후 변경에 더 유연하게 대처할 수 있다.
- 사용 : 설정은 신경쓰지 않고, `DataSource`의 `getConnection()` 만 호출해서 사용하면 된다.

이 부분이 작아보이지만 큰 차이를 만들어 낸다.
필요한 데이터를 `DataSource`가 만들어지는 시점에 미리 다 넣어두게 되면, `DataSource`를 사용하는 곳에서는  
`dataSource.getConnection()`만 호출하면 되므로, `URL, USERNAME, PASSWORD` 같은 속성들에 의존하지 않아도 된다.  
그냥 `DataSource`만 주입받아서 `getConnection()`만 호출하면 된다.  
쉽게 말해 Repository는 `DataSource`만 의존하고 `URL, USERNAME, PASSWORD` 같은 속성을 몰라도 된다.  
애플리케이션을 개발해보면 보통 설정은 한 곳에서 하지만, 사용은 수 많은 곳에서 하게 된다.  
이 덕분에 객체 설정 부분과, 사용 부분을 좀 더 명확하게 분리할 수 있다.  


 - ## ConnectionPoot 테스트
    ```java
    @Slf4j
    class ConnectionTest {
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
            
            Thread.sleep(1000); //대기시간 1초
        }
    
        private void useDataSource(DataSource dataSource) throws SQLException {
            Connection con1 = dataSource.getConnection();
            Connection con2 = dataSource.getConnection();
            log.info("connection={}, class={}", con1, con1.getClass()); // conn0
            log.info("connection={}, class={}", con2, con2.getClass()); // conn1
        }
    }
    ```
   일반적으로 커넥션 풀에서 커넥션을 생성하는 작업은 애플리케이션 실행 속도에 영향을 주지 않기 위해 별도의 스레드에서 작동한다.
   별도의 스레드에서 동작하기 때문에 테스트가 먼저 종료되어 버리므로 Pool에 추가되는 과정이 제대로 진행되지 않는다. 
   따라서 테스트 실행 후 대기시간을 주어야 로그 확인이 가능하다.
    
```test/plain
jdbcUrl.........................jdbc:h2:tcp://localhost/~/jdbc
username........................"sa"
password........................<masked>
maximumPoolSize.................10
poolName........................"MyPool"
```
설정한 값들이 여러 로그들과 함께 사용할 수 있게 된다.

### MyPool Connection Adder ▼
```text/plain
MyPool - Added connection conn1: url=jdbc:h2:tcp://localhost/~/jdbc user=SA
MyPool - Added connection conn2: url=jdbc:h2:tcp://localhost/~/jdbc user=SA
MyPool - Added connection conn3: url=jdbc:h2:tcp://localhost/~/jdbc user=SA
MyPool - Added connection conn4: url=jdbc:h2:tcp://localhost/~/jdbc user=SA
MyPool - Added connection conn5: url=jdbc:h2:tcp://localhost/~/jdbc user=SA
MyPool - Added connection conn6: url=jdbc:h2:tcp://localhost/~/jdbc user=SA
MyPool - Added connection conn7: url=jdbc:h2:tcp://localhost/~/jdbc user=SA
MyPool - Added connection conn8: url=jdbc:h2:tcp://localhost/~/jdbc user=SA
MyPool - Added connection conn9: url=jdbc:h2:tcp://localhost/~/jdbc user=SA
```

로그를 통해 별도의 스레드를 사용해서 커넥션 풀을 채우고 있는것을 확인할 수 있다.  
이 쓰레드는 커넥션 풀에 커넥션을 최대 풀 수('10') 까지 채운다.  
커넥션 풀에 커넥션을 채우는것은 상대적으로 오래걸리는 일이다.  
애플리케이션을 실행할 때 커넥션 풀을 채울 때 까지 마냥 대기하고 있다면 애플리케이션 실행 시간이 늦어진다.  
따라서 별도의 스레드를 사용해 커넥션 풀을 채워야 애플리케이션 실행 시간에 영향을 주지 않게 된다.

### 커넥션 풀에서 커넥션 획득 ▼

```test/plain
connection=HikariProxyConnection@1151704483 wrapping conn0: url=jdbc:h2:tcp://localhost/~/jdbc user=SA, class=class com.zaxxer.hikari.pool.HikariProxyConnection
connection=HikariProxyConnection@28094269 wrapping conn1: url=jdbc:h2:tcp://localhost/~/jdbc user=SA, class=class com.zaxxer.hikari.pool.HikariProxyConnection
```
HikariProxyConnection은 Hikari가 Connection Pool에서 관리하는 Connection이다.  
HikariProxyConnection안에 실제적으로 wrapping된 JDBC Connection이 들어있다.  

전체 커넥션 10개, active(사용) 2개, idle(대기) 8개
```text/plain
16:15:34.482 [MyPool housekeeper] DEBUG com.zaxxer.hikari.pool.HikariPool -- MyPool - Pool stats (total=8, active=2, idle=6, waiting=0)
```

그렇다면 Connection Pool에 커넥션이 10개까지 차기 전에 Connection을 획득하면 어떻게 될까?
이때는 내부적으로 대기해준 뒤 커넥션이 채워지게되면 그때 반환한다.
(아무것도 없을때 1개를 요청한다면 1개가 찼을때 바로 반환해줌)

또한 설정한 최대 커넥션 수(10개)를 초과하게 된다면, ConnectionPool에 Connection이 확보될 때 까지 대기하고, 
HicariCP의 기본 설정에 의해 30초 후에 커넥션이 끊긴다.

```java
        Connection con1 = dataSource.getConnection();
        Connection con2 = dataSource.getConnection();
        Connection con3 = dataSource.getConnection();
        Connection con4 = dataSource.getConnection();
        Connection con5 = dataSource.getConnection();
        Connection con6 = dataSource.getConnection();
        Connection con7 = dataSource.getConnection();
        Connection con8 = dataSource.getConnection();
        Connection con9 = dataSource.getConnection();
        Connection con10 = dataSource.getConnection();
        Connection con11 = dataSource.getConnection(); // 커넥션 초과
```

```text/plain
java.sql.SQLTransientConnectionException: MyPool - Connection is not available, request timed out after 30009ms.

	at com.zaxxer.hikari.pool.HikariPool.createTimeoutException(HikariPool.java:696)
	at com.zaxxer.hikari.pool.HikariPool.getConnection(HikariPool.java:181)
	at com.zaxxer.hikari.pool.HikariPool.getConnection(HikariPool.java:146)
	at com.zaxxer.hikari.HikariDataSource.getConnection(HikariDataSource.java:128)
	at springdb.jdbc.connection.ConnectionTest.useDataSource(ConnectionTest.java:66)
	at springdb.jdbc.connection.ConnectionTest.dataSourceConnectionPool(ConnectionTest.java:45)
	at java.base/java.lang.reflect.Method.invoke(Method.java:568)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
```
테스트 이기 때문에 pool에 반환되는것이 없으므로 전체 연결이 끈기게 된다.
(대기 시간또한 설정법에 의해 설정이 가능하다.)


# 실제 Connection Pool 테스트
```java
@Slf4j
class MemberRepositoryV1Test {

    MemberRepositoryV1 memberRepository;

    @BeforeEach
    void beforeEach() {
        // 기본 DriverManager - 항상 새로운 커넥션 획득
//        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

        //커넥션 풀링 적용 (HikariCP)
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPoolName(PASSWORD);
        memberRepository = new MemberRepositoryV1(dataSource); // MemberRepositoryV1 생성자에 DataSource 주입
    }
    
}
```

```text/plain
getConnection=HikariProxyConnection@1078705341 wrapping conn0: (~ JDBC Connection 정보 생략)
getConnection=HikariProxyConnection@554868511 wrapping conn0:  (~ JDBC Connection 정보 생략)
getConnection=HikariProxyConnection@586358252 wrapping conn0:  (~ JDBC Connection 정보 생략)
getConnection=HikariProxyConnection@885002305 wrapping conn0:  (~ JDBC Connection 정보 생략)
getConnection=HikariProxyConnection@1066615508 wrapping conn0:  (~ JDBC Connection 정보 생략)
getConnection=HikariProxyConnection@1262548561 wrapping conn0:  (~ JDBC Connection 정보 생략)
```
connection은 모두 conn0 객체가 주입된다.
즉, 커넥션 풀 사용시 conn0이 재사용 된 것을 확인할 수 있으며, 이는 테스트 로직내부에서 순서대로 실행되기 때문에 동일한 커넥션을 사용한뒤 반환한 객체를 다시 돌려주는것을 반복한 것이다.  
따라서 conn0만 사용된 것이다.
예를들어 다른 스레드를 통해 다른 기능이 호출되어 JDBC Connection객체를 사용하게 되면 커넥션 풀에 존재하는 다른 Connection객체 (conn1)을 반환받아,  
해당 기능(트랜잭션단위)이 종료될때 까지가 하나의 스레드내에서 동작되므로 웹 어플리케이션에 동시에 여러 요청이 들어오면 여러 스레드에서 커넥션 풀의 커넥션을 다양하게 꺼내 쓰는 상황을 확인할 수 있다.  

이때 직접 꺼내 사용하기 전 Pool에 존재하는 Connection들은 Proxy 객체로 존재하고 있다.  
히카리 객체가 생성될 때 Proxy객체의 실질적인 주소 인스턴스는 사실 다르다. (@~~~~~~~~ 주소값)   
생성된 히카리 Connection객체를 Pool에서 직접적으로 꺼내어 사용할 때  
실질적으로 JDBC Connection객체(conn0)이 각각의 Hicari프록시 객체에 Wrapping되어 제공된다. 

DI 관점에서 DriverManagerDataSource에서 HikariDataSource로 변경을 해도 Repository의 코드는 전혀 변경하지 않아도 된다.
Repository는 DataSource 인터페이스에만 의존하기 때문이다.
이것이 DataSource를 사용하는 장점이다.

# 트랜잭션
트랜잭션 이름 그대로 번역하면 `거래` 라는 뜻이다.  
데이터베이스에서 트랜잭션은 하나의 거래를 안전하게 처리하도록 보장해주는 것을 뜻한다.  
하나의 거래를 안전하게 처리하려면 생각보다 고려할 점이 많다.
A의 5000을 B에게 계좌이체 한다고 가정해보자.
A의 잔고를 5000원 감소하고, B의 잔고를 5000원 증가해야 한다.

- [ 트랜잭션 ] 5000원 계좌 이체
  1) A의 잔고 5000원 감소
  2) B의 잔고 5000원 증가

위와같이 계좌이체라는 거래는 2가지 작업이 합쳐져서 하나의 작업처럼 동작해야 한다.  
만약 1번은 성공했으나, 2번에서 시스템 문제가 발생하면 계좌이체는 실패하고 A의 잔고만 5000원 감소하는 문제가 발생한다.
이때 데이터베이스가 제공하는 트랜잭션이라는 기능을 사용하면 1, 2번 둘다 함께 성공해야 저장하고, 중간에 하나라도 실패하면  
거래 전의 상태로 돌아갈 수 있다.  
1번은 성공했는데 2번에서 시스템 문제가 발생하면 계좌이체는 실패하고, 거래 전의 상태로 완전히 돌아가 결과적으로 A의 잔고가 감소하지 않게된다.  
모든 작업이 성공해 데이터베이스에 정상 반영하는 것을 `Commit` 이라 하고,   
작업 중 하나라도 실패해서 거래 이전으로 되돌리는 것을 `Rollback` 이라 한다.

# 트랜잭션 ACID

- `원자성`: 트랜잭션 내에서 실행한 작업들은 마치 하나의 작업인 것처럼 모두 성공 하거나 모두 실패해야 한다.  
- `일관성`: 모든 트랜잭션은 일관성 있는 데이터베이스 상태를 유지해야 한다.  
           예를들어 데이터베이스에서 정한 무결성 제약 조건을 항상 만족해야 한다.  
- `격리성`: 동시에 실행되는 트랜잭션들이 서로에게 영향을 미치지 않도록 격리한다.  
           예를들어 동시에 같은 데이터를 수정하지 못하도록 해야한다.  
           격리성은 동시성과 관련된 성능 이슈로 인해 트랜잭션 격리 수준(Isolation level)을 선택할 수 있다.  
- `지속성`: 트랜잭션을 성공적으로 끝내면 그 결과가 항상 기록되어야 한다.  
           중간에 시스템에 문제가 발생해도 데이터베이스 로그 등을 사용해서 성공한 트랜잭션 내용을 복구해야 한다.  

트랜잭션은 원자성, 일관성, 지속성,을 보장한다.  
문제는 `격리성`인데 트랜잭션 간에 격리성을 완벽히 보장하려면 트랜잭션을 거의 순서대로 실행해야 한다.  
(예를들어 멀티스레드가 동시에 100개의 요청이 온다면 조금이라도 빨리 온 요청 먼저 처리해주고 나머지는 모두 대기해야한다.)
이렇게 하면 동시 처리 성능(병렬처리)이 매우 나빠진지기 때문에, ANSI표준은 트랜잭션 격리 수준을 4단계로 나누어 정의했다.

### 트랜잭션 격리 수준
 1) READ UNCOMMITED(커밋되지 않은 읽기)
 2) READ COMMITED(커밋된 읽기)
 3) REPEATALBLE READ(반복 가능한 읽기)
 4) SERIALIZABLE(직렬화 기능)
 
데이터베이스 입장에서 단계가 높아질수록 성능은 점점 느려지는 반면 순서대로 처리하기 때문에, 현재 스레드의 트랜잭션에 대한 데이터에 대해서  
다른 트랜잭션이 관여할 수 없도록 막을 수 있다.

# 데이터베이스 연결 구조와 DB SESSION

 ### 사용자 → `클라이언트` [커넥션]→[커넥션] `데이터베이스 서버`

기본적으로 사용자가 웹 애플리케이션 서버나 DB 접근 툴 같은 클라이언트를 사용해서 데이터베이스 서버에 접근할 수 있다.  
클라이언트는 데이터베이스 서버에 연결을 요청하고 커넥션을 맺게 된다.  
이때 데이터베이스 서버는 내부에 `세션`이라는 것을 만들며, 해당 커넥션을 통한 모든 요청은 만들어진 `세션`을 통해 실행하게 된다.  
개발자가 클라이언트를 통해 SQL을 전달하면 현재 커넥션에 연결된 세션이 SQL을 실행한다.  
(개발자가 클라이언트를 통해 커넥션에 SQL을 전달하면 해당 커넥션에 연결된 세션이 SQL을 실행한다)  
세션은 트랜잭션을 시작하고, 커밋 또는 롤백을 통해 트랜잭션을 종료하며, 이후 새로운 트랜잭션을 다시 시작할 수 있다.  
(즉 Session이 SQL을 실행하고, 트랜잭션의 생명주기를 관리한다.)  
사용자가 커넥션을 닫거나, 또는 DBA(DB 관리자)가 세션을 강제로 종료하면 세션은 종료된다.
만약 커넥션 풀이 10개의 커넥션을 생성하면, 데이터베이스 내부에 세션 또한 10개 만들어진다.  
(각 커넥션은 각 세션과 연결되어있음.)  

 ### 트랜잭션 사용법
 - 데이터 조작 쿼리를 실행하고, 데이터베이스에 결과를 반영하기 위해 커밋 명령어인 `commit`을 호출하고,
   결과를 반영하고 싶지 않으면 롤백 명령어인 `rollback`을 호출한다.
 - 커밋을 호출하기 전까지는 임시로 데이터를 저장 하는 것이다. 
   따라서 해당 트랜잭션을 시작한 세션(사용자)에게만 변경 데이터가 보이고 다른 세션 (사용자)에게는 변경 데이터가 보이지 않는다.
 - 등록, 수정, 삭제 모두 같은 원리로 동작한다.

| member_id | name  | money | 상태  |
|-----------|-------|-------|-----|
| oldId     | 기존 회원 | 10000 | 완료  |
| 　         |       |       |     |
| 　         |       |       |     |
| 　         |       |       |     |

데이터베이스에 위와같은 데이터가 저장되어 있고 A, B 두개의 세션이 있다고 가정해보자.  
세션 A와 세션 B 둘다 테이블을 조회한다면 두 세션 모두 해당 데이터가 그대로 조회될것이다.


세션A가 트랜잭션을 시작하고, 신규회원1, 신규회원2를 DB에 추가한다. (커밋은 하지 않은 상태.)
 - 세션A 조회 시점
    
    | member_id | name   | money | 상태      |
    |---------|----------|-------|---------|
    | oldId     | 기존 회원  | 10000 | 완료      |
    | newId1　   | 신규 회원1 | 20000 | 임시(A) |
    | newId1　   | 신규 회원1 | 20000 | 임시(A) |
    | 　         |        |       |         |

    새로운 데이터는 위와 같이 임시 상태로 저장되며 데이터를 추가한 세션 A에게만 SELECT쿼리를 실행하여 임시 상태의 데이터를 조회할 수 있다.  

 - 세션B 조회 시점
    
    | member_id | name  | money | 상태  |
    |-----------|-------|-------|-----|
    | oldId     | 기존 회원 | 10000 | 완료  |
    | 　         |       |       |     |
    | 　         |       |       |     |
    | 　         |       |       |     |
    세션 B는 세션 A가 추가한 데이터가 아직 커밋되지 않았으므로 DB로부터 세션 A가 저장한 신규 회원 데이터를 조회할 수 없다.  

만약 위와같은 상황에서 세션 A가 커밋하지 않은 데이터를 세션 B에서 조회가 된다면, 데이터 정합성에 큰 문제가 발생한다.  
예를들어 세션 B가 신규회원1과 신규회원2에 대해 money를 감소시키는 등의 기능을 수행하고, 세션 A가 롤백을 수행하면  
신규 회원1과 신규회원2 두 데이터 모두 삭제된다.  
따라서 커밋 전의 데이터는 다른 세션에서 조회되면 안된다.  
이것이 트랜잭션 격리 수준에서 READ COMMITED(커밋된 읽기) 이다.  

# DB 락
세션 1이 트랜잭션(set autocommit false)을 시작하고 데이터를 수정하는 동안 아직 커밋을 수행하지 않았는데,  
세션 2에서 동시에 같은 데이터를 수정하게 되면 여러가지 문제가 발생한다.  
바로 트랜잭션의 원자성이 깨지는 것이다.  
여기에 더해 세션1이 중간에 롤백을 하게 되면 세션2는 잘못된 데이터를 수정하는 문제가 발생한다.  
이러한 문제를 방지하기 위해 세션이 트랜잭션을 시작하고 데이터를 수정하는 동안에는 커밋이나 롤백 (트랜잭션 종료 시점)전까지  
다른 세션에서 해당 데이터를 수정할 수 없게 막아야 한다.  

세션1은 `memberA`의금액을 500원으로 변경하고 싶고, 세션2는 같은 `memberA`의 금액을 1000원으로 변경하고 싶어한다.  
데이터베이스는 이렇게 동시에 데이터를 수정하는 문제를 해결하기 위해 락(Lock)이라는 개념을 제공한다.
테이블에 저장된 데이터의 각 로우는 락이 존재한다.  
트랜잭션이 시작되고 데이터 변경을 시도할 때, 변경할 로우의 락을 획득해야 데이터 수정이 가능하다.  
다른 세션이 동일한 로우에 접근하여 데이터 변경을 시도한다면, 락이 존재하지 않으므로 락을 획득할 때 까지 대기한다.  
이때 락을 무한정 대기하는것만은 아니다.  
락 대기시간을 설정할 수 있으며 설정한 대기시간을 넘어가면 락 타임아웃 오류가 발생한다.
(1초간 락 타임아웃 설정 명령 `SET LOCK_TIMEOUT 10000;`)
 ### 조회 Lock - *for update*
일반적인 조회는 락을 사용하지 않는다.
데이터베이스마다 다르지만, 보통 데이터를 조회할 때는 락을 획득하지 않고 바로 데이터를 조회할 수 있다.  
예를 들어 세션1이 락을 획득하고 데이터를 변경하고 있어도, 세션2에서 데이터를 조회할 수 있다.  
물론 세션2에서 조회가 아니라 데이터를 변경하려면 락이 필요하기 때문에 락이 돌아올 때 까지 대기해야 한다.  

데이터를 조회하고 커밋할 때 까지 다른 데이터 변경을 원치 않는 경우가 있다.
위와 같이 데이터를 조회할 때도 락을 획득하고 싶을 때 사용하는 명령어는 `SELECT FOR UPDATE` 구분을 사용한다.  
이렇게 하면 해당 명령을 실행한 세션이 조회시점에 락을 가져가버리기 때문에 다른 세션에서 해당 데이터를 변경할 수 없다.    
(물론 이 경우에도 트랜잭션을 커밋하면 락을 반납한다.)  
ex) select * from [테이블명] where [조건] `for update`;

# 트랜잭션 적용 계층
트랜잭션은 비즈니스 로직이 있는 서비스 계층 에서 시작해야 한다.  
비즈니스 로직이 잘못되면 해당 비즈니스 로직으로 인해 문제가 되는 부분을 함께 롤백해야 하기 때문이다.  
트랜잭션을 시작하려면 커넥션이 필요하기 때문에, 결국 서비스 계층에서 커넥션을 만들고, 트랜잭션 커밋 이후에 커넥션을 종료해야 한다.  
애필르케이션에서 DB 트랜잭션을 사용하려면 `트랜잭션을 사용하는 동안 같은 커넥션을 유지`해야 같은 세션을 사용할 수 있다.
