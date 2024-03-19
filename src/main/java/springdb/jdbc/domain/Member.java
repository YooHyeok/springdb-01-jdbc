package springdb.jdbc.domain;

import lombok.Data;

@Data
public class Member {
    private String memberId; // 회원아이디
    private int money; //소지 금액

    public Member() {
    }

    public Member(String memberId, int money) {
        this.memberId = memberId;
        this.money = money;
    }
}
