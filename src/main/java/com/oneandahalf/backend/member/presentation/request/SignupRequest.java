package com.oneandahalf.backend.member.presentation.request;

import com.oneandahalf.backend.member.application.command.SignupCommand;
import com.oneandahalf.backend.member.domain.ActivityArea;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record SignupRequest(
        @NotEmpty(message = "아이디는 비어있으면 안됩니다.") String username,
        @NotEmpty(message = "비밀번호는 비어있으면 안됩니다.") String password,
        @NotNull(message = "활동 지역을 입력해주세요.") ActivityArea activityArea,
        @NotEmpty(message = "닉네임은 비어있으면 안됩니다.") String nickname,
        MultipartFile profileImage
) {

    public SignupCommand toCommand(String profileImageName) {
        return SignupCommand.builder()
                .username(username)
                .password(password)
                .activityArea(activityArea)
                .nickname(nickname)
                .profileImageName(profileImageName)
                .build();
    }
}
