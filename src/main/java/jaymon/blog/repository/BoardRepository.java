package jaymon.blog.repository;

import jaymon.blog.model.Board;
import jaymon.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Integer> {

}

