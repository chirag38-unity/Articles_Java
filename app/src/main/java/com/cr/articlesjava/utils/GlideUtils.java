package com.cr.articlesjava.utils;

import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

public class GlideUtils {

    /**
     * Utility method to load an image into an ImageView using Glide
     */
    public static void loadArticleImage(ImageView imageView, String url) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .override(Target.SIZE_ORIGINAL)
                .transform(new CenterCrop());

        Glide.with(imageView.getContext())
                .load(url)
                .apply(options)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

}
