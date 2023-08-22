package shop.mtcoding.blogv2._core.util;

import lombok.Getter;
import lombok.Setter;

// 공통 DTO
// JS의 응답을 모두 아래의 DTO를 사용한다.

@Getter
@Setter
public class ApiUtil<T> {
    private boolean success; // true
    private T data; // 댓글쓰기 성공
    // 제네릭 사용, 응답의 타입이 어떤 것을 할 지 모르기 때문에
    // 클라이언트 사이드 렌더링을 위해서는 응답이 Y or N와 댓글에 대한 정보를 브라우저에게 전달해야
    // user_id와 reply_id를 통해 삭제와 렌더링을 할 수 있다.

    public ApiUtil(boolean success, T data) {
        this.success = success;
        this.data = data;
    }
}
