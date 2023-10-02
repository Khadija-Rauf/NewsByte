package com.a2.newsbyte.news;

import com.a2.newsbyte.newspaper.NewspaperService;
import com.a2.newsbyte.tag.Tag;
import com.a2.newsbyte.tag.TagService;
//import com.fasterxml.jackson.databind.JsonNode;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
//import org.springframework.web.util.UriBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private TagService tagService;
    @Autowired
    private NewspaperService newspaperService;

    @Value("${news.nyTimes.rss}")
    private URL nyRssFeed;

    @Value("${news.alJazeera.rss}")
    private URL alJazeeraRss;
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

    public List<News> fetchLatestNews(String newspaper) throws IOException, FeedException {
        List<News> newsList = null;
        if(newspaper.equals("Dawn")) {
            newsList = this.fetchDawnNews();
        }
        else if(newspaper.equals("92 News")) {
            newsList = this.getNews92();
        }
        else if(newspaper.equals("Ary News")) {
            newsList = this.getNewsAry();
        }

        else if(newspaper.equals("NyTimes")) {
            newsList = this.getNewsByRss("NyTimes");
        }
        else if(newspaper.equals("Al-Jazeera")) {
            newsList = this.getNewsByRss("Al-Jazeera");
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
    private List<News> getNewsAry() {
        String url = "https://arynews.tv/";
        Set<String> uniqueNewsUrls = new HashSet<>();  // to store unique news
        List<News> articles = new ArrayList<>();
        try {
            Document document = Jsoup.connect(url).get();
            Element outerDiv = document.select(".vc_column.tdi_96.wpb_column.vc_column_container.tdc-column.td-pb-span12").first();
            Elements topNews = document.select(".vc_column.tdi_73.wpb_column.vc_column_container.tdc-column.td-pb-span3");
            Elements editorNews = document.select(".vc_column.tdi_82.wpb_column.vc_column_container.tdc-column.td-pb-span6");
            if (outerDiv != null) {
                Elements newsList1 = outerDiv.select(".td-module-container.td-category-pos-above");
                Elements newsList2 = topNews.select(".td-module-container.td-category-pos-above");
                Elements newsList3 = editorNews.select(".td-module-container.td-category-pos-");
                Elements newsList = new Elements();
                newsList.addAll(newsList1);
                newsList.addAll(newsList2);
                newsList.addAll(newsList3);
                if (newsList != null) {
                    for (Element news : newsList) {
                        Element link = news.select("a").first();
                        String hrefValue = link.attr("href");
                        if (!uniqueNewsUrls.contains(hrefValue)) {
                            Element titleElement = news.select("h3 > a").first();
                            if (titleElement == null || titleElement.text().isEmpty()) {
                                titleElement = news.select(".td-image-wrap ").first();
                            }
                            String title = (titleElement != null) ? titleElement.attr("title") : "No Title Found";
                            String publishedAt = getDate(hrefValue);
                            Element span = news.select("span.entry-thumb").first();
                            String imageUrl = "";
                            if (span != null) {
                                String styleAttribute = span.attr("style");

                                if (!styleAttribute.isEmpty()) {
                                    int startIndex = styleAttribute.indexOf("url(");
                                    int endIndex = styleAttribute.indexOf(")");

                                    if (startIndex != -1 && endIndex != -1) {
                                        imageUrl = styleAttribute.substring(startIndex + 4, endIndex);
                                    }
                                }
                            }
                            News newsArticle = new News(title, publishedAt, imageUrl, hrefValue, LocalDate.now().toString(),
                                    tagService.getTagById(3L), newspaperService.getNewspaperByName("Ary News"));
                            articles.add(newsArticle);
                            uniqueNewsUrls.add(hrefValue);
                        }
                    }
                } else {
                    System.out.println("Response is empty!!");
                }
            }else {
                System.out.println("Outer div not found!!");
            }

        } catch(IOException e){
            throw new RuntimeException(e);
        }
        return articles;
    }
    private static String getDate(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            Elements newsList = document.select(".vc_column.tdi_64.wpb_column.vc_column_container.tdc-column.td-pb-span12");

            if (newsList != null) {
                String date = null;
                for (Element news : newsList) {
                    date = news.select("time.entry-date").text();
                    if (!date.isEmpty()) {
                        return date;
                    }
                }
            }
            return String.valueOf(LocalDate.now());
        } catch (IOException e) {
            e.printStackTrace();
            return String.valueOf(java.sql.Date.valueOf(LocalDate.now()));
        }
    }
    public List<News> getNewsByRss(String newspaper) throws FeedException, MalformedURLException {
        URL rssFeedUrl = null;

        switch (newspaper){
            case "NyTimes":
                rssFeedUrl = nyRssFeed;
                break;
            case "Al-Jazeera":
                rssFeedUrl = alJazeeraRss;
                break;
        }
//        List<URL> urls = new ArrayList<>();
//        urls.add(nyRssFeed);
//        urls.add(alJazeeraRss);
        List<News> entryList = new ArrayList<>();
//        for (URL rssFeed: urls) {
            try {
                SyndFeedInput input = new SyndFeedInput();
                SyndFeed feed = input.build(new XmlReader(rssFeedUrl));

                feed.getEntries().forEach(entry -> {
                    News rssEntry = new News(entry.getTitle(), entry.getPublishedDate().toString(), getImageFromDescription(entry.getDescription().getValue()), entry.getLink(), LocalDate.now().toString(),
                            tagService.getTagById(4L), newspaperService.getNewspaperByName(newspaper));

                    entryList.add(rssEntry);
                });
                return entryList;
            } catch (IOException | FeedException e) {
                e.printStackTrace();
            }


        return entryList;
    }
    public String getImageFromDescription(String descriptionHtml){
        String imageUrl = null;
        Document doc = Jsoup.parse(descriptionHtml);
        Elements imgElement = doc.select("img");

        if (!imgElement.isEmpty()) {
            imageUrl = imgElement.attr("src");
        } else {
            System.out.println("No image found in description.");
        }
        return imageUrl;
    }
    public void fetchAndSaveAll() throws IOException, FeedException {
        List<News> news92 = getNews92();
        List<News> newsAry = getNewsAry();
        List<News> nyNews = getNewsByRss("NyTimes");
        List<News> alJazeeraNews = getNewsByRss("Al-Jazeera");
        /*
        *           call all other news channel functions
        */
        List<News> allNews = new ArrayList<>(news92);
        allNews.addAll(newsAry);
        allNews.addAll(nyNews);
        allNews.addAll(alJazeeraNews);

        /*
        *       add your's list of news into allNews object
        */


        /*    You have nothing to do with the below code      */
        List<News> getLatest = extractOnlyNewNews(allNews);
        for (News newsItem : getLatest) {
            newsRepository.save(newsItem);
        }
    }

    /************************************************************************************************/

//    public List<News> getTrendingNews(){
//        List<News> trendingNews = newsRepository.findTop3TrendingNews();
//        return trendingNews;
//    }



}
