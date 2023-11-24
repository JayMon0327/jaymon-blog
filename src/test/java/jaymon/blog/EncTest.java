package jaymon.blog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class EncTest {

    @Test
    void 해쉬_암호화(){
        String encode = new BCryptPasswordEncoder().encode("1234");
        System.out.println("해쉬암호: "+encode);
    }
}
