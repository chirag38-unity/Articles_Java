package com.cr.articlesjava.data.models;

import com.cr.articlesjava.domain.models.NewsResponse;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class NewsResponseDao {
    private String status;
    private int totalResults;
    private List<ArticleDao> articles = Collections.emptyList();

    public String getStatus() { return status; }
    public int getTotalResults() { return totalResults; }
    public List<ArticleDao> getArticles() { return articles; }

    public NewsResponse toNewsResponse() {
        return new NewsResponse(
                this.status,
                this.totalResults,
                this.articles.stream().map(ArticleDao::toArticle).collect(Collectors.toList())
        );
    }

}
