package anthill.Anthill.service;

import anthill.Anthill.domain.board.Board;
import anthill.Anthill.dto.board.*;
import anthill.Anthill.repository.BoardRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertThrows;


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
    void 페이징() {
        //given
        BoardRequestDTO boardRequestDTO = makeBoardRequestDTO();
        for (int i = 0; i < 43; i++) {
            boardService.posting(boardRequestDTO);
        }

        //when
        Page<BoardPagingDTO> firstPaging = boardService.paging(0);

        Pageable LastPageWithTenElements = PageRequest.of(4, 10);
        Page<BoardPagingDTO> lastPaging = boardService.paging(4);

        //then
        Assertions.assertThat(10)
                  .isEqualTo(firstPaging.getContent()
                                        .size());
        Assertions.assertThat(3)
                  .isEqualTo(lastPaging.getContent()
                                       .size());
    }

    @Test
    void 페이징음수() {
        //given
        BoardRequestDTO boardRequestDTO = makeBoardRequestDTO();
        boardService.posting(boardRequestDTO);

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            //when
            Page<BoardPagingDTO> firstPaging = boardService.paging(-1);
        });

    }

    @Test
    void 페이징초과(){
        //given
        BoardRequestDTO boardRequestDTO = makeBoardRequestDTO();
        boardService.posting(boardRequestDTO);

        //when
        Page<BoardPagingDTO> firstPaging = boardService.paging(100);

        //then
        Assertions.assertThat(firstPaging.getContent().size()).isEqualTo(0);
    }


    @Test
    void 게시글_작성_테스트() {
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
    void 게시글_조회_테스트() throws Exception {
        //given
        BoardRequestDTO boardRequestDTO = makeBoardRequestDTO();
        boardService.posting(boardRequestDTO);

        Board result = boardRepository.findAll()
                                      .get(0);
        final Long id = result.getId();

        //when
        BoardResponseDTO boardResponseDTO = boardService.select(id);

        //then
        Assertions.assertThat(boardResponseDTO.getTitle())
                  .isEqualTo("제목");
        Assertions.assertThat(boardResponseDTO.getContent())
                  .isEqualTo("본문");
    }

    @Test
    void 게시글_수정_테스트() throws Exception {
        //given
        BoardRequestDTO boardRequestDTO = makeBoardRequestDTO();
        boardService.posting(boardRequestDTO);

        //when
        Board result = boardRepository.findAll()
                                      .get(0);
        final Long id = result.getId();

        boardService.changeInfo(makeBoardUpdateDTO(id));
        boardRepository.flush();

        result = boardRepository.findAll()
                                .get(0);

        //then
        final String changedTitle = "changedTitle";
        final String changedContent = "changedContent";

        Assertions.assertThat(result.getTitle())
                  .isEqualTo(changedTitle);
        Assertions.assertThat(result.getContent())
                  .isEqualTo(changedContent);

    }

    @Test
    void 게시글_삭제_테스트() throws Exception {
        //given
        BoardRequestDTO boardRequestDTO = makeBoardRequestDTO();
        boardService.posting(boardRequestDTO);

        //when
        final Long id = boardRepository.findAll()
                                       .get(0)
                                       .getId();
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