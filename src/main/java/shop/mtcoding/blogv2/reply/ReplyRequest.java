package shop.mtcoding.blogv2.reply;

import lombok.Getter;
import lombok.Setter;

public class ReplyRequest {

    // 댓글등록 DTO -> x-www-urlencoded
    // @Getter
    // @Setter
    // public static class SaveDTO {
    // private Integer boardId;
    // private String comment;
    // }

    // 댓글등록 DTO -> JS, JSON
    @Getter
    @Setter
    public static class SaveDTO {
        private Integer boardId;
        private String comment;
    }

}
