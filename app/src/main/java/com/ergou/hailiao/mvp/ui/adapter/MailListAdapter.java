package com.ergou.hailiao.mvp.ui.adapter;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ergou.hailiao.R;
import com.ergou.hailiao.mvp.bean.MailListBean;
import com.ergou.hailiao.mvp.ui.adapter.recycleradapter.OnItemClickListener;
import com.ergou.hailiao.utils.dataUtils.SPUtilsData;
import com.ergou.hailiao.utils.glide.GlideManager;
import com.ergou.hailiao.widget.recyclerview.multitype.ItemViewProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LuoCY on 2019/8/14.
 */
public class MailListAdapter extends ItemViewProvider<MailListBean,
        MailListAdapter.MyViewHolder> implements View.OnClickListener {

    private OnItemClickListener<Integer> mOnItemClickListener = null;

    @NonNull
    @Override
    protected MyViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        MyViewHolder holder = new MyViewHolder(inflater.inflate(R.layout.item_mail_list, parent, false));
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onBindViewHolder(@NonNull final MyViewHolder holder,
                                    @NonNull final MailListBean mailListBean) {

        GlideManager.loadImageView(holder.itemView.getContext(), mailListBean.getUser_header_img(),
                holder.head_img, R.mipmap.ic_launcher);//头像
        holder.nick_name.setText(mailListBean.getNick_name());//昵称
        holder.hailiao_number.setText(mailListBean.getUser_id());//嗨聊号

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setClass(holder.itemView.getContext(), BillDetailsActivity.class);
//                intent.putExtra("coinname", billBean.getMark()+"");//币种
//                intent.putExtra("turn_type", billBean.getType()+"");////1转入，2转出
//                intent.putExtra("arrive_num", billBean.getArrive_num()+"");//数量
//                intent.putExtra("fee", billBean.getFee()+"");//手续费
//                intent.putExtra("residue_asset", billBean.getResidue_asset()+"");//账户余额
//                intent.putExtra("add_time", billBean.getAdd_time()+"");//账单时间
//                holder.itemView.getContext().startActivity(intent);
//            }
//        });

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(view, 3, view.getTag().toString());
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.head_img)
        ImageView head_img;
        @BindView(R.id.nick_name)
        TextView nick_name;
        @BindView(R.id.hailiao_number)
        TextView hailiao_number;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(view);
            head_img = (ImageView) view.findViewById(R.id.head_img);// 头像
            nick_name = (TextView) view.findViewById(R.id.nick_name);//昵称
            hailiao_number = (TextView) view.findViewById(R.id.hailiao_number);//嗨聊号
        }
    }

}
