package com.metacoding.springrocketdanv2._core.error;

import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi400;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.List;

@Component
@Aspect
public class GlobalValidationHandler {

    @Before("@annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void badRequestAdvice(JoinPoint jp) {
        Object[] args = jp.getArgs();

        for (Object arg : args) {

            if (arg instanceof Errors) {
                Errors errors = (Errors) arg;

                if (errors.hasErrors()) {
                    List<FieldError> fErrors = errors.getFieldErrors();
                    for (FieldError fError : fErrors) {
                        throw new ExceptionApi400(fError.getField() + ":" + fError.getDefaultMessage());
                    }
                }
            }
        }

    }
}