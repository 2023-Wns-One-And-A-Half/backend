package com.oneandahalf.backend.member.query.dao;

import com.oneandahalf.backend.member.query.dao.support.MemberQuerySupport;
import com.oneandahalf.backend.member.query.response.MemberProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MemberProfileResponseDao {

    private final MemberQuerySupport memberQuerySupport;

    public MemberProfileResponse find(Long id) {
        return MemberProfileResponse.from(memberQuerySupport.getById(id));
    }
}
