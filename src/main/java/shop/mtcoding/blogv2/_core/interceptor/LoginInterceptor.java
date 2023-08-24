package shop.mtcoding.blogv2._core.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.blogv2._core.util.ApiUtil;
import shop.mtcoding.blogv2._core.util.Script;
import shop.mtcoding.blogv2.user.User;

// 1. 인터셉터 기능 생성

// default
// 인터페이스의 모든 메소드를 구현하지 않고, 하나만 재정의해서 구현할 수 있도록 하는 것
public class LoginInterceptor implements HandlerInterceptor {

    // return이 true이면 컨트롤러 메소드 진입
    // return이 false이면 요청이 종료됨 <- 인증이 되지 않았을 때
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("LoginInterceptor preHandle");

        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null) {
            String startEndPoint = request.getRequestURI().split("/")[1];
            // api일 경우, json 데이터를 넘겨서 js로 응답
            if (startEndPoint.equals("api")) {
                response.setHeader("Content-Type", "application/json; charset=uft-8");
                // 응답할 내용의 header
                PrintWriter out = response.getWriter();
                // 버퍼생성
                ApiUtil apiUtil = new ApiUtil<String>(false, "인증이 필요합니다.");
                // 자바언어로 ajax 통신에게 응답할 내용
                String responseBody = new ObjectMapper().writeValueAsString(apiUtil);
                // 응답할 내용을 JSON으로 변경
                out.println(responseBody);
                // 보내기

                // 그 외를 alert 등으로 응답
            } else {
                response.setHeader("Content-Type", "text/html; charset=uft-8");
                PrintWriter out = response.getWriter();
                out.println(Script.href("/loginForm", "인증이 필요합니다."));
            }

            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        System.out.println("LoginInterceptor PostHandle");
    }

}
