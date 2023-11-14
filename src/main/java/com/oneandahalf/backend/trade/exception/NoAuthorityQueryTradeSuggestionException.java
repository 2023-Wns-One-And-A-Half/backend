package com.oneandahalf.backend.trade.exception;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import com.oneandahalf.backend.common.exception.ApplicationException;
import com.oneandahalf.backend.common.exception.ErrorCode;

public class NoAuthorityQueryTradeSuggestionException extends ApplicationException {

    public NoAuthorityQueryTradeSuggestionException() {
        super(new ErrorCode(FORBIDDEN, "상품 거래 요청을 볼 권한이 없습니다."));
    }
}
