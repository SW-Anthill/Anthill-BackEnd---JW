package anthill.Anthill.service;

import anthill.Anthill.domain.member.Member;
import anthill.Anthill.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService{

    private MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public int join(Member member){
        memberRepository.save(member);
        return 0;
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
