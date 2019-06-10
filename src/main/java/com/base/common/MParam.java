package com.base.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.mytest.utils.KeyValuePair;
import com.mytest.utils.KeyValuePair.Operater;
import com.mytest.utils.UUIDGenerator;

/**
 * 参数组
 *
 */
public class MParam {
	
	public enum LogicalOp{
		AND,
		OR
	}
	
	public static MParam build(String... keyvalue){
		if(keyvalue.length % 2 != 0)
			return null;
		
		MParam mparam = new MParam();
		for(int i=0;i<keyvalue.length;i=i+2){
			mparam.add(keyvalue[i], keyvalue[i+1]);
		}
		return mparam;
	}
	
	public static MParam build(String orderbyHQL){
		MParam mparam = new MParam();
		mparam.orderbyHQL = orderbyHQL;
		return mparam;
	}

	// 字段条件
	private List<KeyValuePair> params = new ArrayList<KeyValuePair>();
	
	private LogicalOp logicalOp = LogicalOp.AND;
	
	// 排序（仅用于HQL执行部分）
	private String orderbyHQL = "";	// order by xxx

	public MParam(){
	}

	public MParam(LogicalOp logicalOp){
		this.logicalOp = logicalOp;
	}
	
	public MParam(String keyname, Object value){
		params.add(new KeyValuePair(keyname, value));
	}
	
	public MParam(String keyname, Object value, String orderbyHQL){
		params.add(new KeyValuePair(keyname, value));
		this.orderbyHQL = orderbyHQL;
	}
	
	public void clear(){
		params.clear();
	}
	
	public void remove(String keyname){
		for(KeyValuePair kvp : params){
			if(kvp.getKey().equals(keyname)){
				params.remove(kvp);
				break;
			}
		}
	}

	public MParam add(String keyname, Object value){
		if(logicalOp==LogicalOp.AND){
			remove(keyname);
		}
		
		params.add(new KeyValuePair(keyname, value));
		return this;
	}

	public MParam add(KeyValuePair kvp){
		if(logicalOp==LogicalOp.AND && kvp.getOp()==Operater.EQ){
			remove(kvp.getKey());
		}
		
		params.add(kvp);
		return this;
	}
	
	public boolean has(String keyname){
		for(KeyValuePair kvp : params){
			if(kvp.getKey().equals(keyname)){
				return true;
			}
		}
		return false;
	}
	
	public Object get(String keyname){
		for(KeyValuePair kvp : params){
			if(kvp.getKey().equals(keyname)){
				return kvp.getValue();
			}
		}
		return null;
	}
	
	public String getString(String keyname){
		return (String)get(keyname);
	}
	
	public LogicalOp getLogicalOp() {
		return logicalOp;
	}

	public boolean isEmpty(){
		return params.isEmpty();
	}
	
	public void setOrderbyHQL(String orderbyHQL) {
		this.orderbyHQL = orderbyHQL;
	}

	public String getOrderbyHQL() {
		return orderbyHQL;
	}
	
	public String toQueryString(){
		String querystring = "";
		List<KeyValuePair> kvpList = getHqlKVPList();
		for(KeyValuePair kvp : kvpList){
			querystring += kvp.getKey() + "=" + kvp.getValue() + "&";
		}
		
		if(querystring.length()>0)
			querystring = querystring.substring(0, querystring.length()-1);
		
		return querystring;
	}

	@Override
	public String toString(){
		return "[" + toQueryString() + "]";
	}
	
	// 转换为健值对列表，用于DAO顺序处理（过滤掉非String/int类型参数）
	public List<KeyValuePair> getHqlKVPList(){
		List<KeyValuePair> hqlKvpList = new ArrayList<KeyValuePair>();
		Set<String> keys = new HashSet<String>();
		for(KeyValuePair kvp : params){
			if(keys.contains(kvp.getKey())){
				kvp.setKeyalias("a"+UUIDGenerator.getUUID());
			}else if(kvp.getKey().contains(".")){
				kvp.setKeyalias("a"+UUIDGenerator.getUUID());
			}else
				keys.add(kvp.getKey());
			if(kvp.getValue()==null
					|| kvp.getValue() instanceof String
					|| kvp.getValue() instanceof Integer
					|| kvp.getValue() instanceof Double
					|| kvp.getValue() instanceof Long
					|| kvp.getValue() instanceof Short
					|| kvp.getValue() instanceof Byte
					|| kvp.getValue() instanceof Char
					|| kvp.getValue() instanceof Date
					|| kvp.getValue() instanceof String[]
					|| kvp.getValue() instanceof Integer[]
					|| kvp.getValue() instanceof Long[]
					|| kvp.getValue() instanceof int[]
					|| kvp.getValue() instanceof long[]
					|| kvp.getValue() instanceof List){
				hqlKvpList.add(kvp);
			}
		}
		
		return hqlKvpList;
	}
}
