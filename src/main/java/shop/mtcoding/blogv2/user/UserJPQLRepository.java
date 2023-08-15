package shop.mtcoding.blogv2.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// JPQL - Java Persistence Query Language
// 실제 DB쿼리가 아닌 Entity 객체를 대상으로 쿼리한다. DB쿼리와 비슷하지만 자바쿼리다. = 객체지향쿼리
// 하지만 결국 DB쿼리로 변경된다.
// JPA에서 제공하는 메소드 호출만으로 섬세한 쿼리 작성이 어렵다는 문제에서 JPQL이 탄생했다.
// 특징
// 1. 테이블이 아닌 객체 검색 -> model(@Entity 객체)로 CRUD가능하다. 왜냐하면 model로 테이블을 만들었기 때문
// 2. 대소문자 구분
// 3. insert / update / delete 지원하지 X
// 기본문법(별칭)
// String jpql = "select M(별칭 = *) from Member(Entity) as m(별칭) where m.name = 'coco'"

public interface UserJPQLRepository extends JpaRepository<User, Integer> {

    @Query(value = "select u from User as u where u.id = :id")
    Optional<User> mFindById(@Param("id") Integer id);
    // 본인이 만든 메소드에도 Optional 사용하기

    @Query(value = "select u from User as u where u.username = :username")
    User findByUsername(@Param("username") String username);

    @Modifying // JPQL 제외
    @Query(value = "insert into user_tb(created_at, email, password, username) values(now(), :email, :password, :username)", nativeQuery = true)
    void mSave(@Param("username") String username, @Param("password") String password, @Param("email") String email);
}
