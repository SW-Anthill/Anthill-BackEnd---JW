package anthill.Anthill.service;

import anthill.Anthill.domain.board.Board;
import anthill.Anthill.dto.board.*;
import anthill.Anthill.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public void posting(BoardRequestDTO boardRequestDTO) {
        boardRepository.save(boardRequestDTO.toEntity());
    }

    @Override
    public void changeInfo(BoardUpdateDTO boardUpdateDTO) throws AuthenticationException {
        Board board = boardRepository.findById(boardUpdateDTO.getId())
                                     .orElseThrow(() -> new IllegalArgumentException());

        if (board.getWriter() != boardUpdateDTO.getWriter()) {
            throw new AuthenticationException("권한 없음");
        }
        board.changeInfo(boardUpdateDTO.getTitle(), boardUpdateDTO.getContent());
    }

    @Override
    public void delete(BoardDeleteDTO boardDeleteDto) throws AuthenticationException {
        Board board = boardRepository.findById(boardDeleteDto.getId())
                                     .orElseThrow(() -> new IllegalArgumentException());

        if (board.getWriter() != boardDeleteDto.getWriter()) {
            throw new AuthenticationException("권한 없음");
        }

        boardRepository.deleteById(boardDeleteDto.getId());
    }

    @Override
    public Page<BoardPagingDTO> paging(int id) {

        Pageable curPage = PageRequest.of(id, 10, Sort.by("id")
                                                      .descending());
        Page<Board> result = boardRepository.findAll(curPage);
        Page<BoardPagingDTO> map = result.map(board -> board.toBoardPagingDTO(board));
        return map;
    }
    
    @Override
    public BoardResponseDTO select(Long id) {

        Board board = boardRepository.findById(id)
                                     .orElseThrow(() -> new IllegalArgumentException());
        board.increaseHits();

        return board.toBoardResponseDTO();

    }


}
