package com.oneandahalf.backend.member.exception;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.oneandahalf.backend.common.exception.ApplicationException;
import com.oneandahalf.backend.common.exception.ErrorCode;

public class MissMatchPasswordException extends ApplicationException {

    public MissMatchPasswordException() {
        super(new ErrorCode(UNAUTHORIZED, "비밀번호가 일치하지 않습니다."));
    }
}
