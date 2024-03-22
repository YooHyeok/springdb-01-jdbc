package springdb.jdbc.service;

import lombok.RequiredArgsConstructor;
import springdb.jdbc.domain.Member;
import springdb.jdbc.repository.MemberRepositoryV1;

import java.sql.SQLException;

@RequiredArgsConstructor
public class MemberServiceV1 {
    private final MemberRepositoryV1 memberRepository;

    /**
     * 계좌 송금
     */
    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);

        memberRepository.update(fromId, fromMember.getMoney() - money);
        validation(toMember);//오류 케이스
        memberRepository.update(toId, fromMember.getMoney() + money);

    }

    private void validation(Member toMember) {
        if(toMember.getMemberId().equals("ex")) throw new IllegalStateException("이체중 예외 발생");
    }
}
