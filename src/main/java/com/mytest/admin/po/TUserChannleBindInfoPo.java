package com.mytest.admin.po;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.base.annotation.BeanMeta;
import com.base.common.MBeanBase;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "t_user_chennel_bind")
@BeanMeta
@Getter
@Setter
public class TUserChannleBindInfoPo extends MBeanBase implements Serializable {

	private static final long serialVersionUID = 6693510389152485947L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name="user_id")
	private long userId;
	@Column(name="channel_id")
	private long channelId;
	@Column(name="createtime")
	private Timestamp createTime;
	@Column(name="updatetime")
	private Timestamp updateTime;
	@Column(name="status")
	private int status;
	
	@OneToOne(targetEntity=TChannleInfoPo.class)
	@JoinColumn(name="channel_id", referencedColumnName="id", unique=false, nullable=false,insertable=false, updatable=false)
	private TChannleInfoPo channleInfoPo;
	
}
