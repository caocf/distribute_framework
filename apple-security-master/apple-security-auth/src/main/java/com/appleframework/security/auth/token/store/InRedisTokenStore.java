package com.appleframework.security.auth.token.store;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.redisson.Redisson;
import org.springframework.util.Assert;

import com.appleframework.security.auth.token.AuthenticationKeyGenerator;
import com.appleframework.security.auth.token.DefaultAuthenticationKeyGenerator;
import com.appleframework.security.auth.token.TokenStore;
import com.appleframework.security.core.token.AccessToken;
import com.appleframework.security.core.auth.Authentication;
import com.appleframework.security.core.token.RefreshToken;

/**
 * Implementation of token services that stores tokens in haselcast.
 * 
 * @author cruise.xu
 */
public class InRedisTokenStore implements TokenStore {

	private static final int DEFAULT_FLUSH_INTERVAL = 1000;

	private Map<String, AccessToken> accessTokenStore = new ConcurrentHashMap<String, AccessToken>();;

	private Map<String, AccessToken> authenticationToAccessTokenStore = new ConcurrentHashMap<String, AccessToken>();;

	private Map<String, Collection<AccessToken>> userNameToAccessTokenStore = new ConcurrentHashMap<String, Collection<AccessToken>>();

	private Map<String, Collection<AccessToken>> clientIdToAccessTokenStore = new ConcurrentHashMap<String, Collection<AccessToken>>();

	private Map<String, RefreshToken> refreshTokenStore = new ConcurrentHashMap<String, RefreshToken>();

	private Map<String, String> accessTokenToRefreshTokenStore = new ConcurrentHashMap<String, String>();

	private Map<String, Authentication> authenticationStore = new ConcurrentHashMap<String, Authentication>();

	private Map<String, Authentication> refreshTokenAuthenticationStore = new ConcurrentHashMap<String, Authentication>();

	private Map<String, String> refreshTokenToAccessTokenStore = new ConcurrentHashMap<String, String>();

	private Queue<TokenExpiry> expiryQueue = new DelayQueue<TokenExpiry>();

	private Map<String, TokenExpiry> expiryMap = new ConcurrentHashMap<String, TokenExpiry>();

	private int flushInterval = DEFAULT_FLUSH_INTERVAL;

	private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

	private AtomicInteger flushCounter = new AtomicInteger(0);
	
	private Redisson redisson;
	
	public void init() {
		
		accessTokenStore = redisson.getMap("accessTokenStore");
		authenticationToAccessTokenStore = redisson.getMap("authenticationToAccessTokenStore");
		userNameToAccessTokenStore = redisson.getMap("userNameToAccessTokenStore");
		clientIdToAccessTokenStore = redisson.getMap("clientIdToAccessTokenStore");
		refreshTokenStore = redisson.getMap("refreshTokenStore");
		accessTokenToRefreshTokenStore = redisson.getMap("accessTokenToRefreshTokenStore");
		authenticationStore = redisson.getMap("authenticationStore");
		refreshTokenAuthenticationStore = redisson.getMap("refreshTokenAuthenticationStore");
		refreshTokenToAccessTokenStore = redisson.getMap("refreshTokenToAccessTokenStore");
		expiryMap = redisson.getMap("expiryMap");
		
		expiryQueue = redisson.getQueue("expiryQueue");

	}

	/**
	 * The number of tokens to store before flushing expired tokens. Defaults to 1000.
	 * 
	 * @param flushInterval the interval to set
	 */
	public void setFlushInterval(int flushInterval) {
		this.flushInterval = flushInterval;
	}

	/**
	 * The interval (count of token inserts) between flushing expired tokens.
	 * 
	 * @return the flushInterval the flush interval
	 */
	public int getFlushInterval() {
		return flushInterval;
	}

	public void setRedisson(Redisson redisson) {
		this.redisson = redisson;
	}

	/**
	 * Convenience method for super admin users to remove all tokens (useful for testing, not really in production)
	 */
	public void clear() {
		accessTokenStore.clear();
		authenticationToAccessTokenStore.clear();
		clientIdToAccessTokenStore.clear();
		refreshTokenStore.clear();
		accessTokenToRefreshTokenStore.clear();
		authenticationStore.clear();
		refreshTokenAuthenticationStore.clear();
		refreshTokenToAccessTokenStore.clear();
		expiryQueue.clear();
	}

	public void setAuthenticationKeyGenerator(AuthenticationKeyGenerator authenticationKeyGenerator) {
		this.authenticationKeyGenerator = authenticationKeyGenerator;
	}

	public int getAccessTokenCount() {
		Assert.state(accessTokenStore.isEmpty() || accessTokenStore.size() >= accessTokenToRefreshTokenStore.size(),
				"Too many refresh tokens");
		Assert.state(accessTokenStore.size() == authenticationToAccessTokenStore.size(),
				"Inconsistent token store state");
		Assert.state(accessTokenStore.size() <= authenticationStore.size(), "Inconsistent authentication store state");
		return accessTokenStore.size();
	}

	public int getRefreshTokenCount() {
		Assert.state(refreshTokenStore.size() == refreshTokenToAccessTokenStore.size(),
				"Inconsistent refresh token store state");
		return accessTokenStore.size();
	}

	public int getExpiryTokenCount() {
		return expiryQueue.size();
	}

	public AccessToken getAccessToken(Authentication authentication) {
		String key = authenticationKeyGenerator.extractKey(authentication);
		AccessToken accessToken = authenticationToAccessTokenStore.get(key);
		if (accessToken != null
				&& !key.equals(authenticationKeyGenerator.extractKey(readAuthentication(accessToken.getValue())))) {
			// Keep the stores consistent (maybe the same user is represented by this authentication but the details
			// have changed)
			storeAccessToken(accessToken, authentication);
		}
		return accessToken;
	}

	public Authentication readAuthentication(AccessToken token) {
		return readAuthentication(token.getValue());
	}

	public Authentication readAuthentication(String token) {
		return this.authenticationStore.get(token);
	}

	public Authentication readAuthenticationForRefreshToken(RefreshToken token) {
		return readAuthenticationForRefreshToken(token.getValue());
	}

	public Authentication readAuthenticationForRefreshToken(String token) {
		return this.refreshTokenAuthenticationStore.get(token);
	}

	public void storeAccessToken(AccessToken token, Authentication authentication) {
		if (this.flushCounter.incrementAndGet() >= this.flushInterval) {
			flush();
			this.flushCounter.set(0);
		}
		this.accessTokenStore.put(token.getValue(), token);
		this.authenticationStore.put(token.getValue(), authentication);
		this.authenticationToAccessTokenStore.put(authenticationKeyGenerator.extractKey(authentication), token);
		addToCollection(this.userNameToAccessTokenStore, getApprovalKey(authentication), token);
		
		addToCollection(this.clientIdToAccessTokenStore, authentication.getClientDetails().getClientId(), token);
		if (token.getExpiration() != null) {
			TokenExpiry expiry = new TokenExpiry(token.getValue(), token.getExpiration());
			// Remove existing expiry for this token if present
			try {
				expiryQueue.remove(expiryMap.put(token.getValue(), expiry));
			} catch (NullPointerException e) {
			}
			try {
				this.expiryQueue.add(expiry);
			} catch (Exception e) {
				
			}
		}
		if (token.getRefreshToken() != null && token.getRefreshToken().getValue() != null) {
			this.refreshTokenToAccessTokenStore.put(token.getRefreshToken().getValue(), token.getValue());
			this.accessTokenToRefreshTokenStore.put(token.getValue(), token.getRefreshToken().getValue());
		}
	}

	private String getApprovalKey(Authentication authentication) {
		String userName = authentication.getUser().getUsername();
		return getApprovalKey(authentication.getClientDetails().getClientId(), userName);
	}

	private String getApprovalKey(String clientId, String userName) {
		return clientId + (userName==null ? "" : ":" + userName);
	}

	private void addToCollection(Map<String, Collection<AccessToken>> store, String key,
			AccessToken token) {
		if (!store.containsKey(key)) {
			synchronized (store) {
				if (!store.containsKey(key)) {
					store.put(key, new HashSet<AccessToken>());
				}
			}
		}
		store.get(key).add(token);
	}

	public void removeAccessToken(AccessToken accessToken) {
		removeAccessToken(accessToken.getValue());
	}

	public AccessToken readAccessToken(String tokenValue) {
		return this.accessTokenStore.get(tokenValue);
	}

	public void removeAccessToken(String tokenValue) {
		AccessToken removed = this.accessTokenStore.remove(tokenValue);
		this.accessTokenToRefreshTokenStore.remove(tokenValue);
		// Don't remove the refresh token - it's up to the caller to do that
		Authentication authentication = this.authenticationStore.remove(tokenValue);
		if (authentication != null) {
			this.authenticationToAccessTokenStore.remove(authenticationKeyGenerator.extractKey(authentication));
			Collection<AccessToken> tokens;
			tokens = this.userNameToAccessTokenStore.get(authentication.getUser().getUsername());
			if (tokens != null) {
				tokens.remove(removed);
			}
			String clientId = authentication.getClientDetails().getClientId();
			tokens = this.clientIdToAccessTokenStore.get(clientId);
			if (tokens != null) {
				tokens.remove(removed);
			}
			this.authenticationToAccessTokenStore.remove(authenticationKeyGenerator.extractKey(authentication));
		}
	}

	public void storeRefreshToken(RefreshToken refreshToken, Authentication authentication) {
		this.refreshTokenStore.put(refreshToken.getValue(), refreshToken);
		this.refreshTokenAuthenticationStore.put(refreshToken.getValue(), authentication);
	}

	public RefreshToken readRefreshToken(String tokenValue) {
		return this.refreshTokenStore.get(tokenValue);
	}

	public void removeRefreshToken(RefreshToken refreshToken) {
		removeRefreshToken(refreshToken.getValue());
	}

	public void removeRefreshToken(String tokenValue) {
		this.refreshTokenStore.remove(tokenValue);
		this.refreshTokenAuthenticationStore.remove(tokenValue);
		this.refreshTokenToAccessTokenStore.remove(tokenValue);
	}

	public void removeAccessTokenUsingRefreshToken(RefreshToken refreshToken) {
		removeAccessTokenUsingRefreshToken(refreshToken.getValue());
	}

	private void removeAccessTokenUsingRefreshToken(String refreshToken) {
		String accessToken = this.refreshTokenToAccessTokenStore.remove(refreshToken);
		if (accessToken != null) {
			removeAccessToken(accessToken);
		}
	}

	public Collection<AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
		Collection<AccessToken> result = userNameToAccessTokenStore.get(getApprovalKey(clientId, userName));
		return result != null ? Collections.<AccessToken> unmodifiableCollection(result) : Collections
				.<AccessToken> emptySet();
	}

	public Collection<AccessToken> findTokensByClientId(String clientId) {
		Collection<AccessToken> result = clientIdToAccessTokenStore.get(clientId);
		return result != null ? Collections.<AccessToken> unmodifiableCollection(result) : Collections
				.<AccessToken> emptySet();
	}

	private void flush() {
		TokenExpiry expiry = expiryQueue.poll();
		while (expiry != null) {
			removeAccessToken(expiry.getValue());
			expiry = expiryQueue.poll();
		}
	}

	private static class TokenExpiry implements Delayed, Serializable {

		private static final long serialVersionUID = 1802368366264504417L;

		private final long expiry;

		private final String value;

		public TokenExpiry(String value, Date date) {
			this.value = value;
			this.expiry = date.getTime();
		}

		public int compareTo(Delayed other) {
			if (this == other) {
				return 0;
			}
			long diff = getDelay(TimeUnit.MILLISECONDS) - other.getDelay(TimeUnit.MILLISECONDS);
			return (diff == 0 ? 0 : ((diff < 0) ? -1 : 1));
		}

		public long getDelay(TimeUnit unit) {
			return expiry - System.currentTimeMillis();
		}

		public String getValue() {
			return value;
		}

	}

}
