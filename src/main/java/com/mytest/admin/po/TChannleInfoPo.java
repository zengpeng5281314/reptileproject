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
@Table(name = "t_channel_info")
@BeanMeta
@Getter
@Setter
public class TChannleInfoPo extends MBeanBase implements Serializable {

	private static final long serialVersionUID = 6693510389152485947L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name="channel_name")
	private String channelName;
	@Column(name="channel_mark")
	private String channelMark;
	@Column(name="chennel_address")
	private String chennelAddress;
	@Column(name="type")
	private int type;
	@Column(name="createtime")
	private Timestamp createTime;
	@Column(name="updatetime")
	private Timestamp updateTime;
	@Column(name="status")
	private int status;
	
}
