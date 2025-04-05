package org.mycrawling.webcrawling.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mycrawling.webcrawling.service.CrawlingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CrawlingController {

    private final CrawlingService crawlingService;

    /**
     *  === 웹 크롤링 요청 ===
     */
    @GetMapping("/api/crawl")
    public ResponseEntity<String> crawl() {
        log.info("웹 크롤링 실행");
        crawlingService.crawlWebsite();

        return ResponseEntity.status(HttpStatus.OK)
                .body("응답성공");
    }
}
