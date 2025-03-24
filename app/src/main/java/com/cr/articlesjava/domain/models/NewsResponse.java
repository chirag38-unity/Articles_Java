package com.cr.articlesjava.domain.models;

import com.cr.articlesjava.data.models.ArticleDao;

import java.util.Collections;
import java.util.List;

public class NewsResponse {

    private String status;
    private int totalResults;
    private List<Article> articles = Collections.emptyList();

    public String getStatus() { return status; }
    public int getTotalResults() { return totalResults; }
    public List<Article> getArticles() { return articles; }

    public NewsResponse(String status, int totalResults, List<Article> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

}
