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