package com.oneandahalf.backend.admin.auth.application;

import com.oneandahalf.backend.admin.auth.domain.Admin;
import com.oneandahalf.backend.admin.auth.domain.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AdminAuthService {

    private final AdminRepository adminRepository;

    @Transactional(readOnly = true)
    public Long login(String username, String password) {
        Admin admin = adminRepository.getByUsername(username);
        admin.login(password);
        return admin.getId();
    }
}
