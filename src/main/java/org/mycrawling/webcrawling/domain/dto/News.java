package org.mycrawling.webcrawling.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class News {

    private String id; // 식별값
    private String title; // 기사 제목
    private String href; // 기사 링크

    @Builder
    public News(String id, String title, String href) {
        this.id = id;
        this.title = title;
        this.href = href;
    }
}
