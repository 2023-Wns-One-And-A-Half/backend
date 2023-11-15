package com.oneandahalf.backend.admin.auth.presentation.support;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@RequestScope
@Component
public class AdminAuthContext {

    private Long adminId;

    public boolean unAuthenticated() {
        return adminId == null;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }
}
