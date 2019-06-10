package com.mytest.web.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mytest.admin.service.ChannelDetailedInfoService;

@Component
public class ChannelDetaileTask {
	
	@Autowired
	private ChannelDetailedInfoService channelDetailedInfoService;

	/**
	 * 每天凌晨执行
	 */
//	@Scheduled(cron = "0 0 0 * * ?")
//	public void flushChannelDetaile() {
//		//生成新数据
//		channelDetailedInfoService.insertTChannleDetailedInfoPo(0);
//	}
	
}
