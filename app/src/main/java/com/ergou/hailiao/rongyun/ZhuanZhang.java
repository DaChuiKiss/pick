package com.ergou.hailiao.rongyun;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.ergou.hailiao.R;
import com.ergou.hailiao.mvp.ui.activity.ConversationActivity;
import com.ergou.hailiao.utils.Utils;

import java.util.ArrayList;
import java.util.Locale;

import io.rong.common.RLog;
import io.rong.imkit.RongExtension;
import io.rong.imkit.RongIM;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imkit.plugin.IPluginRequestPermissionResultCallback;
import io.rong.imkit.utilities.PermissionCheckUtil;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Discussion;

/**
 * Created by LuoCY on 2019/8/22.
 */
public class ZhuanZhang implements IPluginModule , IPluginRequestPermissionResultCallback {
    private static final String TAG = "AudioPlugin";
    private ArrayList<String> allMembers;
    private Context context;
    private static final int REQEUST_CODE_RECORD_AUDIO_PERMISSION = 101;

    private Conversation.ConversationType conversationType;
    private String targetId;

    @Override
    public Drawable obtainDrawable(Context context) {
        return context.getResources().getDrawable(R.drawable.rc_ic_phone_selector);
    }

    @Override
    public String obtainTitle(Context context) {
        return context.getString(R.string.save_information);
    }

    @Override
    public void onClick(final Fragment currentFragment, final RongExtension extension) {
        context = currentFragment.getActivity().getApplicationContext();
        conversationType = extension.getConversationType();
        targetId = extension.getTargetId();
        Log.i(TAG,"---- targetId=="+targetId);
        String[] permissions = Utils.getCallpermissions();
        if (PermissionCheckUtil.checkPermissions(currentFragment.getActivity(), permissions)) {
            Log.i(TAG,"---- startAudioActivity ----");
            startAudioActivity(currentFragment, extension);
        } else {
            Log.i(TAG,"---- requestPermissionForPluginResult ----");
            extension.requestPermissionForPluginResult(permissions, REQEUST_CODE_RECORD_AUDIO_PERMISSION, this);
        }
    }

    private void startAudioActivity(Fragment currentFragment, final RongExtension extension) {
//        RongCallSession profile = RongCallClient.getInstance().getCallSession();
//        if (profile != null && profile.getStartTime() > 0) {
//            Toast.makeText(context,
//                    profile.getMediaType() == RongCallCommon.CallMediaType.AUDIO ?
//                            currentFragment.getString(R.string.rc_voip_call_audio_start_fail) :
//                            currentFragment.getString(R.string.rc_voip_call_video_start_fail),
//                    Toast.LENGTH_SHORT)
//                    .show();
//            return;
//        }
        if (!Utils.isNetworkAvailable(context)) {
            Toast.makeText(context, currentFragment.getString(R.string.search), Toast.LENGTH_SHORT).show();
            return;
        }

        if (conversationType.equals(Conversation.ConversationType.PRIVATE)) {
            Intent intent = new Intent("io.rong.intent.action.voip.MULTIAUDIO");
            intent.putExtra("conversationType", conversationType.getName().toLowerCase());
            Log.i(TAG,"---- conversationType.getName().toLowerCase() =-"+conversationType.getName().toLowerCase());
            intent.putExtra("targetId", targetId);
            intent.putExtra("callAction", RongCallAction.ACTION_OUTGOING_CALL.getName());
            Log.i(TAG,"---- callAction="+RongCallAction.ACTION_OUTGOING_CALL.getName());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Log.i(TAG,"getPackageName==="+context.getPackageName());
            intent.setPackage(context.getPackageName());
            context.startActivity(intent);
        } else if (conversationType.equals(Conversation.ConversationType.DISCUSSION)) {
            RongIM.getInstance().getDiscussion(targetId, new RongIMClient.ResultCallback<Discussion>() {
                @Override
                public void onSuccess(Discussion discussion) {
                    Intent intent = new Intent(context, ConversationActivity.class);
                    allMembers = (ArrayList<String>) discussion.getMemberIdList();
                    intent.putStringArrayListExtra("allMembers", allMembers);
                    intent.putExtra("conversationType", conversationType.getValue());
                    String myId = RongIMClient.getInstance().getCurrentUserId();
                    ArrayList<String> invited = new ArrayList<>();
                    invited.add(myId);
                    intent.putStringArrayListExtra("invitedMembers", invited);
//                    intent.putExtra("mediaType", RongCallCommon.CallMediaType.AUDIO.getValue());
                    extension.startActivityForPluginResult(intent, 110, ZhuanZhang.this);
                }

                @Override
                public void onError(RongIMClient.ErrorCode e) {
                    RLog.d(TAG, "get discussion errorCode = " + e.getValue());
                }
            });
        } else if (conversationType.equals(Conversation.ConversationType.GROUP)) {
            Intent intent = new Intent(context, ConversationActivity.class);
            String myId = RongIMClient.getInstance().getCurrentUserId();
            ArrayList<String> invited = new ArrayList<>();
            invited.add(myId);
            intent.putStringArrayListExtra("invitedMembers", invited);
            intent.putExtra("conversationType", conversationType.getValue());
            intent.putExtra("groupId", targetId);
//            intent.putExtra("mediaType", RongCallCommon.CallMediaType.AUDIO.getValue());
            extension.startActivityForPluginResult(intent, 110, this);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        Intent intent = new Intent("io.rong.intent.action.voip.MULTIAUDIO");
        ArrayList<String> userIds = data.getStringArrayListExtra("invited");
        ArrayList<String> observers=data.getStringArrayListExtra("observers");
        userIds.add(RongIMClient.getInstance().getCurrentUserId());
        intent.putExtra("conversationType", conversationType.getName().toLowerCase());
        intent.putExtra("targetId", targetId);
        intent.putExtra("callAction", RongCallAction.ACTION_OUTGOING_CALL.getName());
        intent.putStringArrayListExtra("invitedUsers", userIds);
        intent.putStringArrayListExtra("observers",observers);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage(context.getPackageName());
        context.startActivity(intent);
    }

    @Override
    public boolean onRequestPermissionResult(Fragment fragment, RongExtension extension, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (PermissionCheckUtil.checkPermissions(fragment.getActivity(), permissions)) {
            startAudioActivity(fragment, extension);
        } else {
            extension.showRequestPermissionFailedAlter(PermissionCheckUtil.getNotGrantedPermissionMsg(context, permissions, grantResults));
        }
        return true;
    }
}
