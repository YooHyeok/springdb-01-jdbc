package springdb.jdbc.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import springdb.jdbc.domain.Member;
import springdb.jdbc.repository.MemberRepositoryV3;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static springdb.jdbc.connection.ConnectionConst.*;

/**
 * 트랜잭션 - @Transactional AOP
 * @SpringBootTest 스프링 AOP를 적용하려면 스프링 컨테이너가 필요하다. <br/>
 * 이 어노테이션을 선언하면 테스트시 스프링 부트를 통해 스프링 컨테이너를 생성하며, @Autowired등을 통해 스프링 컨테이너가 관리하는 빈들을 사용할 수 있다. <br/>
 */
@Slf4j
@SpringBootTest // Spring Bean에 대한 의존관계 주입이 된다.
class MemberServiceV3_3Test {
    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_EX = "ex";

    @Autowired
    private MemberRepositoryV3 memberRepository;
    @Autowired
    private MemberServiceV3_3 memberService;

    /**
     * @TestConfiguration 테스트 안에서 내부 설정 클래스를 만들고 클래스레벨에 해당 어노테이션을 선언하면 <br/>
     * 스프링 부트가 자동으로 만들어주는 빈들에 추가로 필요한 스프링 빈들을 등록하고 테스트를 수행할 수 있다. <br/>
     */
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

    @AfterEach // 각 테스트별 테스트 종료후 실행된다.
    void after() throws SQLException {
        memberRepository.delete(MEMBER_A);
        memberRepository.delete(MEMBER_B);
        memberRepository.delete(MEMBER_EX);
    }

    /**
     * @Transaction이 붙어있으면 Spring이 해당 서비스를 상속받아 트랜잭션Proxy로 재구현되며, <br/>
     * Proxy에 구현된 트랜잭션 적용 로직에서 @Transaction 애노테이션으로 적용된 메소드가 호출된다.<br/>
     * 이후 해당 로직이 적용된 트랜잭션Proxy(Service)가 Spring Bean에 등록된다.<br/>
     * 트랜잭션 Proxy에 의해 관리되는 메소드가 호출될 경우 AOP에 의해 Transaction이 적용된다. <br/>
     * 이때 서비스를 상속받아 트랜잭션이 적용되는 메소드는 @Transaction 어노테이션의 레벨에 따라 달라진다.<br/>
     * 클래스레벨일 경우 모든 메소드에 적용, 메소드레벨일 경우 해당 메소드에만 적용
     * (메소드 레벨에만 적용되더라도 Service 인스턴스 자체가 Proxy로 감싸지지만, 트랜잭션은 애노테이션이 붙은 메소드에만 적용된다.)
     */
    @Test
    @DisplayName("AOP 적용 확인 테스트")
    void AopCheck () throws Exception {
        log.info("memberService class={}", memberService.getClass()); // 트랜잭션 Proxy: MemberServiceV3_3$$SpringCGLIB$$0
        log.info("memberRepository class={}", memberRepository.getClass());
        Assertions.assertThat(AopUtils.isAopProxy(memberService)).isTrue();
        Assertions.assertThat(AopUtils.isAopProxy(memberRepository)).isFalse();
    }

    @Test
    @DisplayName("정상 이체")
    void accountTransfer() throws SQLException {
        //given
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberB = new Member(MEMBER_B, 10000);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        //when
        log.info("Start TX");
        memberService.accountTransfer(memberA.getMemberId(), memberB.getMemberId(), 2000);
        log.info("END TX");

        //then
        Member findMemberA = memberRepository.findById(memberA.getMemberId());
        Member findMemberB = memberRepository.findById(memberB.getMemberId());

        assertThat(findMemberA.getMoney()).isEqualTo(8000);
        assertThat(findMemberB.getMoney()).isEqualTo(12000);
    }
    @Test
    @DisplayName("이체중 예외 발생 - MemberA의 값만 증가하고 바로 Exception이 터지므로 Exception이후의 MemberEx의 값은 감소되지 않고 롤백되지않고 커밋된다")
    void accountTransferEx() throws SQLException {
        //given
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberEx = new Member(MEMBER_EX, 10000);
        memberRepository.save(memberA);
        memberRepository.save(memberEx);

        //when
        assertThatThrownBy(()-> memberService.accountTransfer(memberA.getMemberId(), memberEx.getMemberId(), 2000))
                .isInstanceOf(IllegalStateException.class); // meberA 값만 변경되고, memberEx의 값은 변경되지 않음

        //then
        Member findMemberA = memberRepository.findById(memberA.getMemberId());
        Member findMemberEx = memberRepository.findById(memberEx.getMemberId());

        assertThat(findMemberA.getMoney()).isEqualTo(10000);
        assertThat(findMemberEx.getMoney()).isEqualTo(10000);
    }
}