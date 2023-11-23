package jaymon.blog.controller.api;

import jaymon.blog.dto.ResponseDto;
import jaymon.blog.model.RoleType;
import jaymon.blog.model.User;
import jaymon.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PostMapping("/api/user")
    public ResponseDto<Integer> save(@RequestBody User user) {
        System.out.println("UserApiController:save호출됨");
        user.setRole(RoleType.USER);
        userService.회원가입(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1); // 자바오브젝트를 JSON으로 변환해서 리턴
    }

    @PostMapping("/blog/api/user/login")
    public ResponseDto<Integer> login(@RequestBody User user) {
        System.out.println("login호출됨");
        userService.로그인(user); //principal = 접근주체
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);

    }
}
