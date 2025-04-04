package com.cr.articlesjava.presentation.ListScreen;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.cr.articlesjava.data.repository.ArticlesRepository;
import com.cr.articlesjava.domain.models.NewsResponse;
import com.cr.articlesjava.utils.DataError;
import com.cr.articlesjava.utils.DataResultState;
import com.cr.articlesjava.utils.InternetConnectivityObserver;
import com.cr.articlesjava.utils.PreferencesManager;
import com.cr.articlesjava.utils.Result;
import javax.inject.Inject;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class ArticlesListScreenViewModel extends ViewModel {

    // Manages user preferences such as grid or list mode.
    private PreferencesManager preferencesManager;
    // Observes internet connectivity status.
    private InternetConnectivityObserver internetConnectivityObserver;
    // Repository for fetching articles from the data source.
    private ArticlesRepository repository;
    // Holds all disposables to manage RxJava subscriptions.
    private CompositeDisposable disposables = new CompositeDisposable();
    // Holds the current state of article data (Loading, Success, or Error).
    private final BehaviorSubject<DataResultState<NewsResponse>> resultState =
            BehaviorSubject.createDefault(new DataResultState.Idle<>());
    // Tracks the internet connectivity status.
    private MutableLiveData<Boolean> connectivityStatus = new MutableLiveData<>(true);
    // Stores the current view mode (Grid/List) as LiveData.
    private MutableLiveData<Boolean> isGridMode = new MutableLiveData<>();

    @Inject
    public ArticlesListScreenViewModel(ArticlesRepository repository, PreferencesManager preferencesManager, InternetConnectivityObserver internetConnectivityObserver) {
        this.repository = repository;
        this.preferencesManager = preferencesManager;
        this.internetConnectivityObserver = internetConnectivityObserver;
        // Initialize grid mode from stored preferences.
        isGridMode.setValue(preferencesManager.isGridMode());
        // Load articles and start observing connectivity.
        loadArticles();
        observeConnectivity();
    }

    /**
     * Fetches articles from the repository and updates the result state.
     */
    private void loadArticles() {
        // Set state to Loading before fetching data.
        resultState.onNext(new DataResultState.Loading<>());
        // Subscribe to repository data fetch operation.
        disposables.add(repository.fetchData("9b040bf5-62aa-4ba6-b3f2-f7a1e146097a")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {

                            Log.d("NewsListScreenViewModel", "Response: " + response);
                            response
                                    .onSuccess(newsResponse -> {
                                        resultState.onNext(new DataResultState.Success<>(newsResponse));
                                    })
                                    .onError(error -> {
                                        resultState.onNext(new DataResultState.Error<>(error));
                                    });
                        },
                        throwable -> {
                            Log.e("NewsListScreenViewModel", "Error loading articles", throwable);
                            resultState.onNext(new DataResultState.Error<>(DataError.NetworkError.UNKNOWN));
                        }
                ));
    }

    /**
     * Observes the internet connectivity status and updates LiveData accordingly.
     */
    private void observeConnectivity() {
        disposables.add(
                internetConnectivityObserver.isConnected()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                connectivityStatus::setValue,
                                throwable -> {
                                    Log.e("NewsListScreenViewModel", "Error observing connectivity", throwable);
                                    connectivityStatus.setValue(false);
                                }
                        )
        );
    }

    /**
     * Toggles between grid and list view modes.
     */
    public void toggleViewMode() {
        boolean currentMode = isGridMode.getValue() != null ? isGridMode.getValue() : false;
        isGridMode.setValue(!currentMode);
        preferencesManager.setGridMode(!currentMode);
    }

    /**
     * Retries loading articles in case of failure.
     */
    public void retry() {
        loadArticles();
    }

    /**
     * Provides an observable stream of article data states.
     */
    public Observable<DataResultState<NewsResponse>> getResultState() {
        return resultState.hide();
    }

    /**
     * Returns LiveData for the connectivity status.
     */
    public LiveData<Boolean> getConnectivityStatus() {
        return connectivityStatus;
    }

    /**
     * Returns LiveData for the current view mode.
     */
    public LiveData<Boolean> getIsGridMode() { return isGridMode; }

    /**
     * Clears all RxJava disposables when ViewModel is destroyed.
     */
    @Override
    protected void onCleared() {
        disposables.clear();
        super.onCleared();
    }

}
