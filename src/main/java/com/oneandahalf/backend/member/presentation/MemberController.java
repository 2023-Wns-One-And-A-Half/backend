package com.oneandahalf.backend.member.presentation;

import static com.oneandahalf.backend.member.presentation.support.AuthConstant.SESSION_ATTRIBUTE_MEMBER_ID;

import com.oneandahalf.backend.common.image.ImageUploadClient;
import com.oneandahalf.backend.member.application.MemberService;
import com.oneandahalf.backend.member.presentation.request.LoginRequest;
import com.oneandahalf.backend.member.presentation.request.SignupRequest;
import com.oneandahalf.backend.member.presentation.response.LoginResponse;
import com.oneandahalf.backend.member.presentation.support.Auth;
import com.oneandahalf.backend.member.query.MemberQueryService;
import com.oneandahalf.backend.member.query.response.MemberProfileResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
public class MemberController {

    private final ImageUploadClient imageUploadClient;
    private final MemberService memberService;
    private final MemberQueryService memberQueryService;

    @PostMapping
    public ResponseEntity<Void> signup(
            @Valid @ModelAttribute SignupRequest request
    ) {
        String profileImageName = imageUploadClient.upload(request.profileImage());
        Long memberId = memberService.signup(request.toCommand(profileImageName));
        return ResponseEntity
                .created(URI.create("/members/" + memberId))
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest loginRequest,
            HttpServletRequest request
    ) {
        Long memberId = memberService.login(loginRequest.toCommand());
        HttpSession session = request.getSession(true);
        session.setAttribute(SESSION_ATTRIBUTE_MEMBER_ID, memberId);
        return ResponseEntity.ok(new LoginResponse(session.getId()));
    }

    @GetMapping("/my")
    public ResponseEntity<MemberProfileResponse> findProfile(
            @Auth Long memberId
    ) {
        return ResponseEntity.ok(memberQueryService.findProfile(memberId));
    }
}
