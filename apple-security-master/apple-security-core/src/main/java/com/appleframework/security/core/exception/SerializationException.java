package com.appleframework.security.core.exception;

/**
 * Thrown during a problem serialization/deserialization.
 *
 * @author Ryan Heaton
 */
@SuppressWarnings("serial")
public class SerializationException extends RuntimeException {

  public SerializationException() {
  }

  public SerializationException(String message) {
    super(message);
  }

  public SerializationException(String message, Throwable cause) {
    super(message, cause);
  }

  public SerializationException(Throwable cause) {
    super(cause);
  }
}
