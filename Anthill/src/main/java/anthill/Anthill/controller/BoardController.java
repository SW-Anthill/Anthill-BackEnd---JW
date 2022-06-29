package anthill.Anthill.controller;

import anthill.Anthill.dto.board.BoardDeleteDTO;
import anthill.Anthill.dto.board.BoardRequestDTO;
import anthill.Anthill.dto.board.BoardUpdateDTO;
import anthill.Anthill.dto.common.BasicResponseDTO;
import anthill.Anthill.service.BoardService;
import anthill.Anthill.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;

    private final String FAIL = "failure";
    private final String SUCCESS = "success";

    @PostMapping
    public ResponseEntity<BasicResponseDTO> posting(@Valid @RequestBody BoardRequestDTO boardRequestDTO) {

        boardService.posting(boardRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(makeBasicResponseDTO(SUCCESS));

    }

    @PutMapping("/{boardid}")
    public ResponseEntity<BasicResponseDTO> update(@RequestBody BoardUpdateDTO boardUpdateDTO) throws Exception {
        boardService.changeInfo(boardUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(makeBasicResponseDTO(SUCCESS));
    }


    @DeleteMapping("/{boardid}")
    public ResponseEntity<BasicResponseDTO> delete(@RequestBody BoardDeleteDTO boardDeleteDTO) throws Exception {
        boardService.delete(boardDeleteDTO);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(makeBasicResponseDTO(SUCCESS));
    }


    private BasicResponseDTO makeBasicResponseDTO(String message) {
        return BasicResponseDTO.builder()
                               .message(message)
                               .build();
    }

}
