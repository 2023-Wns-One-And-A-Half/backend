package com.oneandahalf.backend.trade.application;

import com.oneandahalf.backend.member.domain.Member;
import com.oneandahalf.backend.member.domain.MemberRepository;
import com.oneandahalf.backend.trade.application.command.ConfirmTradeCommand;
import com.oneandahalf.backend.trade.domain.Trade;
import com.oneandahalf.backend.trade.domain.TradeRepository;
import com.oneandahalf.backend.trade.domain.TradeSuggestion;
import com.oneandahalf.backend.trade.domain.TradeSuggestionRepository;
import com.oneandahalf.backend.trade.domain.TradeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class TradeService {

    private final MemberRepository memberRepository;
    private final TradeSuggestionRepository tradeSuggestionRepository;
    private final TradeRepository tradeRepository;
    private final TradeValidator tradeValidator;

    public Long confirm(ConfirmTradeCommand command) {
        TradeSuggestion suggestion = tradeSuggestionRepository.getById(command.tradeSuggestionId());
        Member seller = memberRepository.getById(command.sellerId());
        Trade trade = suggestion.confirm(seller, tradeValidator);
        return tradeRepository.save(trade)
                .getId();
    }
}
