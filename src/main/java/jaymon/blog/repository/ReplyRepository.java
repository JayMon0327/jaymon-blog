package jaymon.blog.repository;

import jaymon.blog.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    @Modifying
    @Query(value = "INSERT INTO reply(user_id, board_id, content, create_date) VALUES(?,?,?,now())", nativeQuery = true)
    int mSave(int userId, int boardId, String content); // JDBC가 업데이트된 행의 개수를 리턴해줌.(정상: 1,null: 0,오류: -1)
}
