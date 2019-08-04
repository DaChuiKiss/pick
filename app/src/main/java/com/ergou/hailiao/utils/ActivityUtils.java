package com.ergou.hailiao.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;

import java.util.List;

/**
 * <pre>
 *     desc  : Activity相关工具类
 * </pre>
 */
public class ActivityUtils {

    public static String companyInfo = "Info";

    private ActivityUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    /**
     * 读出字段
     *
     * @param ctx
     * @param key
     * @return String 如果未保存则返回null
     */
    public static String getFromSP(Context ctx, String key) {
        SharedPreferences se = ctx.getSharedPreferences(companyInfo,
                Context.MODE_PRIVATE);
        if (se == null) {
            return "";
        }
        String value = se.getString(key, "");
        if (value.equals("")) {
            return "";
        } else {
            return value;
        }
    }


    /**
     * 判断是否存在Activity
     *
     * @param context     上下文
     * @param packageName 包名
     * @param className   activity全路径类名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isActivityExists(Context context, String packageName, String className) {
        Intent intent = new Intent();
        intent.setClassName(packageName, className);
        return !(context.getPackageManager().resolveActivity(intent, 0) == null ||
                intent.resolveActivity(context.getPackageManager()) == null ||
                context.getPackageManager().queryIntentActivities(intent, 0).size() == 0);
    }

    /**
     * 保存数据
     *
     * @param title
     * @param value
     */
    public static void saveToMP(Context ctx, String title, String value) {
        SharedPreferences sp = ctx.getSharedPreferences(companyInfo, 0);
        sp.edit().putString(title, value).commit();
    }

    /**
     * 打开Activity
     *
     * @param context     上下文
     * @param packageName 包名
     * @param className   全类名
     */
    public static void launchActivity(Context context, String packageName, String className) {
        launchActivity(context, packageName, className, null);
    }

    /**
     * 打开Activity
     *
     * @param context     上下文
     * @param packageName 包名
     * @param className   全类名
     * @param bundle      bundle
     */
    public static void launchActivity(Context context, String packageName, String className, Bundle bundle) {
        context.startActivity(IntentUtils.getComponentIntent(packageName, className, bundle));
    }

    /**
     * 获取launcher activity
     *
     * @param context     上下文
     * @param packageName 包名
     * @return launcher activity
     */
    public static String getLauncherActivity(Context context, String packageName) {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo info : infos) {
            if (info.activityInfo.packageName.equals(packageName)) {
                return info.activityInfo.name;
            }
        }
        return "no " + packageName;
    }

}
