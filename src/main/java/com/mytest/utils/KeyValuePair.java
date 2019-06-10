package com.mytest.utils;

import java.io.Serializable;
import java.util.List;

/**
 * 键值对
 * @author zwt
 *
 */
public class KeyValuePair implements Serializable {
	
	private static final long serialVersionUID = -5575999157515852579L;

	public enum Operater {
		EQ,		// 等于（默认）
		NOT,	// 不等于
		IN,		// 包含
		LIKE,    //此时空将转换为空字符串
		GT,       //大于
		ET,       //小于
		GTANDEQ,  //大于等于
		ETANDEQ   //小于等于
	}
	
	private String key;
	private Object value;
	private Operater op;
	private String keyalias;
	private Long[] longValue;
	private Integer[] intVlaue;
	
	public Integer[] getIntVlaue() {
		return intVlaue;
	}

	public void setIntVlaue(Integer[] intVlaue) {
		this.intVlaue = intVlaue;
	}

	public Long[] getLongValue() {
		return longValue;
	}

	public void setLongValue(Long[] longValue) {
		this.longValue = longValue;
	}

	public String getKeyalias() {
		return keyalias;
	}

	public void setKeyalias(String keyalias) {
		this.keyalias = keyalias;
	}

	public KeyValuePair(String key, Object value){
		this.key = key;
		this.value = value;
		
		if(value instanceof List)
			this.op = Operater.IN;
		else
			this.op = Operater.EQ;
	}
	
	public KeyValuePair(Operater op, String key, Object value){
		this.op = op;
		this.key = key;
		
		if(value instanceof int[]){
			int[] varray = (int[])value;
			intVlaue = new Integer[varray.length];
			for(int i=0;i<varray.length;i++){
				intVlaue[i] = varray[i];
			}
		}else if(value instanceof long[]){
			long[] varray = (long[])value;
			longValue = new Long[varray.length];
			for(int i=0;i<varray.length;i++){
				longValue[i] = varray[i];
			}
		}
			this.value = value;
	}

	public static void main(String[] args) {
		long[] varray = new long[]{1,2,3};
		Long[] longArray = new Long[varray.length];
		for(int i=0;i<varray.length;i++){
			longArray[i] = varray[i];
		}
		Object value = longArray;
		
		System.out.println((Object[])value);
	}
	
	public Operater getOp() {
		return op;
	}

	public String getKey() {
		return key;
	}

	public Object getValue() {
		return value;
	}

	public String getValueSQL() {
		if(value instanceof String)
			return "'" + value + "'";
		else 
			return String.valueOf(value);
	}
	
	public void setKey(String key) {
		this.key = key;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString(){
		return key + " " + op.toString() + " " + value;
	}
}
