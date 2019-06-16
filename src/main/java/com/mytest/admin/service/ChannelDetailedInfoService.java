package com.mytest.admin.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.common.MParam;
import com.base.dao.MBeanDAO;
import com.base.service.MFrameworkService;
import com.mytest.admin.po.TChannleInfoPo;
import com.mytest.admin.po.TChannleXZInfoPo;
import com.mytest.admin.po.TUserChannleBindInfoPo;
import com.mytest.utils.Page;

@Service
public class ChannelDetailedInfoService {

	@Autowired
	MBeanDAO mBeanDAO;
	@Autowired
	MFrameworkService mFrameworkService;
	@Autowired
	UserInfoService userInfoService;

	public TChannleXZInfoPo getTChannleDetailedInfoPo(long id) {
		return mFrameworkService.get(TChannleXZInfoPo.class, String.valueOf(id));
	}

	public List<TChannleXZInfoPo> listTChannleDetailedInfoPo(long userId, long channelId, Timestamp startTime,
			Timestamp endTime) {
		MParam mparam = new MParam();
		mparam.add("userId", userId);
		if (channelId != 0)
			mparam.add("channelId", channelId);
		if (startTime != null && endTime != null)
			mparam.setOrderbyHQL(" and createTime>='" + startTime + "' and createTime<='" + endTime + "'");
		return mFrameworkService.list(TChannleXZInfoPo.class, mparam);
	}

	public Page pageTChannleDetailedInfoPo(long userId, long channelId, Timestamp startTime, Timestamp endTime,
			Page page) {
		MParam mparam = new MParam();
		mparam.add("userId", userId);
		if (channelId != 0)
			mparam.add("channelId", channelId);
		if (startTime != null && endTime != null)
			mparam.setOrderbyHQL(" and createTime>='" + startTime + "' and createTime<='" + endTime + "'");
		return mFrameworkService.listPageInfo(TChannleXZInfoPo.class, page, mparam);
	}

	public List<TUserChannleBindInfoPo> allTChannleInfoPo(long userId) {
		MParam mparam = new MParam();
		if (userId != 0)
			mparam.add("userId", userId);
		mparam.add("status", 1);
		return mFrameworkService.list(TUserChannleBindInfoPo.class, mparam);
	}

	public List<TChannleInfoPo> allTChannleInfoPo(){
		MParam mparam = new MParam();
		mparam.add("status", 1);
		return mFrameworkService.list(TChannleInfoPo.class, mparam);
	}
	
	public boolean getTodayChannleDetailedInfo(long userId, long channelId, String date) {
		MParam mparam = new MParam();
		if (userId != 0)
			mparam.add("userId", userId);
		if (channelId != 0)
			mparam.add("channelId", channelId);
		mparam.add("status", 1);
		if (!date.equals(""))
			mparam.setOrderbyHQL(" and createTime like '" + date + "%'");
		List<TChannleXZInfoPo> list = mFrameworkService.list(TChannleXZInfoPo.class, mparam);
		if (list != null && list.size() > 0)
			return true;
		return false;
	}

	public Page allTodayChannleDetailedList(long userId, long channelId, String date, Page page) {
		MParam mparam = new MParam();
		if (userId != 0)
			mparam.add("userId", userId);
		if (channelId != 0)
			mparam.add("channelId", channelId);
		mparam.add("status", 1);
		if (!date.equals(""))
			mparam.setOrderbyHQL(" and createTime like '" + date + "%'");
		return mFrameworkService.listPageInfo(TChannleXZInfoPo.class, page, mparam);

	}

	public TChannleXZInfoPo saveOrUpdateTChannleDetailedInfoPo(TChannleXZInfoPo channleDetailedInfoPo) {
		return (TChannleXZInfoPo) mBeanDAO.saveOrUpdate(channleDetailedInfoPo);
	}

	
	public List<TUserChannleBindInfoPo> listTUserChannleBindInfoPo(long userId,String channelName){
		String hql ="SELECT b FROM TUserChannleBindInfoPo b,TChannleInfoPo t WHERE t.id=b.channelId ";
		if(userId!=0)
			hql+= " AND b.userId="+userId;
		if(!channelName.equals(""))
			hql+=" AND t.channelName LIKE '%"+channelName+"%'";
		return (List<TUserChannleBindInfoPo>) mBeanDAO.listByHQL(hql);
	}
}
