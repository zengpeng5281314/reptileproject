package com.mytest.web.controller.admin;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mytest.admin.po.TChannleXZDetailedInfoPo;
import com.mytest.admin.po.TChannleXZInfoPo;
import com.mytest.admin.po.TXZDownUserInfoPo;
import com.mytest.admin.service.AnalysisMoJingService;
import com.mytest.admin.service.DownLoadDetailedService;
import com.mytest.admin.service.DownLoadService;
import com.mytest.admin.service.DownLoadUserInfoService;
import com.mytest.utils.FileDownLoad;
import com.mytest.utils.SessionStorage;
import com.mytest.web.controller.base.BaseController;

@Controller
@RequestMapping("/mojing")
public class MoJingReptileMainController2 extends BaseController {

	@Value("${downloadUrl}")
	private String downloadUrl;

	@Autowired
	private DownLoadService downLoadService;
	@Autowired
	private DownLoadUserInfoService downLoadUserInfoService;
	@Autowired
	private AnalysisMoJingService analysisMoJingService;
	@Autowired
	private DownLoadDetailedService downLoadDetailedService;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@RequestMapping("/test1")
	@Transactional
	@ResponseBody
	public String test(@RequestParam(defaultValue = "0", required = false, value = "userId") long userId,
			@RequestParam(defaultValue = "", required = false, value = "date") String date, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		List<TXZDownUserInfoPo> list = downLoadUserInfoService.allTXZDownUserInfoPoList(2);
		if (date.equals(""))
			date = sdf.format(new Date());
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
						+ "/back/channelReport/getChannelReportPage?pageNum=1&numPerPage=200&channelCode=&startTime="+date+"&endTime="+date+"&token="
						+ sef);
				
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/pre")));
				// Thread.sleep(5000);
				WebElement json = driver.findElement(By.xpath("/html/body/pre"));

				String userInfo = json.getText();
				analysisMoJingService.analysisChannelUserInfo(txzDownUserInfoPo, date, userInfo);
				driver.get(txzDownUserInfoPo.getAddress()
						+ "/back/channel/getChannelRecordPage?pageNum=1&numPerPage=2000&userName=&userTel=&userStatus=&beginTime="+date+"&endTime="+date+"&token="
						+ sef);
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
		return successJson(null);
	}

}
