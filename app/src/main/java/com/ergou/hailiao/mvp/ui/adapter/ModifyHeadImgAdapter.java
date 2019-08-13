package com.ergou.hailiao.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ergou.hailiao.R;
import com.ergou.hailiao.mvp.bean.HeadImgBean;
import com.ergou.hailiao.utils.glide.GlideManager;

import java.util.List;

/**
 * Created by LuoCY on 2019/8/13.
 */
public class ModifyHeadImgAdapter extends RecyclerView.Adapter {
    private final Context context;
    private final List<HeadImgBean> datas;
    private OnItemClickListener mOnItemClickListener;

    public ModifyHeadImgAdapter(Context context, List<HeadImgBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_modify_head_img, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final HeadImgBean data = datas.get(position);
        MyViewHolder myHolder = (MyViewHolder) holder;
        GlideManager.loadImageView(myHolder.itemView.getContext(), data.getImg_url(),
                myHolder.headImg, R.mipmap.ic_launcher);//头像
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mOnItemClickListener != null)//回调选中事件
                {
                    mOnItemClickListener.onItemClick(data.getImg_url());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView headImg;

        public MyViewHolder(View itemView) {
            super(itemView);
            headImg = (ImageView) itemView.findViewById(R.id.head_img);
        }
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(String url);
    }
}
