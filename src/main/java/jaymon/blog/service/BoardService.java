package jaymon.blog.service;

import jaymon.blog.model.*;
import jaymon.blog.repository.BoardRepository;
import jaymon.blog.repository.ReplyRepository;
import jaymon.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    public void 글쓰기(Board board, User user) { //title, content
        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);
    }

    public Page<Board> 글목록(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    public Board 글상세보기(int id) {
        return boardRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("상세보기 실패: 게시판 아이디가 없습니다.");
        });
    }

    @Transactional
    public void 글삭제하기(int id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void 글수정하기(int id, Board requestBoard) {
        Board board = boardRepository.findById(id)
                .orElseThrow(()->{
            return new IllegalArgumentException("글 찾기 실패");
        }); //영속화 완료
        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());
        //해당 함수로 종료시에(service가 종료될때) 트랜잭션이 종료됩니다. 이때 더티체킹- 자동업데이트가 됨. db flush
    }

    @Transactional
    public void 댓글쓰기(ReplySaveRequestDto dto) {
        int replyResult = replyRepository.mSave(dto.getUserId(), dto.getBoardId(), dto.getContent());
    }

    @Transactional
    public void 댓글삭제(int replyId) {
        replyRepository.deleteById(replyId);
    }
}
