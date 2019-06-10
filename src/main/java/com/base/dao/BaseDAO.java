package com.base.dao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseDAO{
	
	/**
	 * 注入一个sessionFactory属性到父类(HibernateDaoSupport)里
	 */
	@Autowired
	SessionFactory sessionFactory;	


	public Session getSession() {
		Session session = sessionFactory.getCurrentSession();
		return session;
	}
	
	/**
	 * 打开session 记得一定要关闭
	 * @return Session
	 */
	public Session openSession(){
		Session session = sessionFactory.openSession();
		return session;
	}
	
   //关闭Session  
   public static void closeSession(Session session){  
       if(session!=null){  
           if(session.isOpen()){  
               session.close();  
           }  
       }  
   }  
}

