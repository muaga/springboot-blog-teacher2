package shop.mtcoding.blogv2._core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import shop.mtcoding.blogv2._core.interceptor.LoginInterceptor;

// 톰캣의 web.xml - 톰캣이 들고 있는 설정파일
// 개발자가 xml에 작성하면, 톰캣이 스스로 java파일로 변환시켜준다.
// xml을 실행하려면 java로 변환되어야 실행이 가능하다.
// 내장톰캣이 돌면, web.xml이 java파일로 IoC컨테이너에 띄어져 있다.
// IoC컨테이너에 존재하는 web.xml을 커스텀하고 싶으면, 경로를 직접 xml에 적어줘야 하는데
// 스프링의 pojo 때문에 xml에 직접 적을 수 있다.
// 아래의 방법을 사용하면 web.xml을 재정의(오버라이딩)할 수 있다.

// 컴포넌트 스캔
// 기존의 web.xml을 오버라이딩 한다.
// 타입을 맞추기 위해 WebMvcConfigurer을 implements한다.
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // xml 재정의
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 기존의 xml
        WebMvcConfigurer.super.addResourceHandlers(registry);

        // 추가설정
        registry.addResourceHandler("/images/**") // 요청이 '/images/파일' 들어오면
                .addResourceLocations("file:" + "./images/") // files : 탐색기가 "./images/"로 이동해서 파일을 찾는다.
                .setCachePeriod(60) // 10초 - 캐싱유지
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }

    // 2. 인터셉터에 기능을 가진 인터셉터 주입
    // session 필터링
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/user/update", "/user/updateForm")
                .addPathPatterns("/api/**")
                .addPathPatterns("/board/**") // 발동 조건
                .excludePathPatterns("/board/{id:[0-9]+}"); // 발동 제외

    }

}
