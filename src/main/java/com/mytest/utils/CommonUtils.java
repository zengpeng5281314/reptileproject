package com.mytest.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

public class CommonUtils {

	public static String getDate(){
		return getDate(new Date());
	}
	
	public static String getDate(String format){
		return getDate(new Date(), format);
	}
	
	public static String getDate(Date date){
		return getDate(date, "yyyy-MM-dd HH:mm:ss");
	}
	
    /**
     * 获取年纪
     * @param dateOfBirth
     * @return
     */
	public static int getAge(Date dateOfBirth) {
		int age = 0;
		Calendar born = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		if (dateOfBirth != null) {
			now.setTime(new Date());
			born.setTime(dateOfBirth);
			if (born.after(now)) {
				return 0;
                // throw new IllegalArgumentException("年龄不能超过当前日期");
			}
			age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
			int nowDayOfYear = now.get(Calendar.DAY_OF_YEAR);
			int bornDayOfYear = born.get(Calendar.DAY_OF_YEAR);
            // System.out.println("nowDayOfYear:" + nowDayOfYear + " bornDayOfYear:" + bornDayOfYear);
			if (nowDayOfYear < bornDayOfYear) {
				age -= 1;
			}
		}
		return age;
	}
	 
	public static String getDate(Date date, String format) {
		if(date==null)
			return "-";
		
        // Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.CHINA);
        String datenewformat = formatter.format(date);
        return datenewformat;
    }
	
	/**
     * MD5加密函数
     * @param s 加密源字符串
     * @return MD5加密后的字符串
     */
	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String replace(String str, String substr, String restr) {
		String tmp[] = split(str, substr);
		String returnstr = null;
		if (tmp.length != 0) {
			returnstr = tmp[0];
			for (int i = 0; i < tmp.length - 1; i++)
				returnstr = dealNull(returnstr) + restr + tmp[i + 1];
		}
		return dealNull(returnstr);
	}

    public static String[] split(String source, String div)
    {
        int arynum = 0;
        int intIdx = 0;
        int intIdex = 0;
        int div_length = div.length();
        if(source.compareTo("") != 0)
        {
            if(source.indexOf(div) != -1)
            {
                intIdx = source.indexOf(div);
                int intCount = 1;
                do {
                    if(source.indexOf(div, intIdx + div_length) != -1)
                    {
                        intIdx = source.indexOf(div, intIdx + div_length);
                        arynum = intCount;
                    } else
                    {
                        arynum += 2;
                        break;
                    }
                    intCount++;
                } while(true);
            } else {
                arynum = 1;
            }
        } else {
            arynum = 0;
        }
        intIdx = 0;
        intIdex = 0;
        String returnStr[] = new String[arynum];
        if(source.compareTo("") != 0)
        {
            if(source.indexOf(div) != -1)
            {
                intIdx = source.indexOf(div);
                returnStr[0] = source.substring(0, intIdx);
                int intCount = 1;
                do {
                    if(source.indexOf(div, intIdx + div_length) != -1)
                    {
                        intIdex = source.indexOf(div, intIdx + div_length);
                        returnStr[intCount] = source.substring(intIdx + div_length, intIdex);
                        intIdx = source.indexOf(div, intIdx + div_length);
                    } else {
                        returnStr[intCount] = source.substring(intIdx + div_length, source.length());
                        break;
                    }
                    intCount++;
                } while(true);
            } else {
                returnStr[0] = source.substring(0, source.length());
                return returnStr;
            }
        } else
        {
            return returnStr;
        }
        return returnStr;
    }
    
	public static String dealNull(String str) {
		String returnstr = null;
		if (str == null)
			returnstr = "";
		else
			returnstr = str;
		return returnstr;
	}

	// Verify that no character has a hex value greater than 0xFFFD, or less
	// than 0x20.
	// Check that the character is not equal to the tab ("t), the newline ("n),
	// the carriage return ("r), or is an invalid XML character below the range
	// of 0x20. If any of these characters occur, an exception is thrown.
	public static String CheckUnicodeString(String values) {
		char[] value = new char[values.length()];
		value = values.toCharArray();
		for (int i = 0; i < values.length(); ++i) {
			if (value[i] > 0xFFFD) {
				value[i] = '\n';

                // throw new Exception("Invalid Unicode");//或者直接替换掉0x0
				// value[i]='"n';
			}

			else if (value[i] < 0x20 && value[i] != '\t' & value[i] != '\n'
					& value[i] != '\r') {
				value[i] = '\n';

                // throw new Exception("Invalid Xml Characters");//或者直接替换掉0x0
				// value[i]='"n';
			}
		}

		String s = new String(value);
		return s;
	}
	
	public static String getStackTrace(Throwable aThrowable) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		aThrowable.printStackTrace(printWriter);
		return result.toString();
	}

	public static String encodeURLByUTF8(String value){
		try{
			return URLEncoder.encode(value, "UTF-8");
		} catch(Exception e){
			return value;
		}
	}
	
    // 非法字符过滤（防止HTMl注入攻击）
	public static String filterIllegalCharacter(String str){
		Pattern pattern = Pattern.compile("<([^>]*)>|(['\"])");
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result1 = matcher.find();
		while (result1) {
			matcher.appendReplacement(sb, "");
			result1 = matcher.find();
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
	
    // HQL字段名，非法字符过滤
	public static String filterIllegalKey(String keyname){
		keyname = keyname.replaceAll("'", "");
		keyname = keyname.replaceAll(";", "");
		keyname = keyname.replaceAll("\"", "");
		keyname = keyname.replaceAll("/", "");
		return keyname;
	}
	
    // HQL拼接时对value进行转义
	public static String escapingValue(String keyname){
		keyname = keyname.replaceAll("'", "\'");
		return keyname;
	}
	
    // 转换text为HTML
	public static String text2html(String text){
		return CommonUtils.replace(text, "\n", "<br/>");
	}
	
    // 剔除参数串中的指定一个或多个变量
	public static String filterQueryString(String querystring, String... removekeys){
		if(StringUtils.isEmpty(querystring))
			return querystring;
		
		for(String removekey : removekeys){
			if(StringUtils.isEmpty(removekey))
				continue;
			
			int pos1 = querystring.indexOf(removekey + "=");
			if(pos1>=0){
				int pos2 = querystring.indexOf("&", pos1);
				pos2 = pos2>=0 ? pos2+1 : querystring.length();
				
				querystring = querystring.substring(0, pos1) + querystring.substring(pos2);
				if(querystring.endsWith("&"))
					querystring = querystring.substring(0, querystring.length()-1);
			}
		}
		return querystring;
	}
	
	public static boolean isClass(Object obj, String name){
		return obj.getClass().getName().equals(name) || obj.getClass().getSimpleName().equals(name);
	}
	
}
