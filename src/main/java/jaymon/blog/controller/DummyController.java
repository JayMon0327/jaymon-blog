package jaymon.blog.controller;

import jaymon.blog.model.RoleType;
import jaymon.blog.model.User;
import jaymon.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

@RestController
@RequiredArgsConstructor
public class DummyController {

    private final UserRepository userRepository;

    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable int id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return "삭제에 실패하였습니다. 해당 id는 없습니다.";
        }
        return "삭제되었습니다 id = "+id;
    }

    //Email, password
    @PutMapping("/dummy/user/{id}")
    @Transactional
    public User update(@PathVariable int id, @RequestBody User requestUser) {
        //RequestBody = Json데이터가 요청 -> Java Object로 자동으로 받아줌(메세지 컨버터 작동)
        System.out.println("id: "+id);
        System.out.println("password: "+requestUser.getPassword());
        System.out.println("email: "+requestUser.getEmail());

        User user = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("수정에 실패하였습니다.");
        });
        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());

        return user;
    }

    @GetMapping("/dummy/users")
    public List<User> list() {
        return userRepository.findAll();
    }

    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC)
                                   Pageable pageable) {
        Page<User> pagingUser = userRepository.findAll(pageable);
        List<User> users = pagingUser.getContent();
        return users;
    }

    // http://localhost:8080/dummy/join(요청)
    // http의 body에 username, password, email 데이터를 가지고 (요청)
    @PostMapping("/dummy/join")
    public String join(User user) {
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getEmail());

        user.setRole(RoleType.USER);
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }

    //{id} 주소로 파라미터를 전달 받을 수 있음
    //http://localhost:8080/dummy/user/3
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id) {
        //user/4 을 찾으면 내가 데이터베이스에서 못 찾아오게 되면 user가 null이 될것아냐?
        //그럼 return null이 리턴이 되자나.. 그럼 프로그램에 문제가 있지 않겠니?
        //Optional로 너의 User객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return 해!


        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당유저는 없습니다." + id);
            }
        });

        //요청: 웹브라우저
        //user 객체 = 자바 오브젝트
        //변환(웹브라우저가 이해할 수 있는 데이터) -> json
        // 스프링부트 = MessageConverter 자동작동
        // 만약 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson라이브러리를 호출해서
        // user 오브젝트를 json으로 변환해서 브라우저에게 던져줍니다.
        return user;
    }
}
