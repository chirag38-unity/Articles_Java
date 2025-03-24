package com.cr.articlesjava.di.ViewModelFactories;

import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.savedstate.SavedStateRegistryOwner;

import com.cr.articlesjava.presentation.DetailScreen.ArticleDetailViewModel;
import com.cr.articlesjava.presentation.ListScreen.ArticlesListScreenViewModel;

import javax.inject.Singleton;

@Singleton
public class ArticleDetailViewModelFactory implements ViewModelProvider.Factory {
    private final ArticleDetailViewModel viewModel;

    /**
     * Constructor for ArticleDetailViewModelFactory.
     */
    public ArticleDetailViewModelFactory(ArticleDetailViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Creates and returns the ViewModel instance.
     */
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ArticleDetailViewModel.class)) {
            return (T) viewModel;
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

    /**
     * Creates and returns the ViewModel instance with creation extras.
     */
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass, @NonNull CreationExtras extras) {
        return create(modelClass);
    }

}
