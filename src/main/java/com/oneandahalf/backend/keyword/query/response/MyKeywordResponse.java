package com.oneandahalf.backend.keyword.query.response;

import com.oneandahalf.backend.keyword.domain.Keyword;

public record MyKeywordResponse(
        Long id,
        String content
) {

    public static MyKeywordResponse from(Keyword keyword) {
        return new MyKeywordResponse(
                keyword.getId(),
                keyword.getContent()
        );
    }
}
