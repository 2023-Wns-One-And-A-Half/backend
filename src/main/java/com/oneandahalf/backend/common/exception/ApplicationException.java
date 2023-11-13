package com.oneandahalf.backend.common.exception;

import static com.oneandahalf.backend.common.exception.ErrorCode.INTERNAL_SERVER_ERROR_CODE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

    private final ErrorCode errorCode;

    public ApplicationException() {
        this(INTERNAL_SERVER_ERROR_CODE);
    }

    public ApplicationException(String message) {
        this(new ErrorCode(INTERNAL_SERVER_ERROR, message));
    }

    public ApplicationException(ErrorCode errorCode) {
        super(errorCode.message());
        this.errorCode = errorCode;
    }
}
