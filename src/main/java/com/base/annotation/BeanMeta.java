package com.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.base.common.BeanInterface;
import com.base.common.MBeanBase;
import com.base.common.NoneBeanInterface;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BeanMeta {
	
	/**
	 * 对像名称
	 */
	String name() default "";

	/**
	 * 对像别名（用于后台管理，不可重复）
	 */
	String alias() default "";

	/**
	 * 后台是否可创建更新删除操作
	 */
	boolean manageability() default true;

	/**
	 * 是否开启伪删除（需status字段，9为删除状态）
	 */
	boolean fakedelete() default false;
	
	/**
	 * 是否开启缓存支持
	 */
	boolean cache() default true;

	/**
	 * 数据源接口处理器
	 */
	Class<? extends BeanInterface<? extends MBeanBase>> beaninterface() default NoneBeanInterface.class;

	/**
	 * Cache数据有效性时间设置（单位秒），0为永久有效。（如dbttl设置有效时间，cachettl应小于或等于dbttl时长）
	 */
	int cachettl() default 0;

	/**
	 * DAO数据有效性时间设置（单位秒），0为永久有效且不支持MBean的list接口调用
	 */
	int dbttl() default 0;
	
}
