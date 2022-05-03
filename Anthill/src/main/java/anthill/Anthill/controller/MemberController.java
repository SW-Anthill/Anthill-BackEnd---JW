package anthill.Anthill.controller;

import anthill.Anthill.dto.member.MemberRequestDTO;
import anthill.Anthill.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {

    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String helloMessage(){
        return "ok";
    }

    @PostMapping
    public ResponseEntity<?> registerMember(@RequestBody MemberRequestDTO memberRequestDTO){
        try{
            int result = memberService.join(memberRequestDTO.toEntity());
            return new ResponseEntity<Integer>(result, HttpStatus.CREATED);
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
