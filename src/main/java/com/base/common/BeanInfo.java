package com.base.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Transient;

import org.springframework.util.StringUtils;

import com.base.annotation.BeanMeta;
import com.base.annotation.FormMeta.EDITCOMPONENT;

/**
 * MFramework Bean描述对像
 *
 */
public class BeanInfo {
	
	public static BeanInfo getBeanInfo(Class<?> beancls) {
		BeanInfo beanInfo = new BeanInfo();
		beanInfo.beanclass = beancls;
		beanInfo.beanname = beancls.getSimpleName();
		
		BeanMeta beanMeta = beancls.getAnnotation(BeanMeta.class);
		if(beanMeta!=null){
			beanInfo.name = beanMeta.name();
			beanInfo.alias = StringUtils.isEmpty(beanMeta.alias()) ? beancls.getSimpleName().toLowerCase() : beanMeta.alias();
			beanInfo.fakedelete = beanMeta.fakedelete();
			beanInfo.manageability = beanMeta.manageability();
			beanInfo.cache = beanMeta.cache();
			beanInfo.beaninterface = beanMeta.beaninterface();
			beanInfo.cacheTTL = beanMeta.cachettl();
			beanInfo.dbTTL = beanMeta.dbttl();
		}
		
		Field[] fields = beancls.getDeclaredFields();
		for (Field field : fields) {
			Transient trans = field.getAnnotation(Transient.class);
			if(trans!=null)
				continue;
				
			SortableField sField = SortableField.buildSortableField(beanInfo, field);
			beanInfo.allList.add(sField);
			
			if(sField.isSummary()){
				beanInfo.summaryList.add(sField);
			}
			
			if(sField.isId()){
			 	beanInfo.pkname = field.getName();
			}
		}

		Collections.sort(beanInfo.summaryList);
		Collections.sort(beanInfo.allList);
		return beanInfo;
	}
	
	private Class<?> beanclass;
	private String beanname;	// 类名
	private String name;		// 对像中文名称
	private String alias;		// MFramework框架中的对像别名，唯一，用于管理
	private boolean fakedelete;	// 是否开启伪删除
	private boolean manageability;	// 后台管理是否支持写操作处理
	private boolean cache;		// 是否开启缓存支持
	private Class<? extends BeanInterface<? extends MBeanBase>> beaninterface;	// 后台管理是否支持写操作处理
	private int cacheTTL = 0;		// Cache数据有效性时间设置（单位秒），0为永久有效
	private int dbTTL = 0;			// DAO数据有效性时间设置（单位秒），0为永久有效且不支持MBean的list接口调用
	
	private String pkname;			// 主键字段名
	public List<SortableField> msearchList;	// 支持搜索的字段列表
	
	private List<SortableField> allList = new ArrayList<SortableField>();		// 列表中显示的列
	private List<SortableField> summaryList = new ArrayList<SortableField>();	// 列表中显示的列
	
	public Class<?> getBeanclass() {
		return beanclass;
	}

	public String getBeanname() {
		return beanname;
	}

	public String getName() {
		return name;
	}
	
	public String getAlias() {
		return alias;
	}

	public boolean isFakedelete() {
		return fakedelete;
	}

	public boolean isManageability() {
		return manageability;
	}

	public boolean isCache() {
		return cache;
	}

	public Class<? extends BeanInterface<? extends MBeanBase>> getBeaninterface() {
		return beaninterface;
	}

	public String getPkname() {
		return pkname;
	}

	public int getCacheTTL() {
		return cacheTTL;
	}

	public int getDbTTL() {
		return dbTTL;
	}

	public List<SortableField> getMsearchList() {
		return msearchList;
	}

	public List<SortableField> getAllList() {
		return allList;
	}

	public List<SortableField> getSummaryList() {
		return summaryList;
	}
	
	// 获取指定字段描述对像
	public SortableField getField(String fieldname){
		for(SortableField sortableField : allList){
			if(sortableField.getName().equals(fieldname))
				return sortableField;
		}
		return null;
	}
	
	// 设置隐藏所有的字段
	public void hideAllField(){
		for(SortableField sortableField : allList){
			sortableField.setHide(true);
		}
	}
	
	// 获取要求唯一的字段名列表
	public List<SortableField> getUniquenessFieldList(){
		List<SortableField> uniquenessField = new ArrayList<SortableField>();
		for(SortableField sortableField : allList){
			if(sortableField.isUniqueness())
				uniquenessField.add(sortableField);
		}
		return uniquenessField;
	}
	
	// 获取主键字段
	public String getIdField(){
		for(SortableField sortableField : allList){
			if(sortableField.isId())
				return sortableField.getName();
		}
		
		return null;
	}
	
	// 获取默认的排序字段
	public SortableField getOrderField(){
		// 查找自定义的排序字段
		for(SortableField sortableField : allList){
			if(sortableField.getEditcomponent()==EDITCOMPONENT.ORDER)
				return sortableField;
		}
		
		// 查找createtime字段
		for(SortableField sortableField : allList){
			if(sortableField.isCreatetime())
				return sortableField;
		}
		
		return null;
	}
	
	// 遍历字段设置，检测是否需要ImageCrop支持
	public boolean hasImageCrop(){
		for(SortableField sortableField : allList){
			if(sortableField.getCropImageWidth()>0)
				return true;
		}
		return false;
	}
	
	@Override
	public String toString(){
		return beanname;
	}
}
