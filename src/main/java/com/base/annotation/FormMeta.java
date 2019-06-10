package com.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.util.StringUtils;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FormMeta {
	
	public enum UPLOADTYPE {
		NONE("none"),
		IMAGE("image"),
		AUDIO("audio"),
		FILE("file");

	    private final String val;

	    UPLOADTYPE(String val) {
	        this.val = val;
	    }

	    public String getUploadtype() {
	        return val;
	    }
	}
	
	/** 选项表单类型 */
	public enum OPTIONTYPE {
		/** 非选项类型（默认） */
		NONE,
		/** 单选框 */
		RADIO,
		/** 下拉菜单 */
		SELECT,
		/** 下拉菜单 */
		SELECTDB
	}
	
	/** 选项表单类型 */
	public enum EDITCOMPONENT {
		NONE,
		CUSTOM,			// 自定义
		CKEDITOR,		// 富文本编辑
		ORDER			// 排序
	}
	
	public static class MFormOption {
		public static String getOptionText(MFormOption[] options, String value){
			for(MFormOption option : options){
				if(option.getOptionValue().equals(value))
					return option.getOptionText();
			}
			return null;
		}

		/*// 数据库选项转值
		@SuppressWarnings("unchecked")
		public static String getHQLOptionTextByValue(String optionTextHQL, String value){
			if(StringUtils.isEmpty(value))
				return "";
			
			String hql = CommonUtils.replace(optionTextHQL, "?", value); 
			MBeanListService mbeanlistservice = MModuleSpringUtils.getBean(MBeanListService.class);
			List<Object> resultList = (List<Object>)mbeanlistservice.listByHQL(hql);
    		if(resultList.size()>0)
    			return (String)resultList.get(0);
    		
    		return null;
		}
		// 数据库选项加载
		@SuppressWarnings("unchecked")
		private static MFormOption[] buildHQLOptions(String optionHQL){
			MBeanListService mbeanlistservice = MModuleSpringUtils.getBean(MBeanListService.class);
			List<Object[]> optionsList = (List<Object[]>)mbeanlistservice.listByHQL(optionHQL);
			MFormOption[] formoptions = new MFormOption[optionsList.size()];
			for(int i=0;i<optionsList.size();i++){
				Object[] optiondata = optionsList.get(i);
				MFormOption formoption = new MFormOption();
				formoption.optionValue = (String)optiondata[0];
				formoption.optionText = (String)optiondata[1];
				formoptions[i] = formoption;
			}
			
			return formoptions;
		}*/
		
		public static MFormOption[] build(String[] options){
			MFormOption[] formoptions = new MFormOption[options.length];
			for(int i=0;i<options.length;i++){
				String optiondata = options[i];
				if(StringUtils.isEmpty(optiondata))
					continue;

				MFormOption formoption = new MFormOption();
				int pos = optiondata.indexOf(":");	// 无冒号分格时，解析为显示与值一致
				if(pos<0){
					formoption.optionValue = optiondata;
					formoption.optionText = optiondata;
				} else {
					formoption.optionValue = optiondata.substring(0, pos);
					formoption.optionText = optiondata.substring(pos+1);
				}
				formoptions[i] = formoption;
			}
			
			return formoptions;
		}
		
		private String optionValue;
		private String optionText;
		
		public String getOptionValue() {
			return optionValue;
		}
		public String getOptionText() {
			return optionText;
		}
	}
	
	
	
	/**
	 * 是否做为登录用户名使用
	 */
	boolean loginid() default false;
	
	/**
	 * 是否做为登录密码（只允许一个字段）
	 */
	boolean loginpasswd() default false;
	
	/**
	 * 是否为密码字段，管理表单中显示为***效果
	 */
	boolean password() default false;
	
	/**
	 * 在编辑、新建中是否隐藏该字段
	 */
	boolean hide() default false;
	
	/**
	 * 是否为上传文件
	 */
	UPLOADTYPE upload() default UPLOADTYPE.NONE;
	
	/**
	 * 上传地址
	 */
	String upload_posturi() default "/account/upload";
	
	/**
	 * 上传文件扩展名适配正则
	 */
	String upload_regexp() default "/[\\.mp3 || \\.MP3]$/";	// 原样为：/[\.mp3 || \.MP3]$/
	
	/**
	 * 表单占位提示内容
	 */
	String placeholder() default "";
	
	/**
	 * 表单填写说明
	 */
	String formtip() default "";
	
	/**
	 * 表单字段是否为必填项
	 */
	boolean required() default true;
	
	/**
	 * 表单输入是否要求不可重复
	 */
	boolean uniqueness() default false;

	/**
	 * 字段默认值（用于创建界面）
	 */
	String defvalue() default "";
	
	/**
	 * 文本内容最大长度
	 */
	int maxlength() default 100;		// 500以上时为textarea
	
	/**
	 * 选项类型
	 */
	OPTIONTYPE optiontype() default OPTIONTYPE.NONE;
	
	/**
	 * 选项数据
	 */
	String[] options() default {};
	
	/**
	 * 选项数据（OPTIONTYPE.SELECTDB）加载HQL
	 * （输出字段为：ID，显示文本）
	 */
	String optionsHQL() default "";			// 列表语句（如：select id, text from MBean）
	String optionTextHQL() default "";		// 反查语句（如：select text from MBean where id=?）
	
	
	/**
	 * 图片上传切图尺寸（比例）
	 */
	int cropImageWidth() default 0;
	int cropImageHeight() default 0;
	
	/**
	 * Date类型时的输入输出格式
	 */
	String dateformat() default "yyyy-MM-dd HH:mm:ss";
	
	
	/**
	 * 编辑器组件名
	 */
	EDITCOMPONENT editcomponent() default EDITCOMPONENT.NONE;	// ckeditor
	String componentvm() default "";
	
}
