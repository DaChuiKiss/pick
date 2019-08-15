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
import com.ergou.hailiao.mvp.bean.GameBean;
import com.ergou.hailiao.mvp.bean.MailListBean;
import com.ergou.hailiao.mvp.ui.adapter.recycleradapter.OnItemClickListener;
import com.ergou.hailiao.utils.glide.GlideManager;
import com.ergou.hailiao.widget.recyclerview.multitype.ItemViewProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LuoCY on 2019/8/14.
 */
public class GameAdapter extends ItemViewProvider<GameBean.GroupBean,
        GameAdapter.MyViewHolder> implements View.OnClickListener {

    private OnItemClickListener<Integer> mOnItemClickListener = null;

    @NonNull
    @Override
    protected MyViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        MyViewHolder holder = new MyViewHolder(inflater.inflate(R.layout.item_game, parent, false));
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onBindViewHolder(@NonNull final MyViewHolder holder,
                                    @NonNull final GameBean.GroupBean gameBean) {

        GlideManager.loadImageView(holder.itemView.getContext(), gameBean.getGroup_img(),
                holder.head_img, R.mipmap.ic_launcher);//头像
        holder.group_chat_name.setText(gameBean.getGroup_name());//群聊名
        holder.group_chat_number.setText(gameBean.getGroup_id());//群聊号

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
        @BindView(R.id.group_chat_name)
        TextView group_chat_name;
        @BindView(R.id.hailiao_number)
        TextView group_chat_number;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(view);
            head_img = (ImageView) view.findViewById(R.id.head_img);// 头像
            group_chat_name = (TextView) view.findViewById(R.id.group_chat_name);//群聊名
            group_chat_number = (TextView) view.findViewById(R.id.group_chat_number);//群聊号
        }
    }

}
