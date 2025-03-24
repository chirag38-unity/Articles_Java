package com.cr.articlesjava.di;

import static com.cr.articlesjava.data.remote.WebServices.BASE_URL;

import androidx.annotation.NonNull;

import com.cr.articlesjava.data.remote.NewsApiInterface;

import java.io.IOException;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.Interceptor;

@Module
public class NetworkModule {

    /**
     * Provides a logging interceptor to monitor network requests and responses.
     */
    @Provides
    @Singleton
    HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;

    }

    /**
     * Provides an interceptor to enforce JSON headers in all API requests.
     */
    @Provides
    @Singleton
    Interceptor provideNetworkInterceptor() {
        Interceptor networkInterceptor = new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request modifiedRequest = chain.request().newBuilder()
                        .header("Content-Type", "application/json") // Ensuring JSON request
                        .header("Accept", "application/json") // Expecting JSON response
                        .build();

                return chain.proceed(modifiedRequest);
            }
        };
        return networkInterceptor;
    }

    /**
     * Provides an OkHttpClient with logging and a network interceptor.
     */
    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor, Interceptor networkInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(networkInterceptor)
                .build();
    }

    /**
     * Provides a configured Retrofit instance.
     */
    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    /**
     * Provides the API service for making network requests.
     */
    @Provides
    @Singleton
    NewsApiInterface provideApiService(Retrofit retrofit) {
        return retrofit.create(NewsApiInterface.class);
    }

}
