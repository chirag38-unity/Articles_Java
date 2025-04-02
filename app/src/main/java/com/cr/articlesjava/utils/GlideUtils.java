package com.cr.articlesjava.utils;

import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.cr.articlesjava.R;

public class GlideUtils {

    /**
     * Utility method to load an image into an ImageView using Glide
     */
    public static void loadArticleImage(ImageView imageView, String url) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .override(Target.SIZE_ORIGINAL)
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .transform(new CenterCrop());

        Glide.with(imageView.getContext())
                .load(url)
                .apply(options)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

}
