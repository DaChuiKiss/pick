package com.ergou.hailiao.mvp.ui.adapter.recycleradapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.ergou.hailiao.R;
import com.ergou.hailiao.utils.ConstUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luoy on 2016/7/18.
 * 上拉加载基类,T数据类型,VH绑定的ViewHolder
 */
public abstract class BaseLoadMoreAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {

    public static final int TYPE_FOOTER = Integer.MAX_VALUE;
    public static final int ITEM_TYPE = 0;
    private final List<T> mList = new ArrayList<>();
    private boolean hasMore = true, isLoading = true;


    public static class FooterViewHolder extends RecyclerView.ViewHolder {

        public final ProgressBar progressBar;
        public final TextView textView;

        public FooterViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_view);
            textView = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {   // 滑动到底部的样式
            if (hasMore) {
                if (mList.size() == 0) {
                    ((FooterViewHolder) holder).progressBar.setVisibility(View.GONE);
                    ((FooterViewHolder) holder).textView.setVisibility(View.GONE);
                } else {
                    ((FooterViewHolder) holder).progressBar.setVisibility(View.VISIBLE);
                    ((FooterViewHolder) holder).textView.setVisibility(View.VISIBLE);
                    ((FooterViewHolder) holder).textView.setText("正在加载...");
                }

            } else {
                ((FooterViewHolder) holder).textView.setVisibility(View.VISIBLE);
                ((FooterViewHolder) holder).progressBar.setVisibility(View.GONE);
                ((FooterViewHolder) holder).textView.setText("已全部加载");
            }
        } else {  // 否则是显示的条目
            onBindItemViewHolder((VH) holder, mList.get(position), position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) { //  为尾部返回尾部布局
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_footer_view, parent, false);
            return new FooterViewHolder(view);
        } else { //  否则创建绑定条目布局
            return onCreateItemViewHolder(parent, viewType);
        }
    }

    // 数据源条目布局
    public abstract void onBindItemViewHolder(VH holder, T data, int position);

    public abstract VH onCreateItemViewHolder(ViewGroup parent, int viewType);

    //  增加头部和尾部是根据这个方法来判断的
    @Override
    public int getItemViewType(int position) {
        if (position == mList.size()) {
            return TYPE_FOOTER;
        } else {
            return ITEM_TYPE;
        }
    }

    // 条目个数为数据源数量+尾部
    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    // 上拉加载把数据添加到数据源
    public void addLoadMoreData(List<T> data) {
        if (data == null) return;
        this.mList.addAll(data);
        this.hasMore = data.size() == ConstUtils.PAGE_SIZE;
        this.isLoading = false;
        notifyDataSetChanged();
    }

    // 下拉刷新把数据源清空然后替换新的数据源
    public void addRefreshData(List<T> data) {
        if (data == null) return;
        this.mList.clear();
        this.mList.addAll(data);
        this.hasMore = data.size() == ConstUtils.PAGE_SIZE;
        this.isLoading = false;
        notifyDataSetChanged();
    }

    // 是否正在加载或已全部加载
    public boolean canLoadMore() {
        return hasMore && !isLoading;
    }

}
