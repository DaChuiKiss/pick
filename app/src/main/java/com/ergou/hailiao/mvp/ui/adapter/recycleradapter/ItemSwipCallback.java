package com.ergou.hailiao.mvp.ui.adapter.recycleradapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * 拖拽排序和滑动删除
 */
public class ItemSwipCallback extends ItemTouchHelper.Callback {

    private final ItemTouchHelperAdapter mAdapter;


    public ItemSwipCallback(ItemTouchHelperAdapter adapter) {

        mAdapter = adapter;

    }

    // 长按进入拖拽
    @Override
    public boolean isLongPressDragEnabled() {
        return super.isLongPressDragEnabled();
    }

    // 启用滑动操作
    @Override
    public boolean isItemViewSwipeEnabled() {

        return super.isItemViewSwipeEnabled();
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;     // 长按后支持上下拖动

        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;  // 支持左右滑动删除
        // 如果是尾部或者头部那么不允许拖住或者删除
        if (viewHolder.getItemViewType() == BaseLoadMoreAdapter.TYPE_FOOTER) {
            return makeMovementFlags(0, 0);
        }
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    // 拖拽完成，viewHolder拖拽的view,target目标的view
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        // 回调拖拽事件
        mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    // 滑动完成
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        // 回调滑动事件
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }
}
