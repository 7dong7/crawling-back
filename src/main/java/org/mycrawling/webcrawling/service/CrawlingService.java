package org.mycrawling.webcrawling.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CrawlingService {

    public void crawlWebsite() {
        try {
            // 대상 url
            String url = "https://news.naver.com/";
            Document document = Jsoup.connect(url).get();
            log.info("\ndocument: {}", document);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
