package anthill.Anthill.dto.member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberDuplicateResponseDTO {
    private String message;

    @Builder
    public MemberDuplicateResponseDTO(String message) {
        this.message = message;
    }
}
