package com.oneandahalf.backend.trade.exception;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import com.oneandahalf.backend.common.exception.ApplicationException;
import com.oneandahalf.backend.common.exception.ErrorCode;

public class NoAuthoritySellProductException extends ApplicationException {

    public NoAuthoritySellProductException() {
        super(new ErrorCode(FORBIDDEN, "상품을 판매할 권한이 없습니다."));
    }
}
