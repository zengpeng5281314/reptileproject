package com.mytest.utils;

/**.
 * 
 * @author xusq
 */
public final class Constants {

	
	/**
	 *.
	 */
	private Constants(){
		
	}
	
    /**
     * redisKey
     */
    
	public static String REDIS_KEY = "order.cart";
	
	/**
	 * 登录图片验证码  
	 */
	public static String LOGIN_KEY = "loginKey";
	/**
	 * 注册图片验证码
	 */
	public static String REGISTER_KEY = "registerKey";
	/**
	 * 忘记密码图片验证码
	 */
	public static String FORGETPASSWORD_KEY = "forgetPasswordKey";
	/**
	 * 绑定手机图片验证码
	 */
	public static String BINDPHONE_KEY = "bindPhoneKey";
	
	/**
	 * 操作失败
	 */
	public static final int OPERATION_FAILED = 0;
	
	/**
	 * 操作成功
	 */
	public static final int OPERATION_SUCCESSFUL = 1;

	/**
	 * 待审核
	 */
	public static final int TO_BE_APPROVED = 0;
	
	/**
	 * 审核通过
	 */
	public static final int APPROVED  = 1;
	
	/**
	 * 审核未通过
	 */
	public static final int UNAPPROVED = 2;

	
	/**
	 * 一页显示的条数.
	 */
	public static final int PAGE_NUM_TEN = 10;
	/**
	 * 失效时间30*60.
	 */
	public static final int OUT_TIME_1800 = 1800;
	/**
	 * 失效时间60*60.
	 */
	public static final int OUT_TIME_3600 = 3600;
	/**
	 * 永不过期.
	 */
	public static final int OUT_TIME_FOREVER = -1;
	
	/**
	 * 公用常量 数字0.
	 */
	public static final int PUBLIC_STATIC_NUM_0= 0;
	/**
	 * 公用常量 数字1.
	 */
	public static final int PUBLIC_STATIC_NUM_1= 1;
	/**
	 * 公用常量 数字10.
	 */
	public static final int PUBLIC_STATIC_NUM_10= 10;
	/**
	 * 公用常量 数字100.
	 */
	public static final int PUBLIC_STATIC_NUM_100= 100;
	/**
	 * 公用常量 数字100.
	 */
	public static final int PUBLIC_STATIC_NUM_1000= 1000;
	/**
	 * 公用常量 数字1024.
	 */
	public static final int PUBLIC_STATIC_NUM_1024= 1024;
	
	/**
	 * 公用常量 数字Long1.
	 */
	public static final Long PUBLIC_STATIC_LONG_1= 1l;
	
	/**
	 * 状态正常1
	 */
	public static final String SUCCESS = "1";
	/**
	 * 状态错误0
	 */
	public static final String ERROR = "0";

	/**
	 * 删除数据  修改status 为-1
	 */
	public static final Integer STATUS_DEL = -1;
	/**
	 * 正常数据  修改status 为1
	 */
	public static final Integer STATUS_SUCCESS = 1;

	/**
	 * 标识PC端
	 */
	public static final Integer SITE_PC = 2;
	
	/**
	 * 标识微信端
	 */
	public static final Integer SITE_WX = 3;
	
	
	

	public static String IMAGES_VIEW2 = SysServiceSingleton.getImagePath();
	
	public static String FRISTHB_TIME = "2016-12-10 00:00:00";
	
}
