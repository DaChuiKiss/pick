package com.ergou.hailiao.mvp.ui.config;


import com.ergou.hailiao.utils.ConfigValue;

//单例模式
public class Config {
	
	private static volatile Config instance;
	private ConfigValue configValue;
	public static final String ACCOUNTID = "AccountId";
	public static final String BG_IMAGE="bgImage";
	public static final String MAIN_IMAGE1="mainImage1";
	public static final String MAIN_IMAGE2="mainImage2";
	public static final String INDEX_IMAGE1="indexImage1";
	public static final String INDEX_IMAGE2="indexImage2";
	public static final String INDEX_IMAGE3="indexImage3";
	public static final String INDEX_IMAGE4="indexImage4";
	
	public static final String INDEX_IMAGE11="indexImage11";
	public static final String INDEX_IMAGE22="indexImage22";
	public static final String INDEX_IMAGE33="indexImage33";
	public static final String INDEX_IMAGE44="indexImage44";
	public static final String POST_IMAGE1="postImage1";
	public static final String POST_IMAGE2="postImage2";
	public static final String CONFIGUUID="5dd79cd3-1616-4615-94c2-0e9e43622313";
	// 头像url
	public static String HEADURL = "sendavatar";
	// 昵称
	public static String NICKNAME = "sendnick";
//	public static final String SPEECHAPPID=SpeechConstant.APPID + "=568c8aea";
	
	public static final int COUNT=13;
	
	private Config(String config){
		configValue=new ConfigValue(config);
	}
	

	
	//获取实例
	public static Config getInstance(){
		if(instance==null){
			synchronized (Config.class) {
				if(instance==null){
//					instance=new Config(App.getInstance().getmPreferences().getSysConfig());
				}
			}
		}
		return instance;
	}
	
	
	public ConfigValue getConfigValue() {
		return configValue;
	}

}
