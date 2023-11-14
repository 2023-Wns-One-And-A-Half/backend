package com.oneandahalf.backend.keyword.exception;

import static org.springframework.http.HttpStatus.CONFLICT;

import com.oneandahalf.backend.common.exception.ApplicationException;
import com.oneandahalf.backend.common.exception.ErrorCode;

public class DuplicateKeywordException extends ApplicationException {

    public DuplicateKeywordException() {
        super(new ErrorCode(CONFLICT, "중복된 키워드가 존재합니다."));
    }
}
