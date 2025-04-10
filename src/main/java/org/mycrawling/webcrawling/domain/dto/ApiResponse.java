package org.mycrawling.webcrawling.domain.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ApiResponse {

    private Map<String, List<News>> news; // 뉴스
    private Map<String, Integer> wordFrequency; // 뉴스 단어 빈도수
}
