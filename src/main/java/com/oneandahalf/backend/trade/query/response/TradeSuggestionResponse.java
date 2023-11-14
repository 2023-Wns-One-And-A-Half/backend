package com.oneandahalf.backend.trade.query.response;

import com.oneandahalf.backend.member.domain.ActivityArea;
import com.oneandahalf.backend.member.domain.Member;
import com.oneandahalf.backend.trade.domain.TradeSuggestion;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record TradeSuggestionResponse(
        Long id,
        TradeSuggesterInfo suggesterInfo,
        LocalDateTime suggestDate
) {

    public static TradeSuggestionResponse from(TradeSuggestion suggestion) {
        return TradeSuggestionResponse.builder()
                .id(suggestion.getId())
                .suggesterInfo(TradeSuggesterInfo.from(suggestion.getSuggester()))
                .suggestDate(suggestion.getCreatedDate())
                .build();
    }

    @Builder
    public record TradeSuggesterInfo(
            Long suggesterId,
            String nickName,
            String profileImageName,
            ActivityArea activityArea
    ) {
        public static TradeSuggesterInfo from(Member suggester) {
            return TradeSuggesterInfo.builder()
                    .suggesterId(suggester.getId())
                    .nickName(suggester.getNickname())
                    .profileImageName(suggester.getProfileImageName())
                    .activityArea(suggester.getActivityArea())
                    .build();
        }
    }
}
