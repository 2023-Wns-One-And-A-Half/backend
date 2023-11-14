package com.oneandahalf.backend.trade.application;

import com.oneandahalf.backend.member.domain.Member;
import com.oneandahalf.backend.member.domain.MemberRepository;
import com.oneandahalf.backend.product.domain.Product;
import com.oneandahalf.backend.product.domain.ProductRepository;
import com.oneandahalf.backend.trade.application.command.SuggestTradeCommand;
import com.oneandahalf.backend.trade.domain.TradeSuggestion;
import com.oneandahalf.backend.trade.domain.TradeSuggestionRepository;
import com.oneandahalf.backend.trade.domain.TradeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class TradeSuggestionService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final TradeSuggestionRepository tradeSuggestionRepository;
    private final TradeValidator tradeValidator;

    public Long suggest(SuggestTradeCommand command) {
        Member suggester = memberRepository.getById(command.suggesterId());
        Product product = productRepository.getById(command.productId());
        TradeSuggestion tradeSuggestion = new TradeSuggestion(suggester, product);
        tradeSuggestion.suggest(tradeValidator);
        return tradeSuggestionRepository.save(tradeSuggestion)
                .getId();
    }
}
