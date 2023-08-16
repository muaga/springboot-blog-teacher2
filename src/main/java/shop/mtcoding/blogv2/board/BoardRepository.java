package shop.mtcoding.blogv2.board;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/*
 * @Repository 생략이 가능한 JpaRepository
 * <model, PK타입>
 * 기본 CRUD를 가지고 있다(update) - 만들 필요 없다.
 * save(), findById(), findAll(), count(), deleteById()
 */
public interface BoardRepository extends JpaRepository<Board, Integer> {

    // JPA N+1 실행 해결 - join
    // = select id, title, content, user_id, created_at from board_tb as b inner
    // join user_tb as u on b.user_id = u.id;

    // + fetch
    // = select * : 연관된 객체 안에 들어가서 모든 정보를 다 뺀다.
    @Query("select b from Board as b join b.user")
    List<Board> mFindAll();
    // JPQL에서 innerjoin = join
}