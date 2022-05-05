package anthill.Anthill.service;

import anthill.Anthill.domain.member.Member;
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
        if(checkPhoneNumberDuplicate(member.getPhoneNumber())){
            return false;
        }
        if(checkNicknameDuplicate(member.getNickName())){
            return false;
        }
        if(checkUserIdDuplicate(member.getUserId())){
            return false;
        }
        return true;
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

}
