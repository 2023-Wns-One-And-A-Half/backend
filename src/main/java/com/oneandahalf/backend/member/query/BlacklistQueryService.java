package com.oneandahalf.backend.member.query;

import com.oneandahalf.backend.member.query.dao.BlacklistResponseDao;
import com.oneandahalf.backend.member.query.response.BlacklistResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BlacklistQueryService {

    private final BlacklistResponseDao blacklistResponseDao;

    public List<BlacklistResponse> findAll() {
        return blacklistResponseDao.findAll();
    }
}
