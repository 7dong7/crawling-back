package org.mycrawling.webcrawling.api.controller;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mycrawling.webcrawling.domain.dto.News;
import org.mycrawling.webcrawling.service.CrawlingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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

        // 각 포털의 뉴스 가져오기
        List<News> naverNews = crawlingService.crawlingNaverNews();
        List<News> daumNews = crawlingService.crawlingDaumNews();
        List<News> googleNews = crawlingService.crawlingGoogleNews();
        List<News> nateNews = crawlingService.crawlingNateNews();

        // 각 뉴스제목의 핵심 키워드 작성
        List<News> allNews = new ArrayList<>();
        allNews.addAll(naverNews);
        allNews.addAll(daumNews);
        allNews.addAll(googleNews);
        allNews.addAll(nateNews);

        Map<String, Integer> keywordFrequency = new HashMap<>(); // 단어 빈도수 저장

        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);

        for (News news : allNews) {
            System.out.println("======================= 뉴스 하나 ==============");
            KomoranResult analyze = komoran.analyze(news.getTitle()); // 분석할 문장 넣기
            String plainText = analyze.getPlainText();
            System.out.println("plainText = " + plainText);
            List<Token> tokenList = analyze.getTokenList();
            for (Token token : tokenList) {
                System.out.println("token = " + token);
            }
        }


        map.put("naver", naverNews);
        map.put("daum", daumNews);
        map.put("google", googleNews);
        map.put("nate", nateNews);
        return ResponseEntity.status(HttpStatus.OK)
                .body(map);
    }
}
