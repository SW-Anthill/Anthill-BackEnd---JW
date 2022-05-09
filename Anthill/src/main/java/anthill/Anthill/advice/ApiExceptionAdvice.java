package anthill.Anthill.advice;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class ApiExceptionAdvice {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> dataBaseExceptionHandler(DataIntegrityViolationException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body("데이터 중복 에러");
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> internalServerExceptionHandler(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 에러");
    }
}
