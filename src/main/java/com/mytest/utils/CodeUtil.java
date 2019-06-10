package com.mytest.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class CodeUtil {

	private static AtomicInteger mfOrderCounter = new AtomicInteger(0);
	
	
	private static AtomicInteger gfOrderCounter = new AtomicInteger(0);
	
	
	/**
	 * 获取MF订单编号
	 */
	public static String getMFOrderCode() {
		/*if (mfOrderCounter.get() > 999999) {
			mfOrderCounter.set(1);
		}*/
		RandomGUID myGUID = new RandomGUID();
		StringBuffer buffer  = new StringBuffer();
		buffer.append("MF");
		buffer.append(DateUtils.getCurDateStr("yyyyMMdd"));
		/*buffer.append(String.format("%06d", mfOrderCounter.getAndIncrement()));*/
		buffer.append(myGUID.toString().substring(0,6));
		return buffer.toString();
	}
	
	
	/**
	 * 获取订单编号
	 */
	public static String getGFOrderCode() {
		/*if (gfOrderCounter.get() > 999999) {
			gfOrderCounter.set(1);
		}*/
		RandomGUID myGUID = new RandomGUID();
		StringBuffer buffer  = new StringBuffer();
		buffer.append("GF");
		buffer.append(DateUtils.getCurDateStr("yyyyMMdd"));
		buffer.append(myGUID.toString().substring(0,6));
		return buffer.toString();
	}
	
}
