package com.mytest.web.controller.admin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.mytest.utils.CaptchaUtil;
import com.mytest.utils.FileDownLoad;

public class ReptileHTTPMain {

	public static void main(String[] args) {

		//doGet("http://whfq.hsxia.cn/business/login");
		try {
			testJSFile();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
	}
	
	public static String doGet(String loginUrl) {
		HttpClient httpClient = new HttpClient();
		 httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		 StringBuffer tmpcookies = new StringBuffer();
         
        // 模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
        GetMethod getMethod = new GetMethod(loginUrl);
        getMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
        try {
			httpClient.executeMethod(getMethod);
			 // 获得登陆后的 Cookie
			org.apache.commons.httpclient.Cookie[] cookies = httpClient.getState().getCookies();
            for (org.apache.commons.httpclient.Cookie c : cookies) {
                tmpcookies.append(c.toString() + ";");
                System.out.println("cookies = "+c.toString());
            }
            System.out.println("---"+tmpcookies);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return tmpcookies.toString();
        
	}
	
	public static String doPost(Map<String, String> map, String charset) {
		CloseableHttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			CookieStore cookieStore = new BasicCookieStore();
			httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
			httpPost = new HttpPost("http://peiqibao.zhulidao.com/login");
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> elem = (Entry<String, String>) iterator.next();
				list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
			}
			if (list.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
				httpPost.setEntity(entity);
			}
			httpClient.execute(httpPost);
			String JSESSIONID = null;
			String cookie_user = null;
			List<Cookie> cookies = cookieStore.getCookies();
			for (int i = 0; i < cookies.size(); i++) {
				if (cookies.get(i).getName().equals("JSESSIONID")) {
					JSESSIONID = cookies.get(i).getValue();
				}
				if (cookies.get(i).getName().equals("cookie_user")) {
					cookie_user = cookies.get(i).getValue();
				}
			}
			if (cookie_user != null) {
				result = JSESSIONID;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	
	
	
	private static void testJSFile() throws Exception { 
        ScriptEngineManager mgr = new ScriptEngineManager(); 
        ScriptEngine engine = mgr.getEngineByName("javascript"); 
        String url ="D:\\workspace_git\\reptileproject\\src\\main\\webapp\\commons\\js\\loginRsa.js";
        //String url1 ="D:\\workspace_git\\reptileproject\\src\\main\\webapp\\commons\\js\\jsbn.js";
        //String url2 ="D:\\workspace_git\\reptileproject\\src\\main\\webapp\\commons\\js\\prng4.js";
        //String url3 ="D:\\workspace_git\\reptileproject\\src\\main\\webapp\\commons\\js\\rng.js";
        String url4 ="D:\\workspace_git\\reptileproject\\src\\main\\webapp\\commons\\js\\rsa.js";
        String url5 ="D:\\workspace_git\\reptileproject\\src\\main\\webapp\\commons\\js\\base64.js";
        String script = readJSFile(url4)+"   "+readJSFile(url5)+"   "+readJSFile(url)+"   ";
//        engine.eval(readJSFile()); 
//        engine.eval(readJSFile()); 
//        engine.eval(readJSFile()); 
        engine.eval(script); 
        
//        engine.eval(readJSFile(url)); 
        Invocable inv = (Invocable) engine; 
        Object res = (Object) inv.invokeFunction("rsaKey", new String[] { "AITv/LGRSJGv3G1MUHWfwF/L6Ogbja6lCE9x+ush7j0I12J3r63qimjSMwV7j8tzb7bXYv6qd2WV+SNdhQKFVFHgImlHwvcL814ET8JH5TV/gSNofP5qtw8zU6dI79xiRzlMvVdoadwGxChc5gk5vHhOBnSY4sZSaGx4Ou5oezn1", "AQAB","123321" }); 
        System.out.println("res:" + res); 
    } 

    private static String readJSFile(String url) throws Exception { 
        StringBuffer script = new StringBuffer(); 
        File file = new File(url); 
        FileReader filereader = new FileReader(file); 
        BufferedReader bufferreader = new BufferedReader(filereader); 
        String tempString = null; 
        while ((tempString = bufferreader.readLine()) != null) { 
            script.append(tempString).append("\n"); 
        } 
        bufferreader.close(); 
        filereader.close(); 
        return script.toString(); 
    }

}
