package com.oneandahalf.backend.common.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.oneandahalf.backend.common.exception.ApplicationException;
import com.oneandahalf.backend.common.exception.ErrorCode;

public class NotFoundEntityException extends ApplicationException {

    public NotFoundEntityException(String message) {
        super(new ErrorCode(NOT_FOUND, message));
    }
}
