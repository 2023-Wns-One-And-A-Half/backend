package com.oneandahalf.backend.admin.auth.presentation;

import static com.oneandahalf.backend.admin.auth.presentation.support.AdminAuthConstant.SESSION_ATTRIBUTE_ADMIN_ID;

import com.oneandahalf.backend.admin.auth.application.AdminAuthService;
import com.oneandahalf.backend.admin.auth.presentation.request.AdminLoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

    private final AdminAuthService adminAuthService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @RequestBody AdminLoginRequest loginRequest,
            HttpServletRequest request
    ) {
        Long adminId = adminAuthService.login(loginRequest.username(), loginRequest.password());
        HttpSession session = request.getSession(true);
        session.setAttribute(SESSION_ATTRIBUTE_ADMIN_ID, adminId);
        return ResponseEntity.ok().build();
    }
}
