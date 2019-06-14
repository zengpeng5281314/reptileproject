package com.mytest.utils;

import java.io.File;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

public class CaptchaUtil {
//    private static String username = "qianbaoocr";
//    private static String password = "haodaibao@123";
//    private static String softid = "99";
    public static final String CODETYPE_4 = "1004";
    public static final String CODETYPE_5 = "1005";
    public static final String CODETYPE_6 = "1006";
    private static String str_debug = "test";
    
    private static String username;
    private static String password;
    private static String softid = "99";
    
    public static void initCaptcha(){
    	
    		username = "ruiyinxin";
    		password = "1qazXSW@";
    		softid = "898718";
    }
    
    public static String convert(File file, String codeType){
    	initCaptcha();
		String rsJson = convert(username, password, softid, codeType, str_debug, file);
        if(rsJson.equals("未知问题")){
            return null;
        }
        return rsHandle(rsJson);
    }
    public static String convert(byte[] byteArr, String codeType){
    	initCaptcha();
        String rsJson = convert(username, password, softid, codeType, str_debug, byteArr);
        if(rsJson.equals("未知问题")){
            return null;
        }
        return rsHandle(rsJson);
    }
    
    public static String convert(String username, String password, String softid, String codetype, String str_debug, File file){
        String len_min = file.length() + "";
        String time_add = new Date().getTime()+"";
       return ChaoJiYing.PostPic(username, password, softid, codetype, len_min, time_add, str_debug, file.getAbsolutePath());
    }
    public static String convert(String username, String password, String softid, String codetype, String str_debug, byte[] byteArr){
        String len_min = byteArr.length + "";
        String time_add = new Date().getTime()+"";
       return ChaoJiYing.PostPic(username, password, softid, codetype, len_min, time_add, str_debug, byteArr);
    }
    
    
    private static String rsHandle(String rsJson){
        JSONObject jsonObject = JSONObject.parseObject(rsJson);
        String errStr = jsonObject.getString("err_str");
        String picStr = jsonObject.getString("pic_str");
//        String md5 = jsonObject.getString("md5");
//        String strDebug = jsonObject.getString("str_debug");
        if(errStr.trim().equals("OK")){
            return picStr.trim();
        }
        return null;
    }
}
