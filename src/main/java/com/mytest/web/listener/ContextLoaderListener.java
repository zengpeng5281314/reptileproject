package com.mytest.web.listener;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;

import com.mytest.utils.SystemEnvironment;


/**
 * 系统常量初始化
 *
 */
public class ContextLoaderListener extends org.springframework.web.context.ContextLoaderListener {
	
	public ContextLoaderListener() {
		super();
	}

	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
	}
	
	public void contextInitialized(ServletContextEvent event) {
		Properties prop = new Properties();
		try {
			prop.load(this.getClass().getResourceAsStream("/environment.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		SystemEnvironment.init(prop);
		
	}
}
