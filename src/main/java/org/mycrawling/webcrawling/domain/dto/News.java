package org.mycrawling.webcrawling.domain.dto;

import lombok.Data;

@Data
public class News {

    private String id; // 식별값
    private String title; // 기사 제목
    private String href; // 기사 링크
}
