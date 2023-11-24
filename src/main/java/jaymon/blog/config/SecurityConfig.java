package jaymon.blog.config;

import jaymon.blog.config.auth.PrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//빈 등록: 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것. 아래 3개는 세트임
@Configuration //빈등록(IoC관리)
@EnableWebSecurity // 시큐리티 필터가 등록이 된다.
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 처리하겠다는 뜻
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encoderPWD() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private PrincipalDetailService principalDetailService;

    //시큐리티가 대신 로그인 해줄때 password를 가로채게 되는데
    //해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
    // 같은 해쉬로 암호화 해서 DB에 있는 해쉬랑 비교할 수 있음
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailService).passwordEncoder(encoderPWD());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //csrf 토큰 비활성화
                .authorizeHttpRequests()//요청이 들어오면
                .antMatchers("/","/auth/**","/css/**","/js/**","/image/**")//이 주소로 들어오는건
                .permitAll()//모두 허용한다.
                .anyRequest()//설정된 주소가 아닌 다른 모든 요청은
                .authenticated() //인증이 되어야 해
                .and()
                .formLogin()
                .loginPage("/auth/loginForm")
                .loginProcessingUrl("/auth/loginProc")//스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인해줌
                .defaultSuccessUrl("/");
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}


//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { //WebSecurityConfigurerAdapter 없이
//        http
//            .csrf().disable() //csrf 토큰 비활성화
//            .authorizeHttpRequests()//요청이 들어오면
//                .antMatchers("/","/auth/**","/css/**","/js/**","/image/**")//이 주소로 들어오는건
//                .permitAll()//모두 허용한다.
//                .anyRequest()//설정된 주소가 아닌 다른 모든 요청은
//                .authenticated() //인증이 되어야 해
//            .and()
//                .formLogin()
//                .loginPage("/auth/loginForm")
//                .loginProcessingUrl("/auth/loginProc")//스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인해줌
//                .defaultSuccessUrl("/");
//        return http.build();
//
//
//    }
