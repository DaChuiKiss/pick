package com.ergou.hailiao.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ergou.hailiao.R;
import com.ergou.hailiao.base.BaseActivity;
import com.ergou.hailiao.mvp.bean.RechargeRecordBean;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 充值记录详情
 */
public class RechargeRecordDetailsActivity extends BaseActivity {

    @BindView(R.id.title_share)
    TextView titleShare;
    @BindView(R.id.recharge_time)
    TextView rechargeTime;//充值时间
    @BindView(R.id.recharge_mode)
    TextView rechargeMode;//充值方式
    @BindView(R.id.recharge_money)
    TextView rechargeMoney;//充值金额
    @BindView(R.id.recharge_type)
    TextView rechargeType;//充值状态
    @BindView(R.id.remarks)
    TextView remarks;//备注
    private RechargeRecordBean rechargeRecordBean = new RechargeRecordBean();

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_recharge_record_details;
    }

    @Override
    protected void initEventAndData() {
        titleShare.setText(getResources().getText(R.string.recharge_record_details));
        Intent intent = getIntent();
        rechargeRecordBean = (RechargeRecordBean) intent.getSerializableExtra("rechargeRecordBean");//
        rechargeTime.setText(rechargeRecordBean.getCreate_time());//充值时间
        rechargeMode.setText(rechargeRecordBean.getStatus());//充值方式
        rechargeMoney.setText(rechargeRecordBean.getTop_up_amount());//充值金额
        rechargeType.setText(rechargeRecordBean.getType());//充值状态
        remarks.setText(rechargeRecordBean.getMark());//备注
    }

    @Override
    public void showError() {

    }

    @Override
    public void timeShowError() {

    }


    @OnClick({R.id.fallback})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fallback:
                finish();
                break;
        }
    }
}
