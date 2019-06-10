package com.base.dao;

import java.util.List;
import java.util.Map;


import com.mytest.utils.KeyValuePair;
import com.mytest.utils.Page;



public interface MBeanDAO {
	
	public void save(Object obj);
	
	public Object saveOrUpdate(Object obj);
	
	public Object merge(Object obj);
	
	public void delete(Object obj);
	
	public Object get(Class<?> cls, String id);
	
	public Object get(Class<?> cls, long id);
	
	public Object getByHQL(String hql, List<KeyValuePair> kvpList);
	
	public Object getBySQL(String sql);
	
	public long getCount(String hql);
	
	
	public void updateSQL(String updateSQL);
	
	public void updateHQL(String updateHQL);
	
	public List<Map<String,Object>> listMapBySQL(String sql);
	
	public List<?> listBySQL(String sql);
	
	public List<?> listBySQL(final String sql, final Class<?> clazz);
	
	public List<?> listBySQL(final String sql, final Class<?> clazz, final Page page);
	
	public List<?> listByHQL(String hql);
	
	public List<?> listByHQL(String hql, Page page);
	
	public List<?> listByHQL(String hql, Page page, List<KeyValuePair> kvpList);
	
	
	/**
	 * 分页
	 */
	public Page listByHQLPageInfo(String hql, Page page);
	
	public Page listMapBySQLPageInfo(final String sql, final Page page);
	
	public Page listByHQLPageInfo(String hql, Page page, List<KeyValuePair> kvpList);
	
	public Page listBySQLPageInfo(final String sql, final Class<?> clazz, final Page page);
	
	public void deleteBatchByKey(Class<?> cls,  String key, Object[] ids);
}
