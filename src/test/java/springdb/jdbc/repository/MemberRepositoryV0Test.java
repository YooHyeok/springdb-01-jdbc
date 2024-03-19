package springdb.jdbc.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import springdb.jdbc.domain.Member;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 memberRepository = new MemberRepositoryV0();
    @Test
    void crud() throws SQLException {
        // save
        Member member = new Member("memvberV0", 10000);
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
    }
}