package com.appleframework.oss.boss.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("serial")
public class RtsRights extends BaseEntity implements Serializable {

	private Integer id;
	//private String resourceKey;
	private String resouceDescription;
	private Integer state;
	
	private Set<RtsRoleRights> rtsRoleRightses = new HashSet<RtsRoleRights>(0);
	private Set<RtsMenu> rtsMenus = new HashSet<RtsMenu>(0);
	private Set<RtsFunctions> rtsFunctionses = new HashSet<RtsFunctions>(0);

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/*public String getResourceKey() {
		return this.resourceKey;
	}

	public void setResourceKey(String resourceKey) {
		this.resourceKey = resourceKey;
	}*/

	public String getResouceDescription() {
		return this.resouceDescription;
	}

	public void setResouceDescription(String resouceDescription) {
		this.resouceDescription = resouceDescription;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Set<RtsRoleRights> getRtsRoleRightses() {
		return rtsRoleRightses;
	}

	public void setRtsRoleRightses(Set<RtsRoleRights> rtsRoleRightses) {
		this.rtsRoleRightses = rtsRoleRightses;
	}

	public Set<RtsMenu> getRtsMenus() {
		return rtsMenus;
	}

	public void setRtsMenus(Set<RtsMenu> rtsMenus) {
		this.rtsMenus = rtsMenus;
	}

	public Set<RtsFunctions> getRtsFunctionses() {
		return rtsFunctionses;
	}

	public void setRtsFunctionses(Set<RtsFunctions> rtsFunctionses) {
		this.rtsFunctionses = rtsFunctionses;
	}

}