package org.mycrawling.webcrawling.api.controller;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mycrawling.webcrawling.domain.dto.ApiResponse;
import org.mycrawling.webcrawling.domain.dto.News;
import org.mycrawling.webcrawling.service.TestCrawlingService;
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
public class TestApiController {

    private final TestCrawlingService testCrawlingService;
    /**
     *  === 성능 개선 테스트 ===
     */
    @GetMapping("/api/crawlTest")
    public ResponseEntity<String> crawlTest() {
//        System.out.println("== 성능 개선 테스트 진행중 ==");
    // ================= 크롤링 테스트 ================= 시작
//        long start = System.currentTimeMillis();

        List<News> naverNews = testCrawlingService.testCrawlingNaverNews();
//        List<News> daumNews = testCrawlingService.testCrawlingDaumNews();
//        List<News> googleNews = testCrawlingService.testCrawlingGoogleNews();
//        List<News> nateNews = testCrawlingService.testCrawlingNateNews();


//        long end = System.currentTimeMillis();
//        System.out.println("모든처리 시간 process time: " + (end - start));
    // ================= 크롤링 테스트 ================= 끝
    /**
     *  === 크롤링 처리 시간 기록 ===
     *  1회
     *      네이버 처리시간 process time: 291
     *      다음 처리시간 process time: 100
     *      구글 처리시간 process time: 585
     *      네이트 처리시간 process time: 35
     *      모든처리 시간 process time: 1011
     *  2회
     *      네이버 처리시간 process time: 128
     *      다음 처리시간 process time: 81
     *      구글 처리시간 process time: 534
     *      네이트 처리시간 process time: 24
     *      모든처리 시간 process time: 768
     *  3회
     *      네이버 처리시간 process time: 79
     *      다음 처리시간 process time: 89
     *      구글 처리시간 process time: 658
     *      네이트 처리시간 process time: 35
     *      모든처리 시간 process time: 862
     *
     *
     * 네이버 처리시간 process time: 296
     * 네이버 처리시간 process time: 50
     * 네이버 처리시간 process time: 67
     * 네이버 처리시간 process time: 260
     * 네이버 처리시간 process time: 48
     * 네이버 처리시간 process time: 51
     * 네이버 처리시간 process time: 46
     *
     */





    // ================= 형태소 분석 테스트 ================= 시작
//        System.out.println("\n========= 형태소 분석 테스트 =========");
//        start = System.currentTimeMillis();
//        ApiResponse apiResponse = new ApiResponse(); // 응답 객체 생성
//
//        // 각 뉴스제목의 핵심 키워드 작성
//        List<News> allNews = new ArrayList<>();
//        allNews.addAll(naverNews);
//        allNews.addAll(daumNews);
//        allNews.addAll(googleNews);
//        allNews.addAll(nateNews);
//
//        Map<String, Integer> wordFrequency = new HashMap<>(); // 단어 빈도수 저장
//        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL); // 형태소 분석기 설정
//
//        /**
//         *  뉴스 하나가 들어가고
//         *      뉴스를 각각의 형태소로 분석 진행
//         *          NNP 고유명사
//         *          NNG 일반명사 만 추출 후 빈도수 계산
//         */
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
//        end = System.currentTimeMillis();
//        System.out.println("모든처리 시간 process time: " + (end - start));
    // ================= 형태소 분석 테스트 ================= 끝
        /**
         *  === 처리 시간 기록 ===
         *      모든처리 시간 process time: 2081
         *      모든처리 시간 process time: 1899
         *      모든처리 시간 process time: 1941
         */
        
        return new ResponseEntity<>("응답 테스트", HttpStatus.OK);
    }
}
