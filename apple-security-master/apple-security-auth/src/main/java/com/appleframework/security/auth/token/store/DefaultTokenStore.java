package com.appleframework.security.auth.token.store;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.util.Assert;

import com.appleframework.security.auth.token.AuthenticationKeyGenerator;
import com.appleframework.security.auth.token.DefaultAuthenticationKeyGenerator;
import com.appleframework.security.auth.token.TokenStore;
import com.appleframework.security.core.token.AccessToken;
import com.appleframework.security.core.auth.Authentication;
import com.appleframework.security.core.token.RefreshToken;
import com.appleframework.security.core.utils.SerializationUtils;

/**
 * Implementation of token services that stores tokens in a database.
 *
 * @author Ken Dombeck
 * @author Luke Taylor
 * @author Dave Syer
 */
public class DefaultTokenStore implements TokenStore {

	private static final Log LOG = LogFactory.getLog(DefaultTokenStore.class);

	private static final String DEFAULT_ACCESS_TOKEN_INSERT_STATEMENT = "insert into oauth_access_token (token_id, token, authentication_id, user_name, client_id, authentication, refresh_token) values (?, ?, ?, ?, ?, ?, ?)";

	private static final String DEFAULT_ACCESS_TOKEN_SELECT_STATEMENT = "select token_id, token from oauth_access_token where token_id = ?";

	private static final String DEFAULT_ACCESS_TOKEN_AUTHENTICATION_SELECT_STATEMENT = "select token_id, authentication from oauth_access_token where token_id = ?";

	private static final String DEFAULT_ACCESS_TOKEN_FROM_AUTHENTICATION_SELECT_STATEMENT = "select token_id, token from oauth_access_token where authentication_id = ?";

	private static final String DEFAULT_ACCESS_TOKENS_FROM_USERNAME_AND_CLIENT_SELECT_STATEMENT = "select token_id, token from oauth_access_token where user_name = ? and client_id = ?";

	private static final String DEFAULT_ACCESS_TOKENS_FROM_USERNAME_SELECT_STATEMENT = "select token_id, token from oauth_access_token where user_name = ?";

	private static final String DEFAULT_ACCESS_TOKENS_FROM_CLIENTID_SELECT_STATEMENT = "select token_id, token from oauth_access_token where client_id = ?";

	private static final String DEFAULT_ACCESS_TOKEN_DELETE_STATEMENT = "delete from oauth_access_token where token_id = ?";

	private static final String DEFAULT_ACCESS_TOKEN_DELETE_FROM_REFRESH_TOKEN_STATEMENT = "delete from oauth_access_token where refresh_token = ?";

	private static final String DEFAULT_REFRESH_TOKEN_INSERT_STATEMENT = "insert into oauth_refresh_token (token_id, token, authentication) values (?, ?, ?)";

	private static final String DEFAULT_REFRESH_TOKEN_SELECT_STATEMENT = "select token_id, token from oauth_refresh_token where token_id = ?";

	private static final String DEFAULT_REFRESH_TOKEN_AUTHENTICATION_SELECT_STATEMENT = "select token_id, authentication from oauth_refresh_token where token_id = ?";

	private static final String DEFAULT_REFRESH_TOKEN_DELETE_STATEMENT = "delete from oauth_refresh_token where token_id = ?";

	private String insertAccessTokenSql = DEFAULT_ACCESS_TOKEN_INSERT_STATEMENT;

	private String selectAccessTokenSql = DEFAULT_ACCESS_TOKEN_SELECT_STATEMENT;

	private String selectAccessTokenAuthenticationSql = DEFAULT_ACCESS_TOKEN_AUTHENTICATION_SELECT_STATEMENT;

	private String selectAccessTokenFromAuthenticationSql = DEFAULT_ACCESS_TOKEN_FROM_AUTHENTICATION_SELECT_STATEMENT;

	private String selectAccessTokensFromUserNameAndClientIdSql = DEFAULT_ACCESS_TOKENS_FROM_USERNAME_AND_CLIENT_SELECT_STATEMENT;

	private String selectAccessTokensFromUserNameSql = DEFAULT_ACCESS_TOKENS_FROM_USERNAME_SELECT_STATEMENT;

	private String selectAccessTokensFromClientIdSql = DEFAULT_ACCESS_TOKENS_FROM_CLIENTID_SELECT_STATEMENT;

	private String deleteAccessTokenSql = DEFAULT_ACCESS_TOKEN_DELETE_STATEMENT;

	private String insertRefreshTokenSql = DEFAULT_REFRESH_TOKEN_INSERT_STATEMENT;

	private String selectRefreshTokenSql = DEFAULT_REFRESH_TOKEN_SELECT_STATEMENT;

	private String selectRefreshTokenAuthenticationSql = DEFAULT_REFRESH_TOKEN_AUTHENTICATION_SELECT_STATEMENT;

	private String deleteRefreshTokenSql = DEFAULT_REFRESH_TOKEN_DELETE_STATEMENT;

	private String deleteAccessTokenFromRefreshTokenSql = DEFAULT_ACCESS_TOKEN_DELETE_FROM_REFRESH_TOKEN_STATEMENT;

	private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

	private final JdbcTemplate jdbcTemplate;

	public DefaultTokenStore(DataSource dataSource) {
		Assert.notNull(dataSource, "DataSource required");
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void setAuthenticationKeyGenerator(AuthenticationKeyGenerator authenticationKeyGenerator) {
		this.authenticationKeyGenerator = authenticationKeyGenerator;
	}

	public AccessToken getAccessToken(Authentication authentication) {
		AccessToken accessToken = null;

		String key = authenticationKeyGenerator.extractKey(authentication);
		try {
			accessToken = jdbcTemplate.queryForObject(selectAccessTokenFromAuthenticationSql,
					new RowMapper<AccessToken>() {
						public AccessToken mapRow(ResultSet rs, int rowNum) throws SQLException {
							return deserializeAccessToken(rs.getBytes(2));
						}
					}, key);
		}
		catch (EmptyResultDataAccessException e) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Failed to find access token for authentication " + authentication);
			}
		}
		catch (IllegalArgumentException e) {
			LOG.error("Could not extract access token for authentication " + authentication, e);
		}

		if (accessToken != null
				&& !key.equals(authenticationKeyGenerator.extractKey(readAuthentication(accessToken.getValue())))) {
			removeAccessToken(accessToken.getValue());
			// Keep the store consistent (maybe the same user is represented by this authentication but the details have
			// changed)
			storeAccessToken(accessToken, authentication);
		}
		return accessToken;
	}

	public void storeAccessToken(AccessToken token, Authentication authentication) {
		String refreshToken = null;
		if (token.getRefreshToken() != null) {
			refreshToken = token.getRefreshToken().getValue();
		}
		
		if (readAccessToken(token.getValue())!=null) {
			removeAccessToken(token.getValue());
		}

		jdbcTemplate.update(insertAccessTokenSql, new Object[] { extractTokenKey(token.getValue()),
				new SqlLobValue(serializeAccessToken(token)), authenticationKeyGenerator.extractKey(authentication),
				authentication.getUser().getUsername(),
				authentication.getClientDetails().getClientId(),
				new SqlLobValue(serializeAuthentication(authentication)), extractTokenKey(refreshToken) }, new int[] {
				Types.VARCHAR, Types.BLOB, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.BLOB, Types.VARCHAR });
	}

	public AccessToken readAccessToken(String tokenValue) {
		AccessToken accessToken = null;

		try {
			accessToken = jdbcTemplate.queryForObject(selectAccessTokenSql, new RowMapper<AccessToken>() {
				public AccessToken mapRow(ResultSet rs, int rowNum) throws SQLException {
					return deserializeAccessToken(rs.getBytes(2));
				}
			}, extractTokenKey(tokenValue));
		}
		catch (EmptyResultDataAccessException e) {
			if (LOG.isInfoEnabled()) {
				LOG.info("Failed to find access token for token " + tokenValue);
			}
		}
		catch (IllegalArgumentException e) {
			LOG.warn("Failed to deserialize access token for " + tokenValue, e);
			removeAccessToken(tokenValue);
		}

		return accessToken;
	}

	public void removeAccessToken(AccessToken token) {
		removeAccessToken(token.getValue());
	}

	public void removeAccessToken(String tokenValue) {
		jdbcTemplate.update(deleteAccessTokenSql, extractTokenKey(tokenValue));
	}

	public Authentication readAuthentication(AccessToken token) {
		return readAuthentication(token.getValue());
	}

	public Authentication readAuthentication(String token) {
		Authentication authentication = null;

		try {
			authentication = jdbcTemplate.queryForObject(selectAccessTokenAuthenticationSql,
					new RowMapper<Authentication>() {
						public Authentication mapRow(ResultSet rs, int rowNum) throws SQLException {
							return deserializeAuthentication(rs.getBytes(2));
						}
					}, extractTokenKey(token));
		}
		catch (EmptyResultDataAccessException e) {
			if (LOG.isInfoEnabled()) {
				LOG.info("Failed to find access token for token " + token);
			}
		}
		catch (IllegalArgumentException e) {
			LOG.warn("Failed to deserialize authentication for " + token, e);
			removeAccessToken(token);
		}

		return authentication;
	}

	public void storeRefreshToken(RefreshToken refreshToken, Authentication authentication) {
		jdbcTemplate.update(insertRefreshTokenSql, new Object[] { extractTokenKey(refreshToken.getValue()),
				new SqlLobValue(serializeRefreshToken(refreshToken)),
				new SqlLobValue(serializeAuthentication(authentication)) }, new int[] { Types.VARCHAR, Types.BLOB,
				Types.BLOB });
	}

	public RefreshToken readRefreshToken(String token) {
		RefreshToken refreshToken = null;

		try {
			refreshToken = jdbcTemplate.queryForObject(selectRefreshTokenSql, new RowMapper<RefreshToken>() {
				public RefreshToken mapRow(ResultSet rs, int rowNum) throws SQLException {
					return deserializeRefreshToken(rs.getBytes(2));
				}
			}, extractTokenKey(token));
		}
		catch (EmptyResultDataAccessException e) {
			if (LOG.isInfoEnabled()) {
				LOG.info("Failed to find refresh token for token " + token);
			}
		}
		catch (IllegalArgumentException e) {
			LOG.warn("Failed to deserialize refresh token for token " + token, e);
			removeRefreshToken(token);
		}

		return refreshToken;
	}

	public void removeRefreshToken(RefreshToken token) {
		removeRefreshToken(token.getValue());
	}

	public void removeRefreshToken(String token) {
		jdbcTemplate.update(deleteRefreshTokenSql, extractTokenKey(token));
	}

	public Authentication readAuthenticationForRefreshToken(RefreshToken token) {
		return readAuthenticationForRefreshToken(token.getValue());
	}

	public Authentication readAuthenticationForRefreshToken(String value) {
		Authentication authentication = null;

		try {
			authentication = jdbcTemplate.queryForObject(selectRefreshTokenAuthenticationSql,
					new RowMapper<Authentication>() {
						public Authentication mapRow(ResultSet rs, int rowNum) throws SQLException {
							return deserializeAuthentication(rs.getBytes(2));
						}
					}, extractTokenKey(value));
		}
		catch (EmptyResultDataAccessException e) {
			if (LOG.isInfoEnabled()) {
				LOG.info("Failed to find access token for token " + value);
			}
		}
		catch (IllegalArgumentException e) {
			LOG.warn("Failed to deserialize access token for " + value, e);
			removeRefreshToken(value);
		}

		return authentication;
	}

	public void removeAccessTokenUsingRefreshToken(RefreshToken refreshToken) {
		removeAccessTokenUsingRefreshToken(refreshToken.getValue());
	}

	public void removeAccessTokenUsingRefreshToken(String refreshToken) {
		jdbcTemplate.update(deleteAccessTokenFromRefreshTokenSql, new Object[] { extractTokenKey(refreshToken) },
				new int[] { Types.VARCHAR });
	}

	public Collection<AccessToken> findTokensByClientId(String clientId) {
		List<AccessToken> accessTokens = new ArrayList<AccessToken>();

		try {
			accessTokens = jdbcTemplate.query(selectAccessTokensFromClientIdSql, new SafeAccessTokenRowMapper(),
					clientId);
		}
		catch (EmptyResultDataAccessException e) {
			if (LOG.isInfoEnabled()) {
				LOG.info("Failed to find access token for clientId " + clientId);
			}
		}
		accessTokens = removeNulls(accessTokens);

		return accessTokens;
	}

	public Collection<AccessToken> findTokensByUserName(String userName) {
		List<AccessToken> accessTokens = new ArrayList<AccessToken>();

		try {
			accessTokens = jdbcTemplate.query(selectAccessTokensFromUserNameSql, new SafeAccessTokenRowMapper(),
					userName);
		}
		catch (EmptyResultDataAccessException e) {
			if (LOG.isInfoEnabled())
				LOG.info("Failed to find access token for userName " + userName);
		}
		accessTokens = removeNulls(accessTokens);

		return accessTokens;
	}

	public Collection<AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
		List<AccessToken> accessTokens = new ArrayList<AccessToken>();

		try {
			accessTokens = jdbcTemplate.query(selectAccessTokensFromUserNameAndClientIdSql, new SafeAccessTokenRowMapper(),
					userName, clientId);
		}
		catch (EmptyResultDataAccessException e) {
			if (LOG.isInfoEnabled()) {
				LOG.info("Failed to find access token for clientId " + clientId + " and userName " + userName);
			}
		}
		accessTokens = removeNulls(accessTokens);

		return accessTokens;
	}

	private List<AccessToken> removeNulls(List<AccessToken> accessTokens) {
		List<AccessToken> tokens = new ArrayList<AccessToken>();
		for (AccessToken token : accessTokens) {
			if (token != null) {
				tokens.add(token);
			}
		}
		return tokens;
	}

	protected String extractTokenKey(String value) {
		if (value == null) {
			return null;
		}
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
		}

		try {
			byte[] bytes = digest.digest(value.getBytes("UTF-8"));
			return String.format("%032x", new BigInteger(1, bytes));
		}
		catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
		}
	}

	private final class SafeAccessTokenRowMapper implements RowMapper<AccessToken> {
		public AccessToken mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				return deserializeAccessToken(rs.getBytes(2));
			}
			catch (IllegalArgumentException e) {
				String token = rs.getString(1);
				jdbcTemplate.update(deleteAccessTokenSql, token);
				return null;
			}
		}
	}

	protected byte[] serializeAccessToken(AccessToken token) {
		return SerializationUtils.serialize(token);
	}

	protected byte[] serializeRefreshToken(RefreshToken token) {
		return SerializationUtils.serialize(token);
	}

	protected byte[] serializeAuthentication(Authentication authentication) {
		return SerializationUtils.serialize(authentication);
	}

	protected AccessToken deserializeAccessToken(byte[] token) {
		return SerializationUtils.deserialize(token);
	}

	protected RefreshToken deserializeRefreshToken(byte[] token) {
		return SerializationUtils.deserialize(token);
	}

	protected Authentication deserializeAuthentication(byte[] authentication) {
		return SerializationUtils.deserialize(authentication);
	}

	public void setInsertAccessTokenSql(String insertAccessTokenSql) {
		this.insertAccessTokenSql = insertAccessTokenSql;
	}

	public void setSelectAccessTokenSql(String selectAccessTokenSql) {
		this.selectAccessTokenSql = selectAccessTokenSql;
	}

	public void setDeleteAccessTokenSql(String deleteAccessTokenSql) {
		this.deleteAccessTokenSql = deleteAccessTokenSql;
	}

	public void setInsertRefreshTokenSql(String insertRefreshTokenSql) {
		this.insertRefreshTokenSql = insertRefreshTokenSql;
	}

	public void setSelectRefreshTokenSql(String selectRefreshTokenSql) {
		this.selectRefreshTokenSql = selectRefreshTokenSql;
	}

	public void setDeleteRefreshTokenSql(String deleteRefreshTokenSql) {
		this.deleteRefreshTokenSql = deleteRefreshTokenSql;
	}

	public void setSelectAccessTokenAuthenticationSql(String selectAccessTokenAuthenticationSql) {
		this.selectAccessTokenAuthenticationSql = selectAccessTokenAuthenticationSql;
	}

	public void setSelectRefreshTokenAuthenticationSql(String selectRefreshTokenAuthenticationSql) {
		this.selectRefreshTokenAuthenticationSql = selectRefreshTokenAuthenticationSql;
	}

	public void setSelectAccessTokenFromAuthenticationSql(String selectAccessTokenFromAuthenticationSql) {
		this.selectAccessTokenFromAuthenticationSql = selectAccessTokenFromAuthenticationSql;
	}

	public void setDeleteAccessTokenFromRefreshTokenSql(String deleteAccessTokenFromRefreshTokenSql) {
		this.deleteAccessTokenFromRefreshTokenSql = deleteAccessTokenFromRefreshTokenSql;
	}

	public void setSelectAccessTokensFromUserNameSql(String selectAccessTokensFromUserNameSql) {
		this.selectAccessTokensFromUserNameSql = selectAccessTokensFromUserNameSql;
	}

	public void setSelectAccessTokensFromUserNameAndClientIdSql(String selectAccessTokensFromUserNameAndClientIdSql) {
		this.selectAccessTokensFromUserNameAndClientIdSql = selectAccessTokensFromUserNameAndClientIdSql;
	}

	public void setSelectAccessTokensFromClientIdSql(String selectAccessTokensFromClientIdSql) {
		this.selectAccessTokensFromClientIdSql = selectAccessTokensFromClientIdSql;
	}

}
