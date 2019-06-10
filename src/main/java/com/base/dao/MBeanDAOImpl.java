package com.base.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mytest.utils.KeyValuePair;
import com.mytest.utils.KeyValuePair.Operater;
import com.mytest.utils.Page;



@Repository
public class MBeanDAOImpl extends BaseDAO implements MBeanDAO {

    public void save(Object obj) {
        Session session = this.openSession();
        Transaction tx = session.beginTransaction();  
        session.saveOrUpdate(obj);
        tx.commit();
        session.close();
    }

    public Object get(Class<?> cls, long id) {
        Session session = this.openSession();
        Object obj = session.get(cls, id);
        session.close();
        return obj;
    }

    public Object get(Class<?> cls, String id) {
        Session session = this.openSession();
        Object obj = session.get(cls, id);
        session.close();
        return obj;
    }

    public Object getByHQL(final String hql, final List<KeyValuePair> kvpList) {
        Session session = this.openSession();
        Query q = session.createQuery(hql);
        for (int i = 0; i < kvpList.size(); i++) {
            q.setParameter(i, kvpList.get(i).getValue());
        }

        List<?> list = q.list();
        session.close();
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public Object getBySQL(final String sql) {
        Session session = this.openSession();
        Query q = session.createSQLQuery(sql);
        List<?> list = q.list();
        session.close();
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public long getCount(final String hql) {
        Query query = this.getSession().createQuery(hql);
        return (Long) query.uniqueResult();
    }

    @Transactional(readOnly = false)
    public void updateSQL(final String updateSQL) {
        try {
            Session session = this.openSession();
            SQLQuery query = session.createSQLQuery(updateSQL);
            query.executeUpdate();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional(readOnly = false)
    public void updateHQL(final String updateHQL) {
        Session session = this.openSession();
        Query q = session.createQuery(updateHQL);
        q.executeUpdate();
        session.close();
    }

    public List<?> listBySQL(final String sql) {
        Session session = this.openSession();
        List<?> list = listBySQL(sql, null);
        session.close();
        return list;
    }

    public List<?> listBySQL(final String sql, final Class<?> clazz) {
        Session session = this.openSession();
        SQLQuery q = session.createSQLQuery(sql);
        if (clazz != null)
            q.addEntity(clazz);
        List<?> list = q.list();
        session.close();
        return list;
    }

    public List<?> listBySQL(final String sql, final Class<?> clazz,
            final Page page) {
        Session session = this.openSession();
        SQLQuery q = session.createSQLQuery(sql);
        if (clazz != null)
            q.addEntity(clazz);

        if (page != null) {
            q.setFirstResult(page.getBeginIndex());
            q.setMaxResults(page.getPageSize());
        }
        List<?> list = q.list();
        session.close();
        return list;
    }

    public List<?> listByHQL(final String hql) {
        Session session = this.openSession();
        List<?> list = session.createQuery(hql).list();
        session.close();
        return list;
        /*Query q = this.getSession().createQuery(hql);
        return q.list();*/
    }

    public List<?> listByHQL(final String hql, final Page page) {
        Session session = this.openSession();
        Query q = session.createQuery(hql);

        if (page != null) {
            q.setFirstResult(page.getBeginIndex());
            q.setMaxResults(page.getPageSize());
        }
        List<?> list = q.list();
        session.close();
        return list;
    }

	public List<?> listByHQL(final String hql, final Page page,
			final List<KeyValuePair> kvpList) {
	    Session session = this.openSession();
        Query q = session.createQuery(hql);

		if (kvpList != null) {
			for (int i = 0; i < kvpList.size(); i++) {
				KeyValuePair kvp = kvpList.get(i);
				if (kvp.getValue() == null)
					continue;
				
				String keyalias = kvp.getKeyalias()==null?kvp.getKey():kvp.getKeyalias();
				if (kvp.getOp() == Operater.EQ || kvp.getOp() == Operater.NOT
						|| kvp.getOp() == Operater.ETANDEQ
						|| kvp.getOp() == Operater.ET
						|| kvp.getOp() == Operater.GTANDEQ
						|| kvp.getOp() == Operater.GT){
					q.setParameter(keyalias, kvpList.get(i).getValue());
				}else if (kvp.getOp() == Operater.LIKE) {
					q.setParameter(keyalias, kvpList.get(i).getValue() == null ? ""
							: kvpList.get(i).getValue());
				} else if (kvp.getOp() == Operater.IN) {
					if (kvp.getValue() instanceof List) {
						q.setParameterList(keyalias,
								(List<?>) kvp.getValue());
					} else if (kvp.getValue() instanceof String[]) {
						q.setParameterList(keyalias,
								(String[]) kvp.getValue());
					} else if (kvp.getValue() instanceof Integer[] || kvp.getValue() instanceof int[] ) {
						q.setParameterList(keyalias,
								(Integer[]) kvp.getIntVlaue());
					} else if (kvp.getValue() instanceof Long[] || kvp.getValue() instanceof long[] ) {
						q.setParameterList(keyalias,
								(Long[]) kvp.getLongValue());
					}
				}
			}
		}

		if (page != null) {
			q.setFirstResult(page.getBeginIndex());
			q.setMaxResults(page.getPageSize());
		}
        List<?> list = q.list();
        session.close();
        return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kahui.base.dao.MBeanDAO#delete(java.lang.Object)
	 */
	@Override
	public void delete(Object obj) {
	    Session session = this.openSession();
	    Transaction tx = session.beginTransaction();  
        session.delete(obj);
        tx.commit();
        session.close();
	}

	@Override
	public Page listBySQLPageInfo(final String sql, final Class<?> clazz,
			final Page page) {
	    Session session = this.openSession();
	    SQLQuery q = session.createSQLQuery(sql);
		if (clazz != null)
			q.addEntity(clazz);
		if (page != null) {
			int total = q.list().size();
			page.setTotal(total);
			q.setFirstResult(page.getBeginIndex());
			q.setMaxResults(page.getPageSize());
		}
		page.setList(q.list());
		session.close();
		return page;
	}

	@Override
	public Page listByHQLPageInfo(final String hql, final Page page) {
	    Session session = this.openSession();
	    Query q = session.createQuery(hql);

		if (page != null) {
			page.setTotal(q.list().size());
			q.setFirstResult(page.getBeginIndex());
			q.setMaxResults(page.getPageSize());
		}
		page.setList(q.list());
		session.close();
		return page;
	}

	@Override
	public Page listByHQLPageInfo(final String hql, final Page page,
			final List<KeyValuePair> kvpList) {
	    Session session = this.openSession();
        Query q = session.createQuery(hql);
		if (kvpList != null) {
			for (int i = 0; i < kvpList.size(); i++) {
				KeyValuePair kvp = kvpList.get(i);
				if (kvp.getValue() == null)
					continue;

				String keyalias = kvp.getKeyalias()==null?kvp.getKey():kvp.getKeyalias();
				if (kvp.getOp() == Operater.EQ || kvp.getOp() == Operater.NOT
						|| kvp.getOp() == Operater.ETANDEQ
						|| kvp.getOp() == Operater.ET
						|| kvp.getOp() == Operater.GTANDEQ
						|| kvp.getOp() == Operater.GT){
					q.setParameter(keyalias, kvpList.get(i).getValue());
				}else if (kvp.getOp() == Operater.LIKE) {
					q.setParameter(keyalias, kvpList.get(i).getValue() == null ? ""
							: kvpList.get(i).getValue());
				} else if (kvp.getOp() == Operater.IN) {
					if (kvp.getValue() instanceof List) {
						q.setParameterList(keyalias,
								(List<?>) kvp.getValue());
					} else if (kvp.getValue() instanceof String[]) {
						q.setParameterList(keyalias,
								(String[]) kvp.getValue());
					} else if (kvp.getValue() instanceof Integer[] || kvp.getValue() instanceof int[] ) {
						q.setParameterList(keyalias,
								(Integer[]) kvp.getIntVlaue());
					} else if (kvp.getValue() instanceof Long[] || kvp.getValue() instanceof long[]) {
						q.setParameterList(keyalias,
								(Long[]) kvp.getLongValue());
					}

				}
			}
		}
		if (page != null) {
			page.setTotal(q.list().size());
			q.setFirstResult(page.getBeginIndex());
			q.setMaxResults(page.getPageSize());
		}
		page.setList(q.list());
		session.close();
		return page;
	}

	@Override
	public void deleteBatchByKey(final Class<?> cls, final String key,
			final Object[] ids) {
		Query q = this.getSession().createQuery(
				"delete from "
						+ cls.getName().substring(
								cls.getName().lastIndexOf(".") + 1) + " where "
						+ key + " in (:ids)");
		q.setParameterList("ids", ids);
		q.executeUpdate();
	}

	/* (non-Javadoc)
	 * @see com.yhmall.base.dao.MBeanDAO#listMapBySQL(java.lang.String)
	 */
	@Override
	public List<Map<String,Object>> listMapBySQL(String sql) {
	    Session session = this.openSession();
        SQLQuery q = session.createSQLQuery(sql);
        q.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
        // 执行查询
        List<Map<String,Object>> list = q.list();
        session.close();
        return list;
	}

	/* (non-Javadoc)
	 * @see com.yhmall.base.dao.MBeanDAO#listMapBySQLPageInfo(java.lang.String, com.yhmall.base.utils.Page)
	 */
	@Override
	public Page listMapBySQLPageInfo(String sql, Page page) {
	    Session session = this.openSession();
        SQLQuery q = session.createSQLQuery(sql);
        q.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
        if (page != null) {
            int total = q.list().size();
            page.setTotal(total);
            q.setFirstResult(page.getBeginIndex());
            q.setMaxResults(page.getPageSize());
        }
        page.setList(q.list());
        session.close();
        return page;
	}

	@Override
	public Object saveOrUpdate(Object obj) {
	    Session session = this.openSession();
        Transaction tx = session.beginTransaction();  
        session.saveOrUpdate(obj);
        tx.commit();
        session.close();
        return obj;
	}

    /**.
     * <p>Title: merge</p>
     * <p>Description: </p>
     * @param obj
     * @return
     * @see com.kahui.base.dao.MBeanDAO#merge(java.lang.Object)
     */
    @Override
    public Object merge(Object obj) {
        Session session = this.openSession();
        Transaction tx = session.beginTransaction();  
        obj = session.merge(obj);
        tx.commit();
        session.close();
        return obj;
    }

}
