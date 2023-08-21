package shop.mtcoding.blogv2.board;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    // join 시 화면에 보이는 것 부터 쿼리 작성 후, 필요한 데이터를 추가로 작성하면 된다.
    @Query("select b from Board as b left join fetch b.replies as r left join fetch r.user as ru where b.id = :id")
    Optional<Board> mfindByIdJoinReplyInUseBoard(@Param("id") Integer id);

}