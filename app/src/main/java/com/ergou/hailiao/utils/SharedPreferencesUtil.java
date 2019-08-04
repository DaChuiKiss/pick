package com.ergou.hailiao.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtil {

	private SharedPreferences preferences;
	private static final String spFileName = "BZWelcomePage";
	public static final String FIRST_OPEN = "first_open";
	
	
	public  SharedPreferences putString(Context context,String key, String value) {
		preferences = context.getSharedPreferences("preference",
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
		return preferences;
	}

	public String getString(Context context,String key) {
		preferences = context.getSharedPreferences("preference",
				Context.MODE_PRIVATE);
		String string = preferences.getString(key, "");
		return string;
	}

	public static Boolean getBoolean(Context context, String strKey,
									 Boolean strDefault) {//strDefault	boolean: Value to return if this preference does not exist.
		SharedPreferences setPreferences = context.getSharedPreferences(
				spFileName, Context.MODE_PRIVATE);
		Boolean result = setPreferences.getBoolean(strKey, strDefault);
		return result;
	}

	public static void putBoolean(Context context, String strKey,
								  Boolean strData) {
		SharedPreferences activityPreferences = context.getSharedPreferences(
				spFileName, Context.MODE_PRIVATE);
		Editor editor = activityPreferences.edit();
		editor.putBoolean(strKey, strData);
		editor.commit();
	}

}
