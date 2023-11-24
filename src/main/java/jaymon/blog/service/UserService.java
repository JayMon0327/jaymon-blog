package jaymon.blog.service;

import jaymon.blog.model.RoleType;
import jaymon.blog.model.User;
import jaymon.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
