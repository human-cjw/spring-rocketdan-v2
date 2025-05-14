package com.metacoding.springrocketdanv2._core.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
@Data
public class Resp<T> {
    private Integer status;
    private String msg;
    private T body;

    private Resp() {
    } // 기본생성자를 막아서 setter를 사용못하게 한다

    // ResponseEntity 형태로 바로 리턴 (성공)
    public static <B> ResponseEntity<Resp<B>> ok(B body) {
        Resp<B> resp = new Resp<>(200, "성공", body);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    // ResponseEntity 형태로 바로 리턴 (실패)
    public static ResponseEntity<Resp<?>> fail(HttpStatus status, String msg) {
        Resp<?> resp = new Resp<>(status.value(), msg, null);
        return new ResponseEntity<>(resp, status);
    }

    // 스프링의 도움을 받지 않을 때
    public static Resp<?> fail(Integer status, String msg) {
        Resp<?> resp = new Resp<>(status, msg, null);
        return resp;
    }
}