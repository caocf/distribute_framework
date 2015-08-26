package com.appleframework.security.core.exception;

import java.util.Set;

import com.appleframework.security.core.utils.AuthUtils;

/**
 * Exception representing an invalid scope in a token or authorization request (i.e. from an Authorization Server). Note
 * that this is not the same as an access denied exception if the scope presented to a Resource Server is insufficient.
 * The spec in this case mandates a 400 status code.
 * 
 * @author Ryan Heaton
 * @author Dave Syer
 */
@SuppressWarnings("serial")
public class InvalidScopeException extends AuthException {

	public InvalidScopeException(String msg, Set<String> validScope) {
		this(msg);
		addAdditionalInformation("scope", AuthUtils.formatParameterList(validScope));
	}

	public InvalidScopeException(String msg) {
		super(msg);
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "invalid_scope";
	}

}