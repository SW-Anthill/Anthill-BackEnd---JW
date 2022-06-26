package anthill.Anthill.controller;

import anthill.Anthill.dto.member.MemberLoginRequestDTO;
import anthill.Anthill.dto.member.MemberRequestDTO;
import anthill.Anthill.dto.member.MemberResponseDTO;
import anthill.Anthill.service.JwtService;
import anthill.Anthill.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")

public class MemberController {

    private final MemberService memberService;

    private final JwtService jwtService;

    @GetMapping
    public String helloMessage() {
        return "ok";
    }

    @GetMapping("/{userid}")
    public ResponseEntity<MemberResponseDTO> findByUserID(@PathVariable(value = "userid") final String userId) {
        MemberResponseDTO memberResponseDTO = memberService.findByUserID(userId);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(memberResponseDTO);
    }

    @PostMapping
    public ResponseEntity<String> registerMember(@Valid @RequestBody MemberRequestDTO memberRequestDTO) {

        if (memberService.validateIsDuplicate(memberRequestDTO)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .body("회원 데이터 중복 발생");
        }

        memberService.join(memberRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body("회원가입 완료");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginMember(@RequestBody MemberLoginRequestDTO memberLoginRequestDTO) {

        if (memberService.login(memberLoginRequestDTO)) {
            String token = jwtService.create("userId", memberLoginRequestDTO.getUserId(), "access-token");
            return ResponseEntity.status(HttpStatus.OK)
                                 .header("access-token", token)
                                 .body("로그인 완료");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body("로그인 실패");
    }


}
