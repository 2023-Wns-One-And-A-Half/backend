package com.oneandahalf.backend.member.query;

import com.oneandahalf.backend.member.query.dao.MemberProfileResponseDao;
import com.oneandahalf.backend.member.query.dao.NotBlacklistMemberResponseDao;
import com.oneandahalf.backend.member.query.response.MemberProfileResponse;
import com.oneandahalf.backend.member.query.response.NotBlacklistMemberResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberQueryService {

    private final MemberProfileResponseDao memberProfileResponseDao;
    private final NotBlacklistMemberResponseDao notBlacklistMemberResponseDao;

    public MemberProfileResponse findProfile(Long id) {
        return memberProfileResponseDao.find(id);
    }

    public List<NotBlacklistMemberResponse> findAllNotBlacklist() {
        return notBlacklistMemberResponseDao.find();
    }
}

