package com.ergou.hailiao.mvp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;

/**
 *
 */

public class PictureCarouselLoopAdapter extends LoopPagerAdapter {
    private String[] imgs;
    private Context contex;

    public PictureCarouselLoopAdapter(RollPagerView viewPager, String[] imgs, Context contex) {
        super(viewPager);
        this.imgs = imgs;
        this.contex = contex;
    }

    @Override
    public View getView(ViewGroup container, final int position) {
        ImageView view = new ImageView(container.getContext());
        Glide.with(contex).load(imgs[position]).into(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View onView) {//图片点击跳转
//                ToastUtils.showLongToast(contex, "第" + position + "图片");/**/
            }
        });
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    @Override
    public int getRealCount() {

        return imgs.length;
    }
}
