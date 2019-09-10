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
import com.ergou.hailiao.mvp.bean.RedEnvelopeGrabBean;
import com.ergou.hailiao.mvp.ui.adapter.recycleradapter.OnItemClickListener;
import com.ergou.hailiao.utils.StringUtils;
import com.ergou.hailiao.utils.glide.GlideManager;
import com.ergou.hailiao.widget.recyclerview.multitype.ItemViewProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

/**
 * Created by LuoCY on 2019/8/14.
 */
public class RedEnvelopeGrabAdapter extends ItemViewProvider<RedEnvelopeGrabBean.AllRobBean,
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
                                    @NonNull final RedEnvelopeGrabBean.AllRobBean allRobBean) {

        GlideManager.loadImageView(holder.itemView.getContext(), allRobBean.getUser_header_img(),
                holder.head_img, R.mipmap.ic_launcher);//头像
        holder.nick_name.setText(allRobBean.getNick_name());//昵称
        holder.time.setText(allRobBean.getCreate_time());//时间
        holder.money.setText(allRobBean.getMoney());//金额
        if (allRobBean.getIs_bomb().equals("2")) {//	是否中雷(1:否;2:是)
            holder.lei_img.setVisibility(View.VISIBLE);
        } else {
            holder.lei_img.setVisibility(View.GONE);
        }
        if (!StringUtils.isEmpty(allRobBean.getLuck())) {
            if (allRobBean.getLuck().equals("1")) {//1为最佳
                holder.king_img.setVisibility(View.VISIBLE);
                holder.text.setVisibility(View.VISIBLE);
            } else {
                holder.king_img.setVisibility(View.GONE);
                holder.text.setVisibility(View.INVISIBLE);
            }
        }
//        holder.text.setText(allRobBean.get());//说明
//        holder.king_img.setText(allRobBean.getCreate_time());//手气最好
//        holder.lei_img.setText(allRobBean.getCreate_time());//雷

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
