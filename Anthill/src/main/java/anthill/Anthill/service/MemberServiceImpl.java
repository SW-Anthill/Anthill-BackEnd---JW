package anthill.Anthill.service;

import anthill.Anthill.domain.member.Member;
import anthill.Anthill.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{

    private MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public int join(Member member) {
        return 0;
    }
}
