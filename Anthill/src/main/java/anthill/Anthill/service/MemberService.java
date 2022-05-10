package anthill.Anthill.service;

import anthill.Anthill.domain.member.Member;
import anthill.Anthill.dto.member.MemberRequestDTO;
import anthill.Anthill.dto.member.MemberResponseDTO;

public interface MemberService {
    void join(Member member);

    boolean validateIsDuplicate(MemberRequestDTO memberRequestDTO);

    boolean checkNicknameDuplicate(String nickName);

    boolean checkUserIdDuplicate(String userId);

    boolean checkPhoneNumberDuplicate(String phoneNumber);
}
