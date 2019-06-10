package com.base.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.base.annotation.BeanMeta;
import com.base.common.BeanInfo;
import com.base.common.MBeanBase;
import com.base.common.MParam;
import com.base.service.MBeanListService;
import com.base.service.MBeanService;
import com.base.service.MFrameworkService;
import com.mytest.utils.Page;


@Service
public class MFrameworkService {
	
	public static MFrameworkService mframeworkService;
	
	@Autowired
	private MBeanService mbeanservice;
	@Autowired
	private MBeanListService mbeanlistservice;
	
    // 获取对像列表（前50条）
	public <T> List<T> list(Class<? extends MBeanBase> beancls){
		return list(beancls, new Page(1, 50), null);
	}
	
    // 根据字段条件，获取对像列表（前50条）
	public <T> List<T> list(Class<? extends MBeanBase> beancls, MParam mparam){
		return list(beancls, new Page(1, 50), mparam);
	}
	
    // 获取对像列表（所有数据）
	public <T> List<T> listAll(Class<? extends MBeanBase> beancls){
		return list(beancls, null, null);
	}

    // 根据字段条件，获取对像列表（所有数据）
	public <T> List<T> listAll(Class<? extends MBeanBase> beancls, MParam mparam){
		return list(beancls, null, mparam);
	}

    // 根据字段条件，获取对像列表，支持翻页
	@SuppressWarnings("unchecked")
	public <T> List<T> list(Class<? extends MBeanBase> beancls, Page page, MParam mparam){
		BeanMeta beanMeta = beancls.getAnnotation(BeanMeta.class);
		if(beanMeta==null){
			return null;
		}
		
		BeanInfo beanInfo = BeanInfo.getBeanInfo(beancls);
		return (List<T>)mbeanlistservice.list(beanInfo, page, mparam);
	}
	
	public Page listPageInfo(Class<? extends MBeanBase> beancls, Page page, MParam mparam){
		BeanMeta beanMeta = beancls.getAnnotation(BeanMeta.class);
		if(beanMeta==null){
			return null;
		}
		
		BeanInfo beanInfo = BeanInfo.getBeanInfo(beancls);
		return mbeanlistservice.listPageInfo(beanInfo, page, mparam);
	}
	
    public List<?> listBySQL(String sql, Class<?> clazz){
        return mbeanlistservice.listBySQL(sql, clazz);
    }

    // 根据对像主键ID，获取对像
	@SuppressWarnings("unchecked")
	public <T> T get(Class<?> beancls, String id){
		if(StringUtils.isEmpty(id))
			return null;
		
		BeanInfo beanInfo = BeanInfo.getBeanInfo(beancls);
		return (T)mbeanservice.get(beanInfo, id);
	}
	
    // 根据一个或多个字段条件，获取对像
	@SuppressWarnings("unchecked")
	public <T> T get(Class<?> beancls, MParam mparam){
		BeanInfo beanInfo = BeanInfo.getBeanInfo(beancls);
		return (T)mbeanservice.get(beanInfo, mparam);
	}

    // 根据对像主键ID，删除对像
	public void delete(Class<? extends MBeanBase> beancls, String id){
		BeanInfo beanInfo = BeanInfo.getBeanInfo(beancls);
		mbeanservice.delete(beanInfo, id);
	}
	
    // 根据对像主键ID，删除对像
	public void delete(Class<? extends MBeanBase> beancls, int id){
		BeanInfo beanInfo = BeanInfo.getBeanInfo(beancls);
		mbeanservice.delete(beanInfo, id+"");
	}
		
		
	public <T> void update(Class<? extends MBeanBase> beancls, MBeanBase bean){
		BeanInfo beanInfo = BeanInfo.getBeanInfo(beancls);
		mbeanservice.update(beanInfo, bean);
	}
	
}
