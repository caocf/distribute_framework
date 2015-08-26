package com.appleframework.security.auth.token;

import java.util.Collection;

import com.appleframework.security.core.auth.Authentication;
import com.appleframework.security.core.token.AccessToken;
import com.appleframework.security.core.token.RefreshToken;

/**
 * Persistence interface for OAuth2 tokens.
 */
public interface TokenStore {

	/**
	 * Read the authentication stored under the specified token value.
	 * 
	 * @param token The token value under which the authentication is stored.
	 * @return The authentication, or null if none.
	 */
	Authentication readAuthentication(AccessToken token);

	/**
	 * Read the authentication stored under the specified token value.
	 * 
	 * @param token The token value under which the authentication is stored.
	 * @return The authentication, or null if none.
	 */
	Authentication readAuthentication(String token);

	/**
	 * Store an access token.
	 * 
	 * @param token The token to store.
	 * @param authentication The authentication associated with the token.
	 */
	void storeAccessToken(AccessToken token, Authentication authentication);

	/**
	 * Read an access token from the store.
	 * 
	 * @param tokenValue The token value.
	 * @return The access token to read.
	 */
	AccessToken readAccessToken(String tokenValue);

	/**
	 * Remove an access token from the database.
	 * 
	 * @param token The token to remove from the database.
	 */
	void removeAccessToken(AccessToken token);

	/**
	 * Store the specified refresh token in the database.
	 * 
	 * @param refreshToken The refresh token to store.
	 * @param authentication The authentication associated with the refresh token.
	 */
	void storeRefreshToken(RefreshToken refreshToken, Authentication authentication);

	/**
	 * Read a refresh token from the store.
	 * 
	 * @param tokenValue The value of the token to read.
	 * @return The token.
	 */
	RefreshToken readRefreshToken(String tokenValue);

	/**
	 * @param token a refresh token
	 * @return the authentication originally used to grant the refresh token
	 */
	Authentication readAuthenticationForRefreshToken(RefreshToken token);

	/**
	 * Remove a refresh token from the database.
	 * 
	 * @param token The token to remove from the database.
	 */
	void removeRefreshToken(RefreshToken token);

	/**
	 * Remove an access token using a refresh token. This functionality is necessary so refresh tokens can't be used to
	 * create an unlimited number of access tokens.
	 * 
	 * @param refreshToken The refresh token.
	 */
	void removeAccessTokenUsingRefreshToken(RefreshToken refreshToken);

	/**
	 * Retrieve an access token stored against the provided authentication key, if it exists.
	 * 
	 * @param authentication the authentication key for the access token
	 * 
	 * @return the access token or null if there was none
	 */
	AccessToken getAccessToken(Authentication authentication);

	/**
	 * @param clientId the client id to search
	 * @param userName the user name to search
	 * @return a collection of access tokens
	 */
	Collection<AccessToken> findTokensByClientIdAndUserName(String clientId, String userName);

	/**
	 * @param userName the user name to search
	 * @param clientId the client id to search
	 * @return a collection of access tokens
	 */
	Collection<AccessToken> findTokensByClientId(String clientId);

}
