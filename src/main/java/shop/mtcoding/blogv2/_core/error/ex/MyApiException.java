package shop.mtcoding.blogv2._core.error.ex;

// JSon으로 처리(ajax 통신)
// RuntimeException <-> MyApiException
// 싱글톤으로 관리 X - 커스텀 Exception(message가 다를 것이기 때문에)
public class MyApiException extends RuntimeException {
    public MyApiException(String message) {
        super(message);
    }
}
