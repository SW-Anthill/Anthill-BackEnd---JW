package anthill.Anthill.controller;

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
    public ResponseEntity<Boolean> checkNicknameDuplicate(@PathVariable String nickname) {
        return ResponseEntity.ok(memberService.checkNicknameDuplicate(nickname));
    }

    @GetMapping("/user-id/{userid}")
    public ResponseEntity<Boolean> checkUserIdDuplicate(@PathVariable String userid) {
        return ResponseEntity.ok(memberService.checkUserIdDuplicate(userid));
    }

    @GetMapping("/user-phonenumber/{phonenumber}")
    public ResponseEntity<Boolean> checkPhoneNumberDuplicate(@PathVariable String phonenumber) {
        return ResponseEntity.ok(memberService.checkPhoneNumberDuplicate(phonenumber));
    }
}
