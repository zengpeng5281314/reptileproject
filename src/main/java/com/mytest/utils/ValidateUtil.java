package com.mytest.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
* @ClassName: ValidateUtil 
* @Description: 数据校验类 
* @author create by yushengwei @ 2015年8月12日 下午4:10:36
*/
public class ValidateUtil {
	  static Pattern MOBILE = Pattern.compile("^(((13[0-9]{1})|(15[0-9]{1})|(14[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\\d{8})$");
	  
	  /**
	  * @Title: checkMobile 
	  * @Description:(手机号校验) 
	  * @author create by yushengwei @ 2015年8月12日 下午4:10:56 
	  * @param @param xmobile
	  * @param @return 
	  * @return boolean 返回类型 
	  * @throws
	   */
	  public static boolean checkMobile(String xmobile) {
	          if (null == xmobile || "".equals(xmobile) ) {//StringUtils.isBlank(xmobile)
	               return false;
	          }
	          if(xmobile.length()> 11) {
	               return false;
	          }
	          return MOBILE.matcher(xmobile).matches();
	     }
	    
	  /**
	  * @Title: StringAllFilter 
	  * @Description:(这里用一句话描述这个方法的作用) 
	  * @author create by yushengwei @ 2015年8月12日 下午4:11:56 
	  * @param @param str
	  * @param @return 
	  * @return String 返回类型 
	  * @throws
	   */
	    public static String StringAllFilter(String   str){    
	        // 只允许字母和数字 "[^a-zA-Z]";  清除掉所有特殊字符 
	       String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？0123456789]"; 
	       Pattern   p   =   Pattern.compile(regEx);    
	       Matcher   m   =   p.matcher(str);    
	       return    m.replaceAll("").trim();    
	    }
	    
	    /**
	    * @Title: StringFilter 
	    * @Description:(这里用一句话描述这个方法的作用) 
	    * @author create by yushengwei @ 2015年8月12日 下午4:15:22 
	    * @param @param str
	    * @param @return 
	    * @return String 返回类型 
	    * @throws
	     */
	    public static String StringFilter(String   str){    
	        // 只允许字母和数字 "[^a-zA-Z0-9]";  清除掉所有特殊字符 
	       String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]"; 
	       Pattern   p   =   Pattern.compile(regEx);    
	       Matcher   m   =   p.matcher(str);    
	       return    m.replaceAll("").trim();    
	    }
	    

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
