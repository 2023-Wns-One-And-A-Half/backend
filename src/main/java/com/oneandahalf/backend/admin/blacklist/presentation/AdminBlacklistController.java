package com.oneandahalf.backend.admin.blacklist.presentation;

import static org.springframework.http.HttpStatus.CREATED;

import com.oneandahalf.backend.admin.blacklist.presentation.request.AddBlacklistRequest;
import com.oneandahalf.backend.admin.blacklist.presentation.request.DeleteBlacklistRequest;
import com.oneandahalf.backend.member.application.BlacklistService;
import com.oneandahalf.backend.member.query.BlacklistQueryService;
import com.oneandahalf.backend.member.query.response.BlacklistResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/admin/blacklists")
@RestController
public class AdminBlacklistController {

    private final BlacklistService blacklistService;
    private final BlacklistQueryService blacklistQueryService;

    @PostMapping
    public ResponseEntity<Void> black(@RequestBody AddBlacklistRequest request) {
        blacklistService.black(request.memberId());
        return ResponseEntity.status(CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> unBlack(@RequestBody DeleteBlacklistRequest request) {
        blacklistService.unBlack(request.memberId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<BlacklistResponse>> findAll() {
        return ResponseEntity.ok(blacklistQueryService.findAll());
    }
}
