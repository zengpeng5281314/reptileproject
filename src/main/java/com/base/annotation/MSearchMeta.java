package com.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * MFramework MBean搜索字段定义
 *
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MSearchMeta {
	
	/** 选项表单类型 */
	public enum MSEARCHTYPE {
		INDEX("index"),				// 索引（下拉菜单索引）
		ALONELIKE("alonelike"),		// 模糊查询（独立该字段）
		COMPLEXLIKE("complexlike"),		// 模糊查询（多字段一个输入框）
		COMPLEXEQUALS("complexequals"),	// 精确查询（多字段一个输入框）
		RANGE("range");				// 范围（数值、日期）

	    private final String val;

	    MSEARCHTYPE(String val) {
	        this.val = val;
	    }

	    public String getUploadtype() {
	        return val;
	    }
	}
	
	/**
	 * Date类型时的输入输出格式
	 */
	MSEARCHTYPE searchtype();
	
	
	String searchdefvalue() default "";
	
	
}
