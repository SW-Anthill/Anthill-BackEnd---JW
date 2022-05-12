package anthill.Anthill.repository;

import anthill.Anthill.domain.member.Address;
import anthill.Anthill.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.persistence.EntityManager;
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
        memberRepository.saveAndFlush(member);

        //when
        member.changeNickName("updated");
        memberRepository.saveAndFlush(member);

        //then
        Optional<Member> result = memberRepository.findById(member.getId());
        Assertions.assertThat(result.orElse(fail).getNickName()).isEqualTo("updated");
    }

    @Test
    @DisplayName("회원 아이디로 조회")
    public void selectByUserId(){
        //given
        Member member = Member.builder().userId("Test").name("Test").nickName("Test").password("Test").phoneNumber("Test").address(new Address("a1","a2","a3")).build();
        memberRepository.save(member);
        //when
        Member result = memberRepository.findByUserId("Test").get();
        //then
        Assertions.assertThat("Test").isEqualTo(result.getUserId());
    }

    @Test
    @DisplayName("아무것도 없을때 조회시엔 NoSuchElementException 발생")
    public void selectByUserIdNone(){
        //when
        Optional<Member> result = memberRepository.findByUserId("None");
        //then
        assertThrows(NoSuchElementException.class, () -> {
            result.get();
        });
    }

    @Test
    @DisplayName("아무것도 없을때 삭제시엔 EmptyResultDataAccessException 발생")
    public void deleteByUserIdNone(){
        assertThrows(EmptyResultDataAccessException.class, () -> {
            memberRepository.deleteById(3L);
        });

    }

    @Test
    @DisplayName("Unique값이 겹칠 경우 DataIntegrityViolationException 발생")
    public void insertDuplicateTest(){
        //given
        Member member1 = Member.builder().userId("Test").name("Test").nickName("Test").password("Test").phoneNumber("Test").address(new Address("a1","a2","a3")).build();
        memberRepository.save(member1);

        Member member2 = Member.builder().userId("Test").name("Test").nickName("Test").password("Test").phoneNumber("Test").address(new Address("a1","a2","a3")).build();
        assertThrows(DataIntegrityViolationException.class, ()->{
            memberRepository.save(member2);
        });

    }

    @DisplayName("DB NotNull으로 설정된 값들을 그냥 넣어 버리면 DataIntegrityViolationException 발생")
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

    @Autowired
    private EntityManager entityManager;

    @DisplayName("영속성 컨텍스트 테스트를 clear시키면 동일한 객체x")
    @Test
    public void EntityContextTest(){
        Member member1 = Member.builder().userId("Test").name("Test").nickName("Test").password("Test").phoneNumber("Test").address(new Address("a1","a2","a3")).build();
        memberRepository.save(member1);
        entityManager.clear();

        Member member2 = memberRepository.findByUserId("Test").get();
        Assertions.assertThat(member1).isNotEqualTo(member2);
        //memberRepository.flush();
    }

}