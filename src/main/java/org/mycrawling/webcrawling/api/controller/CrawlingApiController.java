package org.mycrawling.webcrawling.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mycrawling.webcrawling.domain.dto.News;
import org.mycrawling.webcrawling.service.CrawlingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CrawlingApiController {

    private final CrawlingService crawlingService;

    /**
     *  === 웹 크롤링 요청 ===
     */
    @GetMapping("/api/crawl")
    public ResponseEntity<Map<String, List<News>>> crawl() {
        log.info("웹 크롤링 실행");
        Map<String, List<News>> map = new HashMap<>();


        List<News> naverNews = crawlingService.crawlingNaverNews();
        List<News> daumNews = crawlingService.crawlingDaumNews();
        List<News> googleNews = crawlingService.crawlingGoogleNews();
        List<News> nateNews = crawlingService.crawlingNateNews();



        map.put("naver", naverNews);
        map.put("daum", daumNews);
        map.put("google", googleNews);
        map.put("nate", nateNews);
        return ResponseEntity.status(HttpStatus.OK)
                .body(map);
    }
}
