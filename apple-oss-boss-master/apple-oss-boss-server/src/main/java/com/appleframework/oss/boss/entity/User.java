package com.appleframework.oss.boss.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("serial")
public class User extends BaseEntity implements Serializable {

	private Integer id;
	private String username;
	private String realname;
	private String password;
	private String joinip;
	private Integer state;
	private Integer isadmin;
	private String mobile;
	private String email;
	private String roles;
	private Department department;
	private String departPath;
	
	private Set<RtsUserRole> rtsUserRoles = new HashSet<RtsUserRole>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getJoinip() {
		return joinip;
	}

	public void setJoinip(String joinip) {
		this.joinip = joinip;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getIsadmin() {
		return isadmin;
	}

	public void setIsadmin(Integer isadmin) {
		this.isadmin = isadmin;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<RtsUserRole> getRtsUserRoles() {
		return rtsUserRoles;
	}

	public void setRtsUserRoles(Set<RtsUserRole> rtsUserRoles) {
		this.rtsUserRoles = rtsUserRoles;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getDepartPath() {
		return departPath;
	}

	public void setDepartPath(String departPath) {
		this.departPath = departPath;
	}
	
}