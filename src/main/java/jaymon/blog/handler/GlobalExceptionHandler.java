package jaymon.blog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice // 어떤 파일이든 예외가 발생하면 이것을 조회
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(value = IllegalArgumentException.class) //어떤 예외인지 지정
    public String handleArgumentException(IllegalArgumentException e) {
        return "<h1>" + e.getMessage() + "<h1>";
    }
}
