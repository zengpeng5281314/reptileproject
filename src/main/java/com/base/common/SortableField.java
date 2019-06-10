package com.base.common;

import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.persistence.Id;

import com.base.annotation.FieldMeta;
import com.base.annotation.FormMeta;
import com.base.annotation.MSearchMeta;
import com.base.annotation.FormMeta.EDITCOMPONENT;
import com.base.annotation.FormMeta.MFormOption;
import com.base.annotation.FormMeta.OPTIONTYPE;
import com.base.annotation.FormMeta.UPLOADTYPE;
import com.base.annotation.MSearchMeta.MSEARCHTYPE;

public class SortableField implements Comparable<SortableField> {
	
	public static SortableField buildSortableField(BeanInfo beanInfo, Field field){
		// 获取字段中包含fieldMeta的注解
		FieldMeta meta = field.getAnnotation(FieldMeta.class);
		FormMeta formMeta = field.getAnnotation(FormMeta.class);
		MSearchMeta msearchMeta = field.getAnnotation(MSearchMeta.class);
		
		SortableField sf = new SortableField(beanInfo, meta, formMeta, field);
		if(msearchMeta!=null){
			if(beanInfo.msearchList==null)	// 该Bean对像所支持搜索的字段列表
				beanInfo.msearchList = new ArrayList<SortableField>();
			
			sf.loadMSearchMeta(msearchMeta);
			beanInfo.msearchList.add(sf);
		}

		return sf;
	}

	public SortableField() {
	}

	public SortableField(BeanInfo beanInfo, FieldMeta meta, FormMeta formMeta, Field field) {
		super();
		this.beanInfo = beanInfo;
		this.field = field;
		this.name = field.getName();
		this.type = field.getType();
		this.id = field.getAnnotation(Id.class)!=null;
		
		if(meta==null){
			this.metaname = field.getName();
		} else {
			this.metaname = meta.name();
			this.summary = meta.summary();
			this.order = meta.order();
			this.createtime = meta.createtime();
			this.editable = meta.editable();
			this.createable = meta.createable();
		}
		
		if(formMeta==null){
			this.required = true;
			this.maxlength = 100;
			this.placeholder = "请输入" + this.metaname;
			this.dateformat = "yyyy-MM-dd HH:mm:ss";
			this.cropImageHeight = 0;
			this.cropImageWidth = 0;
		} else {
			this.password = formMeta.password();
			this.hide = formMeta.hide();
			this.required = formMeta.required();
			this.uniqueness = formMeta.uniqueness();
			this.defvalue = formMeta.defvalue();
			this.maxlength = formMeta.maxlength();
			this.placeholder = formMeta.placeholder();
			this.formtip = formMeta.formtip();
			this.upload = formMeta.upload();
			this.upload_posturi = formMeta.upload_posturi();
			this.upload_regexp = formMeta.upload_regexp();
			
			// options
			this.optiontype = formMeta.optiontype();
			this.formoptions = MFormOption.build(formMeta.options());
			this.optionsHQL = formMeta.optionsHQL();
			this.optionTextHQL = formMeta.optionTextHQL();
			
			this.dateformat = formMeta.dateformat();
			this.cropImageHeight = formMeta.cropImageHeight();
			this.cropImageWidth = formMeta.cropImageWidth();
			this.editcomponent = formMeta.editcomponent();
			this.componentvm = formMeta.componentvm();
			this.loginid = formMeta.loginid();
			this.loginpasswd = formMeta.loginpasswd();
		}
	}

	private BeanInfo beanInfo;	// 所属MBean描述对像
	private Field field;
	private String name;		// 字段名
	private String metaname;	// 中文名
	private int order;			// 排序
	private boolean id;
	private boolean createtime;
	private boolean editable;
	private boolean createable;
	private boolean password;
	private boolean summary;
	private boolean hide;		// 表单中不显示该字段
	private boolean required;
	private boolean uniqueness;	// 表单输入是否要求不可重复
	private Object defvalue;	// 默认值（用于创建界面）
	private int maxlength;		// 文本内容最大长度
	private String placeholder;
	private String formtip;
	private UPLOADTYPE upload = UPLOADTYPE.NONE;
	private String upload_posturi = "";
	private String upload_regexp = "";
	private OPTIONTYPE optiontype = OPTIONTYPE.NONE;
	private MFormOption[] formoptions;
	private String optionsHQL;		// 选项数据列表查询HQL
	private String optionTextHQL;	// 选项反查HQL
	private String dateformat;
	private int cropImageWidth;
	private int cropImageHeight;
	private EDITCOMPONENT editcomponent = EDITCOMPONENT.NONE;	// 编辑器组件
	private String componentvm = "";							// 自定义编辑模板
	private MSEARCHTYPE msearchtype;
	private String msearchdefvalue;
	private Class<?> type;
	
	private boolean loginid;
	private boolean loginpasswd;
	
	public BeanInfo getBeanInfo() {
		return beanInfo;
	}
	public Field getField() {
		return field;
	}
	public String getName() {
		return name;
	}
	public String getMetaname() {
		return metaname;
	}
	public int getOrder() {
		return order;
	}
	public boolean isId() {
		return id;
	}
	public boolean isCreatetime() {
		return createtime;
	}
	public boolean isEditable() {
		return editable;
	}
	public SortableField setEditable(boolean editable) {
		this.editable = editable;
		return this;
	}
	public boolean isCreateable() {
		return createable;
	}
	public void setCreateable(boolean createable) {
		this.createable = createable;
	}
	public boolean isPassword() {
		return password;
	}
	public boolean isSummary() {
		return summary;
	}
	public SortableField appendtoSummary(BeanInfo beanInfo) {
		beanInfo.getSummaryList().add(this);
		return this;
	}
	public void removeSummary(BeanInfo beanInfo) {
		beanInfo.getSummaryList().remove(this);
	}
	public boolean isHide() {
		return hide;
	}
	public SortableField setHide(boolean hide) {
		this.hide = hide;
		return this;
	}
	public boolean isRequired() {
		return required;
	}
	public SortableField setRequired(boolean required) {
		this.required = required;
		return this;
	}
	public boolean isUniqueness() {
		return uniqueness;
	}
	public Object getDefvalue() {
		return defvalue==null ? "" : defvalue;
	}
	public int getMaxlength() {
		return maxlength;
	}
	public String getPlaceholder() {
		return placeholder;
	}
	public String getFormtip() {
		return formtip;
	}
	public UPLOADTYPE getUpload() {
		return upload;
	}
	public String getUpload_posturi() {
		return upload_posturi;
	}
	public String getUpload_regexp() {
		return upload_regexp;
	}
	public OPTIONTYPE getOptiontype() {
		return optiontype;
	}
	public MFormOption[] getFormoptions() {
		return formoptions;
	}
	public String getOptionsHQL() {
		return optionsHQL;
	}
	public String getOptionTextHQL() {
		return optionTextHQL;
	}
	public String getDateformat() {
		return dateformat;
	}
	public int getCropImageWidth() {
		return cropImageWidth;
	}
	public int getCropImageHeight() {
		return cropImageHeight;
	}
	public EDITCOMPONENT getEditcomponent() {
		return editcomponent;
	}
	public String getComponentvm() {
		return componentvm;
	}
	public MSEARCHTYPE getMsearchtype() {
		return msearchtype;
	}
	public SortableField setMsearchtype(MSEARCHTYPE msearchtype) {
		this.msearchtype = msearchtype;
		return this;
	}
	public String getMsearchdefvalue() {
		return msearchdefvalue;
	}
	public Class<?> getType() {
		return type;
	}
	public boolean isLoginid() {
		return loginid;
	}
	public boolean isLoginpasswd() {
		return loginpasswd;
	}
	@Override
	public int compareTo(SortableField field) {
		return field.order > this.order ? -1 : 1;
	}
	
	// 加载搜索元参数
	public void loadMSearchMeta(MSearchMeta msearchMeta){
		this.msearchtype = msearchMeta.searchtype();
		this.msearchdefvalue = msearchMeta.searchdefvalue();
	}
}
