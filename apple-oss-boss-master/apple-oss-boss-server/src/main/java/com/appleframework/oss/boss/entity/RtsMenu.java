package com.appleframework.oss.boss.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RtsMenu extends RtsRights implements Serializable {
	
	public static final String PATH_SEPARATOR = ",";// 树路径分隔符

	private Integer id;
	private String url;
	private String urlName;
	private String baseUrl;
	private Integer parentId;
	private Integer iorder;
	private Integer state;
	private Integer openStyle;
	private String path;// 树路径
	private Integer grade;// 层级
	private String iconUrl;
	
	private Integer isHidden;

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

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getUrlName() {
		return this.urlName;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}

	public Integer getIorder() {
		return this.iorder;
	}

	public void setIorder(Integer iorder) {
		this.iorder = iorder;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getOpenStyle() {
		return openStyle;
	}

	public void setOpenStyle(Integer openStyle) {
		this.openStyle = openStyle;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
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

	public Integer getIsHidden() {
		return isHidden;
	}

	public void setIsHidden(Integer isHidden) {
		this.isHidden = isHidden;
	}

}