package shop.mtcoding.blogv2.user;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

public class UserRequest {

    // 회원가입 DTO
    @Setter
    @Getter
    public static class JoinDTO {
        private String username;
        private String password;
        private String email;
        private MultipartFile pic;
        // name 값을 잘 적어야한다.
    }

    // 로그인 DTO
    @Setter
    @Getter
    public static class LoginDTO {
        private String username;
        private String password;
    }

    // 회원정보수정 DTO
    @Setter
    @Getter
    public static class UpdateDTO {
        private String password;
        private MultipartFile pic;
        // name 값을 잘 적어야한다.

    }
    // 데이터를 1개 받을 때는 DTO를 굳이 만들 필요 없다.
    // DTO는 받아야 할 데이터가 많을 때 만들자.
}