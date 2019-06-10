package com.mytest.web.controller.admin;

import java.net.URLDecoder;
import java.net.URLEncoder;

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
import com.mytest.admin.po.TUserInfoPo;
import com.mytest.admin.service.UserInfoService;
import com.mytest.web.controller.base.BaseController;

@Controller
@RequestMapping("")
public class MainController extends BaseController {

	Log logger = LogFactory.getLog(MainController.class);
	@Autowired
	private UserInfoService userInfoService;

	@RequestMapping("/qrcode")
	@Transactional
	public ModelAndView qrCode(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		return new ModelAndView("/channel/qrcode", model);
	}

	
}
