package com.oneandahalf.backend.member.exception;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.oneandahalf.backend.common.exception.ApplicationException;
import com.oneandahalf.backend.common.exception.ErrorCode;

public class NoAuthenticationSessionException extends ApplicationException {

    public NoAuthenticationSessionException() {
        super(new ErrorCode(UNAUTHORIZED, "인증 정보가 없거나 만료되었습니다."));
    }
}
