package com.oneandahalf.backend.trade.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.oneandahalf.backend.common.exception.ApplicationException;
import com.oneandahalf.backend.common.exception.ErrorCode;

public class SelfPurchaseForbiddenException extends ApplicationException {

    public SelfPurchaseForbiddenException() {
        super(new ErrorCode(BAD_REQUEST, "자신이 판매하는 상품에 구매요청을 할 수 없습니다."));
    }
}
