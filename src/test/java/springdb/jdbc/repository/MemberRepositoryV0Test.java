package springdb.jdbc.repository;

import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import springdb.jdbc.domain.Member;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
class MemberRepositoryV0Test {

    MemberRepositoryV0 repositoryV0 = new MemberRepositoryV0();
    @Test
    void crud() throws SQLException {
        Member member = new Member("memvberV0", 10000);
        repositoryV0.save(member);
    }
}