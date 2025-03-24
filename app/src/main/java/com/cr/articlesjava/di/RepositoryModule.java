package com.cr.articlesjava.di;

import com.cr.articlesjava.data.remote.NewsApiInterface;
import com.cr.articlesjava.data.repository.ArticlesRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    /**
     * Provides a singleton instance of ArticlesRepository.
     */
    @Provides
    @Singleton
    public ArticlesRepository provideNewsRepository(NewsApiInterface newsApiInterface) {
        return new ArticlesRepository(newsApiInterface);
    }

}
