package com.oneandahalf.backend.member.domain.blacklist;

import com.oneandahalf.backend.common.domain.CommonDomainModel;
import com.oneandahalf.backend.member.domain.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Blacklist extends CommonDomainModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Blacklist(Member member) {
        this.member = member;
        registerEvent(new AddBlacklistEvent(member.getId()));
    }
}
