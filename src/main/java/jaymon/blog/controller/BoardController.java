package jaymon.blog.controller;

import jaymon.blog.config.auth.PrincipalDetail;
import jaymon.blog.model.Board;
import jaymon.blog.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    //컨트롤러에서 세션을 어떻게 찾나?
    //@AuthenticationPrincipal PrincipalDetail principal
    @GetMapping({"/",""})
    public String index(Model model, @PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC)
    Pageable pageable) {
        //WEB-INF/VIEWS/index.jsp
        Page<Board> boardPage = boardService.글목록(pageable);
        model.addAttribute("boards",boardService.글목록(pageable));
        return "index"; //view 리졸버 작동
    }

    //USER권한이 필요
    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    @GetMapping("/board/{id}")
    public String findById(@PathVariable int id, Model model) {
        model.addAttribute(boardService.글상세보기(id));
        return "board/detail";
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id, Model model) {
        model.addAttribute("board", boardService.글상세보기(id));
        return "board/updateForm";
    }
}
