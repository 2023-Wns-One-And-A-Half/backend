package com.oneandahalf.backend.common.session;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Session {

    @Id
    private String id;

    private Long memberId;

    private SessionType type;

    public enum SessionType {
        ADMIN,
        MEMBER
    }

    @CreatedDate
    private LocalDateTime createdDate;

    public Session(Long memberId, SessionType type) {
        this.id = UUID.randomUUID().toString();
        this.memberId = memberId;
        this.type = type;
    }
}
