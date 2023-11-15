package com.oneandahalf.backend.chat.domain;

import com.oneandahalf.backend.common.exception.NotFoundEntityException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatRoomRepository extends JpaRepository<ProductChatRoom, Long> {

    default ProductChatRoom getById(Long id) {
        return findById(id).orElseThrow(() ->
                new NotFoundEntityException("존재하지 않는 채팅방입니다."));
    }

    default boolean existsByProductIdAndClientId(Long productId, Long clientId) {
        return findByProductIdAndClientId(productId, clientId).isPresent();
    }

    @Query("SELECT r  FROM ProductChatRoom  r WHERE r.productId = :productId AND r.client.id = :clientId")
    Optional<ProductChatRoom> findByProductIdAndClientId(
            @Param("productId") Long productId,
            @Param("clientId") Long clientId
    );
}
