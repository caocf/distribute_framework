package com.appleframework.security.core.auth;

import com.appleframework.security.core.User;
import com.appleframework.security.core.client.ClientDetails;


public interface Authentication {
	
	public User getUser();

	public void setUser(User user);

	public ClientDetails getClientDetails();

	public void setClientDetails(ClientDetails clientDetails);
}

