package com.mytest.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/** 
* @ClassName: JSONResult 
* @Description: 构造返回的json串，用于数据校验及返回显示 
* @author create by yushengwei @ 2015年8月12日 下午5:20:56
* @param <T> 
*/
public class JSONResult<T> {
	public final static int ERROR_CODE = -1;
	public final static int ERROR_CODE_ERROR_PARAM = -2;
	public final static int NOLOGIN_CODE = -403;// 没有登录状态码

	public final static int SUCCUESS_CODE = 0;
	
	private boolean success;
	
	private int status;

	private String msg;

	private T result;

	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

    
	public static <M> JSONResult<M> getErrorResult(Integer code, String msg) {
		JSONResult<M> result = new JSONResult<M>();
		result.setSuccess(false);
		result.setStatus(code);
		result.setMsg(msg);
		return result;
	}
	
	public static <M> JSONResult<Object> getErrorResult(Integer code, String msg,Object obj) {
		JSONResult<Object> result = new JSONResult<Object>();
		result.setSuccess(false);
		result.setStatus(code);
		result.setMsg(msg);
		result.setResult(obj);
		return result;
	}

	public static <M> JSONResult<M> getSuccessResult(M obj) {
		JSONResult<M> result = new JSONResult<M>();
		result.setSuccess(true);
		result.setStatus(SUCCUESS_CODE);
		result.setMsg("成功");
		result.setResult(obj);
		return result;
	}

	public static <M> JSONResult<M> getCommonResult(M obj) {
		if (obj != null) {
			return getSuccessResult(obj);
		}
		return getErrorResult(ERROR_CODE, "系统繁忙！请稍后再试！");
	}
	
	/**
	 * 获取没有登录返回的JSON字符串
	 * @return
	 */
	public static String getNoLoginResult() {
		return JSON.toJSONString(JSONResult.getErrorResult(
				JSONResult.NOLOGIN_CODE, "用户信息错误！"),
				SerializerFeature.BrowserCompatible);
	}
	
	public static <M> JSONResult<M>  getNeedLoginResult(){
		JSONResult<M> result = new JSONResult<M>();
		result.setSuccess(false);
		result.setStatus(NOLOGIN_CODE);
		result.setMsg("用户信息错误！");
		result.setResult(null);
		return result;
	}
}