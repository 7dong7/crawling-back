package org.mycrawling.webcrawling.api.controller;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mycrawling.webcrawling.domain.dto.ApiResponse;
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
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CrawlingApiController {

    private final CrawlingService crawlingService;

    /**
     *  === 웹 크롤링 요청 ===
     */
    @GetMapping("/api/crawl")
    public ResponseEntity<ApiResponse> crawl() {
        long startTime = System.currentTimeMillis(); // 실행시간 계산
        
        ApiResponse apiResponse = new ApiResponse(); // 응답 객체
        Map<String, List<News>> portalNews = new HashMap<>(); // 각 뉴스 포털
        List<News> allNews = new ArrayList<>(); // 모든 뉴스
        Map<String, Integer> wordFrequency = new HashMap<>(); // 단어 빈도수 저장
        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL); // 형태소 분석기 설정

            // ==== 비동기 처리 방식 ====
        CompletableFuture<List<News>> naverNewsFuture = crawlingService.crawlingNaverNewsAsy();
        CompletableFuture<List<News>> daumNewsFuture = crawlingService.crawlingDaumNewsAsy();
        CompletableFuture<List<News>> googleNewsFuture = crawlingService.crawlingGoogleNewsAsy();
        CompletableFuture<List<News>> nateNewsFuture = crawlingService.crawlingNateNewsAsy();

        // 모든 비동기 작업이 완료될때 까지 기다린다
        return CompletableFuture.allOf(naverNewsFuture, daumNewsFuture, googleNewsFuture, nateNewsFuture)
                .thenApply(ignored -> {
                    // 각 포털의 뉴스
                    portalNews.put("naver", naverNewsFuture.join());
                    portalNews.put("daum", daumNewsFuture.join());
                    portalNews.put("google", googleNewsFuture.join());
                    portalNews.put("nate", nateNewsFuture.join());

                    portalNews.values().forEach(allNews::addAll); // 모든 뉴스 종합

                    // 뉴스 제목 형태소 분석
                    for (News news : allNews) {
                        KomoranResult analyze = komoran.analyze(news.getTitle()); // 분석할 문장 넣기
                        List<Token> tokenList = analyze.getTokenList(); // 분장 단어로 분해
                        for (Token token : tokenList) {
                            //  고유 명사와 일반 명사 만 추출 빈도수
                            if(token.getPos().equals("NNP") || token.getPos().equals("NNG")) {
                                // 빈도수 계산
                                String word = token.getMorph();
                                wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
                            }
                        }
                    }
                    apiResponse.setNews(portalNews); // 뉴스 응답 설정
                    apiResponse.setWordFrequency(wordFrequency); // 빈도수를 계산 결과 설정

                    long endTime = System.currentTimeMillis();
                    log.info("시간 차이(ms) process time: {}", (endTime - startTime));
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(apiResponse); // 응답
                }).join();


//        // 각 기사 꺼내기
//        List<News> naverNews = naverNewsFuture.join();
//        List<News> daumNews = daumNewsFuture.join();
//        List<News> googleNews = googleNewsFuture.join();
//        List<News> nateNews = nateNewsFuture.join();
//
//        // 각 뉴스제목의 핵심 키워드 작성
//        allNews.addAll(naverNews);
//        allNews.addAll(daumNews);
//        allNews.addAll(googleNews);
//        allNews.addAll(nateNews);

//        Map<String, Integer> wordFrequency = new HashMap<>(); // 단어 빈도수 저장
//        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL); // 형태소 분석기 설정

        /**
         *  뉴스 하나가 들어가고
         *      뉴스를 각각의 형태소로 분석 진행 
         *          NNP 고유명사
         *          NNG 일반명사 만 추출 후 빈도수 계산
         */
//        for (News news : allNews) {
////            System.out.println("======================= 뉴스 하나 ==============");
//            KomoranResult analyze = komoran.analyze(news.getTitle()); // 분석할 문장 넣기
////            String plainText = analyze.getPlainText();
////            System.out.println("plainText = " + plainText);
//            List<Token> tokenList = analyze.getTokenList();
//            for (Token token : tokenList) {
//                //  고유 명사와 일반 명사 만 추출 빈도수
//                if(token.getPos().equals("NNP") || token.getPos().equals("NNG")) {
//                    // 빈도수 계산
//                    String word = token.getMorph();
//                    wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
//                }
//            }
//        }
//        apiResponse.setWordFrequency(wordFrequency);

//        portalNews.put("naver", naverNews);
//        portalNews.put("daum", daumNews);
//        portalNews.put("google", googleNews);
//        portalNews.put("nate", nateNews);
//        apiResponse.setNews(portalNews);


        /**
         *  ==== 동기 처리 시간  ====
         *      시간 차이(ms) useTime: 3127
         *      시간 차이(ms) useTime: 2428
         *      시간 차이(ms) useTime: 2301
         *      시간 차이(ms) useTime: 2803
         *      시간 차이(ms) useTime: 2508
         *
         *  ==== 비동기 처리 시간  ====
         *      시간 차이(ms) useTime: 2549
         *      시간 차이(ms) useTime: 2347
         *      시간 차이(ms) useTime: 2370
         *      시간 차이(ms) useTime: 2114
         *      시간 차이(ms) useTime: 2300
         *
         *
         *  ==== 처리 시간 ===
         *      시간 차이(ms) process time: 2467
         */

    }
}
