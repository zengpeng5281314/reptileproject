package com.base.common;

import java.util.List;



/**
 * MBean对像数据源接口处理器
 *
 */
public abstract class BeanInterface<T> {
	
	public abstract T get(String id);
	
	public abstract T get(MParam mparam);
	
	public abstract List<T> list(MParam mparam);
	
}
