package com.mytest.web.controller.admin;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.mytest.utils.FileDownLoad;

public class MoJingReptileMain {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
		DesiredCapabilities caps = setDownloadsPath();// 更改默认下载路径

		WebDriver driver = new ChromeDriver(caps);
		driver.get("http://47.111.102.135:8082/back/static/login.html");
		WebElement loginName = driver.findElement(By.xpath("//*[@id=\"userName\"]"));
		loginName.sendKeys("13020203030");
		WebElement loginPwd = driver.findElement(By.xpath("//*[@id=\"password\"]"));
		loginPwd.sendKeys("6b8550af");
		WebElement code = driver.findElement(By.xpath("/html/body/div/div/div[4]/img"));
		File file = new File("C:\\Users\\RYX\\Documents\\11.jpg");
		String src = code.getAttribute("src");
		try {
			FileDownLoad.downLoadFromUrl(src, "11.jpg", "C:\\Users\\RYX\\Documents");
		} catch (IOException e) {
			e.printStackTrace();
		}
		String verifyCodeStr = FileDownLoad.captchCode(driver, code, "C:\\Users\\RYX\\Documents\\11.jpg");

		WebElement acode = driver.findElement(By.xpath("//*[@id=\"code\"]"));
		acode.sendKeys(verifyCodeStr);
		try {
			WebElement loginBtn = driver.findElement(By.xpath("//*[@id=\"login\"]"));
			loginBtn.click();

			detail(driver);
		} catch (Throwable e11) {
			driver.switchTo().defaultContent();
			driver.quit();
			e11.printStackTrace();
		}
	}

	private static void detail(WebDriver driver) {
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
			beginTime.sendKeys("2019-06-29");
			WebElement endTime = driver.findElement(By.xpath("//*[@id=\"endTime\"]"));
			endTime.sendKeys("2019-06-29");
			WebElement search = driver.findElement(By.xpath("//*[@id=\"search\"]"));
			search.click();
			
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
			int pageSize = countT%200 == 0 ? (countT/200) : (countT/200)+1;
			for (int i = 1; i <= pageSize; i++) {
				WebElement table = driver.findElement(By.className("layui-table-main")).findElement(By.className("layui-table"));

				System.out.println(table.getText());
				
				  List<WebElement> rows = table.findElements(By.tagName("tr"));
//				  assertEquals(5,rows.size());
				  for(WebElement row:rows){
					  List<WebElement> cols= row.findElements(By.tagName("td"));
					  for(WebElement col:cols){
						  System.out.println(col.getText()+"\t");			  
					  }
					  System.out.println("");
				  }
				
				Thread.sleep(2000);
				WebElement currentPage = driver.findElement(By.className("layui-laypage-skip")).findElement(By.className("layui-input"));
				WebElement button = driver.findElement(By.className("layui-laypage-skip")).findElement(By.className("layui-laypage-btn"));
				
//				int j=5;
//				if(i>=6)
//					j=6;
//				if(i>=11)
//					j=5;
//				WebElement currentPage = driver.findElement(By.xpath("//*[@id=\"layui-laypage-"+i+"\"]/span["+j+"]/input"));
				currentPage.clear();
				currentPage.sendKeys(i+"");
//				WebElement button = driver.findElement(By.xpath("//*[@id=\"layui-laypage-"+i+"\"]/span["+j+"]/button"));
				button.click();
			}
			if(1==1)
				return;
			
			
			
			Thread.sleep(1000);
			WebElement channels = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/ul/li[6]"));
			channels.click();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			String date = sdf.format(new Date());
			// 选择时间
			WebElement start = driver.findElement(By.xpath("//*[@id=\"range-picker\"]/span/input[1]"));
			start.click();
			WebElement startime = driver
					.findElement(By.xpath("/html/body/div[3]/div/div/div/div/div[1]/div[1]/div[1]/div/input"));
			startime.sendKeys(date);
			WebElement endtime = driver
					.findElement(By.xpath("/html/body/div[3]/div/div/div/div/div[1]/div[2]/div[1]/div/input"));
			endtime.sendKeys(date);
		
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			int registNums = 0;
			WebElement registNum = driver.findElement(By.xpath(
					"//*[@id=\"root\"]/div/div[2]/div[2]/div/div[2]/div/div/div/div[2]/div/div/div/div/div/div/div/table/tbody/tr/td[1]"));
			System.out.println("---" + registNum.getText());
			registNums = Integer.valueOf(registNum.getText());

			WebElement applactionNum = driver.findElement(By.xpath(
					"//*[@id=\"root\"]/div/div[2]/div[2]/div/div[2]/div/div/div/div[2]/div/div/div/div/div/div/div/table/tbody/tr/td[2]"));
			System.out.println("---" + applactionNum.getText());

			driver.quit();
		} catch (InterruptedException e) {
			driver.switchTo().defaultContent();
			driver.quit();
			e.printStackTrace();
		}
	}

	// 单独重构成一个方法，然后调用
	public static DesiredCapabilities setDownloadsPath() {
		String downloadsPath = "D:\\dataSource\\outputReport\\Downloads";
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("download.default_directory", downloadsPath);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability(ChromeOptions.CAPABILITY, options);
		return caps;
	}

}
