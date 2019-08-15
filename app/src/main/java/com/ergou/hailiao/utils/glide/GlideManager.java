package com.ergou.hailiao.utils.glide;

import android.content.Context;
import android.text.Layout;
import android.widget.ImageView;
import android.widget.TableLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 *
 */
public class GlideManager {
    /**
     * 基本的加载图片
     *
     * @param context
     * @param url                    加载图片的url
     * @param imageView              要加载的ImageView
     * @param login_register_btn_add
     */
    public static void loadImageView(Context context, String url, ImageView imageView, int login_register_btn_add) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .centerCrop()
                .animate(android.R.anim.fade_in)  // 自己设置渐现动画可以解决加载图片变形问题
                //.placeholder(defaultImg)  //设置正在加载中显示的图片,需要的话自己加上 defaultImg可以为R.drawable.
                //.error(defaultImg)        // 加载失败的时候显示的图片,需要的话自己加上
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .transform(new GlideRoundTransform(context, 0))
                .into(imageView);
    }

    /**
     * 加载圆角图片
     *
     * @param context
     * @param url       加载图片的url
     * @param imageView 要加载的ImageView
     * @param roundSize 圆角的大小
     */
    public static void loadRoundImageView(Context context, String url, ImageView imageView, int roundSize) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .centerCrop()
                .animate(android.R.anim.fade_in)  // 自己设置渐现动画可以解决加载图片变形问题
                .placeholder(roundSize)  //设置正在加载中显示的图片,需要的话自己加上 defaultImg可以为R.drawable.
                .error(roundSize)        // 加载失败的时候显示的图片,需要的话自己加上
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .transform(new GlideRoundTransform(context, roundSize))
                .into(imageView);
    }

    /**
     * 加载圆角图片
     *
     * @param context
     * @param url       加载图片的url
     * @param imageView 要加载的ImageView
     */
    public static void loadCircleImageView(Context context, String url, ImageView imageView, int defaultImg) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .centerCrop()
                .animate(android.R.anim.fade_in)  // 自己设置渐现动画可以解决加载图片变形问题
                //.placeholder(defaultImg)  //设置正在加载中显示的图片,需要的话自己加上 defaultImg可以为R.drawable.
                //.error(defaultImg)        // 加载失败的时候显示的图片,需要的话自己加上
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .transform(new GlideRoundTransform(context, 6))
                .into(imageView);
    }

}
