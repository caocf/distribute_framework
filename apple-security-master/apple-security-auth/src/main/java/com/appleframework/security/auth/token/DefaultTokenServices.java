/*
 * Copyright 2008 Web Cohesion
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.appleframework.security.auth.token;

import java.util.Date;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.appleframework.security.core.token.AccessToken;
import com.appleframework.security.core.token.DefaultAccessToken;
import com.appleframework.security.core.token.DefaultExpiringRefreshToken;
import com.appleframework.security.core.token.ExpiringRefreshToken;
import com.appleframework.security.core.token.TokenServices;
import com.appleframework.security.core.auth.Authentication;
import com.appleframework.security.core.client.ClientDetails;
import com.appleframework.security.core.client.ClientDetailsService;
import com.appleframework.security.core.exception.AuthenticationException;
import com.appleframework.security.core.exception.ClientRegistrationException;
import com.appleframework.security.core.exception.InvalidGrantException;
import com.appleframework.security.core.exception.InvalidTokenException;
import com.appleframework.security.core.token.RefreshToken;

/**
 * Base implementation for token services using random UUID values for the access token and refresh token values. The
 * main extension point for customizations is the {@link TokenEnhancer} which will be called after the access and
 * refresh tokens have been generated but before they are stored.
 * <p>
 * Persistence is delegated to a {@code TokenStore} implementation and customization of the access token to a
 * {@link TokenEnhancer}.
 * 
 * @author Ryan Heaton
 * @author Luke Taylor
 * @author Dave Syer
 */
@Transactional
public class DefaultTokenServices implements TokenServices {

	private int refreshTokenValiditySeconds = 60 * 60 * 24 * 30; // default 30 days.

	private int accessTokenValiditySeconds = 60 * 60 * 12; // default 12 hours.

	private boolean supportRefreshToken = false;

	private boolean reuseRefreshToken = true;

	private TokenStore tokenStore;

	private ClientDetailsService clientDetailsService;
	
	private TokenEnhancer accessTokenEnhancer;

	@Override
	public AccessToken createAccessToken(Authentication authentication) throws AuthenticationException {
		AccessToken existingAccessToken = tokenStore.getAccessToken(authentication);
		RefreshToken refreshToken = null;
		if (existingAccessToken != null) {
			if (existingAccessToken.isExpired()) {
				if (existingAccessToken.getRefreshToken() != null) {
					refreshToken = existingAccessToken.getRefreshToken();
					// The token store could remove the refresh token when the
					// access token is removed, but we want to
					// be sure...
					tokenStore.removeRefreshToken(refreshToken);
				}
				tokenStore.removeAccessToken(existingAccessToken);
			}
			else {
				// Re-store the access token in case the authentication has changed
				tokenStore.storeAccessToken(existingAccessToken, authentication);
				return existingAccessToken;
			}
		}

		// Only create a new refresh token if there wasn't an existing one
		// associated with an expired access token.
		// Clients might be holding existing refresh tokens, so we re-use it in
		// the case that the old access token
		// expired.
		if (refreshToken == null) {
			refreshToken = createRefreshToken(authentication);
		}
		// But the refresh token itself might need to be re-issued if it has
		// expired.
		else if (refreshToken instanceof ExpiringRefreshToken) {
			ExpiringRefreshToken expiring = (ExpiringRefreshToken) refreshToken;
			if (System.currentTimeMillis() > expiring.getExpiration().getTime()) {
				refreshToken = createRefreshToken(authentication);
			}
		}

		AccessToken accessToken = createAccessToken(authentication, refreshToken);
		tokenStore.storeAccessToken(accessToken, authentication);
		// In case it was modified
		refreshToken = accessToken.getRefreshToken();
		if (refreshToken != null) {
			tokenStore.storeRefreshToken(refreshToken, authentication);
		}
		return accessToken;

	}
	
	private AccessToken createAccessToken(Authentication authentication, RefreshToken refreshToken) {
		DefaultAccessToken token = new DefaultAccessToken(UUID.randomUUID().toString());
		int validitySeconds = getAccessTokenValiditySeconds(authentication);
		if (validitySeconds > 0) {
			token.setExpiration(new Date(System.currentTimeMillis() + (validitySeconds * 1000L)));
		}
		else {
			token.setExpiration(new Date(System.currentTimeMillis() + (accessTokenValiditySeconds * 1000L)));
		}
		token.setRefreshToken(refreshToken);

		return accessTokenEnhancer != null ? accessTokenEnhancer.enhance(token, authentication) : token;
		//return token;
	}
	
	private ExpiringRefreshToken createRefreshToken(Authentication authentication) {
		/*if (!isSupportRefreshToken(authentication.getOAuth2Request())) {
			return null;
		}
		int validitySeconds = getRefreshTokenValiditySeconds(authentication.getOAuth2Request());
		ExpiringRefreshToken refreshToken = new DefaultExpiringRefreshToken(UUID.randomUUID().toString(),
				new Date(System.currentTimeMillis() + (validitySeconds * 1000L)));
		return refreshToken;*/
		
		/*if (!isSupportRefreshToken(authentication.getOAuth2Request())) {
			return null;
		}*/
		int validitySeconds = getRefreshTokenValiditySeconds(authentication);
		Date expirationDate = null;
		if (validitySeconds > 0) {
			expirationDate = new Date(System.currentTimeMillis() + (validitySeconds * 1000L));
		}
		else {
			expirationDate = new Date(System.currentTimeMillis() + (refreshTokenValiditySeconds * 1000L));
		}
		ExpiringRefreshToken refreshToken = new DefaultExpiringRefreshToken(UUID.randomUUID().toString(), expirationDate);
		return refreshToken;
	}

	
	/**
	 * The access token validity period in seconds
	 * 
	 * @param authorizationRequest the current authorization request
	 * @return the access token validity period in seconds
	 */
	protected int getAccessTokenValiditySeconds(Authentication authentication) {
		//if (clientDetailsService != null) {
			//ClientDetails client = clientDetailsService.loadClientByClientId(clientAuth.getClientId());
			ClientDetails client = authentication.getClientDetails();
			Integer validity = client.getAccessTokenValiditySeconds();
			if (validity != null) {
				return validity;
			}
		//}
		return accessTokenValiditySeconds;
	}

	/**
	 * The refresh token validity period in seconds
	 * 
	 * @param authorizationRequest the current authorization request
	 * @return the refresh token validity period in seconds
	 */
	protected int getRefreshTokenValiditySeconds(Authentication authentication) {
		//if (clientDetailsService != null) {
			//ClientDetails client = clientDetailsService.loadClientByClientId(clientAuth.getClientId());
			ClientDetails client = authentication.getClientDetails();
			Integer validity = client.getRefreshTokenValiditySeconds();
			if (validity != null) {
				return validity;
			}
		//}
		return refreshTokenValiditySeconds;
	}
	
	/*@Override
	public AccessToken readAccessToken(String accessToken) {
		// TODO Auto-generated method stub
		return null;
	}*/

	@Override
	public AccessToken refreshAccessToken(ClientDetails clientDetails, String refreshTokenValue) 
			throws AuthenticationException {

		if (!supportRefreshToken) {
			throw new InvalidGrantException("Invalid refresh token: " + refreshTokenValue);
		}

		RefreshToken refreshToken = tokenStore.readRefreshToken(refreshTokenValue);
		if (refreshToken == null) {
			throw new InvalidGrantException("Invalid refresh token: " + refreshTokenValue);
		}

		Authentication authentication = tokenStore.readAuthenticationForRefreshToken(refreshToken);
		String clientId = authentication.getClientDetails().getClientId();
		if (clientId == null || !clientId.equals(clientDetails.getClientId())) {
			throw new InvalidGrantException("Wrong client for this refresh token: " + refreshTokenValue);
		}

		// clear out any access tokens already associated with the refresh
		// token.
		tokenStore.removeAccessTokenUsingRefreshToken(refreshToken);

		if (isExpired(refreshToken)) {
			tokenStore.removeRefreshToken(refreshToken);
			throw new InvalidTokenException("Invalid refresh token (expired): " + refreshToken);
		}

		if (!reuseRefreshToken) {
			tokenStore.removeRefreshToken(refreshToken);
			refreshToken = createRefreshToken(authentication);
		}

		AccessToken accessToken = createAccessToken(authentication, refreshToken);
		tokenStore.storeAccessToken(accessToken, authentication);
		if (!reuseRefreshToken) {
			tokenStore.storeRefreshToken(refreshToken, authentication);
		}
		return accessToken;
	}

	@Override
	public AccessToken getAccessToken(String accessToken) {
		return tokenStore.readAccessToken(accessToken);
	}

	public void setTokenStore(TokenStore tokenStore) {
		this.tokenStore = tokenStore;
	}

	public void setClientDetailsService(ClientDetailsService clientDetailsService) {
		this.clientDetailsService = clientDetailsService;
	}
	
	public Authentication loadAuthentication(String accessTokenValue) 
			throws AuthenticationException, InvalidTokenException {
		AccessToken accessToken = tokenStore.readAccessToken(accessTokenValue);
		if (accessToken == null) {
			throw new InvalidTokenException("Invalid access token: " + accessTokenValue);
		}
		else if (accessToken.isExpired()) {
			tokenStore.removeAccessToken(accessToken);
			throw new InvalidTokenException("Access token expired: " + accessTokenValue);
		}

		Authentication result = tokenStore.readAuthentication(accessToken);
		if (clientDetailsService != null) {
			String clientId = result.getClientDetails().getClientId();
			try {
				clientDetailsService.loadClientByClientId(clientId);
			}
			catch (ClientRegistrationException e) {
				throw new InvalidTokenException("Client not valid: " + clientId, e);
			}
		}
		return result;
	}
	
	/**
	 * Whether to support the refresh token.
	 * 
	 * @param supportRefreshToken Whether to support the refresh token.
	 */
	public void setSupportRefreshToken(boolean supportRefreshToken) {
		this.supportRefreshToken = supportRefreshToken;
	}

	/**
	 * Whether to reuse refresh tokens (until expired).
	 * 
	 * @param reuseRefreshToken Whether to reuse refresh tokens (until expired).
	 */
	public void setReuseRefreshToken(boolean reuseRefreshToken) {
		this.reuseRefreshToken = reuseRefreshToken;
	}
	
	protected boolean isExpired(RefreshToken refreshToken) {
		if (refreshToken instanceof ExpiringRefreshToken) {
			ExpiringRefreshToken expiringToken = (ExpiringRefreshToken) refreshToken;
			return expiringToken.getExpiration() == null
					|| System.currentTimeMillis() > expiringToken.getExpiration().getTime();
		}
		return false;
	}

	public boolean isSupportRefreshToken() {
		return supportRefreshToken;
	}

	public boolean isReuseRefreshToken() {
		return reuseRefreshToken;
	}
	
}
