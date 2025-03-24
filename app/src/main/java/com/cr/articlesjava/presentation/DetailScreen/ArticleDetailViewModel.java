package com.cr.articlesjava.presentation.DetailScreen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.cr.articlesjava.domain.models.Article;
import com.cr.articlesjava.utils.DataResultState;
import javax.inject.Inject;

public class ArticleDetailViewModel extends ViewModel {

    // LiveData to hold the UI state of the article
    private final MutableLiveData<DataResultState<Article>> _uiState = new MutableLiveData<>(new DataResultState.Idle());

    @Inject
    public ArticleDetailViewModel() {
        // Default constructor required for ViewModel injection
    }

    /**
     * Exposes an immutable LiveData for observing UI state changes.
     */
    public LiveData<DataResultState<Article>> getUiState() {
        return _uiState;
    }

    /**
     * Sets the article data in the ViewModel and updates the UI state.
     */
    public void setArticle(Article article) {
        // Save to SavedStateHandle to preserve through process death
        _uiState.setValue(new DataResultState.Success(article));
    }

}
