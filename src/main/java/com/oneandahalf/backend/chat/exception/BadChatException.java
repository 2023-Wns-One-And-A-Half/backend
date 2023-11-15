package com.oneandahalf.backend.chat.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.oneandahalf.backend.common.exception.ApplicationException;
import com.oneandahalf.backend.common.exception.ErrorCode;

public class BadChatException extends ApplicationException {

    public BadChatException(String message) {
        super(new ErrorCode(BAD_REQUEST, message));
    }
}
