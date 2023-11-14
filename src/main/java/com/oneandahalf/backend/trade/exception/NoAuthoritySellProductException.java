package com.oneandahalf.backend.trade.exception;

import com.oneandahalf.backend.common.exception.ApplicationException;
import com.oneandahalf.backend.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class NoAuthoritySellProductException extends ApplicationException {

    public NoAuthoritySellProductException() {
        super(new ErrorCode(HttpStatus.FORBIDDEN, "상품을 판매할 권한이 없습니다."));
    }
}
