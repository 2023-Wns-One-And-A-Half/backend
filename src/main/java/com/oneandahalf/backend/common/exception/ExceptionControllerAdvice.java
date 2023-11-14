package com.oneandahalf.backend.common.exception;


import static com.oneandahalf.backend.common.exception.ErrorCode.INTERNAL_SERVER_ERROR_CODE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorCode> handleException(ApplicationException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.info("잘못될 요청이 들어왔습니다. ", e);
        return ResponseEntity.status(errorCode.status())
                .body(errorCode);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorCode> handleException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        String fieldErrors = result.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
        log.info("요청 파라미터가 잘못되었습니다. {}", fieldErrors);
        return ResponseEntity.badRequest()
                .body(new ErrorCode(BAD_REQUEST, fieldErrors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorCode> handleException(Exception e) {
        log.error("예상치 못한 예외가 발생했습니다.  ", e);
        return ResponseEntity.internalServerError()
                .body(INTERNAL_SERVER_ERROR_CODE);
    }
}
