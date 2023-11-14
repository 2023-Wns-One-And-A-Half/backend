package com.oneandahalf.backend.comment.query;

import com.oneandahalf.backend.comment.query.dao.CommentByProductResponseDao;
import com.oneandahalf.backend.comment.query.response.CommentByProductResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentQueryService {

    private final CommentByProductResponseDao commentByProductResponseDao;

    public List<CommentByProductResponse> findByProductId(Long productId) {
        return commentByProductResponseDao.find(productId);
    }
}
