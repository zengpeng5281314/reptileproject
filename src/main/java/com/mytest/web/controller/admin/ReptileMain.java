package com.mytest.web.controller.admin;

import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.mytest.utils.FileDownLoad;

public class ReptileMain {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");

		WebDriver driver = new ChromeDriver();
		driver.get("http://whfq.hsxia.cn/business/");
		WebElement loginName = driver.findElement(By.xpath("//*[@id=\"username\"]"));
		loginName.sendKeys("shen");
		WebElement loginPwd = driver.findElement(By.xpath("//*[@id=\"password\"]"));
		loginPwd.sendKeys("123321");
		WebElement code = driver.findElement(By.id("captchaImg"));
//		code.click();
//		String src = code.getAttribute("src");
		try {
//			URL videoImagePathUrl = new URL("http://peiqibao.zhulidao.com/getCode?1560157303192"); 
//			System.out.println(src);
//			File file = new File(videoImagePathUrl.toURI());
			System.out.println(new Date());
//			File file = new File("C:\\Users\\RYX\\Documents\\11.jpg");
//			FileDownLoad.downLoadFromUrl(src,"11.jpg","C:\\Users\\RYX\\Documents");
			String verifyCodeStr = FileDownLoad.captchCode(driver, code, "C:\\Users\\zengp\\Documents\\11.png");
			//File file = new File("C:\\Users\\RYX\\Documents\\11.png");
			
			//String verifyCodeStr = CaptchaUtil.convert(file, "1004"); // 调用第三方自动解析验证码接口获取验证码值
			
			
//			byte[] byteArr = FileDownLoad.readInputStream(src);
//			String verifyCodeStr = CaptchaUtil.convert(byteArr, "1004");
			System.out.println("verifyCode:" + verifyCodeStr);
			
			WebElement captcha = driver.findElement(By.xpath("//*[@id=\"captcha\"]"));
			captcha.sendKeys(verifyCodeStr.toUpperCase());
			WebElement loginBtn = driver.findElement(By.xpath("//*[@id=\"loginBtn\"]"));
			loginBtn.click();
			
//			driver.get("http://whfq.hsxia.cn/business/link/sub/list");
			
			detail(driver);
			
		}catch (Throwable e11) {
			e11.printStackTrace();
		}
	}

	private static void detail(WebDriver driver) {
		WebElement zw = driver.findElement(By.xpath("//*[@id=\"side-menu\"]/li[3]/a"));
		zw.click();
		
		WebElement z = driver.findElement(By.xpath("//*[@id=\"side-menu\"]/li[3]/ul/li/a"));
		z.click();
		
		WebElement frame = driver.findElement(By.xpath("//*[@id='content-main']/iframe[2]"));
		driver = driver.switchTo().frame(frame);
		
		WebElement one = driver.findElement(By.xpath("//*[@id=\"listForm\"]/tbody/tr[1]/td[8]/a"));
		one.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WebElement oneC = driver.findElement(By.xpath("//*[@id=\"app\"]/div[2]/table/tbody/tr[1]/td[9]/a"));
		oneC.click();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.navigate().back();
		
		//driver.switchTo().defaultContent();
	}

}
