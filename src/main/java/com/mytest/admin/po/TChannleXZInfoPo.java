package com.mytest.admin.po;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

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
@Table(name = "t_channle_xz_info")
@BeanMeta
@Getter
@Setter
public class TChannleXZInfoPo extends MBeanBase implements Serializable {
	private static final long serialVersionUID = -3393641294306938691L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name="user_id")
	private long userId;
	@Column(name="channel_id")
	private long channelId;
	@Column(name="regist_num")
	private int registNum;//
	@Column(name="applicants_num")
	private int applicantsNum;//
	@Column(name="loan_num")
	private int loanNum;//
	@Column(name="ctime")
	private Date ctime;
	@Column(name="source")
	private int source;
	@Column(name="createtime")
	private Timestamp createTime;
	@Column(name="updatetime")
	private Timestamp updateTime;
	@Column(name="status")
	private int status;
	
}
