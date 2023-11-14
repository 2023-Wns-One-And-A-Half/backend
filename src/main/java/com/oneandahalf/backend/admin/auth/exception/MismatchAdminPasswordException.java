package com.oneandahalf.backend.admin.auth.exception;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.oneandahalf.backend.common.exception.ApplicationException;
import com.oneandahalf.backend.common.exception.ErrorCode;

public class MismatchAdminPasswordException extends ApplicationException {

    public MismatchAdminPasswordException() {
        super(new ErrorCode(UNAUTHORIZED, "비밀번호가 일치하지 않습니다."));
    }
}
