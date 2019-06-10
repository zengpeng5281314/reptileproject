package com.mytest.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SysServiceSingleton {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SysServiceSingleton.class);

	
	private static String code = null;
	private static String imagePath = null;
	private static String securityUrl = null;

	public static String getCode() {
		if(null == code){
			InputStream resourceAsStream = SysServiceSingleton.class.getResourceAsStream("/application.properties");
			Properties pps = new Properties();
			try {
				pps.load(resourceAsStream);
				code = pps.getProperty("sys.code");
			} catch (FileNotFoundException e) {
				LOGGER.error(e.getMessage(), e);
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		return code;
	}
	
	public static String getImagePath() {
		if(null == imagePath){
			InputStream resourceAsStream = SysServiceSingleton.class.getResourceAsStream("/application.properties");
			Properties pps = new Properties();
			try {
				pps.load(resourceAsStream);
				imagePath = pps.getProperty("sys.imagePath");
			} catch (FileNotFoundException e) {
				LOGGER.error(e.getMessage(), e);
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		return imagePath;
	}

	public static String getSecurityUrl() {
		if(null == securityUrl){
			InputStream resourceAsStream = SysServiceSingleton.class.getResourceAsStream("/application.properties");
			Properties pps = new Properties();
			try {
				pps.load(resourceAsStream);
				securityUrl = pps.getProperty("sys.securityUrl");
			} catch (FileNotFoundException e) {
				LOGGER.error(e.getMessage(), e);
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		return securityUrl;
	}


	
}

