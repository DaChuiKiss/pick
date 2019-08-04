package com.ergou.hailiao.mvp.ui.adapter.recycleradapter;

import android.view.View;

/**
 */

public   interface OnItemClickListener<T> {

    void onItemClick(View view, T data, String tag);
}
