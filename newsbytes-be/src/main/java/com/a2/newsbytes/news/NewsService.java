package com.a2.newsbytes.news;

import com.a2.newsbytes.newspaper.NewspaperService;
import com.a2.newsbytes.tag.Tag;
import com.a2.newsbytes.tag.TagService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private TagService tagService;
    @Autowired
    private NewspaperService newspaperService;


    private List<News> fetchDawnNews() throws IOException {
        Document doc = Jsoup.connect("https://www.dawn.com/latest-news").get();
        Elements articles = doc.select("div#all article");

        List<News> newsList = new ArrayList<>();

        for (Element article : articles) {

            String imgSrc = article.select("img.lazyload").attr("data-src");
            String title = article.select("a.story__link").html();
            String detailsUrl = article.select("a.story__link").attr("href");
            String description = article.select("div.story__excerpt").html();
            String publishedAt = article.select("span.timestamp__calendar").html() + " " + article.select("span.timestamp__time").html();

            if (imgSrc.equals("")) {
                imgSrc = "https://www.dawn.com/_img/logo.png";            //default dawn logo
            }
            if (description.equals("")) {
                description = title;
            }
            if (publishedAt.equals("")) {
                publishedAt = LocalDateTime.now().toString();
            }

            newsList.add(new News(title, description, publishedAt, imgSrc, detailsUrl, LocalDate.now().toString(), tagService.getTagById(1L), newspaperService.getNewspaperByName("Dawn")));
        }
        return newsList;
    }

    private List<News> fetchTheNewsInternationalNews() throws IOException {
        Document doc = Jsoup.connect("https://www.thenews.com.pk/latest-stories").get();
        Elements newsListItems = doc.select("div.siteContent div.detail-center ul li");

        List<News> newsList = new ArrayList<>();

        for (Element newsItem : newsListItems) {

            String imgSrc = newsItem.selectFirst("a").select("img").attr("src");
            String title = newsItem.select("div.latest-right a.open-section").html();
            String detailsUrl = newsItem.select("div.latest-right a.open-section").attr("href");
            String description = newsItem.select("div.latest-right p").html();
            String publishedAt = newsItem.select("div.latest-right span.latestDate").html();

            if (imgSrc.equals("")) {
                imgSrc = "https://www.thenews.com.pk/assets/front/images/Thenews-logo.svg";            //default logo
            }
            if (description.equals("")) {
                description = title;
            }
            if (publishedAt.equals("")) {
                publishedAt = LocalDateTime.now().toString();
            }

            newsList.add(new News(title, description, publishedAt, imgSrc, detailsUrl, LocalDate.now().toString(), tagService.getTagById(1L), newspaperService.getNewspaperByName("The News International")));
        }
        return newsList;
    }

    public List<News> fetchTheExpressTribuneNews() throws IOException {
        List<News> newsList = new ArrayList<>();

        for(int i=1; i<=6; i++) {
            Document doc = Jsoup.connect("https://tribune.com.pk/latest?page="+i).get();
            Elements newsListItems = doc.select("div.maincontent-customwidth ul.tedit-shortnews li");

            for (Element newsItem : newsListItems) {

                String imgSrc = newsItem.select("div.featured-image-global img").attr("src");
                String title = newsItem.select("div.horiz-news3-caption a h2").html();
                String detailsUrl = newsItem.select("div.horiz-news3-caption a").attr("href");
                String description = newsItem.select("div.horiz-news3-caption p").html();
                String publishedAtData = newsItem.select("div.horiz-news3-caption span").text();
                String publishedAt = publishedAtData.substring(publishedAtData.indexOf("|"));

                if (imgSrc.equals("")) {
                    imgSrc = "https://i.tribune.com.pk/media/images/logos/tribune-logo.webp";            //default logo
                }
                if (description.equals("")) {
                    description = title;
                }
                if (publishedAt.equals("")) {
                    publishedAt = LocalDateTime.now().toString();
                }

                newsList.add(new News(title, description, publishedAt, imgSrc, detailsUrl, LocalDate.now().toString(), tagService.getTagById(1L), newspaperService.getNewspaperByName("The Express Tribune")));
            }
        }
        return newsList;
    }




    //save all fetched news of dawn in your database
    private List<News> extractOnlyNewNews(List<News> newsList) {
        List<News> prevNewsList = this.getAllNewsFromDatabase();
        List<News> onlyNewNewsList = new ArrayList<>();

        if(prevNewsList.isEmpty()) {
            return newsList;
        }
        else {
            //this.deletePrevDatesNews(prevNewsList);     //then delete news of previous dates
            for (News news : newsList) {
                if (newsRepository.getNewsByDetailsUrl(news.getDetailsUrl()) == null) {
                    onlyNewNewsList.add(news);
                }
            }
            return onlyNewNewsList;
        }
    }

    /*private void deletePrevDatesNews(List<News> newsList) {
        String todaysDate = LocalDate.now().toString();
        newsRepository.deletePrevDatesNews(todaysDate);
    }*/

    private void saveNews(List<News> newsList) {
        for(int i=newsList.size()-1; i>=0; i--) {
            newsRepository.save(newsList.get(i));
        }
        /*for (News news : newsList) {
            newsRepository.save(news);
        }*/
    }


    public List<News> getAllNewsFromDatabase() {
        List<News> newsList = new ArrayList<>();
        newsRepository.findAll().forEach(News -> newsList.add(News));
        return newsList;
    }

    public List<News> getAllNews(String type) {
        if(type.equals("national") || type.equals("international")) {
            return null;
        }
        else if(type.equals("Dawn") || type.equals("The News International") || type.equals("The Express Tribune")
         || type.equals("BBC") || type.equals("The New York Times") || type.equals("The Guardian"))
        {
            return newsRepository.getNewsByNewspaper(newspaperService.getNewspaperByName(type).getId());
        }
        else {
            return newsRepository.getNewsByTag(tagService.getTagByName(type).getId());
        }
    }

    public List<News> fetchLatestNews(String newspaper) throws IOException {
        List<News> newsList = null;
        if(newspaper.equals("Dawn")) {
            newsList = this.fetchDawnNews();
        }
        else if(newspaper.equals("The News International")) {
            newsList = this.fetchTheNewsInternationalNews();
        }
        else if(newspaper.equals("The Express Tribune")) {
            newsList = this.fetchTheExpressTribuneNews();
        }
        this.saveNews(this.extractOnlyNewNews(newsList));    //that are not already in DB, it is mandatory else tags not remain persistent
        return this.getNewsByNewspaper(newspaper);
    }

    public List<News> getNewsByNewspaper(String newspaper) {
        return newsRepository.getNewsByNewspaper(newspaperService.getNewspaperByName(newspaper).getId());
    }


    public News assignTagById(Long id, Tag tag) {
        News news = newsRepository.getNewsById(id);
        news.setTag(tag);
        return newsRepository.save(news);
    }

}
