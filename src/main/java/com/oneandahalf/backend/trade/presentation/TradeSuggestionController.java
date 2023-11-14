package com.oneandahalf.backend.trade.presentation;


import com.oneandahalf.backend.member.presentation.support.Auth;
import com.oneandahalf.backend.trade.application.TradeSuggestionService;
import com.oneandahalf.backend.trade.presentation.request.SuggestTradeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/trade-suggests")
@RestController
public class TradeSuggestionController {

    private final TradeSuggestionService tradeSuggestionService;

    @PostMapping
    public ResponseEntity<Long> suggest(
            @Auth Long memberId,
            @RequestBody SuggestTradeRequest request
    ) {
        Long suggestId = tradeSuggestionService.suggest(request.toCommand(memberId));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(suggestId);
    }
}
