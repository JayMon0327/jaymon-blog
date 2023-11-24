package jaymon.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//빈 등록: 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것. 아래 3개는 세트임
@Configuration //빈등록(IoC관리)
@EnableWebSecurity // 시큐리티 필터가 등록이 된다.
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 처리하겠다는 뜻
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder encoderPWD() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests()//요청이 들어오면
                .antMatchers("/","/auth/**","/css/**","/js/**","/image/**")//이 주소로 들어오는건
                .permitAll()//모두 허용한다.
                .anyRequest()//설정된 주소가 아닌 다른 모든 요청은
                .authenticated() //인증이 되어야 해
            .and()
                .formLogin()
                .loginPage("/auth/loginForm");

        return http.build();


    }
}
