package com.ergou.hailiao.mvp.ui.adapter;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ergou.hailiao.R;
import com.ergou.hailiao.mvp.bean.CashWithdrawalRecordBean;
import com.ergou.hailiao.mvp.ui.adapter.recycleradapter.OnItemClickListener;
import com.ergou.hailiao.widget.recyclerview.multitype.ItemViewProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LuoCY on 2019/8/14.
 */
public class CashWithdrawalRecordAdapter extends ItemViewProvider<CashWithdrawalRecordBean,
        CashWithdrawalRecordAdapter.MyViewHolder> implements View.OnClickListener {

    private OnItemClickListener<Integer> mOnItemClickListener = null;

    @NonNull
    @Override
    protected MyViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        MyViewHolder holder = new MyViewHolder(inflater.inflate(R.layout.item_cash_withdrawal_record, parent, false));
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onBindViewHolder(@NonNull final MyViewHolder holder,
                                    @NonNull final CashWithdrawalRecordBean cashWithdrawalRecordBean) {

        holder.time.setText(cashWithdrawalRecordBean.getCreate_time());//时间
        holder.cash_withdrawal_money.setText(cashWithdrawalRecordBean.getWithdrawal_amount());//充值金额
        holder.recharge_type.setText(cashWithdrawalRecordBean.getStatus());//充值状态

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
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.cash_withdrawal_money)
        TextView cash_withdrawal_money;
        @BindView(R.id.recharge_type)
        TextView recharge_type;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(view);
            time = (TextView) view.findViewById(R.id.time);//时间
            cash_withdrawal_money = (TextView) view.findViewById(R.id.cash_withdrawal_money);//提现金额
            recharge_type = (TextView) view.findViewById(R.id.recharge_type);//充值状态
        }
    }

}
