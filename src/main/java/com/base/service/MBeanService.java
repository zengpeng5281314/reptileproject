package com.base.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.common.BeanInfo;
import com.base.common.MBeanBase;
import com.base.common.MParam;
import com.base.common.SortableField;
import com.base.dao.MBeanDAO;
import com.base.service.MBeanInterfaceService;
import com.base.service.MBeanService;
import com.mytest.utils.CommonUtils;
import com.mytest.utils.KeyValuePair;
import com.mytest.utils.KeyValuePair.Operater;

@Service
public class MBeanService {

	private static final Log logger = LogFactory.getLog(MBeanService.class);
	public static final String KEY_MBEAN = "MBEAN:";	// MBEAN:[MBEANALIAS]:[ID]
	
	@Autowired
	private MBeanDAO mbeanDAO;
	@Autowired
	private MBeanInterfaceService mbeanInterfaceService;
	
	// 更新对像缓存
	private void updateBeanCache(BeanInfo beanInfo, Object bean){
		/*try {
			Field field = beanInfo.getBeanclass().getDeclaredField(beanInfo.getPkname());
			field.setAccessible(true);
			String id = (String)field.get(bean);
			
			String cacheKey = CacheKEY(beanInfo, id);
			MemcachedUtils.set(cacheKey, 0, bean);
			logger.debug("bean cache update! beanInfo=" + beanInfo + " id=" + id + " key=" + cacheKey);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	// 删除对像缓存
	private void removeBeanCache(BeanInfo beanInfo, String id){
		/*try {
			MemcachedUtils.delete(CacheKEY(beanInfo, id));
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	// 根据对像更新前后对比，获取更新的字段列表
	private List<SortableField> getBeanUpdatedFieldList(BeanInfo beanInfo, MBeanBase obean, MBeanBase ubean){
		try{
			// 比较并获取变更的字段
			List<SortableField> updatedFieldList = new ArrayList<SortableField>();
			for(SortableField sortableField : beanInfo.getAllList()){
				Field field = sortableField.getField();
				field.setAccessible(true);
				
				Object oValue = field.get(obean);
				Object uValue = field.get(ubean);
				if(oValue==null){
					if(uValue!=null)
						updatedFieldList.add(sortableField);
				} else if(!oValue.equals(uValue)){
					updatedFieldList.add(sortableField);
				}
			}
			
			return updatedFieldList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	// 从数据库中加载并刷新缓存
	public void refreshBeanCache(BeanInfo beanInfo, String id){
		Object bean = mbeanDAO.get(beanInfo.getBeanclass(), id);
		if(bean==null){
			removeBeanCache(beanInfo, id);
		} else {
			updateBeanCache(beanInfo, bean);
		}
	}
	
	// 获取对像
	public MBeanBase get(BeanInfo beanInfo, String id){
		
		if(!beanInfo.isCache()){
			if(id.matches("^\\d+$"))
				return (MBeanBase)mbeanDAO.get(beanInfo.getBeanclass(), Integer.parseInt(id));
			return (MBeanBase)mbeanDAO.get(beanInfo.getBeanclass(), id);
		}
		
		MBeanBase bean = null;/*MemcachedUtils.get(cacheKey);*/
		if(bean==null){
			if(id.matches("^\\d+$"))
				bean = (MBeanBase)mbeanDAO.get(beanInfo.getBeanclass(), Integer.parseInt(id));
			else
				bean = (MBeanBase)mbeanDAO.get(beanInfo.getBeanclass(), id);
			
			if(bean==null){
				bean = mbeanInterfaceService.get(beanInfo, id);
				if(bean!=null){
					mbeanDAO.save(bean);
				}
			}
			
			if(bean!=null)
				updateBeanCache(beanInfo, bean);
		}
		return bean;
	}
	
	// 获取对像（动态条件）
	public MBeanBase get(BeanInfo beanInfo, MParam mparam){
		
		List<KeyValuePair> kvpList = mparam.getHqlKVPList();
		String hql = "from " + beanInfo.getBeanname() + " where 1=1";
		for(KeyValuePair kvp : kvpList){
			hql += " and " + CommonUtils.filterIllegalKey(kvp.getKey());
			if(kvp.getOp()==Operater.NOT)
				hql += "!=?";
			else
				hql += "=?";
		}
		hql += " " + mparam.getOrderbyHQL();
		logger.info(hql);
		
		MBeanBase bean = null;
		// TODO load bean from cache
		
		if(bean==null){
			bean = (MBeanBase)mbeanDAO.getByHQL(hql, kvpList);
			if(bean==null){
				bean = (MBeanBase)mbeanInterfaceService.get(beanInfo, mparam);
				if(bean!=null){
					logger.info("BeanInterface get succeed! beanInfo=" + beanInfo + " mparam=" + mparam);
					mbeanDAO.save(bean);
				}
			}
			
			// update bean cache
		}
		
		return bean;
	}
	
	// 对像更新
	public void update(BeanInfo beanInfo, MBeanBase bean){
		
		// 因Bean字段值变更，自动标记相关列表缓存失效
		if(beanInfo.isCache()){
			MBeanBase obean = null;
			try{
				Field field = beanInfo.getBeanclass().getDeclaredField(beanInfo.getPkname());
				field.setAccessible(true);
				String id = (String)field.get(bean);
				
				// 获取变更前的对像
				obean = get(beanInfo, id);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			// 根据更新的字段，更新相关KVP对应的HashKey失效（以实现以这些字段为查询的列表缓存失效并刷新）
			if(obean!=null) {
				List<SortableField> updatedFieldList = obean==null ? beanInfo.getAllList() : getBeanUpdatedFieldList(beanInfo, obean, bean);
			}
			
			// 更新缓存与数据库
			updateBeanCache(beanInfo, bean);
		}
		mbeanDAO.save(bean);
	}
	
	// 对像删除
	/**
	 * 暂时失效
	 * @param beanInfo
	 * @param id
	 */
	@Deprecated
	public void delete(BeanInfo beanInfo, String id){
		// 删除前加载对像，以便更新所有影响的字段
		MBeanBase bean = this.get(beanInfo, id);
		if(bean==null)
			return;
		
		
		if(beanInfo.isFakedelete()){
			String updateHql = null;
			// 假删（标记status=9）
			if(id.matches("^\\d+$")){
				updateHql = "update " + beanInfo.getBeanname() + " set status=9 where " + beanInfo.getPkname() + "=" + id;
			}else{
				updateHql = "update " + beanInfo.getBeanname() + " set status=9 where " + beanInfo.getPkname() + "='" + id + "'";
			}
			mbeanDAO.updateHQL(updateHql);
		} else {
			// 真删
			String deleteHql = null;
			if(id.matches("^\\d+$")){
				deleteHql = "delete from " + beanInfo.getBeanname() + " where " + beanInfo.getPkname() + "=" + id;
			}else{
				deleteHql = "delete from " + beanInfo.getBeanname() + " where " + beanInfo.getPkname() + "='" + id + "'";
			}
					
			mbeanDAO.updateHQL(deleteHql);
		}
	}
	
	public void updateByHQL(String updateHQL){
		mbeanDAO.updateHQL(updateHQL);
	}
	
}
