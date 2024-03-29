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

은행으로 예를들면 아래와 같은 현상이 발생할 수 있다.  
A에서 5000원을 B에게 송금한다.  
A에는 5000원이 빠져나가 0원이 된다.  
이때 B에 5000원을 더하는 과정중 오류가나서 롤백된다.  
결과적으로 A에게서 빠진 5000이 없는돈이 되어버린다.  

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

# *트랜잭션 적용 계층*
트랜잭션은 비즈니스 로직이 있는 서비스 계층 에서 시작해야 한다.  
비즈니스 로직이 잘못되면 해당 비즈니스 로직으로 인해 문제가 되는 부분을 함께 롤백해야 하기 때문이다.  
트랜잭션을 시작하려면 커넥션이 필요하기 때문에, 결국 서비스 계층에서 커넥션을 만들고, 트랜잭션 커밋 이후에 커넥션을 종료해야 한다.  
애필르케이션에서 DB 트랜잭션을 사용하려면 `트랜잭션을 사용하는 동안 같은 커넥션을 유지`해야 같은 세션을 사용할 수 있다.


# *Connection, Session, Connection Pool, Transaction 연관관계 및 생명주기*
커넥션풀을 사용하면 커넥션풀에 각각 다른 커넥션이 생성된다.(10개)  
하나의 커넥션당 하나의 세션이 생성되며, 커넥션의 생명주기에 종속되어 있다.  
세션은 트랜잭션을 시작하고 커밋을 관리한다.  
커넥션 풀을 사용하면 하나의 스레드내에 각각의 쿼리에 대한 작업 단위가 동일한 커넥션을 반환한다.
커넥션이 동일하다면 세션도 동일하므로 하나의 트랜잭션으로 관리된다? X
→ @Transaction을 걸지 않으면 각각의 트랜잭션으로 처리됨

@Transaction을 거는 순간
같은 커넥션(세션)에 대한 작업들이 하나의 트랜잭션으로 구성된다.  
(기본적으로는 같은 커넥션이여도 각각의 쿼리 호출 단위로 트랜잭션이 처리됨)  

# TransactionManager

스프링에서 제공하는 TransactionManager는 크게 2가지 역할을 한다.
1) 트랜잭션 추상화
2) 리소스 동기화

## 트랜잭션 추상화

- JDBC: `con.setAutoCommit(false)`
- JPA : `transaction.begin`

```java
Connection con = Datasource.getConnection();
con.setAutoCommit(false); //트랜잭션 시작
logic(em);
con.commit(); // 커밋
```

```java
EntityManagerFactory emf = Persistence.createEntityManagerFactory("transaction")
EntityManager em = emf.createEntityManager();
EntityTransaction tx = em.getTransaction();
tx.begin(); //트랜잭션 시작
logic(em);
tx.commit(); // 커밋
```

트랜잭션을 사용하는 코드는 데이터 접근 기술마다 다르기 때문에,
위와같이 JDBC에서 JPA로 데이터 접근 기술을 변경하게 될 경우
서비스 계층의 트랜잭션을 처리하는 코드도 모두 함께 변경되어야 한다.

이는 단일책임 원칙을 위배한다.
즉, 변경 포인트가 하나일 때 버그가 다연발로 여러곳에서 수정해야한다.

트랜잭션을 추상화하면 해결 가능하다.
```java
public interface TxManager {
	begin();
	commit();
	rollback();
}
```
```java
public JdbcTxManager implements TxManager {}
```
```java
public JpaTxManager implements TxManager {}
```

스프링이 제공하는 트랜잭션 추상화 기능을 사용하면 된다.
(데이터 접근 기술에 따른 트랜잭션 구현체도 대부분 구현되어 있음)

### `PlatformTransactionManager`
```java
public interface PlatformTransactionManager extends TransactionManager {
	/* Transaction 시작 메소드 */
	TransactionStatus getTransaction(@Nullable TransactionDefinition definition) throws TransactionException;
	/* Commit Rollback 메소드 */
	void commit(TransactionStatus status) throws TransactionException;
	void rollback(TransactionStatus status) throws TransactionException;
}
```
- `getTransaction()` : 트랜잭션을 시작한다.
    - getTransacton의 의미 -기존 진행중인 트랜잭션이 있는 경우 해당 트랜잭션울 가져와 참여할 수 있기 때문.
- `commit()`
- `rollback()`


## 리소스 동기화
트랜잭션을 유지하려면 트랜잭션의 시작부터 끝까지 같은 데이터베이스 커넥션을 유지해야 한다.  
같은 커넥션을 동기화하기 위해서 스프링은 트랜잭션 동기화 매니저를 제공한다.  
`TransactionSycnchronizationManager`

- 트랜잭션 매니저 내부에서 작동하는 트랜잭션 동기화 매니저는 쓰레드 로컬(`ThreadLocal`)을 사용해서 커넥션을 동기화해준다.  
- 트랜잭션 동기화 매니저는 쓰레드 로컬을 사용하기 때문에 멀티쓰레드 상황에서 안전하게 커넥션을 동기화 할 수 있으므로  
  동일한 커넥션이 필요하면 트랜잭션 동기화 매니저를 통해 커넥션을 획득한다.

### 동작 방식
1) 데이터 소스로 부터 트랜잭션 시작을 위한 커넥션을 획득 후, 트랜잭션 시작.
2) 트랜잭션 매니저는 트랜잭션이 시작된 커넥션을 트랜잭션 동기화 매니저에 보관.
3) 리포지토리는 트랜잭션 동기화 매니저에 보관된 커넥션을 꺼내 사용.
4) 트랜잭션 종료 후 트랜잭션 매니저는 트랜잭션 동기화 매니저에 보관된 커넥션을 통해 트랜잭션 종료 및 커넥션 close

### ThreadLocal
각각의 쓰레드마다 별도의 저장소가 부여되므로 해당 쓰레드만 해당 데이터에 접근가능.

# DataSourceUtils를 통해 Connection 획득 및 release
```java
    private void close(Connection con, Statement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        DataSourceUtils.releaseConnection(con, dataSource); //Connection Release
    }
    
    private Connection getConnection() throws SQLException {
        Connection con = DataSourceUtils.getConnection(dataSource);
        return con;
    }
```
PlatformTransactionManager로부터 TransactionStatus객체를 반환받는다.  
(DefaultTransactionDefinition객체를 넘겨준다)  
반환받은 TransactionStatus 객체로 부터 commit rollback을 호출할 수 잇다.
```java
    private final PlatformTransactionManager transactionManager; // Test코드에서 DataSourceTransactionManager 혹은 JpaTransactionManager를 주입받을 예정
    private final MemberRepositoryV3 memberRepository;
    
    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        //트랜잭션 시작
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            bizLogic(fromId, toId, money); // 비즈니스 로직
            transactionManager.commit(status); // 성공시 커밋
        } catch (Exception e) {
            transactionManager.rollback(status); // 실패시 롤백
            throw new IllegalStateException(e);
        }
    }

```

실제 수행시 DataSourceTransactionManager를 Service의 PlatformTransactionManager에 주입해야 한다.
이때, DataSourceTransactionManager에는 Repository에 주입되는 동일한 Datasource가 주입되어야 한다.
```java
    @BeforeEach // 각 테스트별 테스트 수행전 실행된다.
    void before() {
        //의존성 주입
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        memberRepository = new MemberRepositoryV3(dataSource);
        PlatformTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
        memberService = new MemberServiceV3_1(dataSourceTransactionManager, memberRepository);
    }
```

Test코드에서 service코드 호출 전 PlatformTransactionManager의 구현체인 DataSourceTransactionManager에 의존성을 주입하며,  
이때 DataSourceTransactionManager에 DataSource를 주입하며, Repository에도 동일한 DataSource를 주입한다.  

서비스 계층의 Transaction이 적용될 특정 기능이 호출되었을 때, transaction 트랜잭션이 시작하는 시점에 주입받은   
DataSourceTransactionManager로 부터 getTransaction()메소드를 호출함으로써 트랜잭션을 시작한다.    
트랜잭션이 시작하기에 앞서 트랜잭션 매니저에 초기화한 DataSource를 통해 트랜잭션 매니저 내부적으로 커넥션을 생성한다.  

생성된 커넥션은 트랜잭션 동기화 매니저에 보관한다.  
`getTransaction` → {doGetTransaction → {`TransactionSynchronizationManager의 getResources`}}  
커넥션을 수동커밋 모드로 변경하여 실제 데이터베이스 트랜잭션을 시작한다.  
`getTransaction` → {startTransaction → {`doBegin`}}  
커넥션이 시작된 후 커넥션을 동기화 매니저에 보관하는데, 이때 트랜잭션 동기화 매니저는 쓰레드 로컬에 커넥션을 보관한다.    
(멀티 쓰레드 환경에서 안전하게 커넥션 보관)  
`getTransaction` → {startTransaction → {doBegin → {`TransactionSynchronizationManager의 bindResource`}}}  

이렇게 트랜잭션이 시작하고 Repository로부터 실제 JDBC를 수행하는 기능을 호출한다.  
해당 기능들은 DataSourceUtils의 getConnection을 호출함으로써 트랜잭션 동기화 매니저에 보관된 커넥션을 꺼내어 사용한다.  
위 과정을 통해 자연스럽게 같은 커넥션을 사용하고, 트랜잭션도 유지가 된다.  
(Service에 트랜잭션 매니저를 주입하는 시점에 해당 트랜잭션 매니저에 주입된 DataSource와 동일한 DataSource를 Repository에도 함께 주입하기 때문)  

getTransaction에 의해 반환받은 TransactionStatus 객체로 부터 Commit 혹은 Rollback을 호출하면, 트랜잭션이 종료된다.  
`commit` → {processCommit → {cleanupAfterCompletion → {resume → {doResumeSynchronization → {resume → {`TransactionSynchronizationManager의 bindResource`}}}}}}  
`rollback` → {processRollback → {cleanupAfterCompletion → {resume → {doResumeSynchronization → {resume → {`TransactionSynchronizationManager의 bindResource`}}}}}}  

만약 JPA 기술을 사용한다면 DataSourceTransactionManager 대신 JpaTransactionManager를 PlatformTransactionManager의 구현체로 Service에 주입한다.  

# 트랜잭션 템플릿 - 템플릿 콜백 패턴
일반적으로 TransactionManager를 사용하여 트랜잭션 시작, 비즈니스로직 호출, 성공시 커밋, 예외발생시 롤백한다.  
코드는 `try~catch~finally`를 포함한 `성공-커밋`, `실패-롤백` 형태로 구현되어 있으며 다른 서비스에서도 해당 코드 형태가 반복될것이다.  
템플릿 콜백 패턴을 활용하면 이런 반복되는 코드형태의 문제를 해결할 수 있다.
```java
public class TransactionTemplate {
    private PlatformTransactionManager transactionManager;
    public <T> execute(TransactionCallback<T> action) {/* ... */}
    void executeWithoutResult(Consumer<TransactionStatus> action) {/* ... */}
}
```

- `execute()`: 반환값이 있을 때 사용.
- `executeWithoutResult()`: 반환 값이 없을 때 사용.

## 예제 코드

### 기존 코드는 아래와 같다.
```java
@RequiredArgsConstructor
public class MemberServiceV3_1 {
    private final PlatformTransactionManager transactionManager;
    private final Repository repository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        //트랜잭션 시작
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            bizLogic(fromId, toId, money); // 비즈니스 로직
            transactionManager.commit(status); // 성공시 커밋
        } catch (Exception e) {
            transactionManager.rollback(status); // 실패시 롤백
            throw new IllegalStateException(e);
        }
    }
}
```

### TransactionTemplate - Service에서의 의존성 주입
생성자에 의해 Test코드로부터 PlatformTransactionManager을 주입받은 TransactionTemplate을 주입한다.
```java
public class Service { 
    private final TransactionTemplate txTemplate;
    private final Repository repository;
    public MemberServiceV3_2(PlatformTransactionManager transactionManager, Repository repository) {
        this.txTemplate = new TransactionTemplate(transactionManager);
        this.memberRepository = memberRepository;
    }
}
```

### 템플릿 사용 로직
익명 구현 객체를 람다식으로 구현하며, 실행할 비즈니스로직을 작성한다.  
(람다식의 매개변수는 TransactionStatus 객체이다. - `Consumer<TransactionStatus>`)  
이때 TransactionTemplate의 execute/executeWithoutResult에서는 SQLException을 처리할 수 없으므로 try~catch 구문을 사용하여  
발생하는 SQLException을 직접 던져준다.
```java
```java
txTemplate.executeWithoutResult((status) -> {
    try {
        //비즈니스 로직
        bizLogic(fromId, toId, money);
    } catch (SQLException e) {
        throw new IllegalStateException(e);
    }
});

```
# 선언적 트랜잭션 관리 - @Transaction과 Spring AOP

## 프록시 - 트랜잭션 적용 분리
트랜잭션을 처리하는 객체와 비즈니스 로직을 처리하는 서비스객체를 명확하게 분리할 수 있다.  
트랜잭션 프록시에 트랜잭션 처리 로직을 모두 전가하고, 해당 프록시에서 트랜잭션 시작 후 실제  서비스를 호출한다.  
트랜잭션을 처리하는 프록시 코드는 스프링이 제공하는 AOP에 의해 생성되고 스프링 빈으로 등록한다.

## Spring 트랜잭션 AOP  
스프링이 제공하는 트랜잭션 AOP기능을 사용하면 프록시를 매우 편리하게 적용 가능하다.  
트랜잭션 처리가 필요한 곳에 @Transaction 애노테이션만 붙여주면 된다.

## Spring AOP와 트랜잭션 Proxy의 원리
@Transaction이 붙어있으면 Spring이 해당 서비스를 상속받아 트랜잭션Proxy로 재구현되며,  
Proxy에 구현된 트랜잭션 적용 로직에서 @Transaction 애노테이션으로 적용된 메소드가 호출된다.  
이후 해당 로직이 적용된 트랜잭션Proxy(Service)가 Spring Bean에 등록된다.  
트랜잭션 Proxy에 의해 관리되는 메소드가 호출될 경우 AOP에 의해 Transaction이 적용된다.  
이때 서비스를 상속받아 트랜잭션이 적용되는 메소드는 @Transaction 어노테이션의 레벨에 따라 달라진다.  
클래스레벨일 경우 모든 메소드에 적용, 메소드레벨일 경우 해당 메소드에만 적용  
(메소드 레벨에만 적용되더라도 Service 인스턴스 자체가 Proxy로 감싸지지만, 트랜잭션은 애노테이션이 붙은 메소드에만 적용된다.)

## Service 코드
기존 TransactionTemplate를 걷어내고, 비즈니스 메소드만 남겨두고 메소드 레벨에 @Transactional 애노테이션만 붙여준다.
```java
@Transactional
public void accountTransfer(String fromId, String toId, int money) throws SQLException {
	bizLogic(fromId, toId, money);
}
```

## Test 코드

### @SpringBootTest
테스트 코드에서 Spring AOP를 적용하려면 Spring 컨테이너가 필요하다.  
`@SpringBootTest` 애노테이션을 선언하면 테스트시 SpringBoot를 통해 Spring컨테이너를 생성하며,  
@Autowired등을 통해 Spring컨테이너가 관리하는 빈들을 사용할 수 있다.
### @TestConfiguration
기존 생성자로 주입하던 DataSource와, PlatformTransactionManager 또는 Service와 Repository등이   
이제는 @SpringBootTest에 의해 Spring 테스트로 진행하게 되기 때문에 해당 객체들을 Spring컨테이너에  
빈으로 등록해야 테스트를 수행할 수 있다.  
따라서 `@TestConfiguration` 애노테이션을 선언하여 테스트클래스 안에 내부 설정클래스를 만들어  
의존성 주입이 필요한 클래스들을 @Bean애노테이션을 통해 빈으로 등록하여 관리한다.

```java
@SpringBootTest // Spring Bean에 대한 의존관계 주입이 된다.
class TransactionAopTest {
	@Autowired
    private MemberRepositoryV3 memberRepository;
    @Autowired
    private MemberServiceV3_3 memberService;
	
	@TestConfiguration
	static class TestConfig {
        @Bean
        DataSource dataSource() {
            return new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        }
        @Bean
        PlatformTransactionManager transactionManager() {
            return new DataSourceTransactionManager(dataSource());
        }
        @Bean
        MemberRepositoryV3 memberRepositoryV3() {
            return new MemberRepositoryV3(dataSource());
        }
        @Bean
        MemberServiceV3_3 memberServiceV3_3() {
            return new MemberServiceV3_3(memberRepositoryV3());
        }
    }
}
```
@Transaction 애노테이션에 의해 트랜잭션이 적용된 Service의 비즈니스로직을 호출할 경우  
트랜잭션 AOP 프록시에 의해 트랜잭션이 시작(TxManager-getTransaction)되면, 트랜잭션 매니저로부터 트랜잭션을 시작(setAutoCommit-false)하고,  
트랜잭션 매니저 주입당시 트랜잭션 매니저에 초기화된 받은 DataSource로 부터 Connection을 생성하여 트랜잭션 동기화 매니저에 보관한다.    
AOP에 의해 실제 서비스의 비즈니스로직이 호출되고, 동일한 DataSource를 주입받은 리포지토리의 데이터접근로직이 호출되면    
트랜잭션 동기화 매니저로부터, 트랜잭션 동기화 커넥션을 획득하게된다.  
(리포지토리 비즈니스로직이 2번 호출된더라도 JDBC를 실행하기 위한 Connection은  DataSourceUtils의 getConnection 즉, 동기화된 동일한 커넥션을 계속해서 유지한다.)  

# SpringBoot 자동 Resource 등록

제목 그대로 SpringBoot는 특별한 빈등록이 없다면, DataSource와 TransactionManager를 자동으로 등록해 준다.  
바로 위에서는 @TestConfiguration에서 DriverManagerDataSource와 PlatformTransactionManager를 Bean으로 등록했다.  
이렇게 개발자가 직접 데이터소스, 트랜잭션매니저를 빈으로 등록하면 SpringBoot는 두 객체를 자동으로 빈 등록하지 않는다.  

### DataSource 자동 등록
```properties
spring.datasource.url=jdbc:h2:tcp://localhost/~/db명
spring.datasource.username=sa
spring.datasource.password=
```
데이터 소스에 대한 빈 등록 로직을 직접 작성하지 않는다면 SpringBoot는 application.properties에 위와같이 입력된  
DataSource에 대한 정보를 사용해 DataSource를 생성하고 Spring Bean에 등록한다.  
(`spring.datasource.url` 속성이 없으면 내장 데이터베이스-메모리DB를 생성하려고 시도한다.)  
### TransactionManager 자동 등록
트랜잭션 매니저가 자동으로 등록될 때 어떤 트랜잭션 매니저를 선택할지는 현재 등록된 라이브러리를 보고 판단한다.  
(application.properties의 url 가장 앞에 jdbc로 시작하므로 DataSourceTransactionManager가 적용.)
JDBC기술을 사용하면 DataSourceTransactionManager를 Bean으로 등록하고, JPA 혹은 JDBC 둘다 사용하면 JpaTransactionManger를 등록한다.   
(JpaTransactionManager는 DataSourceTransactionManager가 제공하는 기능도 대부분 지원한다.)  

# 자바 예외
- `Checked`: 컴파일러가 체크하는 예외
- `UnChecked`: 컴파일러가 체크하지 않는 예외 

 - ### Throwable - 최상위 예외
   - **Exception (`Checked`)**  
     애플리케이션 로직에서 사용할 수 있는 실질적 최상위 예외로 `Exception`과 그 하위 예외는 모두 컴파일러가 체크하는 `Check`예외이다.  
     (단, `RuntimeException`은 `UnChecked`임.)
     - SQLException
     - IOException
     - **RuntimeException (`UnChecked`)**  
       - NullPointerException
       - IllegalArgumentException
   - **Error**  
        메모리 부족 혹은 심각한 시스템 오류와 같은 애플리케이션에서 복구가 불가능한 시스템예외  
        개발자는 해당 예외를 잡으려고 해서는 안된다. (그냥 두면 됨)
     - 일반적으로 상위 예외를 Catch로 잡으면 하위 예외까지 잡는데, 만약 애플리케이션 로직에서 `Throwable` 예외를 잡으면
       Error예외도 함께 잡을 수 있기 때문이다.
     - OutOfMemoryError

    ## Check 예외 기본 규칙
    예외는 폭탄 돌리기와 같다.  
    잡아서 처리하거나, 처리할 수 없으면 밖으로 던져야 한다.  
    
    예를들어 `Controller` → `Service` → `Repository` 구성에서 Repository에서 Exception이 발생한다.  
    만약 `Repository`에서 예외를 처리하지 못한다면 `Service`에게 예외를 던져야 한다.  
    `Service`에서 예외를 처리하게 되면, 이후에는 애플리케이션 로직이 정상 흐름으로 동작한다.  
    반면, `Service` 에서 예외를 처리하지 못한다면, `Service`를 호출한 `Controller`에 예외를 던진다.  
    이와같이 예외를 처리하지 못하면 호출한 곳으로 계속해서 예외를 던지게 된다.

    ### 2가지 기본 규칙
     1) 예외는 잡아서 처리하거나 던져야 한다.
     2) 잡거나 던질 때 지정한 예외 뿐만 아니라 그 예외의 자식들도 함께 처리된다.
        - `Exception`을 `catch`로 잡으면 그 하위 예외들도 모두 잡을 수 있다.
        - `Exception`을 `throw`로 던지면 그 하위 예외들도 모두 던질 수 있다.
     
    <br>

    #### 만약 이러한 예외를 처리하지 못하고 계속해서 메소드 호출부로 예외를 던지면 어떻게 될까?  
    자바 `main()`  쓰레드의 경우 예외 로그를 출력하면서 시스템이 종료된다.
    하지만 웹 애플리케이션의 경우 여러 사용자의 요청을 처리하므로 하나의 예외 때문에 시스템이 종료되면 안되기 때문에  
    WAS가 해당 예외를 받아 처리하는데, 주로 사용자에게 개발자가 지정한 오류페이지를 보여준다.  

    #### Checked 예외의 장단점
    체크 예외는 예외를 잡아 처리할 수 없을 때 예외를 밖으로 던지는 `throws Exception`을 필수로 선언해야 한다.  
    그렇지 않으면 컴파일 오류가 발생하며, 이러한 Checked Exception의 방식은 장점과 단점이 동시에 존재한다.  
    - 장점: 개발자가 실수로 예외를 누락하지 않도록 컴파일러를 통해 문제를 잡아주는 훌륭한 안전장치이다.
    - 단점: 실제로는 개발자가 모든 체크 예외를 반드시 잡거나 던지도록 처리해야 하기 때문에, 너무 번거로우며, 크게 신경쓰고 싶지 않은
        예외까지 모두 챙겨야 한다. (의존관계에 따른 단점도 존재한다.)

    ## UnChecked 예외 기본 규칙
    RuntimeException과 그 하위 예외는 모두 Unchecked 예외로 분류되며 Unchecked 예외란 말그대로 컴파일러가 예외를 체크하지 않는다는 뜻이다.    
    기본적으로 Checked예외와 동일 즉, 모든 예외는 잡아서 처리하거나 던져야 하지만 차이가 있다면 예외를 던지는 `throws`를 선언하지 않고, 생략이 가능하다.  
    (이 경우 자동으로 예외를 던짐)

    ### CheckedException vs UnCheckedException  
     - `Checked-Exception`: 예외를 잡아서 처리하지 않으면 항상 `throws`에 던지는 예외를 선언해야 함
     - `UnChecked-Exception`: 예외를 잡아서 처리하지 않아도 `throws` 생략 가능  
   
    언체크 예외도 `throws 예외`를 적용해도 되며, 주로 생략하지만, 중요한 예외의 경우 이렇게 선언해 두면 해당 코드를 호출하는 개발자가 이런 예외가 발생한다는 점을 
    IDE를 통해 좀 더 편리하게 인지할 수 있다. (컴파일 시점에 막을 수 있는 것은 아니고, IDE를 통해 인지할 수 있는 정도)

    ### Unchecked 예외의 장단점
     - `장점`: 신경 쓰고 싶지 않은 언체크 예외를 무시할 수 있음.  
            체크예외의 경우 처리할 수 없는 예외를 밖으로 던지려면 항상 `throws 예외`를 선언해야 하지만, 언체크 예외는 이 부분 생략이 가능하다.  
            또한 신경쓰고 싶지 않은 예외의 의존관계를 참조하지 않아도 되는 장점이 있다.
     - `단점`: 언체크 예외는 개발자가 실수로 예외를 누락할 수 있음.  
            (반면 체크 예외는 컴파일러를 통해 예외 누락을 잡아준다.)

# 체크 예외 활용

- 기본적으로는 언체크(런타임) 예외를 사용한다.
- 체크 예외는 비즈니스 로직상 의도적으로 던지는 예외에만 사용
  - 예외를 받아서 반드시 처리해야만 하는 문제일 때만 체크 예외를 사용  
    **[체크예외 예시]**
    - 계좌 이체 실패 예외
    - 결제시 부족 포인트 부족 예외
    - 로그인 ID/PW 불일치 예외  
    위 예시의 경우라도 100% 체크 예외로 만들어야 하는 것은 아님.  
    계좌 이체 실패처럼 매우 심각한 문제는 개발자가 실수로 예외를 놓치면 안된다고 판단할 수 있으므로  
    체크 예외로 만들어 두면 컴파일러를 통해 놓친 예외를 인지할 수 있다.

체크 예외는 컴파일러가 예외 누락을 체크해주기 때문에 개발자가 실수로 예외를 놓치는 것을 막아준다.  
그래서 항상 명시적으로 예외를 잡아서 처리하거나, 처리할 수 없을 때는 예외를 던지도록 `method() throws 예외` 로 선언해야 한다.

### 체크 예외의 문제점

1) **복구 불가능한 예외**  
   대부분의 예외는 복구가 불가능하다.(일부 복구가 가능한 예외도 있으나 아주 적다.)  
    `SQLException`을 예를 들면 데이터베이스에 문제가 발생하는 예외로 SQL문법의 문제 혹은 데이터베이스 서버 문제 등이 있고    
    이런 문제들은 대부분 복구가 불가능하다.
    대부분의 서비스나 컨트롤러는 이런 문제를 해결할 수 없기 때문에 이러한 문제들은 일관성 있게 공통으로 처리해야 한다.  
    오류 로그를 남기고 개발자가 해당 오류를 빠르게 인지하는 것이 필요하며, 웹 애플리케이션 에서는 `서블릿 필터`, `인터셉터`,
    `ControllerAdvice`를 사용하면 이런 오류에 대한 처리를 깔끔하게 공통으로 해결할 수 있다.
2) **의존 관계에 대한 문제**
   `java.sql.SQLException`에 의존하는 `throws SQLException`을 예로들면 향후 Repository를 JDBC가 아닌 JPA와 같은  
    다른 기술로 변경할때, `JPAException`으로 예외가 변경된다면 `SQLExeption`에 의존하던 모든 서비스, 컨트롤러 코드를 모두  
    `JPAException`에 의존하도록 고쳐야한다.

처리할 수 있는 체크 예외라면 서비스나 컨트롤러에서 처리하겠지만, 데이터베이스나 네트워크 통신처럼 시스템 레벨에서 올라온 예외들은  
대부분 복구가 불가능하며, 실무에서 발생하는 대부분의 예외들은 이러한 시스템 예외들이다.
문제는 이러한 경우 체크 예외를 사용하면 Repository에서 올라온 복구 불가능한 예외를 Service, Controller 같은 각각의 클래스가 모두  
알고 있어야 하며 이로인해 불필요한 의존관게 문제도 발생하게 된다.  

###  throws Exception
`SQLException`, `ConnectException` 같은 시스템 예외를 최상위 예외인 `Exception`으로 던져도 문제를 해결할 수는 있다.    
`Exception`으로 던지면 `Exception`은 물론이고 그 하위의`SQLException`, `ConnectException`도 함께 던지게 된다.    
코드가 깔끔해지는 것 같으나 `Exception`은 최상위 타입이므로 모든 체크예외를 다 밖으로 던지는 문제가 발생한다.  
결과적으로 체크 예외의 최상위 타입인 `Exception`을 던지게 되면 다른 체크 예외를 체크할 수 있는 기능이 무효화 되고, 중요한 체크 예외를 다 놓치게 된다.    
(최상위 타입이므로 어떤 예외가 발생했는지 컴파일 단계에서 체크가 불가능해진다? 이를테면 IDE)  
예를들어 중간에 중요한 체크 예외가 발생해도 컴파일러는 `Exception`을 던지기 때문에 문법에 맞다고 판단해서 컴파일 오류가 발생하지 않는다.  
모든 예외를 다 던지기 때문에 체크 예외를 의도한 대로 사용하는 것이 아니기 때문에 `Exception`은 안티패턴중 하나로 본다.    
꼭 필요한  경우가 아니면 `Exception` 자체를 밖으로 던지는 것은 좋지 않은 방법이다.  

이에대한 대안은 Uncheck - 즉, RuntimeException을 활용하는 것이다.

### RuntimeException
시스템에서 발생한 복구 불가능한 예외에 런타임 예외를 사용하여 서비스나 컨트롤러가 복구 불가능한 예외를 신경쓰지 않게 할 수 있다.    
(물론 이렇게 복구 불가능한 예외는 일관성 있게 공통으로 처리해야함)  
또한 의존 관계에 대한 문제도 예외에 강제로 의존해야하는 체크예외와는 다르게 런타임 예외를 사용하면 해당 객체가 처리할 수 없는 예외는 무시하면 된다.    
(자동으로 전가되기 때문에, 해당 예외가 발생하는 곳에서만 사용하는 기술에 따라 변경하면 됨)
결과적으로 해당 메소드 호출부 메소드에서 더이상 `throws`를 하지 않아도 자동으로 `throws`된다.

체크예외 구현기술 변경에 대한 파급 효과로 중간에 기술이 변경되더라도 해당 예외를 사용하지 않는 컨트롤러, 서비스에서는 더이상 코드를 변경하지 않아도 되고,  
다만. 예외를 공통으로 처리하는 곳에서는 예외에 따른 처리가 필요할 수 있으며, 공통으로 처리하는 한 곳만 변경하면 되기 땜누에 변경의 영향 범위가 최소화 된다.  

## 정리
처음 자바를 설계할 당시에는 체크 예외가 더 나은 선택이라고 생각했기 때문에 자바가 기본으로 제공하는 기능들에는 체크 예외가 많다.    
시간이 흐르면서 라이브러리를 점덤 더 많이 사용하면서 처리해야 하는 예외와 복구할 수 없는 예외가 너무 많아졌다.  
체크 예외는 라이브러리들이 제공하는 모든 예외를 처리할 수 없을 때마다 `throws`에 예외를 덕지덕지 붙여야 했고,  
이로인해 개발자들은 `throws Exception` 이라는 극단적? 안티패턴 방법도 자주 사용하게 되었다.  
이러한 문제점 때문에 최근 라이브러리 들은 대부분 런타임 예외를 기본적으로 제공한다.  
(Spring도, 위에서 예를 든 JPA 기술도 RuntimeException을 사용한다.)  
런타임 예외도 필요하면 잡을 수 있기 때문에 필요한 경우에는 잡아서 처리하고, 그렇지 않으면 자연스럽게 던져 공통으로 처리하는 부분을 만들어서 처리하면 된다.  

+) 런타임 예외는 놓칠 수 있기 때문에 문서화가 중요하다.  

### 런타임 에러 문서화 예시

**JPA EntityManager**
```java
/**
 * Make an instance managed and persistent.
 * @param entity entity instance
 * @throws EntityExistsException if the entity already exists. 
 * @throws IllegalArgumentException if the instance is not an entity 
 * @throws TransactionRequiredException if there is no transaction when
 *          invoked on a container-managed entity manager of that is of type
 *          <code>PersistenceContext.TRANSACTION</code>
 */
public void persist(Object entity);
```
**JdbcTemplate(JdbcOperations)**  - 문서화 뿐만 아니라 코드에도 명시
```java
	/**
	 * Issue a single SQL execute, typically a DDL statement.
	 * @param sql static SQL to execute
	 * @throws DataAccessException if there is any problem
	 */
	void execute(String sql) throws DataAccessException;
```

#### *Tip + ) 예외를 전환할 때는 꼭 기존 예외를 포함해야 한다!*