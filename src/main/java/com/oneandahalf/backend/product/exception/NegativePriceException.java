package com.oneandahalf.backend.product.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.oneandahalf.backend.common.exception.ApplicationException;
import com.oneandahalf.backend.common.exception.ErrorCode;

public class NegativePriceException extends ApplicationException {

    public NegativePriceException() {
        super(new ErrorCode(BAD_REQUEST, "가격은 0원 이상이어야 합니다."));
    }
}
