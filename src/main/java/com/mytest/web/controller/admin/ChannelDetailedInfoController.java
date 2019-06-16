package com.mytest.web.controller.admin;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.mytest.admin.po.TChannleXZInfoPo;
import com.mytest.admin.po.TChannleInfoPo;
import com.mytest.admin.po.TUserChannleBindInfoPo;
import com.mytest.admin.po.TUserInfoPo;
import com.mytest.admin.service.ChannelDetailedInfoService;
import com.mytest.admin.service.UserInfoService;
import com.mytest.utils.Arith;
import com.mytest.utils.JsonDateValueProcessor;
import com.mytest.utils.Page;
import com.mytest.web.controller.base.BaseController;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.test.JSONAssert;

@Controller
@RequestMapping("/channeldetailed")
public class ChannelDetailedInfoController extends BaseController {

	Log logger = LogFactory.getLog(ChannelDetailedInfoController.class);
	@Autowired
	private ChannelDetailedInfoService channelDetailedInfoService;

	@RequestMapping("/channel")
	@Transactional
	public ModelAndView index(@RequestParam(defaultValue = "0", required = false, value = "userId") long userId,
			@RequestParam(defaultValue = "", required = false, value = "channelName") String channelName,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		TUserInfoPo userInfoPo = (TUserInfoPo) request.getAttribute("adminInfo");
		userId = userInfoPo.getId();
		List<TUserChannleBindInfoPo> list = channelDetailedInfoService.listTUserChannleBindInfoPo(userId, channelName);

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonDateValueProcessor("yyyy-MM-dd"));
		model.put("listUserChannleBindInfo", JSONArray.fromObject(list, jsonConfig));
		return new ModelAndView("/channel/list", model);
	}

	@RequestMapping("/list")
	@Transactional
	@ResponseBody
	public String list(@RequestParam(defaultValue = "0", required = false, value = "userId") long userId,
			@RequestParam(defaultValue = "", required = false, value = "channelName") String channelName,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		TUserInfoPo userInfoPo = (TUserInfoPo) request.getAttribute("adminInfo");
		userId = userInfoPo.getId();
		List<TUserChannleBindInfoPo> list = channelDetailedInfoService.listTUserChannleBindInfoPo(userId, channelName);

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonDateValueProcessor("yyyy-MM-dd"));
		JSONObject json = new JSONObject();
		json.put("total", list.size());
		json.put("rows", JSONArray.fromObject(list, jsonConfig));
		// return JSONArray.fromObject(list, jsonConfig).toString();
		return json.toString();
	}
	
	@RequestMapping("/view")
	@Transactional
	public ModelAndView view(@RequestParam(defaultValue = "0", required = false, value = "userId") long userId,
			@RequestParam(defaultValue = "0", required = false, value = "channelId") long channelId,
			@RequestParam(defaultValue = "", required = false, value = "startTime") String startTime,
			@RequestParam(defaultValue = "", required = false, value = "endTime") String endTime,
			@RequestParam(defaultValue = "1", required = false, value = "currentPage") int currentPage,
			@RequestParam(defaultValue = "50", required = false, value = "pageSize") int pageSize,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		TUserInfoPo userInfoPo = (TUserInfoPo) request.getAttribute("adminInfo");
		userId = userInfoPo.getId();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp startT = null;
		Timestamp endT = null;
		if (!startTime.equals("") && !endTime.equals("")) {
			startTime = startTime + " 00:00:00";
			endTime = endTime + " 23:59:59";
			startT = new Timestamp(sdf.parse(startTime).getTime());
			endT = new Timestamp(sdf.parse(endTime).getTime());
		}
		Page page = new Page(currentPage, pageSize);
		Page pageList = channelDetailedInfoService.pageTChannleDetailedInfoPo(userId, channelId,startT,endT,page);

		List<TChannleXZInfoPo> list = (List<TChannleXZInfoPo>) pageList.getList();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonDateValueProcessor("yyyy-MM-dd"));
		model.put("list", JSONArray.fromObject(list, jsonConfig));
		model.put("total", list.size());
		return new ModelAndView("/channel/view", model);
	}
	
	@RequestMapping("/viewlist")
	@Transactional
	@ResponseBody
	public String viewlist(@RequestParam(defaultValue = "0", required = false, value = "userId") long userId,
			@RequestParam(defaultValue = "0", required = false, value = "channelId") long channelId,
			@RequestParam(defaultValue = "", required = false, value = "startTime") String startTime,
			@RequestParam(defaultValue = "", required = false, value = "endTime") String endTime,
			@RequestParam(defaultValue = "1", required = false, value = "currentPage") int currentPage,
			@RequestParam(defaultValue = "50", required = false, value = "pageSize") int pageSize,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		TUserInfoPo userInfoPo = (TUserInfoPo) request.getAttribute("adminInfo");
		userId = userInfoPo.getId();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp startT = null;
		Timestamp endT = null;
		if (!startTime.equals("") && !endTime.equals("")) {
			startTime = startTime + " 00:00:00";
			endTime = endTime + " 23:59:59";
			startT = new Timestamp(sdf.parse(startTime).getTime());
			endT = new Timestamp(sdf.parse(endTime).getTime());
		}
		Page page = new Page(currentPage, pageSize);
		Page pageList = channelDetailedInfoService.pageTChannleDetailedInfoPo(userId, channelId,startT,endT,page);

		List<TChannleXZInfoPo> list = (List<TChannleXZInfoPo>) pageList.getList();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonDateValueProcessor("yyyy-MM-dd"));
		JSONObject json = new JSONObject();
		json.put("listsize", list.size());
		json.put("data", JSONArray.fromObject(list, jsonConfig));
		// return JSONArray.fromObject(list, jsonConfig).toString();
		return json.toString();
	}

}
