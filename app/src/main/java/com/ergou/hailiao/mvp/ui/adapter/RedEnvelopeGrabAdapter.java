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
import com.ergou.hailiao.utils.glide.GlideManager;
import com.ergou.hailiao.widget.recyclerview.multitype.ItemViewProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

/**
 * Created by LuoCY on 2019/8/14.
 */
public class RedEnvelopeGrabAdapter extends ItemViewProvider<MailListBean,
        RedEnvelopeGrabAdapter.MyViewHolder> implements View.OnClickListener {

    private OnItemClickListener<Integer> mOnItemClickListener = null;

    @NonNull
    @Override
    protected MyViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        MyViewHolder holder = new MyViewHolder(inflater.inflate(R.layout.item_red_envelope_grab, parent, false));
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onBindViewHolder(@NonNull final MyViewHolder holder,
                                    @NonNull final MailListBean mailListBean) {

        GlideManager.loadCircleImageView(holder.itemView.getContext(), mailListBean.getUser_header_img(),
                holder.head_img, R.mipmap.ic_launcher);//头像
        holder.nick_name.setText(mailListBean.getNick_name());//昵称
//        holder.hailiao_number.setText(mailListBean.getUser_id());//嗨聊号

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RongIM.getInstance().startConversation(holder.itemView.getContext(), Conversation
                        .ConversationType.PRIVATE , mailListBean.getUser_id(), mailListBean.getNick_name());
            }
        });

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
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.money)
        TextView money;
        @BindView(R.id.text)
        TextView text;
        @BindView(R.id.king_img)
        TextView king_img;
        @BindView(R.id.lei_img)
        TextView lei_img;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(view);
            head_img = (ImageView) view.findViewById(R.id.head_img);// 头像
            nick_name = (TextView) view.findViewById(R.id.nick_name);//昵称
            time = (TextView) view.findViewById(R.id.time);//时间
            money = (TextView) view.findViewById(R.id.money);//金额
            text = (TextView) view.findViewById(R.id.text);//说明
            king_img = (TextView) view.findViewById(R.id.king_img);//手气最好
            lei_img = (TextView) view.findViewById(R.id.lei_img);//雷
        }
    }

}
