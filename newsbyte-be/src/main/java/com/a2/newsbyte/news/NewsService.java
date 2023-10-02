package com.a2.newsbyte.news;

import com.a2.newsbyte.newspaper.NewspaperService;
import com.a2.newsbyte.tag.Tag;
import com.a2.newsbyte.tag.TagService;
import com.fasterxml.jackson.databind.JsonNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
    private NewsRepository newsRepository;
    @Autowired
    private TagService tagService;
    @Autowired
    private NewspaperService newspaperService;

    private static final int CONNECTION_TIMEOUT_MS = 5000; // 5 seconds
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.0.0 Safari/537.36";

    /***************************************************************************************************************************************************************************/

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

            newsList.add(new News(title, publishedAt, imgSrc, detailsUrl, LocalDate.now().toString(), tagService.getTagById(1L), newspaperService.getNewspaperByName("Dawn")));
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
    private void saveNews(List<News> newsList) {
        for(int i=newsList.size()-1; i>=0; i--) {
            newsRepository.save(newsList.get(i));
        }
    }


    public List<News> getAllNewsFromDatabase() {
        List<News> newsList = new ArrayList<>();
        newsRepository.findAll().forEach(News -> newsList.add(News));
        return newsList;
    }

    public List<News> fetchLatestNews(String newspaper) throws IOException {
        List<News> newsList = null;
        if(newspaper.equals("Dawn")) {
            newsList = this.fetchDawnNews();
        }
        if(newspaper.equals("92 News")) {
            newsList = this.getNews92();
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







    /************************************************************************************************/


    public List<News> getLatestNewsForUser() throws IOException {
        List<News> newsList = new ArrayList<>();
        List<String> channelNames = newsRepository.getAllChannelNames();

        for (String name : channelNames) {
            Long newspaperId = newsRepository.getNewsPaperIdByName(name);
            newsList.addAll(newsRepository.getLatestNewsByNewsPaperId(newspaperId));
        }
        return newsList;
    }

    public List<News> getNewsByCategoryForUser(String category) throws IOException {
        List<News> newsList = new ArrayList<>();
        List<String> channelNames = newsRepository.getAllChannelNames();

        for (String name : channelNames) {
            Long newspaperId = newsRepository.getNewsPaperIdByName(name);
            Long tagId = newsRepository.getTagIdByCategory(category);

            newsList.addAll(newsRepository.getNewsByCategory(newspaperId, tagId));
        }
        return newsList;
    }

    public List<News> getNews92(){
        System.out.println("\n in 92 service fetch \n");
        String url = "https://92newshd.tv/latest-news";
        List<News> articles = new ArrayList<>();
        try {
            Document document = Jsoup.connect(url)
                    .timeout(CONNECTION_TIMEOUT_MS)
                    .userAgent(USER_AGENT)
                    .get();
            Elements newsList = document.select(".sub-posts.post-item");

            for (Element news : newsList) {
                Element titleElement = news.select(".title").first();
                String title = titleElement != null ? titleElement.text() : "";

                Element linkElement = news.select("a.post_link").first();
                String postUrl = linkElement != null ? "https://92newshd.tv" + linkElement.attr("href") : "";

                Element imageElement = news.select("img").first();
                String imageUrl = imageElement != null ? "https://92newshd.tv/" + imageElement.attr("data-src") : "";

                Element dateElement = news.select(".published_time").first();
                String date = dateElement != null ? dateElement.text() : "";

                News newsArticle = new News(title, date, imageUrl, postUrl, LocalDate.now().toString(),
                        tagService.getTagById(1L), newspaperService.getNewspaperByName("92 News"));

                articles.add(newsArticle);


            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return articles;
    }
    public void fetchAndSaveAll() throws IOException {
        List<News> news92 = getNews92();
        /*
        *           call all other news channel functions
        */
        List<News> allNews = new ArrayList<>(news92);

        /*
        *       add your's list of news into allNews object
        */


        /*    You have nothing to do with the below code      */
        List<News> getLatest = extractOnlyNewNews(allNews);
        for (News newsItem : getLatest) {
            newsRepository.save(newsItem);
        }
    }


    public List<String> getAllTagsName() throws IOException {
           return newsRepository.getAllTagsNames();
    }



}
