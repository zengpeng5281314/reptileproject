package com.mytest.utils;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.KeyEvent;
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
     * 浠庣綉缁淯rl涓笅杞芥枃浠�
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static void  downLoadFromUrl(String urlStr,String fileName,String savePath) throws IOException{
        URL url = new URL(urlStr);  
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
                //璁剧疆瓒呮椂闂翠负3绉�
        conn.setConnectTimeout(3*1000);
        //闃叉灞忚斀绋嬪簭鎶撳彇鑰岃繑鍥�403閿欒
//        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//        conn.setRequestMethod("POST");
        //寰楀埌杈撳叆娴�
        InputStream inputStream = conn.getInputStream();  
        //鑾峰彇鑷繁鏁扮粍
        byte[] getData = readInputStream(inputStream);    

        //鏂囦欢淇濆瓨浣嶇疆
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
                //璁剧疆瓒呮椂闂翠负3绉�
        conn.setConnectTimeout(3*1000);
        //闃叉灞忚斀绋嬪簭鎶撳彇鑰岃繑鍥�403閿欒
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //寰楀埌杈撳叆娴�
        InputStream inputStream = conn.getInputStream();  
        //鑾峰彇鑷繁鏁扮粍
        byte[] getData = readInputStream(inputStream);    
        return getData;
    }


    /**
     * 浠庤緭鍏ユ祦涓幏鍙栧瓧鑺傛暟缁�
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
            downLoadFromUrl("http://aft.ffgwfc.xyz:8080/back/getVolidateCode?flag=0.32517668923828946",
                    "11.jpg","C:\\Users\\RYX\\Documents");
//        	File f = new File("C:\\Users\\RYX\\Documents\\11.jpg");
//           String verifyCodeStr = CaptchaUtil.convert(f, "1004"); // 璋冪敤绗笁鏂硅嚜鍔ㄨВ鏋愰獙璇佺爜鎺ュ彛鑾峰彇楠岃瘉鐮佸��
//           System.out.println(verifyCodeStr);
        }catch (Exception e) {
            // TODO: handle exception
        	e.printStackTrace();
        }
    }
    
    public static String sibie(String path){
    	File f = new File(path);
    	String verifyCodeStr = CaptchaUtil.convert(f, "1004"); // 璋冪敤绗笁鏂硅嚜鍔ㄨВ鏋愰獙璇佺爜鎺ュ彛鑾峰彇楠岃瘉鐮佸��
		f.delete();
		return verifyCodeStr;
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
    		verifyCodeStr = CaptchaUtil.convert(f, "1004"); // 璋冪敤绗笁鏂硅嚜鍔ㄨВ鏋愰獙璇佺爜鎺ュ彛鑾峰彇楠岃瘉鐮佸��
    		f.delete();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
		return verifyCodeStr;
    }
    
    public static String captchCode1(WebDriver driver,WebElement verifyCode ,String path){
    	String verifyCodeStr = "";
    	try{
    		//姝ゆ柟娉曚粎閫傜敤浜嶫dK1.6鍙婁互涓婄増鏈�  
    	    Robot robot = new Robot();  
    	    robot.delay(10000);  
    	    Dimension d = verifyCode.getSize();
    	    int width = (int) d.getWidth();  
    	    int height = (int) d.getHeight();  
    	    //鏈�澶у寲娴忚鍣�  
    	    robot.keyRelease(KeyEvent.VK_F11);  
    	    robot.delay(2000);  
    	    Image image = robot.createScreenCapture(new Rectangle(0, 0, width,  
    	            height));  
    	    BufferedImage bi = new BufferedImage(width, height,  
    	            BufferedImage.TYPE_INT_RGB);  
    	    Graphics g = bi.createGraphics();  
    	    g.drawImage(image, 0, 0, width, height, null);  
    	    File f = new File(path);
    	    //淇濆瓨鍥剧墖  
    	    ImageIO.write(bi, "jpg", f);  
    	    verifyCodeStr = CaptchaUtil.convert(f, "1004"); // 璋冪敤绗笁鏂硅嚜鍔ㄨВ鏋愰獙璇佺爜鎺ュ彛鑾峰彇楠岃瘉鐮佸��
    		f.delete();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
		return verifyCodeStr;
    }
    
    /*
	 * 鍥剧墖瑁佸壀
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
