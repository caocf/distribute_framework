package com.appleframework.security.core.exception;

/**
 * Abstract superclass for all exceptions related to an {@link Authentication}
 * object being invalid for whatever reason.
 * 
 * @author Ben Alex
 */
public abstract class AuthenticationException extends RuntimeException {
	// ~ Instance fields
	// ================================================================================================

	private static final long serialVersionUID = -470008821176335621L;
	private transient Object extraInformation;

	// ~ Constructors
	// ===================================================================================================

	/**
	 * Constructs an {@code AuthenticationException} with the specified message
	 * and root cause.
	 * 
	 * @param msg
	 *            the detail message
	 * @param t
	 *            the root cause
	 */
	public AuthenticationException(String msg, Throwable t) {
		super(msg, t);
	}

	/**
	 * Constructs an {@code AuthenticationException} with the specified message
	 * and no root cause.
	 * 
	 * @param msg
	 *            the detail message
	 */
	public AuthenticationException(String msg) {
		super(msg);
	}

	/**
	 * @deprecated Use the exception message or use a custom exception if you
	 *             really need additional information.
	 */
	@Deprecated
	public AuthenticationException(String msg, Object extraInformation) {
		super(msg);
		this.extraInformation = extraInformation;
	}

	/**
	 * Any additional information about the exception. Generally a
	 * {@code UserDetails} object.
	 * 
	 * @return extra information or {@code null}
	 * @deprecated Use the exception message or use a custom exception if you
	 *             really need additional information.
	 */
	@Deprecated
	public Object getExtraInformation() {
		return extraInformation;
	}

	@Deprecated
	public void clearExtraInformation() {
		this.extraInformation = null;
	}
}