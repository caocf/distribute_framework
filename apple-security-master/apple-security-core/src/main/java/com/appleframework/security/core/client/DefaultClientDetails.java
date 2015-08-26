package com.appleframework.security.core.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.appleframework.security.core.auth.GrantedAuthority;

/**
 * @author Cruise.Xu
 */
public class DefaultClientDetails implements ClientDetails {

	private static final long serialVersionUID = 6278471337441548406L;
	
	private String clientId;
	private String clientName;
	private String clientSecret;
	private String resourceIds;

	/**
	 * Available values: read,write
	 */
	private String scope;

	/**
	 * grant types include "authorization_code", "password", "assertion", and
	 * "refresh_token". Default value is "authorization_code,refresh_token".
	 */
	private String authorizedGrantTypes = "authorization_code,refresh_token";

	/**
	 * Authorities that are granted to the client (comma-separated). Distinct
	 * from the authorities granted to the user on behalf of whom the client is
	 * acting.
	 * <p/>
	 * For example: ROLE_USER
	 */
	private String authorities;

	/**
	 * The access token validity period in seconds (optional). If unspecified a
	 * global default will be applied by the token services.
	 */
	private Integer accessTokenValidity;

	/**
	 * The refresh token validity period in seconds (optional). If unspecified a
	 * global default will be applied by the token services.
	 */
	private Integer refreshTokenValidity;

	/**
	 * The client is trusted or not. If it is trust, will skip approve step
	 * default false.
	 */
	private boolean trusted = false;

	private boolean archived = true;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getAuthorizedGrantTypes() {
		return authorizedGrantTypes;
	}

	public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
		this.authorizedGrantTypes = authorizedGrantTypes;
	}

	public String getAuthorities() {
		return authorities;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}

	public Integer getAccessTokenValidity() {
		return accessTokenValidity;
	}

	public void setAccessTokenValidity(Integer accessTokenValidity) {
		this.accessTokenValidity = accessTokenValidity;
	}

	public Integer getRefreshTokenValidity() {
		return refreshTokenValidity;
	}

	public void setRefreshTokenValidity(Integer refreshTokenValidity) {
		this.refreshTokenValidity = refreshTokenValidity;
	}

	public boolean isTrusted() {
		return trusted;
	}

	public void setTrusted(boolean trusted) {
		this.trusted = trusted;
	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}


	@Override
	public Integer getAccessTokenValiditySeconds() {
		return this.accessTokenValidity;
	}

	@Override
	public Integer getRefreshTokenValiditySeconds() {
		return this.refreshTokenValidity;
	}

	@Override
	public Set<String> getResourceIdsSet() {
		if (StringUtils.hasText(resourceIds)) {
			Set<String> scopeList = StringUtils.commaDelimitedListToSet(resourceIds);
			if (!scopeList.isEmpty()) {
				return scopeList;
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}
	}

	@Override
	public boolean isSecretRequired() {
		return false;
	}

	@Override
	public boolean isScoped() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<String> getScopeSet() {
		if (StringUtils.hasText(scope)) {
			Set<String> scopeList = StringUtils.commaDelimitedListToSet(scope);
			if (!scopeList.isEmpty()) {
				return scopeList;
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}
	}

	@Override
	public Set<String> getAuthorizedGrantTypesSet() {
		if (StringUtils.hasText(authorizedGrantTypes)) {
			Set<String> scopeList = StringUtils.commaDelimitedListToSet(authorizedGrantTypes);
			if (!scopeList.isEmpty()) {
				return scopeList;
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}
	}

	@Override
	public Collection<GrantedAuthority> getAuthoritiesCollection() {
		return new ArrayList<GrantedAuthority>();
	}

	@Override
	public String toString() {
		return "DefaultClientDetails [clientId=" + clientId + ", clientSecret="
				+ clientSecret + ", resourceIds=" + resourceIds + ", scope="
				+ scope + ", authorizedGrantTypes=" + authorizedGrantTypes
				+ ", authorities=" + authorities + ", accessTokenValidity="
				+ accessTokenValidity + ", refreshTokenValidity="
				+ refreshTokenValidity + ", trusted=" + trusted + ", archived="
				+ archived + "]";
	}	

}