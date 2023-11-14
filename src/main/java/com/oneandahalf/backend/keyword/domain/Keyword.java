package com.oneandahalf.backend.keyword.domain;

import com.oneandahalf.backend.common.domain.CommonDomainModel;
import com.oneandahalf.backend.member.domain.Member;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Keyword extends CommonDomainModel {

    private String content;
    private Member member;

    public Keyword(String content, Member member) {
        this.content = content;
        this.member = member;
    }

    public void create(KeywordValidator validator) {
        validator.validateDuplicateKeyword(member, content);
    }
}
