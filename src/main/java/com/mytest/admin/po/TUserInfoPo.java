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
@Table(name = "t_user_info")
@BeanMeta
@Getter
@Setter
public class TUserInfoPo extends MBeanBase implements Serializable {

	private static final long serialVersionUID = -7125461958934252679L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name="phone")
	private String phone;
	@Column(name="nickname")
	private String nickName;
	@Column(name="email")
	private String email;
	@Column(name="sex")
	private int sex;
	@Column(name="headimgurl")
	private String headImgUrl;
	@Column(name="remark")
	private String remark;
	@Column(name="password")
	private String password;
	@Column(name="type")
	private int type;
	@Column(name="createtime")
	private Timestamp createTime;
	@Column(name="updatetime")
	private Timestamp updateTime;
	@Column(name="status")
	private int status;
	
}
