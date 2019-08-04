package com.ergou.hailiao.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by LuoCy on 2017/4/15.
 */
public class SubListView extends ListView {
    public SubListView(Context context) {
        super(context);
    }

    public SubListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SubListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
}

//    public class ListViewForScrollView extends ListView {
//
//        public ListViewForScrollView(Context context) {
//            super(context);
//        }
//
//        public ListViewForScrollView(Context context, AttributeSet attrs) {
//            super(context, attrs);
//        }
//
//        public ListViewForScrollView(Context context, AttributeSet attrs,
//                                     int defStyle) {
//            super(context, attrs, defStyle);
//
//        }
//
//        @Override
//        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
//                    MeasureSpec.AT_MOST);
//            super.onMeasure(widthMeasureSpec, expandSpec);
//        }
//
//    }


}
