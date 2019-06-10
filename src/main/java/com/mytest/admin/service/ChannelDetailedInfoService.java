package com.mytest.admin.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.crypto.Data;

import org.hibernate.loader.custom.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.common.MParam;
import com.base.dao.MBeanDAO;
import com.base.service.MFrameworkService;
import com.mytest.admin.po.TChannleBDetailedInfoPo;
import com.mytest.admin.po.TChannleInfoPo;
import com.mytest.admin.po.TUserChannleBindInfoPo;
import com.mytest.admin.po.TUserInfoPo;
import com.mytest.utils.Page;

@Service
public class ChannelDetailedInfoService {

	@Autowired
	MBeanDAO mBeanDAO;
	@Autowired
	MFrameworkService mFrameworkService;
	@Autowired
	UserInfoService userInfoService;

	public TChannleBDetailedInfoPo getTChannleDetailedInfoPo(long id) {
		return mFrameworkService.get(TChannleBDetailedInfoPo.class, String.valueOf(id));
	}

	public List<TChannleBDetailedInfoPo> listTChannleDetailedInfoPo(long userId, long channelId, Timestamp startTime,
			Timestamp endTime) {
		MParam mparam = new MParam();
		mparam.add("userId", userId);
		if (channelId != 0)
			mparam.add("channelId", channelId);
		if (startTime != null && endTime != null)
			mparam.setOrderbyHQL(" and createTime>='" + startTime + "' and createTime<='" + endTime + "'");
		return mFrameworkService.list(TChannleBDetailedInfoPo.class, mparam);
	}

	public Page pageTChannleDetailedInfoPo(long userId, long channelId, Timestamp startTime, Timestamp endTime,
			Page page) {
		MParam mparam = new MParam();
		mparam.add("userId", userId);
		if (channelId != 0)
			mparam.add("channelId", channelId);
		if (startTime != null && endTime != null)
			mparam.setOrderbyHQL(" and createTime>='" + startTime + "' and createTime<='" + endTime + "'");
		return mFrameworkService.listPageInfo(TChannleBDetailedInfoPo.class, page, mparam);
		// String sql = "select a.id as id,a.orderid as orderId,a.product_id as
		// productId,a.sale_price as salePrice,a.freight as freight,"
		// +"a.num as num ,a.total_money as totalMoney,"
		// + "a.buy_name as buyName,a.remark as remark,a.createtime as
		// createTime,"
		// + "a.updatetime as updateTime,a.status as status,b.product_name
		// productName from t_order_info a left join t_product_info b on
		// a.product_id=b.id where a.status = 1 ";
		// String totalSql = "select count(*) from t_order_info a where a.status
		// = 1 ";
		// if (userId!=0) {
		// sql += " and productName like '%" + productName + "%'";
		// totalSql += " and productName like '%" + productName + "%'";
		// }
		// if (!buyName.equals("")) {
		// sql += " and a.buy_name like '%" + buyName + "%'";
		// totalSql += " and a.buy_name like '%" + buyName + "%'";
		// }
		// if (startTime != null && endTime != null) {
		// sql += " and (a.createtime > '" + startTime + "' and a.createtime <'"
		// + endTime + "')";
		// totalSql += " and (a.createtime > '" + startTime + "' and
		// a.createtime <'" + endTime + "')";
		// }
		// sql += " order by a.createtime desc ";
		// sql = sql + " limit " + (page.getPageNo() - 1) * page.getPageSize() +
		// "," + page.getPageSize();
		// List<OrderInfoDto> list = (List<OrderInfoDto>)
		// mBeanDAO.listBySQL(sql, OrderInfoDto.class);
		// page.setList(list);
		// Object count = mBeanDAO.getBySQL(totalSql);
		// BigInteger total = (BigInteger) count;
		// page.setTotal(total.longValue());
		//
		//
		// return mBeanDAO.listBySQLPageInfo(sql, clazz, page);
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
		List<TChannleBDetailedInfoPo> list = mFrameworkService.list(TChannleBDetailedInfoPo.class, mparam);
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
		return mFrameworkService.listPageInfo(TChannleBDetailedInfoPo.class, page, mparam);

	}

	public TChannleBDetailedInfoPo saveOrUpdateTChannleDetailedInfoPo(TChannleBDetailedInfoPo channleDetailedInfoPo) {
		return (TChannleBDetailedInfoPo) mBeanDAO.saveOrUpdate(channleDetailedInfoPo);
	}

	public void insertTChannleDetailedInfoPo(long userId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String data = sdf.format(new Date());
		if (userId == 0) {
			// 获取所有用户
			List<TUserInfoPo> listUserInfo = userInfoService.allUserInfoPo();
			for (TUserInfoPo tUserInfoPo : listUserInfo) {
				// 获取渠道关系信息
				List<TUserChannleBindInfoPo> list = allTChannleInfoPo(tUserInfoPo.getId());
				for (TUserChannleBindInfoPo tUserChannleBindInfoPo : list) {
					boolean has = getTodayChannleDetailedInfo(tUserInfoPo.getId(),
							tUserChannleBindInfoPo.getChannelId(), data);
					if (has)
						continue;
					TChannleBDetailedInfoPo t = new TChannleBDetailedInfoPo();
					t.setUserId(tUserInfoPo.getId());
					t.setCertificationNum(0);
					t.setClickNum(0);
					t.setOverdueRate(0);
					t.setPassNum(0);
					t.setChannelId(tUserChannleBindInfoPo.getChannelId());
					t.setCreateTime(new Timestamp(System.currentTimeMillis()));
					t.setLoanNum(0);
					t.setRegistNum(0);
					t.setStatus(1);
					mBeanDAO.saveOrUpdate(t);
				}
			}
		} else {
			// 获取渠道关系信息
			List<TUserChannleBindInfoPo> list = allTChannleInfoPo(userId);
			for (TUserChannleBindInfoPo tUserChannleBindInfoPo : list) {
				boolean has = getTodayChannleDetailedInfo(userId, tUserChannleBindInfoPo.getChannelId(), data);
				if (has)
					continue;
				TChannleBDetailedInfoPo t = new TChannleBDetailedInfoPo();
				t.setUserId(tUserChannleBindInfoPo.getChannelId());
				t.setCertificationNum(0);
				t.setClickNum(0);
				t.setOverdueRate(0);
				t.setPassNum(0);
				t.setChannelId(tUserChannleBindInfoPo.getChannelId());
				t.setCreateTime(new Timestamp(System.currentTimeMillis()));
				t.setLoanNum(0);
				t.setRegistNum(0);
				t.setStatus(1);
				mBeanDAO.saveOrUpdate(t);
			}
		}
	}
	
	public List<TUserChannleBindInfoPo> listTUserChannleBindInfoPo(long userId,String channelName){
//		MParam mparam = new MParam();
//		if (userId != 0)
//			mparam.add("userId", userId);
//		if (channelName != 0)
//			mparam.add("channelId", channelName);
//		mparam.add("status", 1);
//		String sql ="SELECT b.* FROM `t_user_chennel_bind` b,`t_channel_info` t WHERE t.`id`=b.`channel_id` AND t.`channel_name` LIKE '渠道1%'";
//		return mFrameworkService.listPageInfo(TUserChannleBindInfoPo.class, page, mparam);
//		return mFrameworkService.listBySQL(sql, clazz);
		String hql ="SELECT b FROM TUserChannleBindInfoPo b,TChannleInfoPo t WHERE t.id=b.channelId ";
		if(userId!=0)
			hql+= " AND b.userId="+userId;
		if(!channelName.equals(""))
			hql+=" AND t.channelName LIKE '%"+channelName+"%'";
		return (List<TUserChannleBindInfoPo>) mBeanDAO.listByHQL(hql);
	}
}
