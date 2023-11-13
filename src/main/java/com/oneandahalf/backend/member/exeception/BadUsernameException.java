package com.oneandahalf.backend.member.exeception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.oneandahalf.backend.common.exception.ApplicationException;
import com.oneandahalf.backend.common.exception.ErrorCode;

public class BadUsernameException extends ApplicationException {

    public BadUsernameException() {
        super(new ErrorCode(BAD_REQUEST, "아이디는 8글자 이상이어야 합니다."));
    }
}
