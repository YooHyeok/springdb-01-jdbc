package springdb.jdbc.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import springdb.jdbc.connection.ConnectionConst;
import springdb.jdbc.connection.DBConnectionUtil;
import springdb.jdbc.domain.Member;
import springdb.jdbc.repository.MemberRepositoryV1;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static springdb.jdbc.connection.ConnectionConst.*;

/**
 * 기본 동작, 트랜잭션이 없어서 문제 발생
 */
class MemberServiceV1Test {
    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_EX = "ex";

    private MemberRepositoryV1 memberRepository;
    private MemberServiceV1 memberService;

    @BeforeEach // 각 테스트별 테스트 수행전 실행된다.
    void before() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        //의존성 주입
        memberRepository = new MemberRepositoryV1(dataSource);
        memberService = new MemberServiceV1(memberRepository);
    }

    @AfterEach // 각 테스트별 테스트 종료후 실행된다.
    void after() throws SQLException {
        memberRepository.delete(MEMBER_A);
        memberRepository.delete(MEMBER_B);
        memberRepository.delete(MEMBER_EX);
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
        memberService.accountTransfer(memberA.getMemberId(), memberB.getMemberId(), 2000);

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
        Assertions.assertThatThrownBy(()-> memberService.accountTransfer(memberA.getMemberId(), memberEx.getMemberId(), 2000))
                .isInstanceOf(IllegalStateException.class); // meberA 값만 변경되고, memberEx의 값은 변경되지 않음

        //then
        Member findMemberA = memberRepository.findById(memberA.getMemberId());
        Member findMemberEx = memberRepository.findById(memberEx.getMemberId());

        assertThat(findMemberA.getMoney()).isEqualTo(8000);
        assertThat(findMemberEx.getMoney()).isEqualTo(10000);
    }
}