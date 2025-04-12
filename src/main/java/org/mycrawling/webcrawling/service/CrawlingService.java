package org.mycrawling.webcrawling.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mycrawling.webcrawling.domain.dto.News;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class CrawlingService {
    /**
     *  === naver 뉴스 크롤링 ===
     */
    public List<News> crawlingNaverNews() {
        log.info("naver crawling 실행");
        try {
            // naver news 주소
            String url = "https://news.naver.com/"; // 주소
            Document document = Jsoup.connect(url).get(); // 가져오기

            Elements elements = document.select("a.cnf_news_area");

            int limit = Math.min(elements.size(), 10); // 반복수 지정
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
            return naverNews;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Async("taskExecutor")
    public CompletableFuture<List<News>> crawlingNaverNewsAsy() {
        log.info("crawlingNaverNewsAsy 실행");
        try {
            // naver news 주소
            String url = "https://news.naver.com/"; // 주소
            Document document = Jsoup.connect(url).get(); // 가져오기

            Elements elements = document.select("a.cnf_news_area");

            int limit = Math.min(elements.size(), 10); // 반복수 지정
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
            return CompletableFuture.completedFuture(naverNews);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  === Daum 뉴스 크롤링 ===
     */
    public List<News> crawlingDaumNews() {
        log.info("daum crawling 실행");
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
            return daumNews;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Async("taskExecutor")
    public CompletableFuture<List<News>> crawlingDaumNewsAsy() {
        log.info("crawlingDaumNewsAsy 실행");
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
            return CompletableFuture.completedFuture(daumNews);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  === 구글 뉴스 크롤링 ===
     */
    public List<News> crawlingGoogleNews() {
        log.info("구글 crawling 실행");
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
            return googleNews;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Async("taskExecutor")
    public CompletableFuture<List<News>> crawlingGoogleNewsAsy() {
        log.info("crawlingGoogleNewsAsy 실행");
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
            return CompletableFuture.completedFuture(googleNews);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  ==== nate 뉴스 크롤링 ===
     */
    public List<News> crawlingNateNews() {
        log.info("nate crawling 실행");
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
            return nateNews;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Async("taskExecutor")
    public CompletableFuture<List<News>> crawlingNateNewsAsy() {
        log.info("crawlingNateNewsAsy 실행");
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
            return CompletableFuture.completedFuture(nateNews);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
