package shop.mtcoding.blogv2._core.config;

import org.springframework.stereotype.Component;

@Component
// 기능의 특징은 없지만, 컴포넌트 스캔으로 싱글톤화하고 싶을 때 사용
public class Dog {
    public String name = "강아지";
}

/*
 * @Commponent와 @Configuration
 * 둘 다 컴포넌트 스캔하는 것이다.
 * 기능의 특징은 없지만, 컴포넌트 스캔으로 싱글톤화 하고 싶을 때 사용한다.
 * 
 * @Configuration과 @Bean
 * 컴포넌트 스캔을 하면 내부가 리플렉션이 되어서, 내부메소드의 return값도 IoC에 띄운다.
 * 
 * @Bean
 * 메소드의 return값을 IoC에 띄운다.
 * 
 */
