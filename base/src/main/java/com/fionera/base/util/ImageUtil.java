package com.fionera.base.util;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fionera.base.R;

/**
 * ImageUtil
 * Created by fionera on 16-6-15.
 */
@SuppressWarnings("unused")
public class ImageUtil {

    public static void loadImage(String url, ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(R.drawable.iv_default);
            return;
        }
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

    public static void loadImageWithoutCache(String url, ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(R.drawable.iv_default);
            return;
        }
        Glide.with(imageView.getContext()).load(url).skipMemoryCache(true).into(imageView);
    }

    public static void loadImage(String url, ImageView imageView, int holderResId) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(R.drawable.iv_default);
            return;
        }
        Glide.with(imageView.getContext()).load(url).placeholder(holderResId).into(imageView);
    }

    public static void loadImageWithBitmap(String url, ImageView imageView, int holderResId) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(R.drawable.iv_default);
            return;
        }
        Glide.with(imageView.getContext()).load(url).asBitmap().placeholder(holderResId).into(imageView);
    }

    public static void loadImageWithoutCache(String url, ImageView imageView, int holderResId) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(R.drawable.iv_default);
            return;
        }
        Glide.with(imageView.getContext()).load(url).skipMemoryCache(true).placeholder(holderResId)
                .into(imageView);
    }

    public static void loadThumbnail(String url, ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(R.drawable.iv_default);
            return;
        }
        Glide.with(imageView.getContext()).load(url).dontAnimate().thumbnail(0.3f).into(
                imageView);
    }

    public static void loadThumbnail(String url, ImageView imageView,int placeHolder) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(R.drawable.iv_default);
            return;

        }
        Glide.with(imageView.getContext()).load(url).dontAnimate().placeholder(placeHolder).into(
                imageView);
    }

    public static void loadAvatarImage(String url, ImageView imageView, int holderResId) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(R.drawable.iv_avatar_default);
            return;
        }
        Glide.with(imageView.getContext()).load(url).dontAnimate().placeholder(holderResId).into(
                imageView);
    }
}
