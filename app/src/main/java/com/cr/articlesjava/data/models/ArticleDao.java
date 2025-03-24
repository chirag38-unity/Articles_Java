package com.cr.articlesjava.data.models;

import com.cr.articlesjava.domain.models.Article;

public class ArticleDao {
    private SourceDao source;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private String content;

    public SourceDao getSource() { return source; }
    public String getAuthor() { return author; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getUrl() { return url; }
    public String getUrlToImage() { return urlToImage; }
    public String getPublishedAt() { return publishedAt; }
    public String getContent() { return content; }

    public Article toArticle() {
        return new Article(
                this.source.toSource(),
                this.author,
                this.title,
                this.description,
                this.url,
                this.urlToImage,
                this.publishedAt,
                this.content
        );
    }

}
