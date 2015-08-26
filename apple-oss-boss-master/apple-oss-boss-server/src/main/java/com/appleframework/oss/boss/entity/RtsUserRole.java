package com.appleframework.oss.boss.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RtsUserRole extends BaseEntity implements Serializable {

	private Integer id;
	private RtsRole rtsRole;
	private User user;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}