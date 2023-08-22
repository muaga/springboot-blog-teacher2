package shop.mtcoding.blogv2.Board;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.blogv2.board.Board;
import shop.mtcoding.blogv2.board.BoardRepository;
import shop.mtcoding.blogv2.user.User;

// JUnit으로 DB 테스트하기

@DataJpaTest // 모든 Repository, EntityManager- 새로운 스레드 속 IoC컨테이너에 띄운다.
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository; // 현재 null

    @Test
    public void mfindByIdJoinUserNReply_test() {
        Board board = boardRepository.mfindByIdJoinReplyInUseBoard(1).get();
        // System.out.println("board : id : " + board.getId());
        // System.out.println("board : title : " + board.getTitle());
        // System.out.println("board : content : " + board.getContent());
        // System.out.println("board : createdAt : " + board.getCreatedAt());
        // System.out.println("===========================================");
        // System.out.println("board in user : id : " + board.getUser().getId()); //
        // board 작성자 = user라면, LazyLoading 실행 X /
        // // 동일인물이라서 1차 캐싱 발생
        // System.out.println("board in user : username : " +
        // board.getUser().getUsername());
        // System.out.println("===========================================");
        // board.getReplies().stream().forEach(r -> {
        // System.out.println("board in replies : id : " + r.getId());
        // System.out.println("board in replies : id : " + r.getComment());
        // System.out.println("board in replies : id : " + r.getUser().getId());
        // });
        // 추가적인 LazyLoading이 나오면 안된다.
    }

    @Test
    public void deleteById_test() {
        boardRepository.findAll();
        boardRepository.deleteById(6);
        System.out.println("delete 성공");
        Optional<Board> boardOP = boardRepository.findById(6);
        if (boardOP.isPresent()) {
            System.out.println("테스트 : board가 있습니다.");
        } else {
            System.out.println("테스트 : board가 없습니다.");
        }
    }

    @Test
    public void findById_test() {
        Optional<Board> boardOP = boardRepository.findById(5);
        if (boardOP.isPresent()) {
            System.out.println("테스트 : board가 있습니다.");
        }
    }

    // paging - findAll 이용하기
    // 페이지에 따라 true/false를 자동해준다.
    @Test
    public void findAll_paging_test() throws JsonProcessingException {
        Pageable pageable = PageRequest.of(0, 3, Sort.Direction.DESC, "id");
        Page<Board> boardPG = boardRepository.findAll(pageable);

        // List는 JSON으로 보는 것이 가장 좋다!
        ObjectMapper om = new ObjectMapper();
        // 1.자바객체를 JSON으로 변환하게 하는 ObjectMapper를 생성
        // ★ ObjectMapper는 boardPG객체의 getter를 호출하면서 JSON을 만든다!
        String json = om.writeValueAsString(boardPG); // 2. 자바객체를 String타입으로 JSON형태를 가지게 한다.
        // ★ No serialize found for .... 오류
        // FAIL_ON_EMPTY_BEANS = BEANS(객체)가 비었을 때, 오류를 터지게 할까?
        // 이유 : 타이밍의 문제(동시적으로 실행되어서...)
        // Lazy전략일 때, findAll -> PC에 Board객체만 들어갈 것이다.
        // PC에 있는 Board객체를 JSON 형태로 바꾸는 행위 중에
        // 예를 들어 Board.User.getUsername을 가져오는데 Lazy전략으로, 해당 값은 null이지만
        // null값을 조회하는 동시에 User의 객체를 select한다.
        // 이 때, Username도 JSON 형태로 바꾸는 행위와 User의 객체를 select하는 행위의 시간은 같은 시간인데
        // 결국 JSON이 select하고 있는 값을 받아오지 못하는 타이밍의 문제로 터지는 것이다.
        // JSON이 select하는 시간을 조금 기다려준다면 오류는 발생하지 않는다.
        // * mustache는 기다림
        // 해결방법
        // 1. application-fail-on-empty-beans = false(오류 NO)
        // 2. Lazy -> Eager
        System.out.println(json);
    }

    // findAll() test
    @Test
    public void findAll_test() {
        System.out.println("조회 직전");
        List<Board> boardList = boardRepository.findAll();
        System.out.println("조회 후 : Lazy");
        // 행 : 5개 -> 객체 : 5개
        // 각 행(Lazy) : Board(id=1, title=제목1, content=내용1, created_at=날짜, User(id=1)) <-
        // User 필드 없다.
        System.out.println("boardList의 0번지 board_id : " + boardList.get(0).getId()); // 1번
        System.out.println("boardList의 0번지 User_id : " + boardList.get(0).getUser().getId()); // 1번
        // LazyLoading - 지연로딩
        // 연관된 객체에 null 값을 찾으려고 하면(실행시점) 조회가 일어난다.
        // 찾는다 = get객체
        // 즉, 원하는 값이 없을 때, hibernate가 값을 얻기 위해 자동으로 select user를 해서 usename을 가지고 와준다.
        // 필요한 이유
        // 쿼리실행이 LazyLoading = EAGER 똑같다. 왜 필요할까?
        // fetch를 사용하면, 필요없는 데이터까지 조회하므로 LazyLoading을 통해 필요한 데이터만 조회할 수 있도록 한다.
        // 만약, EAGER일 때는 미리 연관된 객체도 조회하기 때문에, LazyLoading이 실행되지 않는다.
        System.out.println(boardList.get(0).getUser().getUsername()); // null -> ssar

        // ★ JPA 1 + N
        // 연관된 쿼리를 모두 실행한다.
        // select 쿼리가 3번(board-1 / user -2) 조회된 이유
        // board findAll과 board를 작성한 user에 대해서 findById를 조회했다.
        // 더미데이터를 보면, board를 작성한 사람은 ssar, cos 총 2명이다.
        // findAll을 실행하면, JPA 1 + N이 실행된다.
        // 실무에서 사용하면 엄청 느려지기 때문에, join으로 해결한다. = 튜닝
    }

    // mFindAll() test
    @Test
    public void mFindAll_test() {
        boardRepository.mFindAll();
        // 1. select b from Board as b join b.user
        // join쿼리가 실행되지만, select 절에 user에 대한 정보가 나오지 않는다. - 총 3번
        // 2. select b from Board as b fetch join b.user
        // select 절에 user의 정보도 나오면서, join쿼리로 1개만 실행된다. - 총 1번
    }

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
