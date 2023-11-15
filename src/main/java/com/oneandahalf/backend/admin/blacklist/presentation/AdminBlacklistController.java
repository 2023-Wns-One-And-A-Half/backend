package com.oneandahalf.backend.admin.blacklist.presentation;

import static org.springframework.http.HttpStatus.CREATED;

import com.oneandahalf.backend.admin.auth.presentation.support.AdminAuth;
import com.oneandahalf.backend.admin.blacklist.presentation.request.AddBlacklistRequest;
import com.oneandahalf.backend.admin.blacklist.presentation.request.DeleteBlacklistRequest;
import com.oneandahalf.backend.member.application.BlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/admin/blacklists")
@RestController
public class AdminBlacklistController {

    private final BlacklistService blacklistService;

    @PostMapping
    public ResponseEntity<Void> black(
            @AdminAuth Long adminId,
            @RequestBody AddBlacklistRequest request
    ) {
        blacklistService.black(request.memberId());
        return ResponseEntity.status(CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> unBlack(
            @AdminAuth Long adminId,
            @RequestBody DeleteBlacklistRequest request
    ) {
        blacklistService.unBlack(request.memberId());
        return ResponseEntity.noContent().build();
    }
}
