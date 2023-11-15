package com.oneandahalf.backend.member.exception;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import com.oneandahalf.backend.common.exception.ApplicationException;
import com.oneandahalf.backend.common.exception.ErrorCode;

public class ForbiddenBlacklistException extends ApplicationException {

    public ForbiddenBlacklistException() {
        super(new ErrorCode(FORBIDDEN, "블랙리스트는 접근할 수 없습니다."));
    }
}
