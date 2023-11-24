package jaymon.blog.controller.api;

import jaymon.blog.dto.ResponseDto;
import jaymon.blog.model.RoleType;
import jaymon.blog.model.User;
import jaymon.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;


    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user) {
        System.out.println("UserApiController:save호출됨");
        user.setRole(RoleType.USER);
        userService.회원가입(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1); // 자바오브젝트를 JSON으로 변환해서 리턴
    }

    @PutMapping("/user")
    public ResponseDto<Integer> update(@RequestBody User user) { // resquestbody 없으면 key=value, x-www-form-urlencoded 데이터를 받아옴
        userService.회원수정(user);
        //여기서는 트랜잭션이 종료되기 때문에 DB의 값은 변경이 됐음.
        //하지만 세션값은 변경되지 않은 상태.
        //세션 등록
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
