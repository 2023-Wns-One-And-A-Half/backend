package com.oneandahalf.backend.member.presentation.request;

import com.oneandahalf.backend.member.application.command.LoginCommand;

public record LoginRequest(
        String username,
        String password
) {

    public LoginCommand toCommand() {
        return new LoginCommand(username, password);
    }
}
