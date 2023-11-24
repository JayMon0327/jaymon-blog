package jaymon.blog.service;

import jaymon.blog.model.RoleType;
import jaymon.blog.model.User;
import jaymon.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public void 회원가입(User user) {
        String rawPassword = user.getPassword(); // 1234
        String endPassword = encoder.encode(rawPassword);
        user.setPassword(endPassword);
        user.setRole(RoleType.USER);
        userRepository.save(user);
    }

    @Transactional
    public void 회원수정(User user) {
        //수정시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고(select으로 먼저불러옴으로써 영속화시킴), 영속화된 User오브젝트를 수정
        //select을 해서 User오브젝트를 DB로 부터 가져오는 이유는 영속화를 하기 위해서 !!
        // 영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려주거든요.
        User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
            return new IllegalArgumentException("회원 찾기 실패");
        });
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);
        persistance.setPassword(encPassword);
        persistance.setEmail(user.getEmail());

        // 회원 수정 함수 종료시 = 서비스 종료 = 트랜잭션 종료 = commit 이 자동으로 됩니다.
        // 영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update문을 날려줌.

    }
}
