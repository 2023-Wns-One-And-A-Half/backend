package com.oneandahalf.backend.member.application.command;

public record LoginCommand(
        String username,
        String password
) {
}
