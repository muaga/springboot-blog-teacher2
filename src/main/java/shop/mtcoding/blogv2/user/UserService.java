package shop.mtcoding.blogv2.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2.user.UserRequest.LoginDTO;
import shop.mtcoding.blogv2.user.UserRequest.UpdateDTO;
import shop.mtcoding.blogv2.user.UserRequest.joinDTO;

@Service
// 컴포넌트 = IoC컨테이너에 속한다.
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    // insert / update / delete
    public void 회원가입(joinDTO joinDTO) {
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
            return null;
        }
        // 2. 패스워드 검증
        if (!user.getPassword().equals(loginDTO.getPassword())) {
            return null;
        }
        // 3. 로그인 성공
        return user;
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

}
