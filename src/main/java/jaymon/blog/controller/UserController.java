package jaymon.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jaymon.blog.model.KakaoProfile;
import jaymon.blog.model.OAuthToken;
import jaymon.blog.model.User;
import jaymon.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

//인증이 아노딘 사용자들이 출입할 수 있는 경로를 /auth/** 허용
//그냥 주소가 /이면 index.jsp 허용
// static 이하에 있는 /js/**, /css/** 허용

@Controller
@RequiredArgsConstructor
public class UserController {

    @Value("${cos.key}")
    private String cosKey;

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/auth/joinForm")
    public String joinForm() {

        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm() {

        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {

        return "user/updateForm";
    }

    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(String code) {
        /**
         * 1. 인가 코드 요청 - 인증
         */
        //POST방식으로 key=value 데이터를 요청(카카오쪽으로) RestTemplate 라이브러리 사용
        RestTemplate rt = new RestTemplate();

        //HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type","application/x-www-form-urlencoded;charset=utf-8");

        //HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id","bcb7a72775a4dbdfe29dd6f52b870a50");
        params.add("redirect_uri","http://localhost:8080/auth/kakao/callback");
        params.add("code",code);

        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        //Http 요청하기 - POST방식으로 - 그리고 response 변수의 응답받음.
        ResponseEntity<String> authorizationResponse = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class

        );

        /**
         * 2. 인가 코드로 (액세스)토큰 발급 요청
         */

        //Json 데이터를 오브젝트에 담을거야 -> Gson, Json Simple, ObjectMapper 라이브러리 들이 있음
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;
        try {
            oAuthToken = objectMapper.readValue(authorizationResponse.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        //POST방식으로 key=value 데이터를 요청(카카오쪽으로) RestTemplate 라이브러리 사용
        RestTemplate rt2 = new RestTemplate();

        //HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("content-type","application/x-www-form-urlencoded;charset=utf-8");
        headers2.add("Authorization", "Bearer "+ oAuthToken.getAccess_token());

        //HttpHeader를 HttpEntity객체로 만듬
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers2);

        //Http 요청하기 - POST방식으로 - 그리고 response 변수의 응답받음.
        ResponseEntity<String> tokenResponse = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class

        );

        /**
         * 3. 토큰 정보 조회 및 검증(사용자 정보 조회)
         */
        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(tokenResponse.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        //User 오브젝트: username, password, email
        System.out.println("카카오 아이디(번호): "+kakaoProfile.getId());
        System.out.println("카카오 회원이름: "+kakaoProfile.getProperties().nickname);
        System.out.println("블로그 서버 유저네임: "+kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
        System.out.println("블로그 서버 이메일: "+kakaoProfile.getKakao_account().getEmail());
        System.out.println("블로그 서버 패스워드: "+cosKey);

        //카카오로부터 받아온 사용자 정보로 빌더패턴으로 유저 객체 생성
        User kakaoUser = new User().builder()
                .username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
                .password(cosKey)
                .email(kakaoProfile.getKakao_account().getEmail())
                .oauth("kakao")
                .build();

        /**
         * 4. 로그인 처리
         */
        //기존 회원인지 확인
        User originUser = userService.회원찾기(kakaoUser.getUsername());

        if (originUser.getUsername() == null) {
            System.out.println("기존 회원이 아닙니다. 자동회원가입을 진행합니다.");
            userService.회원가입(kakaoUser);
        }

        //세션 변경(로그인처리)
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/";
    }
}
