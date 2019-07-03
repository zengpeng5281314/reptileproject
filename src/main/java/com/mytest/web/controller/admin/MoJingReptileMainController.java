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

	@Autowired
	private DownLoadService downLoadService;
	@Autowired
	private DownLoadUserInfoService downLoadUserInfoService;
	@Autowired
	private DownLoadDetailedService downLoadDetailedService;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
			try {
				driver.get(txzDownUserInfoPo.getAddress());
				WebElement loginName = driver.findElement(By.xpath("//*[@id=\"userName\"]"));
				loginName.sendKeys(txzDownUserInfoPo.getUserName());
				WebElement loginPwd = driver.findElement(By.xpath("//*[@id=\"password\"]"));
				loginPwd.sendKeys(txzDownUserInfoPo.getUserPwd());
				WebElement code = driver.findElement(By.xpath("/html/body/div[1]/div/div/img"));
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

				detail(driver, date, txzDownUserInfoPo);
			} catch (Throwable e11) {
				driver.switchTo().defaultContent();
				driver.quit();
				e11.printStackTrace();
			}
		}
		return successJson(null);
	}

	private void detail(WebDriver driver, String date, TXZDownUserInfoPo txzDownUserInfoPo) {
		// WebDriverWait wait = new WebDriverWait(driver, 5);
		// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"LAY-system-side-menu\"]/li[1]/a")));

		try {
			 Thread.sleep(2000);
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@id=\"LAY-system-side-menu\"]/li[1]/a")));
			WebElement menu1 = driver.findElement(By.xpath("//*[@id=\"LAY-system-side-menu\"]/li[1]/a"));
			menu1.click();
			//*[@id="LAY-system-side-menu"]/li[1]/dl/dd/a
//			wait.until(ExpectedConditions
//					.visibilityOfElementLocated(By.xpath("//*[@id=\"LAY-system-side-menu\"]/li[1]/dl/dd[3]/a")));
			 Thread.sleep(2000);
			WebElement qudao = driver.findElement(By.xpath("//*[@id=\"LAY-system-side-menu\"]/li[1]/dl/dd/a"));
			qudao.click();

			// Thread.sleep(1000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"contentBody\"]/iframe[2]")));
			WebElement frame = driver.findElement(By.xpath("//*[@id=\"contentBody\"]/iframe[2]"));
			driver = driver.switchTo().frame(frame);

			WebElement beginTime = driver.findElement(By.xpath("//*[@id=\"beginTime\"]"));
			beginTime.sendKeys(date);
			WebElement endTime = driver.findElement(By.xpath("//*[@id=\"endTime\"]"));
			endTime.sendKeys(date);
			WebElement search = driver.findElement(By.xpath("//*[@id=\"search\"]"));
			search.click();
			// Thread.sleep(1000);
			wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("layui-table-main")));
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
				channleXZInfoPo.setSource(2);
			}
			channleXZInfoPo.setApplicantsNum(applactionNum);
			channleXZInfoPo.setRegistNum(registNums);
			downLoadDetailedService.saveOrUpdatetTChannleXZInfoPo(channleXZInfoPo);

			driver.switchTo().defaultContent();
			WebElement menu2 = driver.findElement(By.xpath("//*[@id=\"LAY-system-side-menu\"]/li[2]/a"));
			menu2.click();
			WebElement regist = driver.findElement(By.xpath("//*[@id=\"LAY-system-side-menu\"]/li[2]/dl/dd/a"));
			regist.click();

			WebElement frame3 = driver.findElement(By.xpath("//*[@id=\"contentBody\"]/iframe[3]"));
			driver = driver.switchTo().frame(frame3);
			// Thread.sleep(2000);
			wait = new WebDriverWait(driver, 5);

			WebElement beginTime2 = driver.findElement(By.xpath("//*[@id=\"beginTime\"]"));
			beginTime2.sendKeys(date);
			WebElement endTime2 = driver.findElement(By.xpath("//*[@id=\"endTime\"]"));
			endTime2.sendKeys(date);
			WebElement search2 = driver.findElement(By.xpath("//*[@id=\"search\"]"));
			search2.click();
			Thread.sleep(2000);
			int istrun = 0;
			try {
				WebElement noThing = driver.findElement(By.className("layui-none"));
				// 如果能找到元素。说明没有数据
				istrun = 1;
			} catch (Exception ex) {

			}
			if (istrun == 1) {
				driver.switchTo().defaultContent();
				driver.quit();
				return;
			}
			// //*[@id="layui-laypage-2"]/span[1]/select
			// if()
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@id=\"layui-laypage-2\"]/span[1]/select")));
			WebElement select = driver.findElement(By.xpath("//*[@id=\"layui-laypage-2\"]/span[1]/select"));
			select.click();
			// Thread.sleep(1000);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@id=\"layui-laypage-2\"]/span[1]/select/option[6]")));
			WebElement option = driver.findElement(By.xpath("//*[@id=\"layui-laypage-2\"]/span[1]/select/option[6]"));
			option.click();
			// Thread.sleep(2000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("layui-laypage-count")));
			WebElement count = driver.findElement(By.className("layui-laypage-count"));
			String countText = count.getText().replace("共", "").replace("条", "").replace(" ", "");
			int countT = Integer.valueOf(countText);
			int pageSize = countT % 200 == 0 ? (countT / 200) : (countT / 200) + 1;
			for (int i = 2; i <= pageSize + 1; i++) {
				Thread.sleep(2000);
				// wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("layui-table-main")));
				WebElement tableTow = driver.findElement(By.className("layui-table-main"))
						.findElement(By.className("layui-table"));

				List<WebElement> rowsTow = tableTow.findElements(By.tagName("tr"));
				System.out.println("sefsef:" + rowsTow.size());
				// assertEquals(5,rows.size());
				for (WebElement row : rowsTow) {
					List<WebElement> cols = row.findElements(By.tagName("td"));

					String nameNo = cols.get(0).getText().trim();
					System.out.println("------:" + nameNo);
					String name = cols.get(1).getText().trim();
					String phone = cols.get(2).getText().trim();
					String channelName = cols.get(3).getText().trim();
					String registTime = cols.get(4).getText().trim();
					String typeName = cols.get(5).getText().trim();
					Timestamp time = new Timestamp(System.currentTimeMillis());
					try {
						time = new Timestamp(sdf1.parse(registTime).getTime());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					TChannleXZDetailedInfoPo channleXZDetailedInfoPo = downLoadDetailedService
							.getTChannleXZDetailedInfoPo(txzDownUserInfoPo.getUserId(), phone, registTime);
					if (channleXZDetailedInfoPo == null) {
						channleXZDetailedInfoPo = new TChannleXZDetailedInfoPo();
						channleXZDetailedInfoPo.setChannelId(txzDownUserInfoPo.getChannelId());
						channleXZDetailedInfoPo.setCreateTime(new Timestamp(System.currentTimeMillis()));
						channleXZDetailedInfoPo.setPhone(phone);
						channleXZDetailedInfoPo.setRegistTime(time);
						channleXZDetailedInfoPo.setStatus(1);
						channleXZDetailedInfoPo.setType(1);
						channleXZDetailedInfoPo.setSource(2);// 1:星座 2:魔镜
						channleXZDetailedInfoPo.setUserId(txzDownUserInfoPo.getUserId());
						// channleXZDetailedInfoPo.setXzId(0);
					}
					channleXZDetailedInfoPo.setTypeName(typeName);
					channleXZDetailedInfoPo.setName(name);
					channleXZDetailedInfoPo.setNameNo(Integer.valueOf(nameNo));
					downLoadDetailedService.saveOrUpdateTChannleXZtailedInfoPo(channleXZDetailedInfoPo);

				}

				if (pageSize + 1 == i)
					continue;

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
		} catch (Exception e) {
			driver.switchTo().defaultContent();
			driver.quit();
			e.printStackTrace();
		}
	}

}
