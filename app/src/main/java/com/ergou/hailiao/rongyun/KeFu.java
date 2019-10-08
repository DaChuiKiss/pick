package com.ergou.hailiao.rongyun;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.ergou.hailiao.R;
import com.ergou.hailiao.mvp.ui.activity.RedEnvelopesActivity;
import com.ergou.hailiao.utils.SobotUtils;

import io.rong.imkit.RongExtension;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imkit.plugin.IPluginRequestPermissionResultCallback;

/**
 * Created by LuoCY on 2019/8/22.
 */
public class KeFu implements IPluginModule, IPluginRequestPermissionResultCallback {
    private Context context;
    private String targetId = "";

    @Override
    public Drawable obtainDrawable(Context context) {
        return context.getResources().getDrawable(R.drawable.customer_service);
    }

    @Override
    public String obtainTitle(Context context) {
        return context.getString(R.string.customer_service);
    }

    @Override
    public void onClick(final Fragment currentFragment, final RongExtension extension) {
        context = currentFragment.getActivity().getApplicationContext();
        targetId = extension.getTargetId();
        SobotUtils.startSobot(context);
//        Intent intent = new Intent();
//        intent.setClass(context, RedEnvelopesActivity.class);
//        intent.putExtra("targetId", targetId);//
//        context.startActivity(intent);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public boolean onRequestPermissionResult(Fragment fragment, RongExtension extension, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        return true;
    }
}
