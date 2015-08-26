/*
 * Cloud Foundry 2012.02.03 Beta
 * Copyright (c) [2009-2012] VMware, Inc. All Rights Reserved.
 *
 * This product is licensed to you under the Apache License, Version 2.0 (the "License").
 * You may not use this product except in compliance with the License.
 *
 * This product includes a number of subcomponents with
 * separate copyright notices and license terms. Your use of these
 * subcomponents is subject to the terms and conditions of the
 * subcomponent's license, as noted in the LICENSE file.
 */

package com.appleframework.security.auth.token;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.appleframework.security.auth.utils.AuthorityUtils;
import com.appleframework.security.core.auth.Authentication;
import com.appleframework.security.core.auth.GrantedAuthority;


/**
 * Default implementation of {@link UserAuthenticationConverter}. Converts to and from an Authentication using only its name and
 * authorities.
 * 
 * @author Dave Syer
 * 
 */
public class DefaultUserAuthenticationConverter implements UserAuthenticationConverter {

	private Collection<? extends GrantedAuthority> defaultAuthorities;

	/**
	 * Default value for authorities if an Authentication is being created and the input has no data for authorities.
	 * Note that unless this property is set, the default Authentication created by {@link #extractAuthentication(Map)}
	 * will be unauthenticated.
	 * 
	 * @param defaultAuthorities the defaultAuthorities to set. Default null.
	 */
	public void setDefaultAuthorities(String[] defaultAuthorities) {
		this.defaultAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
				.arrayToCommaDelimitedString(defaultAuthorities));
	}

	public Map<String, ?> convertUserAuthentication(Authentication authentication) {
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put(USERNAME, authentication.getUser().getUsername());
		/*if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
			response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
		}*/
		return response;
	}

	public Authentication extractAuthentication(Map<String, ?> map) {
		/*if (map.containsKey(USERNAME)) {
			return new UsernamePasswordAuthenticationToken(map.get(USERNAME), "N/A", getAuthorities(map));
		}*/
		return null;
	}

	public Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
		if (!map.containsKey(AUTHORITIES)) {
			return defaultAuthorities;
		}
		Object authorities = map.get(AUTHORITIES);
		if (authorities instanceof String) {
			return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
		}
		if (authorities instanceof Collection) {
			return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
					.collectionToCommaDelimitedString((Collection<?>) authorities));
		}
		throw new IllegalArgumentException("Authorities must be either a String or a Collection");
	}
}
