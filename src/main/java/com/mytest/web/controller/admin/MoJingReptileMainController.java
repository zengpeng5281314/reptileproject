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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mytest.admin.po.TChannleXZInfoPo;
import com.mytest.admin.po.TXZDownUserInfoPo;
import com.mytest.admin.service.DownLoadDetailedService;
import com.mytest.admin.service.DownLoadService;
import com.mytest.admin.service.DownLoadUserInfoService;
import com.mytest.utils.FileDownLoad;
import com.mytest.web.controller.base.BaseController;

@Controller
@RequestMapping("/mojing")
public class MoJingReptileMainController extends BaseController {

	@Value("${downloadUrl}")
	private String downloadUrl;
	
	@Value("${wxToken}")
	private String wxToken;
	
	@Autowired
	private DownLoadService downLoadService;
	@Autowired
	private DownLoadUserInfoService downLoadUserInfoService;
	@Autowired
	private DownLoadDetailedService downLoadDetailedService;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@RequestMapping("/test")
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
			driver.get(txzDownUserInfoPo.getAddress());
			WebElement loginName = driver.findElement(By.xpath("//*[@id=\"userName\"]"));
			loginName.sendKeys(txzDownUserInfoPo.getUserName());
			WebElement loginPwd = driver.findElement(By.xpath("//*[@id=\"password\"]"));
			loginPwd.sendKeys(txzDownUserInfoPo.getUserPwd());
			WebElement code = driver.findElement(By.xpath("/html/body/div/div/div[4]/img"));
			String src = code.getAttribute("src");
			try {
				FileDownLoad.downLoadFromUrl(src, "11.png", downloadUrl+"mojing/");
			} catch (IOException e) {
				e.printStackTrace();
			}
			String verifyCodeStr = FileDownLoad.captchCode(driver, code, downloadUrl + "mojing/11.png");

			WebElement acode = driver.findElement(By.xpath("//*[@id=\"code\"]"));
			acode.sendKeys(verifyCodeStr);
			try {
				WebElement loginBtn = driver.findElement(By.xpath("//*[@id=\"login\"]"));
				loginBtn.click();

				detail(driver, date, txzDownUserInfoPo);
			} catch (Throwable e11) {
				driver.switchTo().defaultContent();
				driver.quit();
				e11.printStackTrace();
			}
		}
		return null;
	}

	private void detail(WebDriver driver, String date, TXZDownUserInfoPo txzDownUserInfoPo) {
		try {
			Thread.sleep(2000);
			WebElement menu1 = driver.findElement(By.xpath("//*[@id=\"LAY-system-side-menu\"]/li[1]/a"));
			menu1.click();

			Thread.sleep(500);
			WebElement qudao = driver.findElement(By.xpath("//*[@id=\"LAY-system-side-menu\"]/li[1]/dl/dd[3]/a"));
			qudao.click();

			Thread.sleep(1000);
			WebElement frame = driver.findElement(By.xpath("//*[@id=\"contentBody\"]/iframe[2]"));
			driver = driver.switchTo().frame(frame);

			WebElement beginTime = driver.findElement(By.xpath("//*[@id=\"beginTime\"]"));
			beginTime.sendKeys(date);
			WebElement endTime = driver.findElement(By.xpath("//*[@id=\"endTime\"]"));
			endTime.sendKeys(date);
			WebElement search = driver.findElement(By.xpath("//*[@id=\"search\"]"));
			search.click();
			Thread.sleep(1000);
			WebElement table = driver.findElement(By.className("layui-table-main"))
					.findElement(By.className("layui-table"));
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			int registNums = 0;
			int applactionNum = 0;
			// assertEquals(5,rows.size());
			for (WebElement row : rows) {
				List<WebElement> cols = row.findElements(By.tagName("td"));
				registNums = Integer.valueOf(cols.get(2).getText().trim());
				applactionNum = Integer.valueOf(cols.get(4).getText().trim());
			}

			TChannleXZInfoPo channleXZInfoPo = downLoadDetailedService
					.getTChannleXZInfoPo(txzDownUserInfoPo.getUserId(), date);
			if (channleXZInfoPo == null) {
				channleXZInfoPo = new TChannleXZInfoPo();
				try {
					channleXZInfoPo.setCtime(sdf.parse(date));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				channleXZInfoPo.setUserId(txzDownUserInfoPo.getUserId());
				channleXZInfoPo.setChannelId(txzDownUserInfoPo.getChannelId());
				channleXZInfoPo.setCreateTime(new Timestamp(System.currentTimeMillis()));
				channleXZInfoPo.setStatus(1);
			}
			channleXZInfoPo.setApplicantsNum(applactionNum);
			channleXZInfoPo.setRegistNum(registNums);
			downLoadDetailedService.saveOrUpdatetTChannleXZInfoPo(channleXZInfoPo);

			driver.switchTo().defaultContent();
			WebElement menu2 = driver.findElement(By.xpath("//*[@id=\"LAY-system-side-menu\"]/li[2]/a"));
			menu2.click();
			WebElement regist = driver.findElement(By.xpath("//*[@id=\"LAY-system-side-menu\"]/li[2]/dl/dd[2]/a"));
			regist.click();

			WebElement frame3 = driver.findElement(By.xpath("//*[@id=\"contentBody\"]/iframe[3]"));
			driver = driver.switchTo().frame(frame3);
			Thread.sleep(2000);
			WebElement select = driver.findElement(By.xpath("//*[@id=\"layui-laypage-1\"]/span[1]/select"));
			select.click();
			Thread.sleep(1000);
			WebElement option = driver.findElement(By.xpath("//*[@id=\"layui-laypage-1\"]/span[1]/select/option[6]"));
			option.click();

			WebElement count = driver.findElement(By.className("layui-laypage-count"));
			String countText = count.getText().replace("共", "").replace("条", "").replace(" ", "");
			int countT = Integer.valueOf(countText);
			int pageSize = countT % 200 == 0 ? (countT / 200) : (countT / 200) + 1;
			for (int i = 1; i <= pageSize; i++) {
				WebElement tableTow = driver.findElement(By.className("layui-table-main"))
						.findElement(By.className("layui-table"));

				System.out.println(tableTow.getText());

				List<WebElement> rowsTow = tableTow.findElements(By.tagName("tr"));
				// assertEquals(5,rows.size());
				for (WebElement row : rowsTow) {
					List<WebElement> cols = row.findElements(By.tagName("td"));
					for (WebElement col : cols) {
						System.out.println(col.getText().replace(" ", "") + "\t");
					}
					System.out.println("");
				}

				Thread.sleep(2000);
				WebElement currentPage = driver.findElement(By.className("layui-laypage-skip"))
						.findElement(By.className("layui-input"));
				WebElement button = driver.findElement(By.className("layui-laypage-skip"))
						.findElement(By.className("layui-laypage-btn"));

				// int j=5;
				// if(i>=6)
				// j=6;
				// if(i>=11)
				// j=5;
				// WebElement currentPage =
				// driver.findElement(By.xpath("//*[@id=\"layui-laypage-"+i+"\"]/span["+j+"]/input"));
				currentPage.clear();
				currentPage.sendKeys(i + "");
				// WebElement button =
				// driver.findElement(By.xpath("//*[@id=\"layui-laypage-"+i+"\"]/span["+j+"]/button"));
				button.click();
			}
			driver.quit();
		} catch (InterruptedException e) {
			driver.switchTo().defaultContent();
			driver.quit();
			e.printStackTrace();
		}
	}

}
