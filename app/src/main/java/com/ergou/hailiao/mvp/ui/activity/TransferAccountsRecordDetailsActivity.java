package com.ergou.hailiao.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ergou.hailiao.R;
import com.ergou.hailiao.base.BaseActivity;
import com.ergou.hailiao.mvp.bean.TransferAccountsRecordBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 转账记录详情
 */
public class TransferAccountsRecordDetailsActivity extends BaseActivity {

    @BindView(R.id.title_share)
    TextView titleShare;
    @BindView(R.id.remarks)
    TextView remarks;//备注
    @BindView(R.id.transfer_accounts_id)
    TextView transferAccountsId;//转账账号
    @BindView(R.id.transfer_accounts_time)
    TextView transferAccountsTime;//转账时间
    @BindView(R.id.transfer_accounts_number)
    TextView transferAccountsNumber;//转账金额
    @BindView(R.id.transfer_accounts_type)
    TextView transferAccountsType;//转账状态
    private TransferAccountsRecordBean transferAccountsRecordBean = new TransferAccountsRecordBean();

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_transfer_accounts_record_details;
    }

    @Override
    protected void initEventAndData() {
        titleShare.setText(getResources().getText(R.string.transfer_accounts_record_details));
        Intent intent = getIntent();
        transferAccountsRecordBean = (TransferAccountsRecordBean) intent.getSerializableExtra("transferAccountsRecordBean");//
        transferAccountsId.setText(transferAccountsRecordBean.getReceive_member_id());//转账账号
        transferAccountsTime.setText(transferAccountsRecordBean.getCreate_time());//转账时间
        transferAccountsNumber.setText(transferAccountsRecordBean.getTransfer_amount());//转账金额
        transferAccountsType.setText(transferAccountsRecordBean.getStatus());//转账状态
        remarks.setText(transferAccountsRecordBean.getMark());//备注
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
