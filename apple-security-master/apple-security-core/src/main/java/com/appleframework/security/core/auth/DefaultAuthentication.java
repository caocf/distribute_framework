package com.appleframework.security.core.auth;

import java.io.Serializable;

import com.appleframework.security.core.User;
import com.appleframework.security.core.client.ClientDetails;

/**
 * Default implementation of {@link UserAuthenticationConverter}. Converts to and from an Authentication using only its name and
 * authorities.
 * 
 * @author Dave Syer
 * 
 */
public class DefaultAuthentication implements Authentication, Serializable {

	private static final long serialVersionUID = -7755467412828119283L;

	private User user;
	
	private ClientDetails clientDetails;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ClientDetails getClientDetails() {
		return clientDetails;
	}

	public void setClientDetails(ClientDetails clientDetails) {
		this.clientDetails = clientDetails;
	}
	
	public DefaultAuthentication() {
		
	}
	
	public DefaultAuthentication(User user, ClientDetails clientDetails) {
		this.user = user;
		this.clientDetails = clientDetails;
	}
	
	public static DefaultAuthentication creat(User user, ClientDetails clientDetails) {
		return new DefaultAuthentication(user, clientDetails);
	}
	
}
