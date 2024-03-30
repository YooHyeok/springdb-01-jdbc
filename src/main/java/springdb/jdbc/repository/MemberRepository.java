package springdb.jdbc.repository;

import springdb.jdbc.domain.Member;

import java.sql.SQLException;

public interface MemberRepository {
    Member save(Member mamber);

    Member findById(String memberId);

    void update(String memberId, int money);

    void delete(String memberId);
}
