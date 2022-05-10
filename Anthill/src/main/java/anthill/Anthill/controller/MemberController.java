package anthill.Anthill.controller;

import anthill.Anthill.dto.member.MemberRequestDTO;
import anthill.Anthill.dto.member.MemberResponseDTO;
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

    @GetMapping
    public String helloMessage() {
        return "ok";
    }

    @PostMapping
    public ResponseEntity<?> registerMember(@Valid @RequestBody MemberRequestDTO memberRequestDTO) {

        if (memberService.validateIsDuplicate(memberRequestDTO)) {
            return new ResponseEntity<String>("Duplicated", HttpStatus.CONFLICT);
        }
        memberService.join(memberRequestDTO.toEntity());
        return new ResponseEntity<String>("회원가입 완료", HttpStatus.CREATED);
    }

}
