package com.mytest.admin.po;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.base.annotation.BeanMeta;
import com.base.common.MBeanBase;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "t_channle_b_detailed")
@BeanMeta
@Getter
@Setter
public class TChannleBDetailedInfoPo extends MBeanBase implements Serializable {
	private static final long serialVersionUID = -3393641294306938691L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name="user_id")
	private long userId;
	@Column(name="channel_id")
	private long channelId;
	@Column(name="click_num")
	private int clickNum;//申请人数
	@Column(name="regist_num")
	private int registNum;//注册人数
	@Column(name="certification_num")
	private int certificationNum;//认证人数
	@Column(name="pass_num")
	private int passNum;//通过人数
	@Column(name="loan_num")
	private int loanNum;//放款人数
	@Column(name="overdue_rate")
	private double overdueRate;//首逾率
	@Column(name="createtime")
	private Timestamp createTime;
	@Column(name="updatetime")
	private Timestamp updateTime;
	@Column(name="status")
	private int status;
	
	@OneToOne(targetEntity=TChannleInfoPo.class)
	@JoinColumn(name="channel_id", referencedColumnName="id", unique=false, nullable=false,insertable=false, updatable=false)
	private TChannleInfoPo channleInfoPo;
	
	@OneToOne(targetEntity=TUserInfoPo.class)
	@JoinColumn(name="user_id", referencedColumnName="id", unique=false, nullable=false,insertable=false, updatable=false)
	private TUserInfoPo userInfoPo;
	
	@Transient
	private double conversionRate;
	
}
