package com.ergou.hailiao.utils.dataUtils;


import com.ergou.hailiao.utils.UserInfoSPUtils;

public class SPUtilsData {
    /**
     * 获取用户token
     */
    public static String getToken() {
        String ticket = UserInfoSPUtils.getInstance().getString("token");
        return ticket;
    }
}
