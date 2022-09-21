package anthill.Anthill.dto.board;

import anthill.Anthill.domain.board.Board;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardPageResponseDTO {
    List<BoardInfoDTO> contents;
    int totalPage;
    long totalElements;
    int curPage;
    int size;

    @Builder
    public BoardPageResponseDTO(List<BoardInfoDTO> contents, int totalPage, long totalElements, int curPage, int size) {
        this.contents = contents;
        this.totalPage = totalPage;
        this.totalElements = totalElements;
        this.curPage = curPage;
        this.size = size;
    }

    public static BoardPageResponseDTO toBoardPagingDTO(Page<Board> page) {
        return BoardPageResponseDTO.builder()
                                   .contents(page.getContent()
                                                 .stream()
                                                 .map(BoardInfoDTO::toBoardPagingDTO)
                                                 .toList())
                                   .totalPage(page.getTotalPages())
                                   .totalElements(page.getTotalElements())
                                   .curPage(page.getNumber())
                                   .size(page.getSize())
                                   .build();
    }

}


