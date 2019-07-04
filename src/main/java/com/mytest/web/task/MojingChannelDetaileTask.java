package com.mytest.web.task;

import java.io.IOException;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mytest.admin.po.TXZDownUserInfoPo;
import com.mytest.admin.service.AnalysisMoJingService;
import com.mytest.admin.service.DownLoadService;
import com.mytest.admin.service.DownLoadUserInfoService;
import com.mytest.utils.FileDownLoad;
import com.mytest.utils.SessionStorage;

@Component
public class MojingChannelDetaileTask {

	Log logger = LogFactory.getLog(ChannelDetaileTask.class);

	@Autowired
	private DownLoadUserInfoService downLoadUserInfoService;
	@Autowired
	private DownLoadService downLoadService;
	@Autowired
	private AnalysisMoJingService analysisMoJingService;
	@Value("${downloadUrl}")
	private String downloadUrl;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 每1分钟执行一次
	 */
	@Scheduled(cron = "0 */5 * * * ?")
	public void flushMojingChannelDetaileTask() {
		List<TXZDownUserInfoPo> list = downLoadUserInfoService.allTXZDownUserInfoPoList(2);
		String date = sdf.format(new Date());
		for (TXZDownUserInfoPo txzDownUserInfoPo : list) {
			System.setProperty("webdriver.chrome.driver",
					"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
			String urlChrome = downloadUrl + "reptileproject\\" + String.valueOf(txzDownUserInfoPo.getUserId());
			DesiredCapabilities caps = downLoadService.setDownloadsPath(urlChrome);// 更改默认下载路径

			WebDriver driver = new ChromeDriver(caps);
			try {
				driver.get(txzDownUserInfoPo.getAddress() + "/back/static/login.html");
				WebDriverWait wait = new WebDriverWait(driver, 10);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"userName\"]")));
				WebElement loginName = driver.findElement(By.xpath("//*[@id=\"userName\"]"));
				loginName.sendKeys(txzDownUserInfoPo.getUserName());
				WebElement loginPwd = driver.findElement(By.xpath("//*[@id=\"password\"]"));
				loginPwd.sendKeys(txzDownUserInfoPo.getUserPwd());
				WebElement code = driver.findElement(By.className("code-img")).findElement(By.tagName("img"));
				String src = code.getAttribute("src");
				try {
					FileDownLoad.downLoadFromUrl(src, "11.png", downloadUrl + "mojing/");
				} catch (IOException e) {
					e.printStackTrace();
				}
				String verifyCodeStr = FileDownLoad.captchCode(driver, code, downloadUrl + "mojing/11.png");

				WebElement acode = driver.findElement(By.xpath("//*[@id=\"code\"]"));
				acode.sendKeys(verifyCodeStr);

				WebElement loginBtn = driver.findElement(By.xpath("//*[@id=\"login\"]"));
				loginBtn.click();
				Thread.sleep(2000);
				SessionStorage localStorage = new SessionStorage(driver);
				String sef = localStorage.getItemFromLocalStorage("token");
				System.out.println(sef);

				driver.get(txzDownUserInfoPo.getAddress()
						+ "/back/channelReport/getChannelReportPage?pageNum=1&numPerPage=200&channelCode=&startTime="
						+ date + "&endTime=" + date + "&token=" + sef);

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/pre")));
				// Thread.sleep(5000);
				WebElement json = driver.findElement(By.xpath("/html/body/pre"));

				String userInfo = json.getText();
				analysisMoJingService.analysisChannelUserInfo(txzDownUserInfoPo, date, userInfo);
				driver.get(txzDownUserInfoPo.getAddress()
						+ "/back/channel/getChannelRecordPage?pageNum=1&numPerPage=2000&userName=&userTel=&userStatus=&beginTime="
						+ date + "&endTime=" + date + "&token=" + sef);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/pre")));
				// Thread.sleep(5000);
				WebElement json2 = driver.findElement(By.xpath("/html/body/pre"));
				String channelDetail = json2.getText();
				System.out.println(channelDetail);
				analysisMoJingService.analysisTChannleXZDetailedInfoPo(txzDownUserInfoPo, channelDetail);

				driver.quit();
			} catch (Throwable e11) {
				driver.switchTo().defaultContent();
				driver.quit();
				e11.printStackTrace();
			}
		}
	}

}
