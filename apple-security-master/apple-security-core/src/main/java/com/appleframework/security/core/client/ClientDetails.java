package com.appleframework.security.core.client;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import com.appleframework.security.core.auth.GrantedAuthority;


/**
 * Client details for OAuth 2
 * 
 * @author Ryan Heaton
 */
public interface ClientDetails extends Serializable {

	/**
	 * The client id.
	 * 
	 * @return The client id.
	 */
	String getClientId();
	
	/**
	 * The client name.
	 * 
	 * @return The client name.
	 */
	String getClientName();

	/**
	 * The resources that this client can access. Can be ignored by callers if empty.
	 * 
	 * @return The resources of this client.
	 */
	Set<String> getResourceIdsSet();
	
	/**
	 * The resources that this client can access. Can be ignored by callers if empty.
	 * 
	 * @return The resources of this client.
	 */
	String getResourceIds();

	/**
	 * Whether a secret is required to authenticate this client.
	 * 
	 * @return Whether a secret is required to authenticate this client.
	 */
	boolean isSecretRequired();

	/**
	 * The client secret. Ignored if the {@link #isSecretRequired() secret isn't required}.
	 * 
	 * @return The client secret.
	 */
	String getClientSecret();

	/**
	 * Whether this client is limited to a specific scope. If false, the scope of the authentication request will be
	 * ignored.
	 * 
	 * @return Whether this client is limited to a specific scope.
	 */
	boolean isScoped();

	/**
	 * The scope of this client. Empty if the client isn't scoped.
	 * 
	 * @return The scope of this client.
	 */
	Set<String> getScopeSet();

	/**
	 * The grant types for which this client is authorized.
	 * 
	 * @return The grant types for which this client is authorized.
	 */
	Set<String> getAuthorizedGrantTypesSet();

	/**
	 * Get the authorities that are granted to the OAuth client. Note that these are NOT the authorities that are
	 * granted to the user with an authorized access token. Instead, these authorities are inherent to the client
	 * itself.
	 * 
	 * @return The authorities.
	 */
	Collection<GrantedAuthority> getAuthoritiesCollection();

	/**
	 * The access token validity period for this client. Null if not set explicitly (implementations might use that fact
	 * to provide a default value for instance).
	 * 
	 * @return the access token validity period
	 */
	Integer getAccessTokenValiditySeconds();

	/**
	 * The refresh token validity period for this client. Zero or negative for default value set by token service.
	 * 
	 * @return the refresh token validity period
	 */
	Integer getRefreshTokenValiditySeconds();

}