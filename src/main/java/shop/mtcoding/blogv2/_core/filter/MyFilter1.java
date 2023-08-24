package shop.mtcoding.blogv2._core.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 2. 필터기능 주입하기

public class MyFilter1 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // chain : 필터와 필터 사이의 연결고리
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // 1. IP 로그 남기기
        System.out.println("접속자 ip : " + req.getRemoteAddr());
        System.out.println("접속자 user agent : " + req.getHeader("User-Agent"));

        // 2. 블랙리스트 추방
        if (req.getHeader("User-Agent").contains("XBox")) {
            // resp.setContentType("text/html; charset=utf-8");
            resp.setHeader("Content-Type", "text/html; charset=utf-8");
            PrintWriter out = resp.getWriter();

            req.setAttribute("name", "홍길동");
            out.println("<h1>나가세요 크롬이면 : " + req.getAttribute("name") + "</h1>");
            return;
        }

        chain.doFilter(request, response); // 다음 필터로 request,response 전달....
    }
}

/*
 * 진행과정
 * 톰캣으로부터 받은 request와 response 객체가
 * spring의 필터를 순서대로 거친다음에, 더이상 필터가 없으면
 * DS로 전달한다.
 */
