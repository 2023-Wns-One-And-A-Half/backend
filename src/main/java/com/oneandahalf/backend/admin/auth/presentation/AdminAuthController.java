package com.oneandahalf.backend.admin.auth.presentation;

import static com.oneandahalf.backend.common.session.Session.SessionType.ADMIN;

import com.oneandahalf.backend.admin.auth.application.AdminAuthService;
import com.oneandahalf.backend.admin.auth.presentation.request.AdminLoginRequest;
import com.oneandahalf.backend.common.session.Session.SessionType;
import com.oneandahalf.backend.common.session.SessionService;
import com.oneandahalf.backend.member.presentation.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/admin/auth")
@RestController
public class AdminAuthController {

    private final SessionService sessionService;
    private final AdminAuthService adminAuthService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody AdminLoginRequest loginRequest
    ) {
        Long adminId = adminAuthService.login(loginRequest.username(), loginRequest.password());
        String sessionId = sessionService.save(adminId, ADMIN);
        return ResponseEntity.ok(new LoginResponse(sessionId));
    }
}
