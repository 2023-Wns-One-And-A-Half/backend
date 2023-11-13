package com.oneandahalf.backend.member.domain;

import lombok.Getter;

@Getter
public enum ActivityArea {

    SEOUL("서울"),
    INCHEON("인천"),
    DAEJEON("대전"),
    GWANGJU("광주"),
    BUSAN("부산"),
    DAEGU("대구"),
    ULSAN("울산"),
    ;

    private final String korName;

    ActivityArea(String korName) {
        this.korName = korName;
    }
}
