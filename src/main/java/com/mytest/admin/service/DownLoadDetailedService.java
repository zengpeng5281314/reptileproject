package com.mytest.admin.service;

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
	
	public TChannleXZInfoPo getTChannleXZtailedInfoPo(long userId){
		MParam mparam = new MParam();
		mparam.add("userId", userId);
		return mFrameworkService.get(TChannleXZInfoPo.class, mparam);
	}
	
	public void saveOrUpdateTChannleXZtailedInfoPo(TChannleXZInfoPo tChannleXZtailedInfoPo){
		mBeanDAO.saveOrUpdate(tChannleXZtailedInfoPo);
	}
	
	public TChannleXZDetailedInfoPo getTChannleXZDetailedInfoPo(long userId,int nameNo){
		MParam mparam = new MParam();
		mparam.add("userId", userId);
		mparam.add("nameNo", nameNo);
		return mFrameworkService.get(TChannleXZDetailedInfoPo.class, mparam);
	}
	
	public void saveOrUpdateTChannleXZtailedInfoPo(TChannleXZDetailedInfoPo tChannleXZInfoPo){
		mBeanDAO.saveOrUpdate(tChannleXZInfoPo);
	}
	
	
	
	
}
