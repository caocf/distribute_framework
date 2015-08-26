package com.appleframework.security.core.auth;

import java.io.Serializable;

import com.appleframework.security.core.auth.Authentication;

/**
 * Represents an authority granted to an {@link Authentication} object.
 *
 * <p>
 * A <code>GrantedAuthority</code> must either represent itself as a
 * <code>String</code> or be specifically supported by an  {@link
 * AccessDecisionManager}.
 *
 * @author Ben Alex
 */
public interface GrantedAuthority extends Serializable {
 
    /**
     * If the <code>GrantedAuthority</code> can be represented as a <code>String</code> and that
     * <code>String</code> is sufficient in precision to be relied upon for an access control decision by an {@link
     * AccessDecisionManager} (or delegate), this method should return such a <code>String</code>.
     * <p>
     * If the <code>GrantedAuthority</code> cannot be expressed with sufficient precision as a <code>String</code>,
     * <code>null</code> should be returned. Returning <code>null</code> will require an
     * <code>AccessDecisionManager</code> (or delegate) to specifically support the <code>GrantedAuthority</code>
     * implementation, so returning <code>null</code> should be avoided unless actually required.
     *
     * @return a representation of the granted authority (or <code>null</code> if the granted authority cannot be
     *         expressed as a <code>String</code> with sufficient precision).
     */
    String getAuthority();
}