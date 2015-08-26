package com.appleframework.oss.boss.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PasswordBO implements Serializable {

	private String oldPassword;
	private String newPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
