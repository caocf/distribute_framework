package com.appleframework.oss.boss.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("serial")
public class RtsRole extends RtsRights implements Serializable {

	private Integer id;
	private String roleName;
	private Integer state;
	private String description;
	private Department department;
	private String departPath;
	private Integer grade;

	private Set<RtsRoleRights> rtsRoleRightses = new HashSet<RtsRoleRights>(0);
	private Set<RtsUserRole> rtsUserRoles = new HashSet<RtsUserRole>(0);

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Set<RtsRoleRights> getRtsRoleRightses() {
		return rtsRoleRightses;
	}

	public void setRtsRoleRightses(Set<RtsRoleRights> rtsRoleRightses) {
		this.rtsRoleRightses = rtsRoleRightses;
	}

	public Set<RtsUserRole> getRtsUserRoles() {
		return rtsUserRoles;
	}

	public void setRtsUserRoles(Set<RtsUserRole> rtsUserRoles) {
		this.rtsUserRoles = rtsUserRoles;
	}

}