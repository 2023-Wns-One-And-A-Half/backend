package com.oneandahalf.backend.member.query.dao;

import com.oneandahalf.backend.member.query.dao.support.BlacklistQuerySupport;
import com.oneandahalf.backend.member.query.response.BlacklistResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BlacklistResponseDao {

    private final BlacklistQuerySupport blacklistQuerySupport;

    public List<BlacklistResponse> findAll() {
        return blacklistQuerySupport.findAllWithMember()
                .stream()
                .map(BlacklistResponse::from)
                .toList();
    }
}
