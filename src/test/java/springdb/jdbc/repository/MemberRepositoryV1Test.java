package springdb.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import springdb.jdbc.domain.Member;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static springdb.jdbc.connection.ConnectionConst.*;

@Slf4j
class MemberRepositoryV1Test {

    MemberRepositoryV1 memberRepository;

    @BeforeEach
    void beforeEach() {
        // 기본 DriverManager - 항상 새로운 커넥션 획득 (5번 호출했으므로 5개의 커넥션을 사용하게 됨)
//        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

        //커넥션 풀링 적용 (HikariCP)
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPoolName(PASSWORD);
        memberRepository = new MemberRepositoryV1(dataSource); // MemberRepositoryV1 생성자에 DataSource 주입
    }
    
    @Test
    void crud() throws SQLException, InterruptedException {
        // save
        Member member = new Member("memvberV1", 10000);
        Member saveMember = memberRepository.save(member);
        // findById
        Member findMember = memberRepository.findById(saveMember.getMemberId());
        log.info("findMember={}", findMember);
        log.info("member == findMember {}", member == findMember);
        log.info("member equals findMember {}", member.equals(findMember)); // 롬복의 @Data가 생성해주는 Equals & HashCode에 의해 객체 동등성을 보장해준다.
        assertThat(findMember).isEqualTo(saveMember);

        // update - money: 10000 -> 20000
        memberRepository.update(member.getMemberId(), 20000);
        Member updatedMember = memberRepository.findById(member.getMemberId());
        assertThat(updatedMember.getMoney()).isEqualTo(20000);

        memberRepository.delete(member.getMemberId());

        assertThatThrownBy( () -> memberRepository.findById(member.getMemberId()) )
                .isInstanceOf(NoSuchElementException.class); // assertThatThrownBy: Exception 유무 비교 delete 이후에는 조회가 되지 않기 때문에 NoSuchElementException이 발생.

        Thread.sleep(1000);
    }
}