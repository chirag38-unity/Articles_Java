package com.cr.articlesjava.utils;

import android.content.Context;
import com.cr.articlesjava.di.AppModule;
import com.cr.articlesjava.di.NetworkModule;
import com.cr.articlesjava.di.RepositoryModule;
import com.cr.articlesjava.presentation.DetailScreen.ArticleDetailScreen;
import com.cr.articlesjava.presentation.ListScreen.ArticlesListScreen;
import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class, RepositoryModule.class})
public interface AppComponent {

    /**
     * Injects dependencies into ArticlesListScreen.
     */
    void inject(ArticlesListScreen articlesListScreen);

    /**
     * Injects dependencies into ArticleDetailScreen.
     */
    void inject(ArticleDetailScreen articleDetailScreen);

    /**
     * Provides the application context.
     */
    Context getContext();
}
