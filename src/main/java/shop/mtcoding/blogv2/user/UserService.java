package shop.mtcoding.blogv2.user;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2._core.error.ex.MyException;
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

        // 1. 파일명에 랜덤한 해시값을 만들어준다. = 충돌방지
        UUID uuid = UUID.randomUUID();
        // uuid : 전세계에서 유일한 식별자를 만들어 주는 표준 규약
        String fileName = uuid + "_" + joinDTO.getPic().getOriginalFilename(); // getOriginalFilename은 확장자를 가지고 있어 제일 뒤에
                                                                               // 위치해야 한다.
        System.out.println("fileName : " + fileName);

        // 2. 경로지정(./ = 상대경로)
        // 상대경로가 좋은 점. OS가 다르면 절대경로 사용시 경로가 오류날 수 있다. 그래서 상대경로가 가장 좋다.
        // 프로젝트 실행 파일변경 -> blogv2-1.0.jar(build-gradel에서 version을 변경했다.)
        // 해당 실행파일 경로에 images 폴더가 필요함
        Path filePath = Paths.get(MyPath.IMG_PATH + fileName);
        try {
            Files.write(filePath, joinDTO.getPic().getBytes());
        } catch (Exception e) {
            // 폴더, 경로, 파일 등의 오류 처리
            throw new MyException(e.getMessage());
        }

        User user = User.builder()
                .username(joinDTO.getUsername())
                .password(joinDTO.getPassword())
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
