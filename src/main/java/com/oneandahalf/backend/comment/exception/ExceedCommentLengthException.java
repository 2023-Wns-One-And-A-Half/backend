package com.oneandahalf.backend.comment.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.oneandahalf.backend.common.exception.ApplicationException;
import com.oneandahalf.backend.common.exception.ErrorCode;

public class ExceedCommentLengthException extends ApplicationException {

    public ExceedCommentLengthException() {
        super(new ErrorCode(BAD_REQUEST, "댓글은 250자 이하로 작성해주세요."));
    }
}
