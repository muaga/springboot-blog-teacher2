package shop.mtcoding.blogv2.Board;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import shop.mtcoding.blogv2.board.Board;
import shop.mtcoding.blogv2.board.BoardRepository;
import shop.mtcoding.blogv2.user.User;

// JUnit으로 DB 테스트하기

@DataJpaTest // 모든 Repository, EntityManager- 새로운 스레드 속 IoC컨테이너에 띄운다.
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository; // 현재 null

    // save() test
    @Test
    public void save_test() {
        // 영속화 되기 전 - PK X = 비영속성 객체
        Board board = Board.builder()
                .title("제목")
                .content("내용6")
                .user(User.builder().id(1).build()).build();
        // insert될 때 user_id만 들어가기 때문에, user 객체가 아닌 id를 넣어준다.
        // session이 생기면 session을 넣으면 된다!!
        // Board.java에 Timestamp에 @CreationTimestamp를 하지 않았다면
        // board.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        System.out.println("용 : " + board.getId());

        // 영속화 된 후 - PK O = 영속성 객체
        boardRepository.save(board); // insert 자동 실행
        // DB와 동기화 됨
        System.out.println("용 : " + board.getId());

    }
}
