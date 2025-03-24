package com.cr.articlesjava.data.remote;

import com.cr.articlesjava.data.models.NewsResponseDao;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;



public interface NewsApiInterface {

    /**
     * Defines an API endpoint to fetch news data based on a query parameter.
     * Uses Retrofit's @GET annotation to specify the endpoint URL.
     * @param query The query string to fetch news for.
     * @return An Observable of NewsResponseDao.
     */

    @GET("v1/{query}" )
    Observable<NewsResponseDao> getNews(
            @Path("query") String query
    );

}
