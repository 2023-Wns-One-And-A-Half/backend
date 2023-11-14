package com.oneandahalf.backend.product.exception;

import static org.springframework.http.HttpStatus.CONFLICT;

import com.oneandahalf.backend.common.exception.ApplicationException;
import com.oneandahalf.backend.common.exception.ErrorCode;

public class DuplicateInterestException extends ApplicationException {

    public DuplicateInterestException() {
        super(new ErrorCode(CONFLICT, "이미 관심등록한 상품입니다."));
    }
}
