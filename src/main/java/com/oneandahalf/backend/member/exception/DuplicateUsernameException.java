package com.oneandahalf.backend.member.exception;

import static org.springframework.http.HttpStatus.CONFLICT;

import com.oneandahalf.backend.common.exception.ApplicationException;
import com.oneandahalf.backend.common.exception.ErrorCode;

public class DuplicateUsernameException extends ApplicationException {

    public DuplicateUsernameException() {
        super(new ErrorCode(CONFLICT, "중복되는 아이디입니다. 다른 아이디로 가입해주세요."));
    }
}
