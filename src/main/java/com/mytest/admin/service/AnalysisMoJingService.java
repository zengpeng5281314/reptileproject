package com.mytest.admin.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mytest.admin.po.TChannleXZDetailedInfoPo;
import com.mytest.admin.po.TChannleXZInfoPo;
import com.mytest.admin.po.TXZDownUserInfoPo;
import com.mytest.utils.JsonDateValueProcessor;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Service
public class AnalysisMoJingService {

	@Autowired
	private DownLoadDetailedService downLoadDetailedService;

	/**
	 * 解析魔镜用户数据
	 * 
	 * @param txzDownUserInfoPo
	 * @param date
	 *            yyyy-MM-dd
	 * @param json
	 */
	public void analysisChannelUserInfo(TXZDownUserInfoPo txzDownUserInfoPo, String date, String json) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
		JSONObject jon = JSONObject.fromObject(json, jsonConfig);
		//{"code":"40000","data":null,"message":"未登录，请重新登陆"}
		if(!jon.containsKey("code"))
			return;
		String code = jon.getString("code");
		if(!code.equals("2000"))
			return;
		if (jon.containsKey("data")) {
			// JSONArray channleList =
			// jon.getJSONObject("data").getJSONArray("channelList");
			JSONObject channelpm = jon.getJSONObject("data").getJSONObject("pm");
			JSONArray items = channelpm.getJSONArray("items");
			int registNums = 0;
			int applactionNum = 0;
			if (items.size() > 0) {
				registNums = items.getJSONObject(0).getInt("registerUserNumber");
				applactionNum = items.getJSONObject(0).getInt("replyBorrowUserNumber");
			}
			TChannleXZInfoPo channleXZInfoPo = downLoadDetailedService
					.getTChannleXZInfoPo(txzDownUserInfoPo.getUserId(), date);
			if (channleXZInfoPo == null) {
				channleXZInfoPo = new TChannleXZInfoPo();
				try {
					channleXZInfoPo.setCtime(sdf.parse(date));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				channleXZInfoPo.setUserId(txzDownUserInfoPo.getUserId());
				channleXZInfoPo.setChannelId(txzDownUserInfoPo.getChannelId());
				channleXZInfoPo.setCreateTime(new Timestamp(System.currentTimeMillis()));
				channleXZInfoPo.setStatus(1);
				channleXZInfoPo.setSource(2);
			}
			channleXZInfoPo.setApplicantsNum(applactionNum);
			channleXZInfoPo.setRegistNum(registNums);
			downLoadDetailedService.saveOrUpdatetTChannleXZInfoPo(channleXZInfoPo);
		}
	}

	/**
	 * 解析魔镜用户数据
	 * 
	 * @param txzDownUserInfoPo
	 * @param json
	 */
	public void analysisTChannleXZDetailedInfoPo(TXZDownUserInfoPo txzDownUserInfoPo, String json) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
		JSONObject jon = JSONObject.fromObject(json, jsonConfig);
		if(!jon.containsKey("code"))
			return;
		String code = jon.getString("code");
		if(!code.equals("2000"))
			return;
		if (jon.containsKey("data")) {
			JSONObject channelpm = jon.getJSONObject("data").getJSONObject("pm");
			JSONArray items = channelpm.getJSONArray("items");

			for (int i = 0; i < items.size(); i++) {
				JSONObject object = items.getJSONObject(i);
				long timeL = object.getJSONObject("createTime").getLong("time");
				String registTime = sdf1.format(new Timestamp(timeL));
				String name = object.getString("realname");
				String phone = object.getString("userPhone");
				Timestamp time = new Timestamp(timeL);

				TChannleXZDetailedInfoPo channleXZDetailedInfoPo = downLoadDetailedService
						.getTChannleXZDetailedInfoPo(txzDownUserInfoPo.getUserId(), phone, registTime);
				if (channleXZDetailedInfoPo == null) {
					channleXZDetailedInfoPo = new TChannleXZDetailedInfoPo();
					channleXZDetailedInfoPo.setChannelId(txzDownUserInfoPo.getChannelId());
					channleXZDetailedInfoPo.setCreateTime(new Timestamp(System.currentTimeMillis()));
					channleXZDetailedInfoPo.setPhone(phone);
					channleXZDetailedInfoPo.setRegistTime(time);
					channleXZDetailedInfoPo.setStatus(1);
					channleXZDetailedInfoPo.setType(1);
					channleXZDetailedInfoPo.setSource(2);// 1:星座 2:魔镜
					channleXZDetailedInfoPo.setUserId(txzDownUserInfoPo.getUserId());
					// channleXZDetailedInfoPo.setXzId(0);
				}
				channleXZDetailedInfoPo.setTypeName("");
				channleXZDetailedInfoPo.setName(name);
				// channleXZDetailedInfoPo.setNameNo(Integer.valueOf(nameNo));
				downLoadDetailedService.saveOrUpdateTChannleXZtailedInfoPo(channleXZDetailedInfoPo);

			}

		}
	}

	public static void main(String[] args) {
		String date = "{'date':27,'day':4,'hours':8,'minutes':50,'month':5,'seconds':20,'time':1561596620000,'timezoneOffset':-480,'year':119}";
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
		try {
			JSONObject jon = JSONObject.fromObject(date, jsonConfig);

			System.out.println(sdf1.format(new Timestamp(jon.getLong("time"))));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
