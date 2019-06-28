package com.mytest.web.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class XingZuoReptileMain {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
		DesiredCapabilities caps = setDownloadsPath();//更改默认下载路径		
		
		WebDriver driver = new ChromeDriver(caps);
		driver.get("https://channel.mieba.4ho7lk.cn:8090/#/login");
		WebElement loginName = driver.findElement(By.xpath("//*[@id=\"name\"]"));
		loginName.sendKeys("cha");
		WebElement loginPwd = driver.findElement(By.xpath("//*[@id=\"password\"]"));
		loginPwd.sendKeys("123456");
		try {
			WebElement loginBtn = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/form/button"));
			loginBtn.click();

			detail(driver);

		} catch (Throwable e11) {
			e11.printStackTrace();
		}
	}

	private static void detail(WebDriver driver) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WebElement zw = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[1]/div/ul/li"));
		zw.click();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		
		WebElement channel = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div/div[2]/div/div/div/div[1]/form/div/div[1]/div/div[2]/div/span/div/div/div"));
		channel.click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
		WebElement search = driver.findElement(By.xpath(
				"//*[@id=\"root\"]/div/div[2]/div[2]/div/div[2]/div/div/div/div[1]/form/div/div[3]/div/div/div/span/span/button[1]"));
		search.click();
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

		
//		//下载
//		WebElement one = driver.findElement(
//				By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div/div[2]/div/div/div/div[1]/form/div/div[4]/button"));
//		one.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.quit();

		// *[@id="root"]/div/div[2]/div[2]/div/div[2]/div/div/div/div[2]/div/div/div/div/div/div/div/table/tbody/tr/td[2]
		// try {
		// Thread.sleep(2000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// WebElement oneC =
		// driver.findElement(By.xpath("//*[@id=\"app\"]/div[2]/table/tbody/tr[1]/td[9]/a"));
		// oneC.click();
		//
		// try {
		// Thread.sleep(2000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// driver.navigate().back();

		// driver.switchTo().defaultContent();
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
