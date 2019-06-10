package com.base.common;

import java.util.List;



/**
 * MBean对像数据源接口处理器
 *
 */
public class NoneBeanInterface extends BeanInterface<MBeanBase> {

	@Override
	public MBeanBase get(String id) {
		return null;
	}

	@Override
	public MBeanBase get(MParam mparam) {
		return null;
	}

	@Override
	public List<MBeanBase> list(MParam mparam) {
		// TODO Auto-generated method stub
		return null;
	}
	
}