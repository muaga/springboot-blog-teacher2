package shop.mtcoding.blogv2.user;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2._core.util.FileWrite;
import shop.mtcoding.blogv2._core.vo.MyDefault;
import shop.mtcoding.blogv2._core.vo.MyPath;
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

        String fileName = null;

        if (joinDTO.getPic().isEmpty()) {
            fileName = MyDefault.DEFAULT_PROFILE_FILENAME;
        } else {
            fileName = FileWrite.save(joinDTO.getPic());
        }
        System.out.println("password1 : " + joinDTO.getPassword());

        String encPassword = BCrypt.hashpw(joinDTO.getPassword(), BCrypt.gensalt());
        System.out.println("password2 : " + encPassword);

        User user = User.builder()
                .username(joinDTO.getUsername())
                .password(encPassword)
                .email(joinDTO.getEmail())
                .picUrl(fileName)
                // DB에는 파일경로를 넣을 것이기 때문에
                // 파일경로 이름을 저장할 때, 파일의 이름만 저장해놓는 것이 좋다.
                // 왜냐하면 경로가 나중에 변경될 수 있는데, 경로가 변경되면 DB도 변경해야 된다.
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
        boolean isValue = BCrypt.checkpw(loginDTO.getPassword(), user.getPassword());
        System.out.println("login의 password : " + loginDTO.getPassword());
        System.out.println("user의 password : " + user.getPassword());
        System.out.println("password의 isValue : " + isValue);
        if (isValue == false) {
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
        User user = userRepository.findById(id).get();

        String fileName = null;

        if (updateDTO.getPic().isEmpty()) {
            fileName = user.getPicUrl();
            user.setPicUrl(fileName);
        } else {
            fileName = FileWrite.save(updateDTO.getPic());
        }
        user.setPicUrl(fileName);

        String encPassword = BCrypt.hashpw(updateDTO.getPassword(), BCrypt.gensalt());
        user.setPassword(encPassword);

        return user;
    }

    public User 유저찾기(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }

}
