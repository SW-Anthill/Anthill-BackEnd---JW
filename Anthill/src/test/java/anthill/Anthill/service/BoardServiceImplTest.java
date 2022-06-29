package anthill.Anthill.service;

import anthill.Anthill.domain.board.Board;
import anthill.Anthill.dto.board.BoardDeleteDTO;
import anthill.Anthill.dto.board.BoardRequestDTO;
import anthill.Anthill.dto.board.BoardUpdateDTO;
import anthill.Anthill.repository.BoardRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;



@DataJpaTest
class BoardServiceImplTest {

    BoardService boardService;

    @Autowired
    BoardRepository boardRepository;

    @BeforeEach
    void setUp() {
        boardService = new BoardServiceImpl(boardRepository);

    }


    @Test
    public void 게시글_작성_테스트() {
        //given
        BoardRequestDTO boardRequestDTO = makeBoardRequestDTO();

        //when
        boardService.posting(boardRequestDTO);

        //then
        Assertions.assertThat(boardRepository.findAll()
                                             .size())
                  .isEqualTo(1);

    }

    @Test
    public void 게시글_수정_테스트() throws Exception {
        //given
        BoardRequestDTO boardRequestDTO = makeBoardRequestDTO();
        boardService.posting(boardRequestDTO);

        //when
        Board result = boardRepository.findAll().get(0);
        Long id = result.getId();

        boardService.changeInfo(makeBoardUpdateDTO(id));
        boardRepository.flush();

        result = boardRepository.findAll().get(0);

        //then
        String changedTitle = "changedTitle";
        String changedContent = "changedContent";

        Assertions.assertThat(result.getTitle())
                  .isEqualTo(changedTitle);
        Assertions.assertThat(result.getContent())
                  .isEqualTo(changedContent);

    }

    @Test
    public void 게시글_삭제_테스트()throws Exception{
        //given
        BoardRequestDTO boardRequestDTO = makeBoardRequestDTO();
        boardService.posting(boardRequestDTO);

        //when
        Long id = boardRepository.findAll().get(0).getId();
        boardService.delete(makeBoardDeleteDTO(id));

        //then
        Assertions.assertThat(boardRepository.findAll()
                                             .size())
                  .isEqualTo(0);

    }

    private BoardDeleteDTO makeBoardDeleteDTO(Long id) {
        BoardDeleteDTO boardDeleteDTO = BoardDeleteDTO.builder()
                                                      .id(id)
                                                      .writer("작성자")
                                                      .build();
        return boardDeleteDTO;
    }

    private BoardUpdateDTO makeBoardUpdateDTO(Long id) {
        BoardUpdateDTO boardUpdateDTO = BoardUpdateDTO.builder()
                                                      .id(id)
                                                      .title("changedTitle")
                                                      .content("changedContent")
                                                      .writer("작성자")
                                                      .build();
        return boardUpdateDTO;
    }


    private BoardRequestDTO makeBoardRequestDTO() {

        BoardRequestDTO boardRequestDTO = BoardRequestDTO.builder()
                                                         .title("제목")
                                                         .content("본문")
                                                         .writer("작성자")
                                                         .build();

        return boardRequestDTO;
    }

}