package com.appleframework.security.auth.token;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.appleframework.security.auth.token.AuthenticationKeyGenerator;
import com.appleframework.security.core.auth.Authentication;

/**
 * Basic key generator taking into account the client id, scope, resource ids and username (principal name) if they
 * exist.
 * 
 * @author Dave Syer
 * 
 */
public class DefaultAuthenticationKeyGenerator implements AuthenticationKeyGenerator {

	private static final String CLIENT_ID = "client_id";

	private static final String USERNAME = "username";

	public String extractKey(Authentication authentication) {
		Map<String, String> values = new LinkedHashMap<String, String>();
		values.put(USERNAME, authentication.getUser().getUsername());
		values.put(CLIENT_ID, authentication.getClientDetails().getClientId());
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
		}

		try {
			byte[] bytes = digest.digest(values.toString().getBytes("UTF-8"));
			return String.format("%032x", new BigInteger(1, bytes));
		}
		catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
		}
	}

}
