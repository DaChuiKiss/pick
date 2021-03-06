package com.ergou.hailiao.mvp.ui.adapter.recycleradapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by Luocy on 2016/8/4.
 * 滑动删除和拖拽排序的接口
 */
public interface ItemTouchHelperAdapter {
    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);

    /**
     * Created by Luocy on 2016/7/8.
     * 监听RecyclerView滑动到了底部调用加载
     */
    abstract class OnRcvScrollListener extends RecyclerView.OnScrollListener {

        public static enum LAYOUT_MANAGER_TYPE {
            LINEAR,
            GRID,
            STAGGERED_GRID
        }

        /**
         * layoutManager的类型（枚举）
         */
        protected LAYOUT_MANAGER_TYPE layoutManagerType;

        /**
         * 最后一个的位置
         */
        private int[] lastPositions;

        /**
         * 最后一个可见的item的位置
         */
        private int lastVisibleItemPosition;

        /**
         * 当前滑动的状态
         */
        private int currentScrollState = 0;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            //  int lastVisibleItemPosition = -1;
            if (layoutManagerType == null) {
                if (layoutManager instanceof LinearLayoutManager) {
                    layoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR;
                } else if (layoutManager instanceof GridLayoutManager) {
                    layoutManagerType = LAYOUT_MANAGER_TYPE.GRID;
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    layoutManagerType = LAYOUT_MANAGER_TYPE.STAGGERED_GRID;
                } else {
                    throw new RuntimeException("Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
                }
            }

            switch (layoutManagerType) {
                case LINEAR:
                    lastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                            .findLastVisibleItemPosition();
                    break;
                case GRID:
                    lastVisibleItemPosition = ((GridLayoutManager) layoutManager)
                            .findLastVisibleItemPosition();
                    break;
                case STAGGERED_GRID:
                    StaggeredGridLayoutManager staggeredGridLayoutManager
                            = (StaggeredGridLayoutManager) layoutManager;
                    if (lastPositions == null) {
                        lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                    }
                    staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                    lastVisibleItemPosition = findMax(lastPositions);
                    break;
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition + 1 == recyclerView.getAdapter().getItemCount()) {
                onLoadMore();
            }
        }


        public abstract void onLoadMore();

        private int findMax(int[] lastPositions) {
            int max = lastPositions[0];
            for (int value : lastPositions) {
                if (value > max) {
                    max = value;
                }
            }
            return max;
        }
    }
}
