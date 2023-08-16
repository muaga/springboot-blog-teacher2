package shop.mtcoding.blogv2.board;

import lombok.Getter;
import lombok.Setter;

// 모든 요청 DTO를 내부 클래스로 모은다.
// 1. public
// 2. static
public class BoardRequest {

    // SaveDTO saveDTO = new BoardRequest.SaveDTO();
    // static을 붙이면 클래스.saveDTO를 new 할 수 있다.
    @Getter
    @Setter
    public static class SaveDTO {
        private String title;
        private String content;
    }
}
