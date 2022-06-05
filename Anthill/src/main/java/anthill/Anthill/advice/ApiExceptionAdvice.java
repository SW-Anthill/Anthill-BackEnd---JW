package anthill.Anthill.advice;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice(annotations = RestController.class)
public class ApiExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> dataInvalidateExceptionHandler(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body("입력 데이터가 유효하지 않습니다.");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> dataBaseExceptionHandler(DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                             .body("데이터 중복 에러");
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> LoginFailException(IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body("로그인 실패");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> DataNotFoundException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body("값이 존재하지 않습니다.");
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> internalServerExceptionHandler(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("서버 내부 에러");
    }


}
