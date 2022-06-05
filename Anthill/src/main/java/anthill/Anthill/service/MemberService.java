package anthill.Anthill.service;

import anthill.Anthill.dto.member.MemberLoginRequestDTO;
import anthill.Anthill.dto.member.MemberRequestDTO;
import anthill.Anthill.dto.member.MemberResponseDTO;


public interface MemberService {
    void join(MemberRequestDTO memberRequestDTO);

    boolean validateIsDuplicate(MemberRequestDTO memberRequestDTO);

    boolean checkNicknameDuplicate(String nickName);

    boolean checkUserIdDuplicate(String userId);

    boolean checkPhoneNumberDuplicate(String phoneNumber);

    boolean login(MemberLoginRequestDTO memberLoginRequestDTO);

    MemberResponseDTO findByUserID(String userId);
}
