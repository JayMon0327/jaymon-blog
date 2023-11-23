package jaymon.blog.handler;

import jaymon.blog.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice // 어떤 파일이든 예외가 발생하면 이것을 조회
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class) //어떤 예외인지 지정
    public ResponseDto<String> handleArgumentException(Exception e) {
        return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}
