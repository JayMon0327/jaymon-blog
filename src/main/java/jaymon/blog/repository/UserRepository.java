package jaymon.blog.repository;

import jaymon.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//자동으로 bean등록이 된다.
//@Repository 생략 가능하다.
public interface UserRepository extends JpaRepository<User, Integer> {












    //쿼리 메소드 생성전략
    // SELECT * FROM user WHERE username = ?1 AND password = ?2;
//    User findByUsernameAndPassword(String username, String password);

    //네이티브 쿼리 전략
//    @Query(value = "SELECT * FROM user WHERE username = ?1 AND password = ?2;", nativeQuery = true)
//    User login(String username, String password);

}
