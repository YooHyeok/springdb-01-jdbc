package springdb.jdbc.repository;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import springdb.jdbc.domain.Member;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 repositoryV0 = new MemberRepositoryV0();
    @Test
    void crud() throws SQLException {
        // save
        Member member = new Member("memvberV0", 10000);
        Member saveMember = repositoryV0.save(member);
        // findById
        Member findMember = repositoryV0.findById(saveMember.getMemberId());
        log.info("findMember={}", findMember);
        log.info("member == findMember {}", member == findMember);
        log.info("member equals findMember {}", member.equals(findMember)); // 롬복의 @Data가 생성해주는 Equals & HashCode에 의해 객체 동등성을 보장해준다.
        Assertions.assertThat(findMember).isEqualTo(saveMember);
    }
}