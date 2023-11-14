package com.oneandahalf.backend.comment.query.dao;

import com.oneandahalf.backend.comment.query.dao.support.CommentQuerySupport;
import com.oneandahalf.backend.comment.query.response.CommentByProductResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CommentByProductResponseDao {

    private final CommentQuerySupport commentQuerySupport;

    public List<CommentByProductResponse> find(Long productId) {
        return commentQuerySupport.findAllWithWriterByProductId(productId)
                .stream()
                .map(CommentByProductResponse::from)
                .toList();
    }
}
