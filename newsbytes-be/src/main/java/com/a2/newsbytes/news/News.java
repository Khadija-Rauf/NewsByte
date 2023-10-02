package com.a2.newsbytes.news;

import com.a2.newsbytes.newspaper.Newspaper;
import com.a2.newsbytes.tag.Tag;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity(name = "news")
public class News {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @Column(name = "published_at")
    private String publishedAt;         //date time
    @Column(name = "image_source")
    private String imageSource;         //imageSrc url
    @Id
    @Column(name = "details_url")
    private String detailsUrl;              //source of news (i.e, link to redirect)
    @Column(name = "added_on")
    private String addedOn;             //date in which this news added in database

    @ManyToOne
    @JoinColumn(name = "tag_id")
    //@JsonProperty(value = "tag", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Tag tag;
    @ManyToOne
    @JoinColumn(name = "newspaper_id")
    private Newspaper newspaper;


    public News() {
    }

    public News(String title, String description, String publishedAt, String imageSource, String detailsUrl, String addedOn, Tag tag, Newspaper newspaper) {
        this.title = title;
        this.description = description;
        this.publishedAt = publishedAt;
        this.imageSource = imageSource;
        this.detailsUrl = detailsUrl;
        this.addedOn = addedOn;
        this.tag = new Tag(tag);
        this.newspaper = new Newspaper(newspaper);
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public String getDetailsUrl() {
        return detailsUrl;
    }

    public void setDetailsUrl(String detailsUrl) {
        this.detailsUrl = detailsUrl;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }

    //@JsonProperty(value = "tag", required = false)
    public Tag getTag() {
        return new Tag(tag);
    }

    public void setTag(Tag tag) {
        this.tag = new Tag(tag);
    }

    public Newspaper getNewspaper() {
        return new Newspaper(newspaper);
    }

    public void setNewspaper(Newspaper newspaper) {
        this.newspaper = new Newspaper(newspaper);
    }

}
