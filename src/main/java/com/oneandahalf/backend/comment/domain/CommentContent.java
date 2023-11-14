package com.oneandahalf.backend.comment.domain;

import static lombok.AccessLevel.PROTECTED;

import com.oneandahalf.backend.comment.exception.ExceedCommentLengthException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Embeddable
public class CommentContent {

    @Column(name = "content", nullable = false)
    private String value;

    public CommentContent(String value) {
        validateLength(value);
        this.value = value;
    }

    private void validateLength(String value) {
        if (value == null || value.length() > 250) {
            throw new ExceedCommentLengthException();
        }
    }
}
