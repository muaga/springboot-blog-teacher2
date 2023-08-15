package shop.mtcoding.blogv2.user;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// QueryRepository
// JpaRespository의 진행을 볼 수 있는 Repository
// JPA의 특징을 가지고 있는 쿼리문이다.
// 아래는 영속성 컨텍스트의 동작을 배우기 위한 코드

@Repository
public class UserQueryRepository {

    @Autowired
    private EntityManager em;

    // insert
    public void save(User user) {
        em.persist(user);
        // 영속화 (영속성 컨텍스트)
        // JpaRepository 속 save가 위와 같이 생겼다.
    }

    // select
    // 사용은 JpaRepository로 사용
    public User findById(Integer id) {
        // Query query = em.createQuery("select u from User as u where u.id = :id", User.class);
        // query.setParameter("id", id);
        // return (User) query.getSingleResult();
        //
        return em.find(User.class, id);
        // JpaRepository 속 find가 위와 같이 생겼다.

        // 둘의 차이
        // 쿼리 -> 요청마다 쿼리를 보낸다
        // find -> 1차 캐시 후 PC에 존재하지 않으면, 쿼리를 보낸다. 
    }
}
