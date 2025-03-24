package com.cr.articlesjava.di;

import android.content.Context;

import androidx.lifecycle.SavedStateHandle;

import com.cr.articlesjava.data.repository.ArticlesRepository;
import com.cr.articlesjava.di.ViewModelFactories.ArticleDetailViewModelFactory;
import com.cr.articlesjava.di.ViewModelFactories.ArticlesViewModelFactory;
import com.cr.articlesjava.presentation.DetailScreen.ArticleDetailViewModel;
import com.cr.articlesjava.presentation.ListScreen.ArticlesListScreenViewModel;
import com.cr.articlesjava.utils.InternetConnectivityObserver;
import com.cr.articlesjava.utils.PreferencesManager;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Context appContext;
    /**
     * Constructor for AppModule to provide application context.
     */
    public AppModule(Context appContext) {
        this.appContext = appContext;
    }

    /**
     * Provides an observer for monitoring internet connectivity.
     */
    @Provides
    @Singleton
    public InternetConnectivityObserver provideInternetConnectivityObserver() {
        return new InternetConnectivityObserver(appContext);
    }

    /**
     * Provides the application context as a singleton.
     */
    @Provides
    @Singleton
    public Context provideAppContext() {
        return appContext;
    }

    /**
     * Provides a singleton instance of PreferencesManager for managing app preferences.
     */
    @Provides
    @Singleton
    public PreferencesManager providePreferencesManager() {
        return new PreferencesManager(appContext);
    }

    /**
     * Provides the ViewModel for the articles list screen.
     */
    @Provides
    @Singleton
    ArticlesListScreenViewModel provideArticlesViewModel(
            ArticlesRepository repository,
            PreferencesManager manager,
            InternetConnectivityObserver connectivityObserver
    ) {
        return new ArticlesListScreenViewModel(repository, manager, connectivityObserver);
    }

    /**
     * Provides a factory for the ArticlesListScreenViewModel.
     */
    @Provides
    ArticlesViewModelFactory provideArticlesListScreenViewModelFactory(ArticlesListScreenViewModel viewModel) {
        return new ArticlesViewModelFactory(viewModel);
    }

    /**
     * Provides a factory for the ArticleDetailViewModel.
     */
    @Provides
    ArticleDetailViewModelFactory provideArticleDetailViewModelFactory(ArticleDetailViewModel viewModel) {
        return new ArticleDetailViewModelFactory(viewModel);
    }

}
