package anthill.Anthill.service;

import anthill.Anthill.domain.member.Member;
import anthill.Anthill.dto.member.MemberLoginRequestDTO;
import anthill.Anthill.dto.member.MemberRequestDTO;
import anthill.Anthill.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public boolean validateIsDuplicate(MemberRequestDTO member) {
        if (checkPhoneNumberDuplicate(member.getPhoneNumber())) {
            return true;
        }
        if (checkNicknameDuplicate(member.getNickName())) {
            return true;
        }
        if (checkUserIdDuplicate(member.getUserId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkNicknameDuplicate(String nickName) {
        return memberRepository.existsByNickName(nickName);
    }

    @Override
    public boolean checkUserIdDuplicate(String userId) {
        return memberRepository.existsByUserId(userId);
    }

    @Override
    public boolean checkPhoneNumberDuplicate(String phoneNumber) {
        return memberRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public boolean login(MemberLoginRequestDTO memberLoginRequestDTO) {
        Optional<Member> userId = memberRepository.findByUserId(memberLoginRequestDTO.getUserId());
        //Optional 사용했으니 isPresent 대신 userId.orElseThrow()를 쓰는게 좋아보인다
        if(!userId.isPresent()){
            return false;
        }
        return userId.get().getPassword().equals(memberLoginRequestDTO.getPassword());
    }

}
