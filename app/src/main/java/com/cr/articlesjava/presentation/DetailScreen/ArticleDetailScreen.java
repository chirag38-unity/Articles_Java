package com.cr.articlesjava.presentation.DetailScreen;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import com.cr.articlesjava.App;
import com.cr.articlesjava.R;
import com.cr.articlesjava.databinding.ActivityArticleDetailScreenBinding;
import com.cr.articlesjava.di.ViewModelFactories.ArticleDetailViewModelFactory;
import com.cr.articlesjava.domain.models.Article;
import com.cr.articlesjava.utils.DataResultState;
import com.cr.articlesjava.utils.GlideUtils;
import javax.inject.Inject;

public class ArticleDetailScreen extends AppCompatActivity {

    @Inject
    ArticleDetailViewModelFactory viewModelFactory;
    private ArticleDetailViewModel viewModel;
    private ActivityArticleDetailScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);    // Enable edge-to-edge UI
        binding = ActivityArticleDetailScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Apply window insets for better UI handling
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI components and ViewModel
        setupPage();
    }

    /**
     * Sets up the UI components and initializes the ViewModel.
     */
    private void setupPage() {

        App.getAppComponent().inject(this); // Inject dependencies

        // Initialize ViewModel using factory
        viewModel = new ViewModelProvider(this, viewModelFactory).get(ArticleDetailViewModel.class);

        // Retrieve article data from intent
        Article articleModel = (Article) getIntent().getParcelableExtra("article");
        if (articleModel != null) {
            viewModel.setArticle(articleModel);
        } else {
            // Close the activity if no article data is found
            finish();
            Toast.makeText(this, "Error reading article", Toast.LENGTH_LONG).show();
        }


        binding.btnBack.setOnClickListener(v -> finish());

        // Observe ViewModel state and update UI accordingly
        viewModel.getUiState().observe(this, state -> {
            if (state instanceof DataResultState.Success) {
                binding.progressBar.setVisibility(View.GONE);
                binding.articleLayout.setVisibility(View.VISIBLE);

                Article article = ((DataResultState.Success<Article>) state).getData();
                binding.tvArticleTitle.setText(article.getTitle());
                binding.tvArticleTime.setText(article.getPublishedAt());
                binding.tvArticleContent.setText(article.getContent());

                GlideUtils.loadArticleImage(binding.ivArticleImage, article.getUrlToImage());

            } else if (state instanceof DataResultState.Error)  {
                finish();
                Toast.makeText(this, "Error reading article", Toast.LENGTH_LONG).show();
            }
        });

    }
}