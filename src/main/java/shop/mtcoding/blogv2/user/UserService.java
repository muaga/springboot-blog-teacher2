package shop.mtcoding.blogv2.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2.user.UserRequest.LoginDTO;
import shop.mtcoding.blogv2.user.UserRequest.UpdateDTO;

@Service
// 컴포넌트 = IoC컨테이너에 속한다.
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    // insert / update / delete
    public void 회원가입(UserRequest.JoinDTO joinDTO) {
        User user = User.builder()
                .username(joinDTO.getUsername())
                .password(joinDTO.getPassword())
                .email(joinDTO.getEmail())
                .build();
        userRepository.save(user); // em.persist
        // JpaRepository를 사용할 때 entity 자리는 명시한 타입인 User만 들어갈 수 있다.
    }

    public User 로그인(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername());
        // 1. username이 존재하지 않음 또는 username을 잘못 작성
        if (user == null) {
            throw new MyException("유저네임이 없습니다.");
        }
        // 2. 패스워드 검증
        if (!user.getPassword().equals(loginDTO.getPassword())) {
            throw new MyException("패스워드가 없습니다.");
        }
        // 3. 로그인 성공
        return user;
        // 위의 throw를 통해, user가 null이 되는 경우는 절대 존재하지 않는다.
        // 그래서 Controller에서 sessionUser의 비즈니스로직이 없어져도 된다.
        // ------------> if로 걸러내는 것이 아주 좋다.
    }

    public User 회원정보보기(Integer id) {
        return userRepository.findById(id).get();
    }

    @Transactional
    public User 회원정보수정(UpdateDTO updateDTO, Integer id) {
        // 1. 조회 (영속화)
        User user = userRepository.findById(id).get();

        // 2. 변경
        user.setPassword(updateDTO.getPassword());

        return user;
    } // 3. flush

    public User 유저찾기(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }

}
