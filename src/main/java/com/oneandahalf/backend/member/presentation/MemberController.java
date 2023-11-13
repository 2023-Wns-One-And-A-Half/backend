package com.oneandahalf.backend.member.presentation;

import com.oneandahalf.backend.member.application.MemberService;
import com.oneandahalf.backend.member.presentation.request.SignupRequest;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Void> signup(
            @Valid @RequestBody SignupRequest request
    ) {
        Long memberId = memberService.signup(request.toCommand());
        return ResponseEntity
                .created(URI.create("/members/" + memberId))
                .build();
    }
}
