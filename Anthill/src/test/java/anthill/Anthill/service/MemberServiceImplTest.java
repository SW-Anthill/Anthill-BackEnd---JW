package anthill.Anthill.service;

import anthill.Anthill.domain.member.Address;
import anthill.Anthill.domain.member.Member;
import anthill.Anthill.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberServiceImplTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("회원 아이디 유효성 검증")
    public void validateUserIdTest() {
        //given
        Member member = Member.builder().userId("Test").name("Test").nickName("Test").password("Test").phoneNumber("Test").address(new Address("a1", "a2", "a3")).build();
        memberRepository.save(member);

        //when
        boolean result = memberRepository.existsByUserId("Test");

        //then
        Assertions.assertEquals(true, result);
    }

    @Test
    @DisplayName("회원 닉네임 유효성 검증")
    public void validateNickNameTest() {
        //given
        Member member = Member.builder().userId("Test").name("Test").nickName("Test").password("Test").phoneNumber("Test").address(new Address("a1", "a2", "a3")).build();
        memberRepository.save(member);

        //when
        boolean result = memberRepository.existsByNickName("Test");

        //then
        Assertions.assertEquals(true, result);
    }

    @Test
    @DisplayName("회원 휴대전화 유효성 검증")
    public void validatePhoneNumberTest() {
        //given
        Member member = Member.builder().userId("Test").name("Test").nickName("Test").password("Test").phoneNumber("01012345678").address(new Address("a1", "a2", "a3")).build();
        memberRepository.save(member);

        //when
        boolean result = memberRepository.existsByPhoneNumber("01012345678");

        //then
        Assertions.assertEquals(true, result);
    }
}