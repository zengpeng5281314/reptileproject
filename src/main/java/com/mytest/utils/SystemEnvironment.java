package com.mytest.utils;

import java.util.Properties;

/**
 * 系统环境变量
 *
 */
public class SystemEnvironment {
	/**
	 * 微信调用地址
	 */
	public static String SYSTEM_HTTP_WX_URL = null;
	/**
	 * wxToken
	 */
	public static String SYSTEM_WXTOKEN = null;
	/**
	 * wxAPPID
	 */
	public static String SYSTEM_WXAPPID = null;
	/**
	 * wxAppSecret
	 */
	public static String SYSTEM_WXAPPSECRET = null;
	/**
	 * encodingAESKey
	 */
	public static String SYSTEM_ENCODINGAESKEY = null;

	public static String SYSTEM_ACCESS_TOKEN = null;

	public static void init(Properties prop) {
		SYSTEM_HTTP_WX_URL = prop.getProperty("wx.http.url");
		SYSTEM_WXTOKEN = prop.getProperty("wxToken");
		SYSTEM_WXAPPID = prop.getProperty("wxAppID");
		SYSTEM_WXAPPSECRET = prop.getProperty("wxAppSecret");
		SYSTEM_ENCODINGAESKEY = prop.getProperty("encodingAESKey");
	}
}
