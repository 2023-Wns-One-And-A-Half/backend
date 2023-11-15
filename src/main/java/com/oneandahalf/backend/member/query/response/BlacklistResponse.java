package com.oneandahalf.backend.member.query.response;


import com.oneandahalf.backend.member.domain.blacklist.Blacklist;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record BlacklistResponse(
        Long id,
        Long memberId,
        LocalDateTime createdData
) {

    public static BlacklistResponse from(Blacklist blacklist) {
        return BlacklistResponse.builder()
                .id(blacklist.getId())
                .memberId(blacklist.getMember().getId())
                .createdData(blacklist.getCreatedDate())
                .build();
    }
}
