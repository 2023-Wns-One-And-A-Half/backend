package com.oneandahalf.backend.keyword.presentation;

import com.oneandahalf.backend.keyword.application.KeywordService;
import com.oneandahalf.backend.keyword.application.command.DeleteKeywordCommand;
import com.oneandahalf.backend.keyword.presentation.request.CreateKeywordRequest;
import com.oneandahalf.backend.keyword.query.KeywordQueryService;
import com.oneandahalf.backend.keyword.query.response.MyKeywordResponse;
import com.oneandahalf.backend.member.presentation.support.Auth;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/keywords")
@RestController
public class KeywordController {

    private final KeywordService keywordService;
    private final KeywordQueryService keywordQueryService;

    @PostMapping
    public ResponseEntity<Void> create(
            @Auth Long memberId,
            @RequestBody CreateKeywordRequest request
    ) {
        Long keywordId = keywordService.create(request.toCommand(memberId));
        return ResponseEntity.created(URI.create("/keywords/" + keywordId)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Auth Long memberId,
            @PathVariable("id") Long keywordId
    ) {
        keywordService.delete(DeleteKeywordCommand.builder()
                .keywordId(keywordId)
                .memberId(memberId)
                .build());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my")
    public ResponseEntity<List<MyKeywordResponse>> findMine(
            @Auth Long memberId
    ) {
        return ResponseEntity.ok(keywordQueryService.findMine(memberId));
    }
}
