package com.ergou.hailiao.mvp.ui.adapter.recycleradapter;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * RecyclerView无法添加onItemClickListener最佳的高效解决方案
 * 如何使用：
 * recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(recyclerView) {
 *
 * @Override public void onItemClick(RecyclerView.ViewHolder vh, int Position) {
 * //item点击事件
 * }
 * });
 * 这是recyclerview条目点击的高效写法，目前有以下问题:
 * 这种写法有以下问题:
 * 设置selector无效
 * item 布局里有控件有点击效果无效
 */
public abstract class OnRecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    //GestureDetectorCompat 就是处理手势的类：手势探测器，它比GestureDetector能更好兼容低版本的api，但使用方法是一致的
    private GestureDetectorCompat compat;
    private RecyclerView recyclerView;

    public OnRecyclerItemClickListener(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        compat = new GestureDetectorCompat(recyclerView.getContext(), new ItemTouchHelperGestureListener());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        compat.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        compat.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {
        //如果是按下的时间超过瞬间，而且在按下的时候没有松开或者是拖动的，那么onShowPress就会执行
        @Override
        public void onShowPress(MotionEvent e) {
            super.onShowPress(e);
        }

        //单击事件。用来判定该次点击是SingleTap而不是DoubleTap，
        //如果连续点击两次就是DoubleTap手势，如果只点击一次，
        //系统等待一段时间后没有收到第二次点击则判定该次点击为SingleTap而不是DoubleTap，
        //然后触发SingleTapConfirmed事件
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return super.onSingleTapConfirmed(e);
        }

        //双击间隔中发生的动作。指触发onDoubleTap以后，在双击之间发生的其它动作
        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return super.onDoubleTapEvent(e);
        }

        //滑屏，用户按下触摸屏、快速移动后松开
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        //在屏幕上拖动事件
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        //用户按下屏幕就会触发
        @Override
        public boolean onDown(MotionEvent e) {
            return super.onDown(e);
        }

        //双击事件
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return super.onDoubleTap(e);
        }

        //长按触摸屏，超过一定时长，就会触发这个事件
        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }

        //一次单独的轻击抬起操作,也就是轻击一下屏幕，就是普通点击事件
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
//            findChildViewUnder()，我们可以通过这个方法获得点击的item
            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null) {
//                getChildViewHolder()，可以获得该item的ViewHolder
                RecyclerView.ViewHolder vh = recyclerView.getChildViewHolder(child);
                if (vh.getItemViewType() != BaseLoadMoreAdapter.TYPE_FOOTER)
                    onItemClick(vh, vh.getLayoutPosition());
            }
            return super.onSingleTapUp(e);
        }
    }

    public abstract void onItemClick(RecyclerView.ViewHolder vh, int Position);
}
