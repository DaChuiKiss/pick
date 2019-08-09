package com.ergou.hailiao.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * @author xiepeixuan
 * @date 2018/5/11
 */
public class UserInfoSPUtils {
    private static UserInfoSPUtils utils;
    private SharedPreferences sp;


    public static UserInfoSPUtils getInstance() {
        return getInstance(Context.MODE_PRIVATE);
    }

    public static UserInfoSPUtils getInstance(final int mode) {
        if (utils == null) {
            utils = new UserInfoSPUtils(mode);
        }
        return utils;
    }

    private UserInfoSPUtils(final int mode) {
        sp = Utils.getApp().getSharedPreferences("UserInfo", mode);
    }

    public void put(@NonNull final String key, final String value) {
        put(key, value, false);
    }

    public void put(@NonNull final String key, final String value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putString(key, value).commit();
        } else {
            sp.edit().putString(key, value).apply();
        }
    }


    public String getString(@NonNull final String key) {
        return getString(key, "");
    }


    public String getString(@NonNull final String key, final String defaultValue) {
        return sp.getString(key, defaultValue);
    }


    public void clear() {
        clear(false);
    }


    public void clear(final boolean isCommit) {
        if (isCommit) {
            sp.edit().clear().commit();
        } else {
            sp.edit().clear().apply();
        }
    }
}
