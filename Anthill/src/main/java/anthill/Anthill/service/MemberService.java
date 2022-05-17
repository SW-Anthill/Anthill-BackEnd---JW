package anthill.Anthill.service;

import anthill.Anthill.domain.member.Member;
import anthill.Anthill.dto.member.MemberLoginRequestDTO;
import anthill.Anthill.dto.member.MemberRequestDTO;

public interface MemberService {
    void join(Member member);

    boolean validateIsDuplicate(MemberRequestDTO memberRequestDTO);

    boolean checkNicknameDuplicate(String nickName);

    boolean checkUserIdDuplicate(String userId);

    boolean checkPhoneNumberDuplicate(String phoneNumber);

    boolean login(MemberLoginRequestDTO memberLoginRequestDTO);
}
