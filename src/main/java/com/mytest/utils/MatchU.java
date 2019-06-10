package com.mytest.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchU {

	public static List<String> doMatcherForList(String source, Pattern pattern){
		Matcher matcher = pattern.matcher(source);
		List<String> rs = new ArrayList<String>();
		while (matcher.find()) {
			rs.add(matcher.group(1));
		}
		return rs;
	}
	
	public static String doMatcher(String source, Pattern pattern) {
		List<String> list = doMatcherForList(source, pattern);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public static String doMatcher(String source, String regex) {
		Pattern pattern = Pattern.compile(regex);
		return doMatcher(source, pattern);
	}
	
	public static void main(String[] args) {
		String test="formParams=>username=111111111111111111&password=22222222222222&type=001&captchaCode=nhqpf";
		String userName = doMatcher(test, "username=(.*?)&password=");
		String password = doMatcher(test, "password=(.*?)&type=");
		String type = doMatcher(test, "type=(.*?)&");
		String captchaCode = doMatcher(test, "captchaCode=(.*?)$");
		System.out.println("userName=>"+userName);
		System.out.println("password=>"+password);
		System.out.println("type=>"+type);
		System.out.println("captchaCode=>"+captchaCode);
	}
}
