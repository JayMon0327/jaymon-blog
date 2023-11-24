package jaymon.blog.controller;

import jaymon.blog.config.auth.PrincipalDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class BoardController {

    @GetMapping({"/",""})
    public String index(@AuthenticationPrincipal PrincipalDetail principal) { //컨트롤러에서 세션을 어떻게 찾나?
        //WEB-INF/VIEWS/index.jsp
        System.out.println("로그인 사용자 아이디: "+principal.getUsername());
        return "index";
    }
}
