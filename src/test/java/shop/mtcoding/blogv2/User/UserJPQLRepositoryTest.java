package shop.mtcoding.blogv2.User;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import shop.mtcoding.blogv2.user.User;
import shop.mtcoding.blogv2.user.UserJPQLRepository;
import shop.mtcoding.blogv2.user.UserRepository;

@DataJpaTest
public class UserJPQLRepositoryTest {

    @Autowired
    private UserJPQLRepository userJPQLRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void findByUsername_test() {
        User user = userJPQLRepository.findByUsername("ssar");
        System.out.println("테스트 : " + user.getEmail());
    } // rollback

    @Test
    public void mFindById() {
        Optional<User> userOP = userJPQLRepository.mFindById(3);

        if (userOP.isPresent()) {
            User user = userOP.get();
            System.out.println(user.getEmail());
        } else {
            System.out.println("해당 id를 찾을 수 없습니다");
        }
    }

}