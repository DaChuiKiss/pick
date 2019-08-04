package com.ergou.hailiao.utils;

import android.content.Context;

import com.ergou.hailiao.mvp.ui.config.Config;


public class UserManager {
	private static UserManager manager;

	private UserManager() {
	}

	public static UserManager getInstance() {
		if (manager == null) {
			manager = new UserManager();
		}
		return manager;
	}

	public String getAccountId(Context context) {
		SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil();
		return sharedPreferencesUtil.getString(context, Config.ACCOUNTID);
	}



	/**
	 * 头像url
	 * 
	 * @param HeadUrl
	 */
	public void setHeadUrl(Context context, String HeadUrl) {
		SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil();
		sharedPreferencesUtil.putString(context, Config.HEADURL, HeadUrl);
	}

	public String getHeadUrl(Context context) {
		SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil();
		return sharedPreferencesUtil.getString(context, Config.HEADURL);
	}

	/**
	 * 用户昵称
	 *
	 * @param NickName
	 */
	public void setNickName(Context context, String NickName) {
		SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil();
		sharedPreferencesUtil.putString(context, Config.NICKNAME, NickName);
	}

	public String getNickName(Context context) {
		SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil();
		return sharedPreferencesUtil.getString(context, Config.NICKNAME);
	}


	/**
	 * 清空用户数据
	 */
	public void clearInfo(Context context) {
		setHeadUrl(context, "");
		setNickName(context, "");

	}

}