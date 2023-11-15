package com.oneandahalf.backend.trade.application;

import com.oneandahalf.backend.member.domain.blacklist.AddBlacklistEvent;
import com.oneandahalf.backend.trade.domain.TradeSuggestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TradeSuggestionEventHandler {

    private final TradeSuggestionRepository tradeSuggestionRepository;

    @EventListener(AddBlacklistEvent.class)
    public void deleteAllBlacklistTradeSuggestion(AddBlacklistEvent event) {
        Long memberId = event.memberId();
        tradeSuggestionRepository.deleteAllBySuggesterId(memberId);
    }
}
