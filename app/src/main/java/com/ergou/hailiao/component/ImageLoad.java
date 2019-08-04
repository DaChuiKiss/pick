package com.ergou.hailiao.component;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 *  Created by LuoCy on 2017/7/28.
 * glide加载工具类
 */

public class ImageLoad {

    public static void loadanim(Activity context, String url, ImageView iv) { //带有渐现的图片加载方式
        Glide.with(context)
                .load(url)
                .animate(android.R.anim.fade_in)  // 渐现动画
//                .placeholder(R.color.loading_img_default_color) // 占位图
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(iv);
    }

    public static void loadanim(Fragment context, String url, ImageView iv) {
        Glide.with(context)
                .load(url)
                .animate(android.R.anim.fade_in)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(iv);
    }

    public static void loadanim(Context context, String url, ImageView iv) {
        Glide.with(context)
                .load(url).centerCrop()//图片适应全屏
                .animate(android.R.anim.fade_in)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(iv);
    }

    public static void load(Context context, String url, ImageView iv) {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
        Glide.with(context).load(url).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv);
    }

    public static void load(Activity activity, String url, ImageView iv) {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
        Glide.with(activity).load(url).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv);
    }

    public static void loadAll(Context context, String url, ImageView iv) {    //不缓存，全部从网络加载
        Glide.with(context).load(url).crossFade().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
    }

    public static void loadAll(Activity activity, String url, ImageView iv) {    //不缓存，全部从网络加载
        Glide.with(activity).load(url).crossFade().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
    }

}
