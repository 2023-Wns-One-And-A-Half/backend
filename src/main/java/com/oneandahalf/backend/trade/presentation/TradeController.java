package com.oneandahalf.backend.trade.presentation;

import com.oneandahalf.backend.member.presentation.support.Auth;
import com.oneandahalf.backend.trade.application.TradeService;
import com.oneandahalf.backend.trade.presentation.request.ConfirmTradeRequest;
import com.oneandahalf.backend.trade.query.TradeQueryService;
import com.oneandahalf.backend.trade.query.response.MyTradeProductResponse;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/trades")
@RestController
public class TradeController {

    private final TradeService tradeService;
    private final TradeQueryService tradeQueryService;

    @PostMapping
    public ResponseEntity<Void> confirm(
            @Auth Long memberId,
            @RequestBody ConfirmTradeRequest request
    ) {
        Long tradeId = tradeService.confirm(request.toCommand(memberId));
        return ResponseEntity.created(URI.create("/trades/" + tradeId)).build();
    }

    @GetMapping("/purchased")
    public ResponseEntity<List<MyTradeProductResponse>> findPurchased(
            @Auth Long memberId
    ) {
        return ResponseEntity.ok(tradeQueryService.findPurchased(memberId));
    }

    @GetMapping("/sold")
    public ResponseEntity<List<MyTradeProductResponse>> findSold(
            @Auth Long memberId
    ) {
        return ResponseEntity.ok(tradeQueryService.findSold(memberId));
    }
}
