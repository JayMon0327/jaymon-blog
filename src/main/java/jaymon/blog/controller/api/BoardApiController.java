package jaymon.blog.controller.api;

import jaymon.blog.config.auth.PrincipalDetail;
import jaymon.blog.dto.ResponseDto;
import jaymon.blog.model.*;
import jaymon.blog.service.BoardService;
import jaymon.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardApiController {

    private final BoardService boardService;

    @PostMapping("/api/board")
    public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
        boardService.글쓰기(board, principal.getUser());
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1); // 자바오브젝트를 JSON으로 변환해서 리턴
    }

    @DeleteMapping("/api/board/{id}")
    public ResponseDto<Integer> deleteById(@PathVariable int id) {
        boardService.글삭제하기(id);
        System.out.println("아이디는 이거야"+id);
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }

    @PutMapping("/api/board/{id}")
    public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board) {
        boardService.글수정하기(id, board);
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }

    //데이터 받을 때 컨트롤러에서 dto를 만들어서 받는게 좋다.
    @PostMapping("/api/board/{id}/reply")
    public ResponseDto<Integer> replySave(@RequestBody ReplySaveRequestDto dto, @AuthenticationPrincipal PrincipalDetail principal) {
        boardService.댓글쓰기(dto);
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }

    @DeleteMapping("/api/board/{boardId}/reply/{replyId}")
    public ResponseDto<Integer> replyDelete(@PathVariable int replyId) {
        boardService.댓글삭제(replyId);
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }
}
