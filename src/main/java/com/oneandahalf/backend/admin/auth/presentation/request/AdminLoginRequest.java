package com.oneandahalf.backend.admin.auth.presentation.request;

public record AdminLoginRequest(
        String username,
        String password
) {
}
