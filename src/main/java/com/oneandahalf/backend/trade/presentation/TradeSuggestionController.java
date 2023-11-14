package com.oneandahalf.backend.trade.presentation;


import com.oneandahalf.backend.member.presentation.support.Auth;
import com.oneandahalf.backend.trade.application.TradeSuggestionService;
import com.oneandahalf.backend.trade.presentation.request.SuggestTradeRequest;
import com.oneandahalf.backend.trade.query.TradeSuggestionQueryService;
import com.oneandahalf.backend.trade.query.response.TradeSuggestionExistResponse;
import com.oneandahalf.backend.trade.query.response.TradeSuggestionResponse;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/trade-suggests")
@RestController
public class TradeSuggestionController {

    private final TradeSuggestionService tradeSuggestionService;
    private final TradeSuggestionQueryService tradeSuggestionQueryService;

    @PostMapping
    public ResponseEntity<Void> suggest(
            @Auth Long memberId,
            @RequestBody SuggestTradeRequest request
    ) {
        Long suggestId = tradeSuggestionService.suggest(request.toCommand(memberId));
        return ResponseEntity.created(URI.create("/trade-suggests/" + suggestId)).build();
    }

    @GetMapping("/exist")
    public ResponseEntity<TradeSuggestionExistResponse> exist(
            @Auth Long memberId,
            @RequestParam("productId") Long productId
    ) {
        return ResponseEntity.ok(tradeSuggestionQueryService.exist(memberId, productId));
    }

    @GetMapping
    public ResponseEntity<TradeSuggestionStatusResponse> findStatus(
            @Auth Long memberId,
            @RequestParam("productId") Long productId
    ) {
        return ResponseEntity.ok(tradeSuggestionQueryService.findStatus(memberId, productId));
    }
}
