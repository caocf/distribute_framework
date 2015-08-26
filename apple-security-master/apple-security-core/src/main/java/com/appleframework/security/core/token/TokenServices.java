/*
 * Copyright 2008 Web Cohesion
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appleframework.security.core.token;

import com.appleframework.security.core.token.AccessToken;
import com.appleframework.security.core.auth.Authentication;
import com.appleframework.security.core.client.ClientDetails;
import com.appleframework.security.core.exception.AuthenticationException;
import com.appleframework.security.core.exception.InvalidTokenException;

/**
 * @author Ryan Heaton
 * @author Dave Syer
 */
public interface TokenServices {

	/**
	 * Create an access token associated with the specified credentials.
	 * @param authentication The credentials associated with the access token.
	 * @return The access token.
	 * @throws AuthenticationException If the credentials are inadequate.
	 */
	AccessToken createAccessToken(Authentication authentication) throws AuthenticationException;

	/**
	 * Refresh an access token. The authorization request should be used for 2 things (at least): to validate that the
	 * client id of the original access token is the same as the one requesting the refresh, and to narrow the scopes
	 * (if provided).
	 * 
	 * @param refreshToken The details about the refresh token.
	 * @param tokenRequest The incoming token request.
	 * @return The (new) access token.
	 * @throws AuthenticationException If the refresh token is invalid or expired.
	 */
	AccessToken refreshAccessToken(ClientDetails clientDetails, String refreshToken) throws AuthenticationException;

	/**
	 * Retrieve an access token stored against the provided authentication key, if it exists.
	 * 
	 * @param authentication the authentication key for the access token
	 * 
	 * @return the access token or null if there was none
	 */
	AccessToken getAccessToken(String accessToken);
	
	
	/**
	 * Load the credentials for the specified access token.
	 *
	 * @param accessToken The access token value.
	 * @return The authentication for the access token.
	 * @throws AuthenticationException If the access token is expired
	 * @throws InvalidTokenException if the token isn't valid
	 */
	Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException;


}