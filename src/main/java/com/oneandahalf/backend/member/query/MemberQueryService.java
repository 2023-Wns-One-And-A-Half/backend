package com.oneandahalf.backend.member.query;

import com.oneandahalf.backend.member.query.dao.MemberProfileResponseDao;
import com.oneandahalf.backend.member.query.response.MemberProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberQueryService {

    private final MemberProfileResponseDao memberProfileResponseDao;

    public MemberProfileResponse findProfile(Long id) {
        return memberProfileResponseDao.find(id);
    }
}

