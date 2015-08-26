package com.appleframework.security.core.token;

import java.io.Serializable;

/**
 * An OAuth 2 refresh token.
 * 
 * @author Ryan Heaton
 * @author Cruise.Xu
 */
public class DefaultRefreshToken implements Serializable, RefreshToken {

	private static final long serialVersionUID = 8349970621900575838L;

	private String value;

	/**
	 * Create a new refresh token.
	 */
	public DefaultRefreshToken(String value) {
		this.value = value;
	}
	
	/**
	 * Default constructor for JPA and other serialization tools.
	 */
	@SuppressWarnings("unused")
	private DefaultRefreshToken() {
		this(null);
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.common.IFOO#getValue()
	 */
	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return getValue();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof DefaultRefreshToken)) {
			return false;
		}

		DefaultRefreshToken that = (DefaultRefreshToken) o;

		if (value != null ? !value.equals(that.value) : that.value != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return value != null ? value.hashCode() : 0;
	}
}
