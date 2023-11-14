package com.oneandahalf.backend.comment.application;

import com.oneandahalf.backend.comment.application.command.WriteCommentCommand;
import com.oneandahalf.backend.comment.domain.Comment;
import com.oneandahalf.backend.comment.domain.CommentRepository;
import com.oneandahalf.backend.member.domain.Member;
import com.oneandahalf.backend.member.domain.MemberRepository;
import com.oneandahalf.backend.product.domain.Product;
import com.oneandahalf.backend.product.domain.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final CommentRepository commentRepository;

    public Long write(WriteCommentCommand command) {
        Member member = memberRepository.getById(command.memberId());
        Product product = productRepository.getById(command.productId());
        Comment comment = command.toDomain(member, product);
        return commentRepository.save(comment)
                .getId();
    }
}
