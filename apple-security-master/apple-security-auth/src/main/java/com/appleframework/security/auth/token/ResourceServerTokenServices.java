package com.appleframework.security.auth.token;

import com.appleframework.security.core.token.AccessToken;
import com.appleframework.security.core.auth.Authentication;
import com.appleframework.security.core.exception.AuthenticationException;
import com.appleframework.security.core.exception.InvalidTokenException;

public interface ResourceServerTokenServices {

	/**
	 * Load the credentials for the specified access token.
	 *
	 * @param accessToken The access token value.
	 * @return The authentication for the access token.
	 * @throws AuthenticationException If the access token is expired
	 * @throws InvalidTokenException if the token isn't valid
	 */
	Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException;

	/**
	 * Retrieve the full access token details from just the value.
	 * 
	 * @param accessToken the token value
	 * @return the full access token with client id etc.
	 */
	AccessToken readAccessToken(String accessToken);

}