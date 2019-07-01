package com.mytest.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.common.MParam;
import com.base.dao.MBeanDAO;
import com.base.service.MFrameworkService;
import com.mytest.admin.po.TXZDownUserInfoPo;

@Service
public class DownLoadUserInfoService {

	@Autowired
	MBeanDAO mBeanDAO;
	@Autowired
	MFrameworkService mFrameworkService;
	
	public List<TXZDownUserInfoPo> allTXZDownUserInfoPoList(int type){
		MParam mparam = new MParam();
		mparam.add("status", 1);
		mparam.add("type", type);
		return mFrameworkService.listAll(TXZDownUserInfoPo.class, mparam);
	}
	
}
