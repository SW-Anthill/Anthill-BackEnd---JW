package anthill.Anthill.controller;

import anthill.Anthill.dto.member.MemberDuplicateResponseDTO;
import anthill.Anthill.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DuplicateController {

    private final MemberService memberService;

    @GetMapping("/user-nickname/{nickname}")
    public ResponseEntity<MemberDuplicateResponseDTO> checkNicknameDuplicate(@PathVariable String nickname) {
        return ResponseEntity.ok(MemberDuplicateResponseDTO.builder()
                                                           .message(String.valueOf(memberService.checkNicknameDuplicate(nickname)))
                                                           .build());
    }

    @GetMapping("/user-id/{userid}")
    public ResponseEntity<MemberDuplicateResponseDTO> checkUserIdDuplicate(@PathVariable String userid) {
        return ResponseEntity.ok(MemberDuplicateResponseDTO.builder()
                                                           .message(String.valueOf(memberService.checkUserIdDuplicate(userid)))
                                                           .build());

    }

    @GetMapping("/user-phonenumber/{phonenumber}")
    public ResponseEntity<MemberDuplicateResponseDTO> checkPhoneNumberDuplicate(@PathVariable String phonenumber) {
        return ResponseEntity.ok(MemberDuplicateResponseDTO.builder()
                                                           .message(String.valueOf(memberService.checkPhoneNumberDuplicate(phonenumber)))
                                                           .build());

    }
}
