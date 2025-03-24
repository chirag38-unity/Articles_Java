package com.cr.articlesjava.di.ViewModelFactories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.cr.articlesjava.presentation.ListScreen.ArticlesListScreenViewModel;

import javax.inject.Singleton;

@Singleton
public class ArticlesViewModelFactory implements ViewModelProvider.Factory {

    private final ArticlesListScreenViewModel viewModel;

    /**
     * Constructor for ArticlesViewModelFactory.
     */
    public ArticlesViewModelFactory(ArticlesListScreenViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Creates and returns the ViewModel instance.
     */
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ArticlesListScreenViewModel.class)) {
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
