package com.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldMeta {
	
	/**
	 * 是否为创建时间
	 */
	boolean createtime() default false;
	
	/**
	 * 字段名称
	 */
	String name() default "";

	/**
	 * 创建时是否可编辑（editable=false时有效）
	 */
	boolean createable() default false;

	/**
	 * 是否可编辑（含创建时，权限>createable）
	 */
	boolean editable() default true;

	/**
	 * 是否在列表中显示
	 */
	boolean summary() default false;

	/**
	 * 排序字段
	 */
	int order() default 999;
	
}
