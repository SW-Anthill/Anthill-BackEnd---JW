package anthill.Anthill.repository;

import anthill.Anthill.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;


@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @DisplayName("CREATE 테스트")
    @Test
    public void insertTest() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Member member = Member.builder().userId("Test" + i).build();
            memberRepository.save(member);
        });

        List<Member> members = memberRepository.findAll();
        Assertions.assertThat(members.size()).isEqualTo(10);

    }

    @DisplayName("DELETE 테스트")
    @Test
    public void deleteTest() {
        //given
        Member member = Member.builder().userId("Test").build();
        memberRepository.save(member);

        //when
        memberRepository.deleteById(member.getId());

        //then
        List<Member> members = memberRepository.findAll();
        System.out.println(members.size());
    }

    @DisplayName("READ 테스트")
    @Test
    public void selectTest() {
        //given
        Member member = Member.builder().userId("test").build();
        Member fail = Member.builder().userId("fail").build();
        memberRepository.save(member);
        //when
        Optional<Member> result = memberRepository.findById(member.getId());

        //then
        Assertions.assertThat(member.getUserId()).isEqualTo(result.orElse(fail).getUserId());
    }

    @DisplayName("UPDATE 테스트")
    @Test
    public void updateTest() {
        //given
        Member member = Member.builder().userId("test").build();
        Member fail = Member.builder().userId("fail").build();
        memberRepository.save(member);

        //when
        Member updateMember = Member.builder().id(member.getId()).userId("updated").build();
        memberRepository.save(updateMember);

        //then
        Optional<Member> result = memberRepository.findById(member.getId());
        Assertions.assertThat(result.orElse(fail).getUserId()).isEqualTo("updated");
    }

}