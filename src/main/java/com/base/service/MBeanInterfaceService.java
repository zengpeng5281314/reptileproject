package com.base.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.base.common.BeanInfo;
import com.base.common.BeanInterface;
import com.base.common.MBeanBase;
import com.base.common.MParam;

/**
 * MBean接口处理器
 * @author zwt
 *
 */
@Service
public class MBeanInterfaceService {
	
	private BeanInterface<? extends MBeanBase> buildInterface(BeanInfo beanInfo){
		try{
			return beanInfo.getBeaninterface().newInstance();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	// 根据主键获取
	public MBeanBase get(BeanInfo beanInfo, String id){
		BeanInterface<? extends MBeanBase> beanInterface = buildInterface(beanInfo);
		return beanInterface.get(id);
	}
	
	// 根据参数获取
	public Object get(BeanInfo beanInfo, MParam mparam){
		BeanInterface<?> beanInterface = buildInterface(beanInfo);
		return beanInterface.get(mparam);
	}
	
	public List<?> list(BeanInfo beanInfo, MParam mparam){
		BeanInterface<?> beanInterface = buildInterface(beanInfo);
		return beanInterface.list(mparam);
	}
}
