package com.oneandahalf.backend.trade.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.oneandahalf.backend.common.exception.ApplicationException;
import com.oneandahalf.backend.common.exception.ErrorCode;

public class AlreadyTradedProductException extends ApplicationException {

    public AlreadyTradedProductException() {
        super(new ErrorCode(BAD_REQUEST, "이미 거래완료된 상품입니다."));
    }
}
