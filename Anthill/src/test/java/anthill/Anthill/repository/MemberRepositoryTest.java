package anthill.Anthill.repository;

import anthill.Anthill.domain.member.Address;
import anthill.Anthill.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @DisplayName("CREATE 테스트")
    @Test
    public void insertSuccessTest() {

        Member member = Member.builder().userId("Test").name("Test").nickName("Test").password("Test").phoneNumber("Test").address(new Address("a1","a2","a3")).build();
        memberRepository.save(member);

        List<Member> members = memberRepository.findAll();
        Assertions.assertThat(members.size()).isEqualTo(1);
    }

    @DisplayName("DELETE 테스트")
    @Test
    public void deleteSuccessTest() {
        //given
        Member member = Member.builder().userId("Test").name("Test").nickName("Test").password("Test").phoneNumber("Test").address(new Address("a1","a2","a3")).build();
        memberRepository.save(member);

        //when
        memberRepository.deleteById(member.getId());

        //then
        List<Member> members = memberRepository.findAll();
        Assertions.assertThat(members.size()).isEqualTo(0);
    }

    @DisplayName("READ 테스트")
    @Test
    public void selectSuccessTest() {
        //given
        Member member = Member.builder().userId("Test").name("Test").nickName("Test").password("Test").phoneNumber("Test").address(new Address("a1","a2","a3")).build();
        Member fail = Member.builder().userId("fail").name("fail").nickName("fail").password("fail").phoneNumber("fail").address(new Address("a1","a2","a3")).build();
        memberRepository.save(member);
        //when
        Optional<Member> result = memberRepository.findById(member.getId());

        //then
        Assertions.assertThat(member.getUserId()).isEqualTo(result.orElse(fail).getUserId());
    }

    @DisplayName("UPDATE 테스트")
    @Test
    public void updateSuccessTest() {
        //given
        Member member = Member.builder().userId("Test").name("Test").nickName("Test").password("Test").phoneNumber("Test").address(new Address("a1","a2","a3")).build();
        Member fail = Member.builder().userId("fail").name("fail").nickName("fail").password("fail").phoneNumber("fail").address(new Address("a1","a2","a3")).build();
        memberRepository.save(member);

        //when
        Member updateMember = Member.builder().id(member.getId()).userId("updated").build();
        memberRepository.save(updateMember);

        //then
        Optional<Member> result = memberRepository.findById(member.getId());
        Assertions.assertThat(result.orElse(fail).getUserId()).isEqualTo("updated");
    }

    @Test
    @DisplayName("회원 아이디로 조회")
    public void selectByUserId(){
        //given
        Member member = Member.builder().userId("Test").name("Test").nickName("Test").password("Test").phoneNumber("Test").address(new Address("a1","a2","a3")).build();
        memberRepository.save(member);
        //when
        Member result = memberRepository.findByUserId("Test");
        //then
        Assertions.assertThat("Test").isEqualTo(result.getUserId());
    }

    @Test
    @DisplayName("아무것도 없을때 조회시")
    public void selectByUserIdNone(){
        //when
        Optional<Member> result = Optional.ofNullable(memberRepository.findByUserId("None"));
        //then
        assertThrows(NoSuchElementException.class, () -> {
            result.get();
        });
    }

    @DisplayName("데이터 무결성 테스트")
    @Test
    public void insertFailTest() {
        //given
        Member member = Member.builder().build();
        //then
        assertThrows(DataIntegrityViolationException.class, () -> {
            //when
            memberRepository.save(member);
        });
    }

}