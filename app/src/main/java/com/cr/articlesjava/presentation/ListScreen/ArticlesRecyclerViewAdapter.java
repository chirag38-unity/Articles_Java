package com.cr.articlesjava.presentation.ListScreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cr.articlesjava.R;
import com.cr.articlesjava.domain.models.Article;
import com.cr.articlesjava.utils.DateUtils;
import com.cr.articlesjava.utils.GlideUtils;
import java.util.ArrayList;
import java.util.List;

public class ArticlesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_GRID = 1;
    private static final int VIEW_TYPE_LIST = 2;
    // Holds the list of articles to display.
    private List<Article> articles = new ArrayList<>();
    // Tracks whether to display items in grid or list mode.
    private boolean isGridMode = true;
    // Listener for handling click events on articles.
    private OnArticleClickListener listener;

    /**
     * Interface to handle article click events.
     */
    public interface OnArticleClickListener {
        void onArticleClick(Article article);
    }

    /**
     * Constructor that accepts a click listener.
     */
    public ArticlesRecyclerViewAdapter(OnArticleClickListener listener) {
        this.listener = listener;
    }

    /**
     * Updates the dataset and refreshes the RecyclerView.
     */
    public void setData(List<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    /**
     * Updates the view mode (Grid or List) and refreshes the layout.
     */
    public void setGridMode(boolean isGridMode) {
        if (this.isGridMode != isGridMode) {
            this.isGridMode = isGridMode;
            notifyDataSetChanged();
        }
    }

    /**
     * Creates view holders based on the current view type.
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_GRID) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article_grid, parent, false);
            return new GridViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article_list, parent, false);
            return new ListViewHolder(view);
        }
    }

    /**
     * Binds the article data to the appropriate view holder.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Article article = articles.get(position);

        if (holder instanceof GridViewHolder) {
            ((GridViewHolder) holder).bind(article);
        } else if (holder instanceof ListViewHolder) {
            ((ListViewHolder) holder).bind(article);
        }
    }

    /**
     * Returns the number of articles in the dataset.
     */
    @Override
    public int getItemCount() {
        return articles.size();
    }

    /**
     * Determines the view type (Grid or List) for each item.
     */
    @Override
    public int getItemViewType(int position) {
        return isGridMode ? VIEW_TYPE_GRID : VIEW_TYPE_LIST;
    }

    /**
     * ViewHolder for grid mode.
     */
    class GridViewHolder extends RecyclerView.ViewHolder {


        ImageView ivArticleImage;
        TextView tvArticleTitle;

        GridViewHolder(@NonNull View itemView) {
            super(itemView);
            ivArticleImage = itemView.findViewById(R.id.ivArticleImage);
            tvArticleTitle = itemView.findViewById(R.id.tvArticleTitle);
        }

        void bind(Article article) {
            tvArticleTitle.setText(article.getTitle());

            GlideUtils.loadArticleImage(ivArticleImage, article.getUrlToImage());

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onArticleClick(article);
                }
            });
        }
    }

    /**
     * ViewHolder for list mode.
     */
    class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView ivArticleImage;
        TextView tvArticleTitle;
        TextView tvArticleDate;
        TextView btnReadMore;

        ListViewHolder(@NonNull View itemView) {
            super(itemView);
            ivArticleImage = itemView.findViewById(R.id.ivArticleImage);
            tvArticleTitle = itemView.findViewById(R.id.tvArticleTitle);
            tvArticleDate = itemView.findViewById(R.id.tvArticleDate);
            btnReadMore = itemView.findViewById(R.id.btnReadMore);
        }

        void bind(Article article) {

            tvArticleTitle.setText(article.getTitle());
            tvArticleDate.setText(DateUtils.convertDateDMY(article.getPublishedAt()));

            GlideUtils.loadArticleImage(ivArticleImage, article.getUrlToImage());

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onArticleClick(article);
                }
            });

            btnReadMore.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onArticleClick(article);
                }
            });

        }
    }
}
