package com.oneandahalf.backend.common.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class NotFoundEntityException extends ApplicationException {

    public NotFoundEntityException(String message) {
        super(new ErrorCode(NOT_FOUND, message));
    }
}
