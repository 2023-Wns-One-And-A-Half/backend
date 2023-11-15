package com.oneandahalf.backend.member.query.dao;

import com.oneandahalf.backend.member.query.dao.support.BlacklistQuerySupport;
import com.oneandahalf.backend.member.query.dao.support.MemberQuerySupport;
import com.oneandahalf.backend.member.query.response.NotBlacklistMemberResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NotBlacklistMemberResponseDao {

    private final BlacklistQuerySupport blacklistQuerySupport;
    private final MemberQuerySupport memberQuerySupport;

    public List<NotBlacklistMemberResponse> find() {
        List<Long> blacklistMemberIds = blacklistQuerySupport.findAll().stream()
                .map(it -> it.getMember().getId())
                .toList();
        return memberQuerySupport.findAllByIdNotIn(blacklistMemberIds)
                .stream()
                .map(NotBlacklistMemberResponse::from)
                .toList();
    }
}
