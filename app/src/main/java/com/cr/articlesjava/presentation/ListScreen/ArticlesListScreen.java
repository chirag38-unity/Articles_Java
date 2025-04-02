package com.cr.articlesjava.presentation.ListScreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cr.articlesjava.App;
import com.cr.articlesjava.R;
import com.cr.articlesjava.databinding.ActivityArticlesListScreenBinding;
import com.cr.articlesjava.di.ViewModelFactories.ArticlesViewModelFactory;
import com.cr.articlesjava.domain.models.Article;
import com.cr.articlesjava.domain.models.NewsResponse;
import com.cr.articlesjava.presentation.DetailScreen.ArticleDetailScreen;
import com.cr.articlesjava.utils.DataError;
import com.cr.articlesjava.utils.DataResultState;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class ArticlesListScreen extends AppCompatActivity {

    @Inject
    ArticlesViewModelFactory viewModelFactory;
    private ArticlesListScreenViewModel viewModel;
    private final CompositeDisposable disposable = new CompositeDisposable();
    ActivityArticlesListScreenBinding binding;
    private ArticlesRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);    // Enable edge-to-edge UI
        binding = ActivityArticlesListScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Apply window insets for better UI handling
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupPage();    // Initialize UI components and ViewModel

    }

    /**
     * Sets up the UI components and initializes the ViewModel.
     */
    private void setupPage() {

        App.getAppComponent().inject(this);    // Inject dependencies

        // Initialize RecyclerView Adapter with click listener
        adapter = new ArticlesRecyclerViewAdapter(
                new ArticlesRecyclerViewAdapter.OnArticleClickListener() {
                    @Override
                    public void onArticleClick(Article article) {
                        Intent intent = new Intent(ArticlesListScreen.this, ArticleDetailScreen.class);
                        intent.putExtra("article", article);
                        startActivity(intent);
                    }
                }
        );

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        // Initialize ViewModel using factory
        viewModel = new ViewModelProvider(this, viewModelFactory).get(ArticlesListScreenViewModel.class);

        // Observe grid mode changes
        viewModel.getIsGridMode().observe(this, isGridMode -> {
            adapter.setGridMode(isGridMode);
            if (isGridMode) {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
                binding.recyclerView.setLayoutManager(gridLayoutManager);
                binding.btnToggleView.setImageResource(R.drawable.grid_icon);
            } else {
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
                binding.btnToggleView.setImageResource(R.drawable.lists_icon);
            }
        });

        binding.btnToggleView.setOnClickListener(v -> viewModel.toggleViewMode());
        binding.retryButton.setOnClickListener(v -> viewModel.retry());

        // Observe result state and update UI accordingly
        disposable.add(
                viewModel.getResultState()
                        .subscribe( resultState -> {
                            Log.d("ArticlesListScreen", "ResultState: " + resultState);
                                if (resultState instanceof DataResultState.Loading) {
                                    showLoading();
                                    hideError();
                                } else if (resultState instanceof DataResultState.Success) {
                                    hideLoading();
                                    NewsResponse response = ((DataResultState.Success<NewsResponse>) resultState).getData();
                                    if (response != null && response.getArticles() != null) {
                                        Log.d("ArticlesListScreen", "Articles count: " + response.getArticles().size());
                                        adapter.setData(response.getArticles());
                                    }
                                } else if (resultState instanceof DataResultState.Error) {
                                    hideLoading();
                                    showError(((DataResultState.Error<NewsResponse>) resultState).getError());
                                }
                            }
                        )
        );

        // Observe internet connectivity status
        viewModel.getConnectivityStatus().observe(this, isConnected -> {
            Log.e("ArticlesListScreen", "Observing connectivity -> " + isConnected);
        });

    }

    private void showLoading() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.recyclerView.setVisibility(View.GONE);
        binding.errorLayout.setVisibility(View.GONE);
    }

    private void hideLoading() {
        binding.progressBar.setVisibility(View.GONE);
        binding.recyclerView.setVisibility(View.VISIBLE);
    }

    private void showError(DataError error) {
        binding.recyclerView.setVisibility(View.GONE);
        // Set error message based on error type
        if (error == DataError.NetworkError.NO_INTERNET) {
            binding.tvError.setText("Network error.");
            binding.tvErrorMessage.setText("Please check your connection and try again.");
        } else if (error == DataError.NetworkError.SERVER_ERROR) {
            binding.tvError.setText("Server error.");
            binding.tvErrorMessage.setText("Please try again later.");
        } else {
            binding.tvError.setText("An error occurred while loading articles.");
            binding.tvErrorMessage.setText("Please try again later.");
        }
        binding.errorLayout.setVisibility(View.VISIBLE);
    }

    private void hideError() {
        binding.errorLayout.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}