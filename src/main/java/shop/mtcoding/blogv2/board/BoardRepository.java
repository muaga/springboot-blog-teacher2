package shop.mtcoding.blogv2.board;

import org.springframework.data.jpa.repository.JpaRepository;

/*
 * @Repository 생략이 가능한 JpaRepository
 * <model, PK타입>
 * 기본 CRUD를 가지고 있다(update) - 만들 필요 없다.
 * save(), findById(), findAll(), count(), deleteById()
 */
public interface BoardRepository extends JpaRepository<Board, Integer> {

}