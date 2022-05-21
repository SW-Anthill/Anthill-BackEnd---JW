package anthill.Anthill.service;

import anthill.Anthill.domain.member.Address;
import anthill.Anthill.domain.member.Member;
import anthill.Anthill.dto.member.MemberLoginRequestDTO;
import anthill.Anthill.dto.member.MemberRequestDTO;
import anthill.Anthill.repository.MemberRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
class MemberServiceImplTest {

    @Autowired
    MemberRepository memberRepository;

    MemberService memberService;

    @BeforeEach
    void setUp() {
        memberService = new MemberServiceImpl(memberRepository);
    }

    @Test
    @DisplayName("회원 가입 정상 로직")
    public void memberNotDuplicateTest() {
        //given
        MemberRequestDTO memberRequestDTO = MemberRequestDTO.builder()
                                                            .userId("Test")
                                                            .name("Test")
                                                            .nickName("Test")
                                                            .password("Test")
                                                            .phoneNumber("Test")
                                                            .address(new Address("a1", "a2", "a3"))
                                                            .build();
        //when
        memberService.join(memberRequestDTO);

        //then
        assertThat("Test").isEqualTo(memberRepository.findByUserId("Test")
                                                                          .get().getUserId());
    }


    @Test
    @DisplayName("회원 가입 중복 발생")
    public void memberDuplicateTest() {

        //given
        MemberRequestDTO memberRequestDTO = MemberRequestDTO.builder()
                                                            .userId("Test")
                                                            .name("Test")
                                                            .nickName("Test")
                                                            .password("Test")
                                                            .phoneNumber("Test")
                                                            .address(new Address("a1", "a2", "a3"))
                                                            .build();

        memberService.join(memberRequestDTO);
        MemberRequestDTO member1 = MemberRequestDTO.builder()
                                                   .userId("Test")
                                                   .name("Test")
                                                   .nickName("Test")
                                                   .password("Test")
                                                   .phoneNumber("Test")
                                                   .build();

        boolean result = memberService.validateIsDuplicate(member1);
        Assertions.assertEquals(result, true);

    }

    @Test
    @DisplayName("회원 아이디 중복 검증")
    public void userIdDuplicateTest() {
        //given
        Member member = Member.builder()
                              .userId("Test")
                              .name("Test")
                              .nickName("Test")
                              .password("Test")
                              .phoneNumber("Test")
                              .address(new Address("a1", "a2", "a3"))
                              .build();
        memberRepository.save(member);

        //when
        boolean result = memberRepository.existsByUserId("Test");

        //then
        Assertions.assertEquals(true, result);
    }

    @Test
    @DisplayName("회원 닉네임 중복 검증")
    public void nickNameDuplicateTest() {
        //given
        Member member = Member.builder()
                              .userId("Test")
                              .name("Test")
                              .nickName("Test")
                              .password("Test")
                              .phoneNumber("Test")
                              .address(new Address("a1", "a2", "a3"))
                              .build();

        memberRepository.save(member);

        //when
        boolean result = memberRepository.existsByNickName("Test");

        //then
        Assertions.assertEquals(true, result);
    }

    @Test
    @DisplayName("회원 휴대전화 중복 검증")
    public void phoneNumberDuplicateTest() {
        //given
        Member member = Member.builder()
                              .userId("Test")
                              .name("Test")
                              .nickName("Test")
                              .password("Test")
                              .phoneNumber("01012345678")
                              .address(new Address("a1", "a2", "a3"))
                              .build();

        memberRepository.save(member);

        //when
        boolean result = memberRepository.existsByPhoneNumber("01012345678");

        //then
        Assertions.assertEquals(true, result);
    }

    @Test
    @DisplayName("회원 로그인 성공")
    public void memberLoginSuccessTest() {
        //given
        MemberRequestDTO memberRequestDTO = MemberRequestDTO.builder()
                                                            .userId("Test")
                                                            .name("Test")
                                                            .nickName("Test")
                                                            .password("Test")
                                                            .phoneNumber("Test")
                                                            .address(new Address("a1", "a2", "a3"))
                                                            .build();
        memberService.join(memberRequestDTO);

        //when
        MemberLoginRequestDTO memberLoginRequestDTO = MemberLoginRequestDTO.builder()
                                                                           .userId("Test")
                                                                           .password("Test")
                                                                           .build();
        boolean result = memberService.login(memberLoginRequestDTO);

        //then
        assertThat(result).isEqualTo(true);
    }

    @Test
    @DisplayName("회원 로그인 실패")
    public void memberLoginFailTest() {
        //given
        MemberRequestDTO memberRequestDTO = MemberRequestDTO.builder()
                                                            .userId("Test")
                                                            .name("Test")
                                                            .nickName("Test")
                                                            .password("Test")
                                                            .phoneNumber("Test")
                                                            .address(new Address("a1", "a2", "a3"))
                                                            .build();

        memberService.join(memberRequestDTO);

        //when
        MemberLoginRequestDTO memberLoginRequestDTO = MemberLoginRequestDTO.builder()
                                                                           .userId("Test")
                                                                           .password("1234")
                                                                           .build();

        boolean result = memberService.login(memberLoginRequestDTO);

        //then
        assertThat(result).isEqualTo(false);
    }


}