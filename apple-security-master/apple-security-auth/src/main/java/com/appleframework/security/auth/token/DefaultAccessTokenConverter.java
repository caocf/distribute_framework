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
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.appleframework.security.core.token.AccessToken;
import com.appleframework.security.core.token.DefaultAccessToken;
import com.appleframework.security.core.auth.Authentication;

/**
 * Default implementation of {@link AccessTokenConverter}.
 * 
 * @author Dave Syer
 * 
 */
public class DefaultAccessTokenConverter implements AccessTokenConverter {

	private UserAuthenticationConverter userTokenConverter = new DefaultUserAuthenticationConverter();
	
	/**
	 * Converter for the part of the data in the token representing a user.
	 * 
	 * @param userTokenConverter the userTokenConverter to set
	 */
	public void setUserTokenConverter(UserAuthenticationConverter userTokenConverter) {
		this.userTokenConverter = userTokenConverter;
	}

	@Override
	public Map<String, ?> convertAccessToken(AccessToken token) {
		// TODO Auto-generated method stub
		return null;
	}



	public Map<String, ?> convertAccessToken(AccessToken token, Authentication authentication) {
		Map<String, Object> response = new HashMap<String, Object>();

		response.putAll(userTokenConverter.convertUserAuthentication(authentication));

		if (token.getScope()!=null) {
			response.put(SCOPE, token.getScope());
		}
		if (token.getAdditionalInformation().containsKey(JTI)) {
			response.put(JTI, token.getAdditionalInformation().get(JTI));
		}

		if (token.getExpiration() != null) {
			response.put(EXP, token.getExpiration().getTime() / 1000);
		}

		response.putAll(token.getAdditionalInformation());

		response.put(CLIENT_ID, authentication.getClientDetails().getClientId());
		String resourceIds = authentication.getClientDetails().getResourceIds();
		if (resourceIds != null && !resourceIds.isEmpty()) {
			response.put(AUD, resourceIds);
		}
		return response;
	}

	public AccessToken extractAccessToken(String value, Map<String, ?> map) {
		DefaultAccessToken token = new DefaultAccessToken(value);
		Map<String, Object> info = new HashMap<String, Object>(map);
		info.remove(EXP);
		info.remove(AUD);
		info.remove(CLIENT_ID);
		info.remove(SCOPE);
		if (map.containsKey(EXP)) {
			token.setExpiration(new Date((Long) map.get(EXP) * 1000L));
		}
		if (map.containsKey(JTI)) {
			info.put(JTI, map.get(JTI));
		}
		@SuppressWarnings("unchecked")
		Collection<String> scope = (Collection<String>) map.get(SCOPE);
		if (scope != null) {
			token.setScope(new HashSet<String>(scope));
		}
		token.setAdditionalInformation(info);
		return token;
	}

	public Authentication extractAuthentication(Map<String, ?> map) {
		/*Map<String, String> parameters = new HashMap<String, String>();
		@SuppressWarnings("unchecked")
		Set<String> scope = new LinkedHashSet<String>(map.containsKey(SCOPE) ? (Collection<String>) map.get(SCOPE)
				: Collections.<String>emptySet());*/
		Authentication user = userTokenConverter.extractAuthentication(map);
		/*String clientId = (String) map.get(CLIENT_ID);
		parameters.put(CLIENT_ID, clientId);
		@SuppressWarnings("unchecked")
		Set<String> resourceIds = new LinkedHashSet<String>(map.containsKey(AUD) ? (Collection<String>) map.get(AUD)
				: Collections.<String>emptySet());
		return new Authentication(request, user);*/
		return user;
	}

}
