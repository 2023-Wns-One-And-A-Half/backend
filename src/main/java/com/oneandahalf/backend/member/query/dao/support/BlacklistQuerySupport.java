package com.oneandahalf.backend.member.query.dao.support;

import com.oneandahalf.backend.member.domain.blacklist.Blacklist;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BlacklistQuerySupport extends JpaRepository<Blacklist, Long> {

    @Query("SELECT b FROM Blacklist b JOIN FETCH b.member")
    List<Blacklist> findAllWithMember();
}
