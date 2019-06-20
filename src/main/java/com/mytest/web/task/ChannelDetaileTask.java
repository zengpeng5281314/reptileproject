package com.mytest.web.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mytest.admin.service.ChannelDetailedInfoService;
import com.mytest.utils.AHttpClient;

@Component
public class ChannelDetaileTask {
	
	@Autowired
	private ChannelDetailedInfoService channelDetailedInfoService;

	/**
	 * 每1分钟执行一次
	 */
	@Scheduled(cron = "0 */3 * * * ?")
	public void flushChannelDetaile() {
		//生成新数据
		AHttpClient aHttpClient = new AHttpClient();
		aHttpClient.doHttpGetRequest("http://localhost:8080/xingzuo/test");
	}
	
}
