package shop.mtcoding.blogv2.board;

import org.springframework.data.jpa.repository.JpaRepository;

/*
 * save(), findById(), findAll(), count(), deleteById()
 */
// 스프링이 실행될 때, BoardRepository의 구현체(인터페이스의 구현 class)가 IoC 컨테이너에 생성된다
public interface BoardRepository extends JpaRepository<Board, Integer> {

}