package com.oneandahalf.backend.keyword.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.oneandahalf.backend.common.DataClearExtension;
import com.oneandahalf.backend.keyword.domain.Keyword;
import com.oneandahalf.backend.keyword.domain.KeywordRepository;
import com.oneandahalf.backend.member.MemberFixture;
import com.oneandahalf.backend.member.domain.Member;
import com.oneandahalf.backend.member.domain.MemberRepository;
import com.oneandahalf.backend.notification.domain.Notification;
import com.oneandahalf.backend.notification.domain.NotificationRepository;
import com.oneandahalf.backend.product.domain.Product;
import com.oneandahalf.backend.product.domain.RegisterProductEvent;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

@DisplayName("키워드 이벤트 핸들러 (KeywordEventHandler) 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@ExtendWith(DataClearExtension.class)
@SpringBootTest
class KeywordEventHandlerTest {

    @Autowired
    private KeywordRepository keywordRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private KeywordEventHandler keywordEventHandler;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 상품_등록_이벤트를_받아_키워드가_해당_상품의_이름에_포함된_경우_알림을_생성한다() {
        // given
        Member me = memberRepository.save(MemberFixture.mallang());
        Member other = memberRepository.save(MemberFixture.member("other"));
        keywordRepository.save(new Keyword("청주", me));
        keywordRepository.save(new Keyword("사과", me));
        keywordRepository.save(new Keyword("주사", me));
        keywordRepository.save(new Keyword("주 사", me));
        keywordRepository.save(new Keyword("팔아요", me));
        keywordRepository.save(new Keyword("맛있는", me));

        keywordRepository.save(new Keyword("맛없는", me));
        keywordRepository.save(new Keyword("건담", me));
        Product product = Product.builder()
                .price(1000)
                .name("맛있는 청주 사과를 팔아요.")
                .productImageNames(List.of())
                .seller(other)
                .build();
        ReflectionTestUtils.setField(product, "id", 100L);

        // when
        keywordEventHandler.notifyRegisteredProductContainsKeyword(new RegisterProductEvent(product));

        // then
        List<Notification> result = notificationRepository.findAll();
        assertThat(result)
                .extracting(Notification::getContent)
                .containsExactlyInAnyOrder(
                        "\"청주\" 키워드 관련 게시물이 올라왔어요.",
                        "\"사과\" 키워드 관련 게시물이 올라왔어요.",
                        "\"주 사\" 키워드 관련 게시물이 올라왔어요.",
                        "\"팔아요\" 키워드 관련 게시물이 올라왔어요.",
                        "\"맛있는\" 키워드 관련 게시물이 올라왔어요."
                );
        assertThat(result)
                .extracting(Notification::getLinkedURI)
                .containsOnly("/products/100");
    }

    @Test
    void 내가_등록한_상품은_내_키워드랑_일치하더라도_알림_생성_X() {
        // given
        Member me = memberRepository.save(MemberFixture.mallang());
        Member other = memberRepository.save(MemberFixture.member("other"));
        keywordRepository.save(new Keyword("사과", me));
        keywordRepository.save(new Keyword("청주", other));
        Product product = Product.builder()
                .price(1000)
                .name("맛있는 청주 사과를 팔아요.")
                .productImageNames(List.of())
                .seller(me)
                .build();
        ReflectionTestUtils.setField(product, "id", 100L);

        // when
        keywordEventHandler.notifyRegisteredProductContainsKeyword(new RegisterProductEvent(product));

        // then
        List<Notification> result = notificationRepository.findAll();
        assertThat(result)
                .extracting(Notification::getContent)
                .containsExactlyInAnyOrder(
                        "\"청주\" 키워드 관련 게시물이 올라왔어요."
                );
    }
}
