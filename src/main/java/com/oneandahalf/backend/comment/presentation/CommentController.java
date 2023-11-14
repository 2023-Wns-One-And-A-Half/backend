package com.oneandahalf.backend.comment.presentation;

import com.oneandahalf.backend.comment.application.CommentService;
import com.oneandahalf.backend.comment.presentation.request.WriteCommentRequest;
import com.oneandahalf.backend.member.presentation.support.Auth;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/comments")
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> write(
            @Auth Long memberId,
            @RequestBody WriteCommentRequest request
    ) {
        Long commentId = commentService.write(request.toCommand(memberId));
        return ResponseEntity.created(URI.create("/comments/" + commentId)).build();
    }
}
