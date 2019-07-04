package com.mytest.web.task;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mytest.admin.po.TChannleXZInfoPo;
import com.mytest.admin.po.TXZDownUserInfoPo;
import com.mytest.admin.service.DownLoadDetailedService;
import com.mytest.admin.service.DownLoadService;
import com.mytest.admin.service.DownLoadUserInfoService;
import com.mytest.utils.HttpClient4;

@Component
public class ChannelDetaileTask {

	Log logger = LogFactory.getLog(ChannelDetaileTask.class);
	
	@Autowired
	private DownLoadUserInfoService downLoadUserInfoService;
	@Autowired
	private DownLoadService downLoadService;
	@Autowired
	private DownLoadDetailedService downLoadDetailedService;
	@Value("${downloadUrl}")
	private String downloadUrl;
	
	/**
	 * 每1分钟执行一次
	 */
	@Scheduled(cron = "0 */5 * * * ?")
	public void flushChannelDetaile() {
		List<TXZDownUserInfoPo> list = downLoadUserInfoService.allTXZDownUserInfoPoList(1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		for (TXZDownUserInfoPo txzDownUserInfoPo : list) {
			System.setProperty("webdriver.chrome.driver",
					"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
			String urlChrome=downloadUrl+"reptileproject\\"+String.valueOf(txzDownUserInfoPo.getUserId());
			DesiredCapabilities caps = downLoadService.setDownloadsPath(urlChrome);// 更改默认下载路径

			WebDriver driver = new ChromeDriver(caps);
			//https://channel.mcdonald.gamescpu.com:8090/#/login
			driver.get(txzDownUserInfoPo.getAddress());
			WebElement loginName = driver.findElement(By.xpath("//*[@id=\"name\"]"));
			loginName.sendKeys(txzDownUserInfoPo.getUserName());
			WebElement loginPwd = driver.findElement(By.xpath("//*[@id=\"password\"]"));
			loginPwd.sendKeys(txzDownUserInfoPo.getUserPwd());
			try {
				WebElement loginBtn = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/form/button"));
				loginBtn.click();
				Thread.sleep(1000);
				WebElement zw = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[1]/div/ul/li"));
				zw.click();

				Thread.sleep(2000);
				
				WebElement chooseChannel = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div/div[2]/div/div/div/div[1]/form/div/div[1]/div/div[2]/div/span/div/div/div"));
				chooseChannel.click();
				Thread.sleep(1000);
				WebElement channels = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/ul/li["+txzDownUserInfoPo.getChannelNum()+"]"));
				channels.click();
				
				// 选择时间
				WebElement start = driver.findElement(By.xpath("//*[@id=\"range-picker\"]/span/input[1]"));
				start.click();
				WebElement startime = driver
						.findElement(By.xpath("/html/body/div[3]/div/div/div/div/div[1]/div[1]/div[1]/div/input"));
				startime.sendKeys(date);
				WebElement endtime = driver
						.findElement(By.xpath("/html/body/div[3]/div/div/div/div/div[1]/div[2]/div[1]/div/input"));
				endtime.sendKeys(date);
				WebElement search = driver.findElement(By.xpath(
						"//*[@id=\"root\"]/div/div[2]/div[2]/div/div[2]/div/div/div/div[1]/form/div/div[3]/div/div/div/span/span/button[1]"));
				search.click();
				Thread.sleep(2000);
				int registNums = 0;
				WebElement registNum = driver.findElement(By.xpath(
						"//*[@id=\"root\"]/div/div[2]/div[2]/div/div[2]/div/div/div/div[2]/div/div/div/div/div/div/div/table/tbody/tr/td[1]"));
				System.out.println("---" + registNum.getText());
				registNums = Integer.valueOf(registNum.getText());
				
				WebElement applactionNum = driver.findElement(By.xpath(
						"//*[@id=\"root\"]/div/div[2]/div[2]/div/div[2]/div/div/div/div[2]/div/div/div/div/div/div/div/table/tbody/tr/td[2]"));
				System.out.println("---" + applactionNum.getText());

				TChannleXZInfoPo channleXZInfoPo = downLoadDetailedService
						.getTChannleXZInfoPo(txzDownUserInfoPo.getUserId(), date);
				if (channleXZInfoPo == null) {
					channleXZInfoPo = new TChannleXZInfoPo();
					channleXZInfoPo.setCtime(sdf.parse(date));
					channleXZInfoPo.setUserId(txzDownUserInfoPo.getUserId());
					channleXZInfoPo.setChannelId(txzDownUserInfoPo.getChannelId());
					channleXZInfoPo.setCreateTime(new Timestamp(System.currentTimeMillis()));
					channleXZInfoPo.setStatus(1);
					channleXZInfoPo.setSource(1);
				}
				channleXZInfoPo.setApplicantsNum(Integer.valueOf(applactionNum.getText()));
				channleXZInfoPo.setRegistNum(registNums);
				downLoadDetailedService.saveOrUpdatetTChannleXZInfoPo(channleXZInfoPo);

				if (registNums > 0) {
					// //下载
					WebElement one = driver.findElement(By.xpath(
							"//*[@id=\"root\"]/div/div[2]/div[2]/div/div[2]/div/div/div/div[1]/form/div/div[4]/button"));
					one.click();
					Thread.sleep(2000);
				}
				driver.quit();
				if (registNums > 0) {
					// 解析数据
					logger.info("解析数据userId:" + txzDownUserInfoPo.getUserId());
					HttpClient4.doGet("http://localhost:8081/xingzuo/analysis?userId=" + txzDownUserInfoPo.getUserId()+"&channelId="+txzDownUserInfoPo.getChannelId());
//					AHttpClient aHttpClient = new AHttpClient();
//					aHttpClient.doHttpGetRequest(
//							"http://localhost:8080/xingzuo/analysis?userId=" + txzDownUserInfoPo.getUserId());
				}
			} catch (Throwable e11) {
				e11.printStackTrace();
				driver.quit();
			}
		}
	}
	
}
