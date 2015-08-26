/*
 * Copyright 2006-2011 the original author or authors.
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

import java.util.Collections;
import java.util.List;

import com.appleframework.security.core.token.AccessToken;
import com.appleframework.security.core.auth.Authentication;


/**
 * A composite token enhancer that loops over its delegate enhancers.
 * 
 * @author Dave Syer
 * 
 */
public class TokenEnhancerChain implements TokenEnhancer {

	private List<TokenEnhancer> delegates = Collections.emptyList();

	/**
	 * @param delegates the delegates to set
	 */
	public void setTokenEnhancers(List<TokenEnhancer> delegates) {
		this.delegates = delegates;
	}

	/**
	 * Loop over the {@link #setTokenEnhancers(List) delegates} passing the result into the next member of the chain.
	 * 
	 * @see org.springframework.security.oauth2.provider.token.TokenEnhancer#enhance(com.zghdsl.biz.user.token.security.oauth2.common.AccessToken,
	 * org.springframework.security.oauth2.provider.Authentication)
	 */
	public AccessToken enhance(AccessToken accessToken, Authentication authentication) {
		AccessToken result = accessToken;
		for (TokenEnhancer enhancer : delegates) {
			result = enhancer.enhance(result, authentication);
		}
		return result;
	}

}
