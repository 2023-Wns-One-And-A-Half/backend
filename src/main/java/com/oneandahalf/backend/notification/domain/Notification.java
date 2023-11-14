package com.oneandahalf.backend.notification.domain;

import com.oneandahalf.backend.common.domain.CommonDomainModel;
import com.oneandahalf.backend.member.domain.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Notification extends CommonDomainModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;

    private String linkedURI;

    @Builder
    public Notification(Member member, String content, String linkedURI) {
        this.member = member;
        this.content = content;
        this.linkedURI = linkedURI;
    }
}
