package shop.mtcoding.blogv2.User;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import shop.mtcoding.blogv2.user.User;
import shop.mtcoding.blogv2.user.UserQueryRepository;

@Import(UserQueryRepository.class) // 강제로 IoC컨테이너에 띄우기
@DataJpaTest
public class UserQueryRepositoryTest {
    
    @Autowired
    private UserQueryRepository userQueryRepository;

    @Autowired
    private EntityManager em;

    // persist(영속화)
    @Test
    public void save_test(){
        User user = User.builder()
        .username("love")
        .password("1234")
        .email("love@nate.com")
        .build();
        userQueryRepository.save(user); 
        // em.flush();
    }

    // clear(초기화)와 1차 캐시
    @Test
    public void findById_test(){
        // 1. pc는 현재 비어있다.
        System.out.println("1. pc는 비어있다.");
        userQueryRepository.findById(1);
        // findById_test를 실행했을 때 select 쿼리가 실행되는 것을 볼 수 있다.
        // 1차캐시를 하는데 pc가 비어있기 때문에, DB에 select 쿼리를 실행하는 것을 볼 수 있다.

        em.clear();
        // PC 초기화

        System.out.println("2. pc의 user 1은 영속화 되어 있다.");
        userQueryRepository.findById(1);
        // 2. pc는 user 1의 객체가 영속화 되어 있다.
        // id 1 : 1차캐시로 인해 이미 PC에 User 1이 존재하여 쿼리가 실행되지 않는다.
        // id 2 : PC에 User 2는 없으므로 쿼리가 실행된다.
    }

    // flush
    @Test
    public void update_test(){
        // JPA update의 알고리즘 - update쿼리 발동시켜도 된다!
        // 1. 업데이트 할 객체를 영속화
        // 2. 객체 상태 변경
        // 3. em.flush() or @Transactionl 정상 종료-자동flush

        User user = userQueryRepository.findById(1); // 영속화 된 객체
        user.setEmail("ssarmango@nate.com"); // 객체 상태 변경
        em.flush();
    }
}
