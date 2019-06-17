package com.mytest.web.controller.admin;

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
		driver.get("https://channel.mcdonald.gamescpu.com:8090/#/login");
		WebElement loginName = driver.findElement(By.xpath("//*[@id=\"name\"]"));
		loginName.sendKeys("accc12");
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

		// WebElement frame =
		// driver.findElement(By.xpath("//*[@id='content-main']/iframe[2]"));
		// driver = driver.switchTo().frame(frame);

		WebElement loanNum = driver.findElement(By.xpath(
				"//*[@id=\"root\"]/div/div[2]/div[2]/div/div[2]/div/div/div/div[2]/div/div/div/div/div/div/div/table/tbody/tr/td[3]"));
		System.out.println("---" + loanNum.getText());

		WebElement applactionNum = driver.findElement(By.xpath(
				"//*[@id=\"root\"]/div/div[2]/div[2]/div/div[2]/div/div/div/div[2]/div/div/div/div/div/div/div/table/tbody/tr/td[2]"));
		System.out.println("---" + applactionNum.getText());

		// //下载
		WebElement one = driver.findElement(
				By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div/div[2]/div/div/div/div[1]/form/div/div[4]/button"));
		one.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.get("chrome://downloads/");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("---" + driver.getPageSource());
		WebElement fileName = driver.findElement(By.xpath("//*[@id=\"title-area\"]"));

		System.out.println("---" + fileName.getText());

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
