package anthill.Anthill.service;

import anthill.Anthill.dto.board.*;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.data.domain.Page;

public interface BoardService {
    void posting(BoardRequestDTO boardRequestDTO);

    void changeInfo(BoardUpdateDTO boardUpdateDTO) throws AuthenticationException;

    void delete(BoardDeleteDTO boardDeleteDTO) throws AuthenticationException;

    Page<BoardPagingDTO> paging(int id);

    BoardResponseDTO select(Long id);

    void updateHitByBoardId(Long id);
}
