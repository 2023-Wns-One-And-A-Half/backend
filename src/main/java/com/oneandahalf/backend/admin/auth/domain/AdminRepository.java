package com.oneandahalf.backend.admin.auth.domain;

import com.oneandahalf.backend.common.exception.NotFoundEntityException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    default Admin getById(Long id) {
        return findById(id).orElseThrow(() ->
                new NotFoundEntityException("Id가 %d인 어드민 정보가 없습니다.".formatted(id)));
    }

    default Admin getByUsername(String username) {
        return findByUsername(username).orElseThrow(() ->
                new NotFoundEntityException("해당 계정정보를 가진 어드민 계정이 존재하지 않습니다."));
    }

    Optional<Admin> findByUsername(String username);
}
