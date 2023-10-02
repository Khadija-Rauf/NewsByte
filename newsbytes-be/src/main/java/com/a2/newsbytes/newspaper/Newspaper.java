package com.a2.newsbytes.newspaper;

import com.a2.newsbytes.news.News;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity(name = "newspapers")
public class Newspaper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;        //localOrInternational
    @Column(name = "logo_url")
    private String logoUrl;
    @Column(name = "site_url")
    private String siteUrl;
    @JsonIgnore
    @OneToMany(mappedBy = "newspaper", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<News> news;

    public Newspaper(){}
    public Newspaper(Newspaper newspaper) {
        this.id=newspaper.id;
        this.name=newspaper.name;
        this.type=newspaper.type;
        this.logoUrl=newspaper.logoUrl;
        this.siteUrl=newspaper.siteUrl;
        this.news=newspaper.news;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public List<News> getNews() {
        return Collections.unmodifiableList(news);
    }

    public void setNews(List<News> news) {
        this.news = new ArrayList<>(news);
    }

}
