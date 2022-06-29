package anthill.Anthill.service;

import anthill.Anthill.dto.board.BoardDeleteDTO;
import anthill.Anthill.dto.board.BoardRequestDTO;
import anthill.Anthill.dto.board.BoardUpdateDTO;
import org.apache.tomcat.websocket.AuthenticationException;

public interface BoardService {
    void posting(BoardRequestDTO boardRequestDTO);
    void changeInfo(BoardUpdateDTO boardUpdateDTO) throws AuthenticationException;
    void delete(BoardDeleteDTO boardDeleteDTO) throws AuthenticationException;
}
