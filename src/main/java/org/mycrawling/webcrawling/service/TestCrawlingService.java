package org.mycrawling.webcrawling.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mycrawling.webcrawling.domain.dto.News;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class TestCrawlingService {

    /**
     *  === naver 뉴스 크롤링 ===
     *
     *      개선 사항
     *          성능: 비동기 처리와 캐싱으로 응답 시간 단축.
     *          안정성: 적절한 예외 처리와 Fallback 로직 추가.
     *          유지보수성: 설정 분리, 코드 모듈화.
     *          확장성: 재사용 가능한 구조로 리팩토링.
     */
    public List<News> testCrawlingNaverNews() {
        long start = System.currentTimeMillis();
        try {
            // 기존 코드
            String url = "https://news.naver.com/"; // 크롤링 주소 설정
            Document document = Jsoup.connect(url).get(); // 페이지 크롤링 가져오기

            Elements elements = document.select("a.cnf_news_area"); // 뉴스에 대한 정보가 담겨있는 태그 추출

            // 최대 뉴스 크롤링 수 지정
            int limit = Math.min(elements.size(), 10); // 반복수 지정

            // 빈 배열 생성
            ArrayList<News> naverNews = new ArrayList();
            for (int i = 0; i < limit; i++) { // 뉴스 추출
                Element element = elements.get(i); // 태그 추출
                // 본 태그의 attr href 값을 추출
                String href = element.attr("href") != null ? element.attr("href") : "no href";
                // 자식 태그중 strong 태그 값 추출
                String title = element.selectFirst("strong") != null ? element.select("strong").text() : "no title";
                // News 객체 생성
                News news = new News();
                news.setId("naver_"+(i+1));
                news.setTitle(title);
                news.setHref(href);
                naverNews.add(news);
            }
            long end = System.currentTimeMillis();
            System.out.println("네이버 처리시간 process time: " + (end - start));
            return naverNews;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     *  === Daum 뉴스 크롤링 ===
     */
    public List<News> testCrawlingDaumNews() {
        System.out.println("daum crawling 실행");
        long start = System.currentTimeMillis();
        try {
            // daum news 주소
            String url = "https://news.daum.net/"; // 주소
            Document document = Jsoup.connect(url).get(); // 가져오기

            Elements elements = document.select("a.item_newsheadline2"); // 가져올 태그 지정

            int limit = Math.min(elements.size(), 10); // 반복수 지정
            ArrayList<News> daumNews = new ArrayList();
            for (int i = 0; i < limit; i++) { // 뉴스 추출
                Element element = elements.get(i); // 태그 추출
                // 본 태그의 attr href 값을 추출
                String href = element.attr("href") != null ? element.attr("href") : "no href";
                // 자식 태그중 strong 태그 값 추출
                String title = element.selectFirst("strong") != null ? element.select("strong").text() : "no title";
                // News 객체 생성
                News news = new News();
                news.setId("daum_"+(i+1));
                news.setTitle(title);
                news.setHref(href);
                daumNews.add(news);
            }
            long end = System.currentTimeMillis();
            System.out.println("다음 처리시간 process time: " + (end - start));
            return daumNews;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     *  === 구글 뉴스 크롤링 ===
     */
    public List<News> testCrawlingGoogleNews() {
        System.out.println("구글 crawling 실행");
        long start = System.currentTimeMillis();
        try {
            // 구글 news 주소
            String url = "https://news.google.com/home?hl=ko&gl=KR&ceid=KR:ko"; // 주소
            Document document = Jsoup.connect(url).get(); // 가져오기

            Elements elements = document.select("a.gPFEn"); // 가져올 태그 지정

            int limit = Math.min(elements.size(), 10); // 반복수 지정
            ArrayList<News> googleNews = new ArrayList();
            for (int i = 0; i < limit; i++) { // 뉴스 추출
                Element element = elements.get(i); // 태그 추출
                // 본 태그의 attr href 값을 추출
                String href = element.attr("href") != null ? element.attr("href") : "no href";
                String title = element.text();
                // 자식 태그중 strong 태그 값 추출
                // News 객체 생성
                News news = new News();
                news.setId("google_"+(i+1));
                news.setTitle(title);
                news.setHref(href);
                googleNews.add(news);
            }

            long end = System.currentTimeMillis();
            System.out.println("구글 처리시간 process time: " + (end - start));
            return googleNews;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     *  ==== nate 뉴스 크롤링 ===
     */
    public List<News> testCrawlingNateNews() {
        System.out.println("nate crawling 실행");
        long start = System.currentTimeMillis();
        try {
            // 구글 news 주소
            String url = "https://news.nate.com/"; // 주소
            Document document = Jsoup.connect(url).get(); // 가져오기

            Elements newsDiv = document.select("div.newsMainIssueList"); // 가져올 태그 지정

            Elements elements = newsDiv.select("a");

            int limit = Math.min(elements.size(), 10); // 반복수 지정
            ArrayList<News> nateNews = new ArrayList();
            for (int i = 0; i < limit; i++) { // 뉴스 추출
                Element element = elements.get(i); // 태그 추출
                // 본 태그의 attr href 값을 추출
                String href = element.attr("href") != null ? element.attr("href") : "no href";
                String title = element.text();
                // 자식 태그중 strong 태그 값 추출
                // News 객체 생성
                News news = new News();
                news.setId("nate_"+(i+1));
                news.setTitle(title);
                news.setHref(href);
                nateNews.add(news);
            }

            long end = System.currentTimeMillis();
            System.out.println("네이트 처리시간 process time: " + (end - start));
            return nateNews;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
