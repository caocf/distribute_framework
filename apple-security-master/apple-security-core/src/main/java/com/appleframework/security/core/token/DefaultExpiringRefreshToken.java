package com.appleframework.security.core.token;

import java.util.Date;


/**
 * @author Ryan Heaton
 */
public class DefaultExpiringRefreshToken extends DefaultRefreshToken implements ExpiringRefreshToken {

	private static final long serialVersionUID = 3449554332764129719L;

	private final Date expiration;

	/**
	 * @param value
	 */
	public DefaultExpiringRefreshToken(String value, Date expiration) {
		super(value);
		this.expiration = expiration;
	}

	/**
	 * The instant the token expires.
	 * 
	 * @return The instant the token expires.
	 */
	public Date getExpiration() {
		return expiration;
	}

}
