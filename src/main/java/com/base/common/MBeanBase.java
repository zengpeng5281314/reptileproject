package com.base.common;

import java.io.Serializable;
import java.lang.reflect.Field;


import com.base.annotation.FormMeta.MFormOption;

import net.sf.json.JSONObject;

@SuppressWarnings("serial")
public abstract class MBeanBase implements Serializable {
	
	public static Object jget(JSONObject jobj, String key){
		try{
			return jobj.get(key);
		}catch(Exception e){
			return null;
		}
	}
	
	public static int jgetInt(JSONObject jobj, String key){
		return jgetInt(jobj, key, 0);
	}
	
	public static int jgetInt(JSONObject jobj, String key, int defvalue){
		try{
			return jobj.getInt(key);
		}catch(Exception e){
			return defvalue;
		}
	}
	
	public JSONObject toJSONObject(){
		return new JSONObject();
	}
	
	public Object get(String fieldname){
		try{
			Field field = this.getClass().getDeclaredField(fieldname);
			field.setAccessible(true);
			return field.get(this);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public String getOptionText(String field, Object value){
		BeanInfo beanInfo = BeanInfo.getBeanInfo(this.getClass());
		SortableField mfield = beanInfo.getField(field);
		return MFormOption.getOptionText(mfield.getFormoptions(), String.valueOf(value));
	}
	
}
