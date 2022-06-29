package anthill.Anthill.service;

import anthill.Anthill.domain.board.Board;
import anthill.Anthill.dto.board.BoardDeleteDTO;
import anthill.Anthill.dto.board.BoardRequestDTO;
import anthill.Anthill.dto.board.BoardUpdateDTO;
import anthill.Anthill.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
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
    public void delete(BoardDeleteDTO boardDeleteDto) throws AuthenticationException{
        Board board = boardRepository.findById(boardDeleteDto.getId())
                                     .orElseThrow(() ->new IllegalArgumentException());

        if(board.getWriter() != boardDeleteDto.getWriter()){
            throw new AuthenticationException("권한 없음");
        }

        boardRepository.deleteById(boardDeleteDto.getId());
    }


}
