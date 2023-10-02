package com.a2.newsbyte.news;

import com.a2.newsbyte.tag.Tag;
import com.rometools.rome.io.FeedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsService;


    @GetMapping("/newspaper")
    public ResponseEntity<Map<String, List<News>>> getNews(@RequestParam(name = "newspaper") String newspaper) throws IOException, FeedException {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("news", newsService.fetchLatestNews(newspaper)));
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @PostMapping("/assign-tag/{id}")
    public ResponseEntity<Map<String, News>> assignTagById(@PathVariable("id") Long id, @RequestBody Tag tag)
    {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("news", newsService.assignTagById(id, tag)));
    }



    /***************************************************************************************************************************************/
    @GetMapping("/latestAll")
    public List<News> getAllLatestNewsForUser() throws IOException {
        return newsService.getLatestNewsForUser();
    }
    @GetMapping("/{category}")
    public List<News> getNewsByCategoryForUser(@PathVariable String category) throws IOException {
        return newsService.getNewsByCategoryForUser(category);
    }

    @GetMapping
    public void fetchAndSaveAll() throws IOException, FeedException {
        newsService.fetchAndSaveAll();
    }

    /***************************************************************************************************************************************/

//    @GetMapping("/trending")
//    public List<News> getTrendingNews(){
//        return newsService.getTrendingNews();
//    }

}


