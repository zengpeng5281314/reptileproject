package com.mytest.web.controller.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class IndexController extends BaseController {
	
	Log logger = LogFactory.getLog(IndexController.class);
	
	@RequestMapping("/index") 
	@Transactional
	public ModelAndView index(
			HttpServletRequest request,HttpServletResponse response,ModelMap model) throws Exception{
		return new ModelAndView("/index",model);
	}
	
	
	
}
