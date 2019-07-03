package com.mytest.admin.po;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.base.annotation.BeanMeta;
import com.base.common.MBeanBase;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "t_channle_xz_detailed_info")
@BeanMeta
@Getter
@Setter
public class TChannleXZDetailedInfoPo extends MBeanBase implements Serializable {
	private static final long serialVersionUID = -3393641294306938691L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name="user_id")
	private long userId;
	@Column(name="channel_id")
	private long channelId;
//	@Column(name="xz_id")
//	private long xzId;//
	@Column(name="name_no")
	private int nameNo;//编号
	@Column(name="name")
	private String name;//姓名
	@Column(name="phone")
	private String phone;//手机号
	@Column(name="regist_time")
	private Timestamp registTime;//注册时间
	@Column(name="type")
	private int type;//类型 1:注册
	@Column(name="type_name")
	private String typeName;//类型 1:注册
	@Column(name="source")
	private int source;//类型 1:注册
	@Column(name="createtime")
	private Timestamp createTime;
	@Column(name="updatetime")
	private Timestamp updateTime;
	@Column(name="status")
	private int status;
	
}
