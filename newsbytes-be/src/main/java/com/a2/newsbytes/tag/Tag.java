package com.a2.newsbytes.tag;

import com.a2.newsbytes.news.News;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @JsonIgnore
    @OneToMany(mappedBy = "tag", fetch = FetchType.LAZY)
    private List<News> news;


    public Tag(){}
    public Tag(Tag tag) {
        this.id=tag.id;
        this.name=tag.name;
        this.description=tag.description;
        this.news=tag.news;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<News> getNews() {
        return Collections.unmodifiableList(news);
    }

    public void setNews(List<News> news) {
        this.news = new ArrayList<>(news);
    }
}
