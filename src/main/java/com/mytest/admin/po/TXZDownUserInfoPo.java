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
@Table(name = "t_xz_down_user")
@BeanMeta
@Getter
@Setter
public class TXZDownUserInfoPo extends MBeanBase implements Serializable {
	private static final long serialVersionUID = -3393641294306938691L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name="user_id")
	private long userId;
	@Column(name="channel_id")
	private long channelId;
	@Column(name="address")
	private String address;
	@Column(name="user_name")
	private String userName;
	@Column(name="user_pwd")
	private String userPwd;//
	@Column(name="channel_num")
	private int channelNum;//
	@Column(name="type")
	private int type;//
	@Column(name="createtime")
	private Timestamp createTime;
	@Column(name="updatetime")
	private Timestamp updateTime;
	@Column(name="status")
	private int status;
	
}
