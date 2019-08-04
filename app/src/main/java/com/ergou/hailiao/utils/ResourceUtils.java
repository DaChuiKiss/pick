package com.ergou.hailiao.utils;


import com.ergou.hailiao.app.App;

/**
 * Created by LuoCY on 2017/7/283 0013.
 */

public class ResourceUtils {
    public static String getResStr(int strId){
        String str= App.getInstance().getResources().getString(strId);
        return str==null?"":str;
    }
}
