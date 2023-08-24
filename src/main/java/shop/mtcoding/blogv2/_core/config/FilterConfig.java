package shop.mtcoding.blogv2._core.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import shop.mtcoding.blogv2._core.filter.MyFilter1;

// 필터설정파일
// 톰캣의 필터를 스프링으로 가져오기
// 필터를 스프링 속 DS 직전에 가져온다.
// 그 때 사용하는 필터객체명이 FilterResitrationBean을 사용하면 저 위치에 놔둬준다.

// 컴포넌트 스캔 => DI 가능
@Configuration
public class FilterConfig {

    // 1. 필터발동조건과 생성
    @Bean
    public FilterRegistrationBean<MyFilter1> myFilter1() {
        FilterRegistrationBean<MyFilter1> bean = new FilterRegistrationBean<>(new MyFilter1());
        bean.addUrlPatterns("/*"); // "/"로 시작되는 모든 주소에 발동됨 = 항상발동
        bean.setOrder(0);
        // 필터번호가 0번부터 실행
        return bean;
    }

}
