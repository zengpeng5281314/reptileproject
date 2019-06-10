package com.mytest.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.common.MParam;
import com.base.dao.MBeanDAO;
import com.base.service.MFrameworkService;
import com.mytest.admin.po.TUserInfoPo;

@Service
public class UserInfoService {

	@Autowired
	MBeanDAO mBeanDAO;
	@Autowired
	MFrameworkService mFrameworkService;

	public TUserInfoPo getTUserInfoPoByOpenId(String nickname, String password) {
		MParam mparam = new MParam();
		mparam.add("password", password);
		mparam.add("status", 1);
		String hql = " and (nickName='" + nickname + "' or email='" + nickname + "' or phone='" + nickname + "')";
		mparam.setOrderbyHQL(hql);
		return mFrameworkService.get(TUserInfoPo.class, mparam);
	}

	public List<TUserInfoPo> allUserInfoPo(){
		MParam mparam = new MParam();
		mparam.add("status", 1);
		return mFrameworkService.listAll(TUserInfoPo.class,mparam);
	}
}
