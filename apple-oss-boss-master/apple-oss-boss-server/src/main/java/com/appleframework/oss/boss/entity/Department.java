package com.appleframework.oss.boss.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Department extends BaseEntity implements Serializable {
	
	public static final String PATH_SEPARATOR = ",";// 树路径分隔符

	private Integer id;
	private String name;
	private Integer parentId;
	private String path;
	private Integer grade;
	private Integer iorder;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Integer getIorder() {
		return iorder;
	}

	public void setIorder(Integer iorder) {
		this.iorder = iorder;
	}

}