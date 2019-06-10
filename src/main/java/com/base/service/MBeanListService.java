package com.base.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.common.BeanInfo;
import com.base.common.MParam;
import com.base.common.MParam.LogicalOp;
import com.base.dao.MBeanDAO;
import com.base.service.MBeanInterfaceService;
import com.base.service.MBeanListService;
import com.mytest.utils.CommonUtils;
import com.mytest.utils.KeyValuePair;
import com.mytest.utils.KeyValuePair.Operater;
import com.mytest.utils.Page;
@Service
public class MBeanListService {

	private static final Log logger = LogFactory.getLog(MBeanListService.class);
	public static final String KEY_MBEAN_DBFLUSH_TIME = "MBEAN_DBFLUSH_TIME:";	// MBEAN_DBFLUSH_TIME:[MBEANALIAS]	TIMESTAMP
	
	@Autowired
	private MBeanDAO mbeanDAO;
	@Autowired
	private MBeanInterfaceService mbeanInterfaceService;
	
	// 通过DAO加载数据列表
	private List<?> listByDAO(BeanInfo beanInfo, Page page, MParam mparam){
		String listHQL = "from " + beanInfo.getBeanname() + " where 1=1";
		if(beanInfo.isFakedelete()){
			listHQL += " and state<>9";
		}
		
		List<KeyValuePair> kvpList = null;
		if(mparam!=null){
			kvpList = mparam.getHqlKVPList();
			
			String kvHql = "(";
			for(KeyValuePair kvp : kvpList){
				kvHql += CommonUtils.filterIllegalKey(kvp.getKey());
				String keyalias = kvp.getKeyalias()==null?kvp.getKey():kvp.getKeyalias();
				if(kvp.getOp()==Operater.IN)
					//kvHql += " in (:" + kvp.getKey() + ")";
					kvHql += " in (:" + keyalias + ")";
				else if(kvp.getOp()==Operater.EQ){
					kvHql += kvp.getValue()==null ? " is null" : "=:"+keyalias;
				}else if(kvp.getOp()==Operater.GT){
					kvHql += kvp.getValue()==null ? " is null" : ">:"+keyalias;
				}else if(kvp.getOp()==Operater.GTANDEQ){
					kvHql += kvp.getValue()==null ? " is null" : ">=:"+keyalias;
				}else if(kvp.getOp()==Operater.ET){
					kvHql += kvp.getValue()==null ? " is null" : "<:"+keyalias;
				}else if(kvp.getOp()==Operater.ETANDEQ){
					kvHql += kvp.getValue()==null ? " is null" : "<=:"+keyalias;
				}else if(kvp.getOp()==Operater.NOT){
					kvHql += kvp.getValue()==null ? " is not null" : "!=:"+keyalias;
				}else if(kvp.getOp()==Operater.LIKE){
					
					if(kvp.getValue()==null){
						kvp.setValue("");
					}
					kvHql +=  "  like :"+keyalias+" ";
					
					
				}
				
				kvHql += mparam.getLogicalOp()==LogicalOp.AND ? " and " : " or ";
			}
			if(kvHql.length()>1){
				kvHql = kvHql.substring(0, kvHql.length()-4) + ")";
				listHQL += " and " + kvHql;
			}
			
			listHQL += " " + mparam.getOrderbyHQL();
		}
		logger.info(listHQL);
		return mbeanDAO.listByHQL(listHQL, page, kvpList);
	}
	
	// 通过DAO加载数据列表
		private Page listByDAOPageInfo(BeanInfo beanInfo, Page page, MParam mparam){
			String listHQL = "from " + beanInfo.getBeanname() + " where 1=1";
			if(beanInfo.isFakedelete()){
				listHQL += " and state<>9";
			}
			
			List<KeyValuePair> kvpList = null;
			if(mparam!=null){
				kvpList = mparam.getHqlKVPList();
				
				String kvHql = "(";
				for(KeyValuePair kvp : kvpList){
					String keyalias = kvp.getKeyalias()==null?kvp.getKey():kvp.getKeyalias();
					kvHql += CommonUtils.filterIllegalKey(kvp.getKey());
					if(kvp.getOp()==Operater.IN)
						kvHql += " in (:" + kvp.getKey() + ")";
					else if(kvp.getOp()==Operater.EQ){
						kvHql += kvp.getValue()==null ? " is null" : "=:"+keyalias;
					}else if(kvp.getOp()==Operater.GT){
						kvHql += kvp.getValue()==null ? " is null" : ">:"+keyalias;
					}else if(kvp.getOp()==Operater.GTANDEQ){
						kvHql += kvp.getValue()==null ? " is null" : ">=:"+keyalias;
					}else if(kvp.getOp()==Operater.ET){
						kvHql += kvp.getValue()==null ? " is null" : "<:"+keyalias;
					}else if(kvp.getOp()==Operater.ETANDEQ){
						kvHql += kvp.getValue()==null ? " is null" : "<=:"+keyalias;
					}else if(kvp.getOp()==Operater.NOT){
						kvHql += kvp.getValue()==null ? " is not null" : "!=:"+keyalias;
					}else if(kvp.getOp()==Operater.LIKE){
						
						if(kvp.getValue()==null){
							kvp.setValue("");
						}
						kvHql +=  "  like :"+keyalias+" ";
						
						
					}
					
					kvHql += mparam.getLogicalOp()==LogicalOp.AND ? " and " : " or ";
				}
				if(kvHql.length()>1){
					kvHql = kvHql.substring(0, kvHql.length()-4) + ")";
					listHQL += " and " + kvHql;
				}
				
				listHQL += " " + mparam.getOrderbyHQL();
			}
			logger.info(listHQL);
			return mbeanDAO.listByHQLPageInfo(listHQL, page, kvpList);
		}
		
	// 列表
	public List<?> list(BeanInfo beanInfo, Page page){
		return list(beanInfo, page, null);
	}

	
	// 更新数据刷新时间（用于计算TTL失效时间）
	/*private void updateDBFlushTime(BeanInfo beanInfo){
		try{
			MemcachedUtils.set(KEY_MBEAN_DBFLUSH_TIME + beanInfo.getAlias(), beanInfo.getDbTTL(), System.currentTimeMillis());
		}catch(Exception e){
			e.printStackTrace();
		}
	}*/
	
	// 获取数据刷新时间（用于计算TTL失效时间）
	/*private long getDBFlushTime(BeanInfo beanInfo){
		try{
			Long dbflushTime = (Long)MemcachedUtils.get(KEY_MBEAN_DBFLUSH_TIME + beanInfo.getAlias());
			return dbflushTime==null ? 0 : dbflushTime.longValue();
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return -1;
	}
	
	// 根据Bean设置，判断当前数据库数据是否有效
	private boolean checkDBTTL(BeanInfo beanInfo, int ttl){
		if(ttl==0)
			return true;
		
		return System.currentTimeMillis() - getDBFlushTime(beanInfo) <= ttl * 1000;
	}*/
	

	
	
	
	// 列表（动态）
	public List<?> list(BeanInfo beanInfo, Page page, MParam mparam){
		if(true){
			return listByDAO(beanInfo, page, mparam);
		}
		
		// list from cache
		List<?> list = null;
		// 检测DAO数据是否处于有效期内
		//int ttl = beanInfo.getDbTTL(); // DAO数据有效时长,0为永久有效
		
			// list from Interface
		List<?> interfaceList = mbeanInterfaceService
				.list(beanInfo, mparam);
		if (interfaceList == null) {
			logger.error("list, Interface Error! interfaceList is null! beanInfo="
					+ beanInfo);

			list = listByDAO(beanInfo, page, mparam);
		} else {
			logger.debug("list, load from Interface! beanInfo=" + beanInfo
					+ " interfaceList.size=" + interfaceList.size());

			// Update DAO
			// TODO 清、插？整表？按条件？
			// updateDBFlushTime(beanInfo);
			list = interfaceList;
		}

		// Update Cache
		return list;
	}
	
	public Page listPageInfo(BeanInfo beanInfo, Page page, MParam mparam){
		return listByDAOPageInfo(beanInfo, page, mparam);
	}
	
	public List<?> listByHQL(String hql){
		return mbeanDAO.listByHQL(hql);
	}
	
	public List<?> listByHQL(String hql, Page page){
		return mbeanDAO.listByHQL(hql, page);
	}
	
	public List<?> listBySQL(String sql){
		return mbeanDAO.listBySQL(sql);
	}
	
	public List<?> listBySQL(String sql, Class<?> clazz){
		return mbeanDAO.listBySQL(sql, clazz);
	}
	
	public List<?> listBySQL(String sql, Class<?> clazz, Page page){
		return mbeanDAO.listBySQL(sql, clazz, page);
	}
	
}
