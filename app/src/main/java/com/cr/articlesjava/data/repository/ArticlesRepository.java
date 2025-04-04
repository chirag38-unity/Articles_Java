package com.cr.articlesjava.data.repository;

import static com.cr.articlesjava.utils.DataError.mapErrorWithStatusCode;
import static com.cr.articlesjava.utils.DataError.mapExceptionWithThrowable;

import android.util.Log;
import com.cr.articlesjava.data.remote.NewsApiInterface;
import com.cr.articlesjava.domain.models.NewsResponse;
import com.cr.articlesjava.utils.DataError;
import com.cr.articlesjava.utils.Result;
import javax.inject.Inject;
import javax.inject.Singleton;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@Singleton
public class ArticlesRepository {

    private final NewsApiInterface newsApiInterface;

    @Inject
    public ArticlesRepository(NewsApiInterface newsApiInterface) {
        this.newsApiInterface = newsApiInterface;
    }

    /**
     * Fetches news data based on the query and wraps it in a Result.
     * Uses RxJava for asynchronous handling.
     * @param query The search term for news retrieval.
     * @return An Observable emitting a Result containing either NewsResponse or a DataError.
     */
    public Observable<Result<NewsResponse, DataError>> fetchData(String query) {
        return Observable.create(emitter -> {
            try {
                Log.d("NewsRepository", "Fetching data...");
                Disposable disposable =newsApiInterface
                                .getNews(query)
                                .subscribeOn(Schedulers.io()) // Run network call on IO thread
                                .observeOn(AndroidSchedulers.mainThread()) // Observe results on main thread
                                .subscribe(
                                        response -> {
                                            if (response.isSuccessful()) {
                                                Log.d("NewsRepository", "Fetched data -> " + response.body());
                                                if (response.body() != null) {
                                                    emitter.onNext(new Result.Success<>(response.body().toNewsResponse()) );
                                                } else {
                                                    emitter.onNext(new Result.Error<>(DataError.NetworkError.UNKNOWN));
                                                }
                                            } else {
                                                emitter.onNext(new Result.Error<>(mapErrorWithStatusCode(response.code())));
                                            }
                                            emitter.onComplete();
                                        },
                                        throwable -> {
                                            Log.d("NewsRepository", "Error fetching data",throwable);
                                            emitter.onNext(new Result.Error<>(mapExceptionWithThrowable(throwable)));
                                            emitter.onComplete();
                                        }
                                );
                // Properly dispose of the request when necessary
                emitter.setDisposable(new Disposable() {
                    @Override
                    public void dispose() {
                        if (!disposable.isDisposed()) {
                            disposable.dispose();
                        }
                    }

                    @Override
                    public boolean isDisposed() {
                        return disposable.isDisposed();
                    }
                });

            } catch (Exception e) {
                Log.e("NewsRepository", "Error fetching data", e);
                emitter.onNext(new Result.Error<>(DataError.NetworkError.UNKNOWN));
                emitter.onComplete();
            }
        });
    }

}
