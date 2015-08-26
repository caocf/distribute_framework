package com.appleframework.oss.boss.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RtsRoleRights extends BaseEntity implements Serializable {

	private Integer id;
	private RtsRole rtsRole;
	private RtsRights rtsRights;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RtsRole getRtsRole() {
		return rtsRole;
	}

	public void setRtsRole(RtsRole rtsRole) {
		this.rtsRole = rtsRole;
	}

	public RtsRights getRtsRights() {
		return rtsRights;
	}

	public void setRtsRights(RtsRights rtsRights) {
		this.rtsRights = rtsRights;
	}

}