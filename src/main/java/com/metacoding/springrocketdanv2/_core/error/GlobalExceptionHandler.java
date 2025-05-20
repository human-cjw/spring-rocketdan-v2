package com.metacoding.springrocketdanv2._core.error;

import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi400;
import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi401;
import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi403;
import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi404;
import com.metacoding.springrocketdanv2._core.util.Resp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // @ControllerAdvice
public class GlobalExceptionHandler {

    // ResponseEntity -> 실제 상태코드를 넘겨주기 위해 사용하는 객체 예외 처리를 해도 200 코드가 날라가니 이 객체를 사용해 각각 상태코드를 다르게 넣어준다
    @ExceptionHandler(ExceptionApi400.class)
    public ResponseEntity<?> exApi400(ExceptionApi400 e) {
        return Resp.fail(HttpStatus.BAD_REQUEST, e.getMessage());

    }

    @ExceptionHandler(ExceptionApi401.class)
    public ResponseEntity<?> exApi401(ExceptionApi401 e) {
        return Resp.fail(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(ExceptionApi403.class)
    public ResponseEntity<?> exApi403(ExceptionApi403 e) {
        return Resp.fail(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(ExceptionApi404.class)
    public ResponseEntity<?> exApi404(ExceptionApi404 e) {
        return Resp.fail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exUnKnown(Exception e) {
        System.out.println("관리자님 보세요 : " + e.getMessage()); // 로그를 파일에 기록해서 나중에 봐야함
        e.printStackTrace();
        return Resp.fail(HttpStatus.INTERNAL_SERVER_ERROR, "관리자에게 문의하세요");
    }
}