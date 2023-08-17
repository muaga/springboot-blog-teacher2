package shop.mtcoding.blogv2._core.error;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import shop.mtcoding.blogv2._core.util.Script;

// 스프링에서 발생하는 모든 Exception을 DS로 가져와서 처리하는 것
// 스프링에서 발생하는 모든 Exception은 throw를 통해서 이리로 가져온다.

@RestControllerAdvice
// 예외 처리시 데이터를 응답하는 어노테이션
// Exception이 터지면 어디에서 터트려도 이 곳으로 모이게 하는 어노테이션

// @ControllerAdvice : 예외 처리시 view를 응답하는 어노테이션
public class MyExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    // () - 어떤 Exception 발생 시 처리할 지 정한다.
    public String error(RuntimeException e) {
        // e : 오류가 발생한 모든 throw new RuntimeException 객체가 모인다.
        return Script.back(e.getMessage());
        //
    }
}
