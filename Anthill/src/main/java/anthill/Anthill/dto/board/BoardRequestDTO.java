package anthill.Anthill.dto.board;


import anthill.Anthill.domain.board.Board;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardRequestDTO {

    @NotBlank(message = "제목을 입력해주세요")
    String title;
    @NotBlank(message = "본문을 입력해주세요")
    String content;
    String writer;

    @Builder
    public BoardRequestDTO(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public Board toEntity() {
        Board board = Board.builder()
                           .title(this.title)
                           .content(this.content)
                           .writer(this.writer)
                           .hits(0L)
                           .build();
        return board;
    }
}
