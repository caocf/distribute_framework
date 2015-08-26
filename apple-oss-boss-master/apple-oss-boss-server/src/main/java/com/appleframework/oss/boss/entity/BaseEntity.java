package com.appleframework.oss.boss.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类 - 基类
 */

public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1244628542449196767L;
	
	public static final String CREATE_TIME_PROPERTY_NAME = "createTime";// "创建日期"属性名称
	public static final String UPDATE_TIME_PROPERTY_NAME = "updateTime";// "修改日期"属性名称

	protected Date createTime;// 创建日期
	protected Date updateTime;// 修改日期
	protected Integer lastOperatorId;// 最后修改操作人ID
	protected String lastOperatorName;// 最后修改操作人姓名

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getLastOperatorId() {
		return lastOperatorId;
	}

	public void setLastOperatorId(Integer lastOperatorId) {
		this.lastOperatorId = lastOperatorId;
	}

	public String getLastOperatorName() {
		return lastOperatorName;
	}

	public void setLastOperatorName(String lastOperatorName) {
		this.lastOperatorName = lastOperatorName;
	}

}