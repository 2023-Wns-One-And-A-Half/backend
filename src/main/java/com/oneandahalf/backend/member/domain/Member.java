package com.oneandahalf.backend.member.domain;

import static lombok.AccessLevel.PROTECTED;

import com.oneandahalf.backend.common.domain.CommonDomainModel;
import com.oneandahalf.backend.member.exception.MissMatchPasswordException;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Member extends CommonDomainModel {

    @Embedded
    private Username username;

    @Embedded
    private Password password;

    @Column(nullable = false)
    @Enumerated
    private ActivityArea activityArea;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = true)
    private String profileImageName;

    private boolean black;

    @Builder
    public Member(
            String username,
            String password,
            ActivityArea activityArea,
            String nickname,
            String profileImageName
    ) {
        this.username = new Username(username);
        this.password = new Password(password);
        this.activityArea = activityArea;
        this.nickname = nickname;
        this.profileImageName = profileImageName;
    }

    public void signup(MemberValidator validator) {
        validator.validateDuplicateUsername(username);
    }

    public void login(String password, MemberValidator validator) {
        if (getPassword().match(password)) {
            validator.validateBlackListLogin(getId());
            return;
        }
        throw new MissMatchPasswordException();
    }

    public void black() {
        this.black = true;
    }

    public void unBlack() {
        this.black = false;
    }
}
