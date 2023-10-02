package com.a2.newsbytes.news;

import com.a2.newsbytes.tag.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    /*@GetMapping
    public ResponseEntity<Map<String, List<News>>> getAllNews() {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("news", newsService.getAllNewsFromDatabase()));
    }*/

    @GetMapping
    public ResponseEntity<Map<String, List<News>>> getAllNews(@RequestParam(name = "type") String type) {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("news", newsService.getAllNews(type)));
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @GetMapping("/latest")
    public ResponseEntity<Map<String, List<News>>> fetchLatestNews(@RequestParam(name = "newspaper") String newspaper) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("news", newsService.fetchLatestNews(newspaper)));
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @PostMapping("/assign-tag/{id}")
    public ResponseEntity<Map<String, News>> assignTagById(@PathVariable("id") Long id, @RequestBody Tag tag)
    {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("news", newsService.assignTagById(id, tag)));
    }

}


