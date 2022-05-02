package anthill.Anthill.service;

import anthill.Anthill.domain.member.Member;

public interface MemberService {
    public int join(Member member);
    boolean checkNicknameDuplicate(String nickName);
    boolean checkUserIdDuplicate(String userId);
    boolean checkPhoneNumberDuplicate(String phoneNumber);
}
