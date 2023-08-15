package shop.mtcoding.blogv2.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blogv2._core.util.Script;

@Controller
// 유효성검사는 Controller의 책임이다.
public class UserController {

    @Autowired // DI
    private UserService userService;

    @Autowired
    private HttpSession session;

    // 회원가입 페이지
    // c - v
    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    // 회원가입 기능
    // M - V - C
    @PostMapping("/join")
    public String join(UserRequest.joinDTO joinDTO) {
        userService.회원가입(joinDTO);
        // service에게 위임
        return "user/loginForm"; // 응답으로 persist 초기화
    }

    // 로그인 페이지
    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    // 로그인 기능
    @PostMapping("/login")
    public @ResponseBody String login(UserRequest.LoginDTO loginDTO) {
        User sessionUser = userService.로그인(loginDTO);
        if (sessionUser == null) {
            return Script.back("로그인 실패");
        }
        session.setAttribute("sessionUser", sessionUser);
        return Script.href("/"); // = "redirect"
    }
    // @ResponseBody를 붙이는 이유
    // @Controller는 파일을 return하는데, Script.back의 파일은 존재하지 않는다.
    // 그래서 데이터를 return해야하는데, 이를 위해 @ResponseBody를 붙여서 데이터를 return하게 한다.

    // 회원정보보기 페이지
    @GetMapping("/user/updateForm")
    public String updateForm(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        User user = userService.회원정보보기(sessionUser.getId());
        request.setAttribute("user", user);
        return "user/updateForm";
    }

    // 회원정보보기 기능
    @PostMapping("/user/update")
    public String update(UserRequest.UpdateDTO updateDTO) {
        // 1. 회원수정(서비스)
        User sessionUser = (User) session.getAttribute("sessionUser");
        User user = userService.회원정보수정(updateDTO, sessionUser.getId());
        // 2. 세션동기화
        session.setAttribute("sessionUser", user);
        return "redirect:/";
    }
}
