package shop.mtcoding.blogv2.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {

    // executeQuery
    @Query(value = "select * from user_tb where id = :id", nativeQuery = true)
    User mFindById(@Param("id") Integer id);

    // 새로운 쿼리형태의 findByUsername
    // executeQuery
    @Query(value = "select * from user_tb where username = :username", nativeQuery = true)
    User findByUsername(@Param("username") String username);
    // Tip! - 추천하지는 않아.... 없는 버전을 보는 사람이 공부해야 하니까, 협업 시 배려
    // @Query(value = "select * from user_tb where username = :username",
    // nativeQuery = true)
    // 없어도 쿼리 발동이 된다. 알아서 findBy를 읽고 만들어준다.
    // findBy가 쿼리메소드이기 때문이다.

    @Modifying // executeUpdate
    @Query(value = "insert into user_tb(created_at, email, password, username) values(now(), :email, :password, :username)", nativeQuery = true)
    void mSave(@Param("username") String username, @Param("password") String password, @Param("email") String email);
}
