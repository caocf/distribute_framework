package com.appleframework.oss.boss.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RtsFunctions extends RtsRights implements Serializable {

	private Integer id;
	private String name;
	private String protectFunction;
	private Integer menuId;
	
	private RtsRights rtsRights;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RtsRights getRtsRights() {
		return this.rtsRights;
	}

	public void setRtsRights(RtsRights rtsRights) {
		this.rtsRights = rtsRights;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProtectFunction() {
		return this.protectFunction;
	}

	public void setProtectFunction(String protectFunction) {
		this.protectFunction = protectFunction;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

}