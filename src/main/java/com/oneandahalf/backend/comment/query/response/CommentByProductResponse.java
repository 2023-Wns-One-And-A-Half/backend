package com.oneandahalf.backend.comment.query.response;

import com.oneandahalf.backend.comment.domain.Comment;
import com.oneandahalf.backend.member.domain.ActivityArea;
import com.oneandahalf.backend.member.domain.Member;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record CommentByProductResponse(
        Long id,
        String content,
        LocalDateTime createdDate,
        CommentWriterInfo writerInfo
) {

    public static CommentByProductResponse from(Comment comment) {
        return CommentByProductResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdDate(comment.getCreatedDate())
                .writerInfo(CommentWriterInfo.from(comment.getWriter()))
                .build();
    }

    @Builder
    public record CommentWriterInfo(
            Long id,
            String nickname,
            String profileImageName,
            ActivityArea activityArea
    ) {

        public static CommentWriterInfo from(Member writer) {
            return CommentWriterInfo.builder()
                    .id(writer.getId())
                    .nickname(writer.getNickname())
                    .profileImageName(writer.getProfileImageName())
                    .activityArea(writer.getActivityArea())
                    .build();
        }
    }
}
