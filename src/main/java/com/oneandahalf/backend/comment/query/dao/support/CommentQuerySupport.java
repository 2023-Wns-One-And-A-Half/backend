package com.oneandahalf.backend.comment.query.dao.support;

import com.oneandahalf.backend.comment.domain.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentQuerySupport extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = {"writer"})
    List<Comment> findAllWithWriterByProductId(Long postId);
}
