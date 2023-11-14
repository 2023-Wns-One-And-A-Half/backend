package com.oneandahalf.backend.comment.presentation;

import com.oneandahalf.backend.comment.application.CommentService;
import com.oneandahalf.backend.comment.presentation.request.WriteCommentRequest;
import com.oneandahalf.backend.comment.query.CommentQueryService;
import com.oneandahalf.backend.comment.query.response.CommentByProductResponse;
import com.oneandahalf.backend.member.presentation.support.Auth;
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
@RequestMapping("/comments")
@RestController
public class CommentController {

    private final CommentService commentService;
    private final CommentQueryService commentQueryService;

    @PostMapping
    public ResponseEntity<Void> write(
            @Auth Long memberId,
            @RequestBody WriteCommentRequest request
    ) {
        Long commentId = commentService.write(request.toCommand(memberId));
        return ResponseEntity.created(URI.create("/comments/" + commentId)).build();
    }

    @GetMapping
    public ResponseEntity<List<CommentByProductResponse>> findByProductId(
            @RequestParam("productId") Long productId
    ) {
        return ResponseEntity.ok(commentQueryService.findByProductId(productId));
    }
}
