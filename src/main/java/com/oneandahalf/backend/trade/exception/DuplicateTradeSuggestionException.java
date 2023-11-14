package com.oneandahalf.backend.trade.exception;

import static org.springframework.http.HttpStatus.CONFLICT;

import com.oneandahalf.backend.common.exception.ApplicationException;
import com.oneandahalf.backend.common.exception.ErrorCode;

public class DuplicateTradeSuggestionException extends ApplicationException {

    public DuplicateTradeSuggestionException() {
        super(new ErrorCode(CONFLICT, "이미 구매요청한 상품입니다."));
    }
}
