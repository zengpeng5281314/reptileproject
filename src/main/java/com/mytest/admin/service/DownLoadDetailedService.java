package com.mytest.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public void saveOrUpdateTChannleXZtailedInfoPo(TChannleXZInfoPo tChannleXZtailedInfoPo){
		mBeanDAO.saveOrUpdate(tChannleXZtailedInfoPo);
	}
	
	public void saveOrUpdateTChannleXZtailedInfoPo(TChannleXZDetailedInfoPo tChannleXZInfoPo){
		mBeanDAO.saveOrUpdate(tChannleXZInfoPo);
	}
	
	
	
}
