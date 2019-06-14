package com.mytest.utils;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class FileDownLoad {
	/**
     * 从网络Url中下载文件
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static void  downLoadFromUrl(String urlStr,String fileName,String savePath) throws IOException{
        URL url = new URL(urlStr);  
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
                //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //得到输入流
        InputStream inputStream = conn.getInputStream();  
        //获取自己数组
        byte[] getData = readInputStream(inputStream);    

        //文件保存位置
        File saveDir = new File(savePath);
        if(!saveDir.exists()){
            saveDir.mkdir();
        }
        File file = new File(saveDir+File.separator+fileName);    
        FileOutputStream fos = new FileOutputStream(file);     
        fos.write(getData); 
        if(fos!=null){
            fos.close();  
        }
        if(inputStream!=null){
            inputStream.close();
        }


        System.out.println("info:"+url+" download success"); 

    }

    public static byte[] readInputStream(String urlStr)throws IOException {  
    	URL url = new URL(urlStr);  
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
                //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //得到输入流
        InputStream inputStream = conn.getInputStream();  
        //获取自己数组
        byte[] getData = readInputStream(inputStream);    
        return getData;
    }


    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        while((len = inputStream.read(buffer)) != -1) {  
            bos.write(buffer, 0, len);  
        }  
        bos.close();  
        return bos.toByteArray();  
    }  

    public static void main(String[] args) {
        try{
            downLoadFromUrl("http://peiqibao.zhulidao.com/getCode?1560225937487",
                    "11.jpg","C:\\Users\\RYX\\Documents");
        }catch (Exception e) {
            // TODO: handle exception
        }
    }
    
    
    
    
    
    public static String captchCode(WebDriver driver,WebElement verifyCode ,String path){
    	String verifyCodeStr = "";
    	try{
    		File srcFile = getScreenshot(driver);
    		Dimension size = verifyCode.getSize();
    		Integer width = size.width;
    		Integer height = size.height;
    		int x=verifyCode.getLocation().x;
    		int y=verifyCode.getLocation().y;
    		File f = new File(path);
    		cutImage(new FileInputStream(srcFile), f, x, y, width, height);
    		verifyCodeStr = CaptchaUtil.convert(f, "1004"); // 调用第三方自动解析验证码接口获取验证码值
    		f.delete();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
		return verifyCodeStr;
    }
    
    /*
	 * 图片裁剪
	 */
	public static void cutImage(InputStream in, File dest, int x, int y, int w, int h) throws IOException {
		Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName("png");
		ImageReader reader = iterator.next();
		ImageInputStream iis = ImageIO.createImageInputStream(in);
		reader.setInput(iis, true);
		ImageReadParam param = reader.getDefaultReadParam();
		Rectangle rect = new Rectangle(x, y, w, h);
		param.setSourceRegion(rect);
		BufferedImage bi = reader.read(0, param);
		ImageIO.write(bi, "png", dest);
	}
	
    public static File getScreenshot(WebDriver driver){
		File srcFile = null;
		if(driver!=null){
			try{
				srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			}catch(Throwable e){
				
			}
		}
		return srcFile;
	}
}
