package com.mytest.admin.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.common.MParam;
import com.base.dao.MBeanDAO;
import com.base.service.MFrameworkService;
import com.mytest.admin.po.TChannleXZDetailedInfoPo;
import com.mytest.admin.po.TChannleXZInfoPo;

@Service
public class DownLoadDetailedService {

	@Autowired
	MBeanDAO mBeanDAO;
	@Autowired
	MFrameworkService mFrameworkService;

	public TChannleXZInfoPo getTChannleXZInfoPo(long userId,String date) {
		MParam mparam = new MParam();
		mparam.add("userId", userId);
		mparam.setOrderbyHQL(" and ctime='"+date+"' ");
		return mFrameworkService.get(TChannleXZInfoPo.class, mparam);
	}

	public void saveOrUpdatetTChannleXZInfoPo(TChannleXZInfoPo channleXZInfoPo) {
		mBeanDAO.saveOrUpdate(channleXZInfoPo);
	}

	public TChannleXZDetailedInfoPo getTChannleXZDetailedInfoPo(long userId, String phone, String registTime) {
		MParam mparam = new MParam();
		mparam.add("userId", userId);
		mparam.add("phone", phone);
		String hql = " and registTime='"+registTime+"'";
		mparam.setOrderbyHQL(hql);
		return mFrameworkService.get(TChannleXZDetailedInfoPo.class, mparam);
	}

	public void saveOrUpdateTChannleXZtailedInfoPo(TChannleXZDetailedInfoPo channleXZDetailedInfoPo) {
		mBeanDAO.saveOrUpdate(channleXZDetailedInfoPo);
	}

}
