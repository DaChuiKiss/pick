package com.ergou.hailiao.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ergou.hailiao.R;
import com.ergou.hailiao.mvp.bean.HeadImgBean;
import com.ergou.hailiao.mvp.bean.RechargeBean;
import com.ergou.hailiao.utils.glide.GlideManager;

import java.util.List;

/**
 * Created by LuoCY on 2019/8/13.
 */
public class RechargAdapter extends RecyclerView.Adapter {
    private final Context context;
    private final List<RechargeBean> datas;
    private OnItemClickListener mOnItemClickListener;

    public RechargAdapter(Context context, List<RechargeBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recharg, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RechargeBean data = datas.get(position);
        MyViewHolder myHolder = (MyViewHolder) holder;
        myHolder.money.setText(data.getNum() + context.getResources().getText(R.string.cny_ch));
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mOnItemClickListener != null)//回调选中事件
                {
                    mOnItemClickListener.onItemClick(data.getNum());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView money;

        public MyViewHolder(View itemView) {
            super(itemView);
            money = (TextView) itemView.findViewById(R.id.money);
        }
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(String url);
    }
}
