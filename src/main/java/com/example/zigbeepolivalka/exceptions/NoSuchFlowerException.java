package com.example.zigbeepolivalka.exceptions;

/**
 * Thrown to indicate that flower being requested does not exist.
 */
public class NoSuchFlowerException extends Exception {

  /**
   * Constructs a {@code NoSuchFlowerException}, saving a reference to the
   * error message string s for later retrieval by the getMessage method.
   * @param message the detail message
   */
  public NoSuchFlowerException(String message) {
    super(message);
  }

  public NoSuchFlowerException() {
    this("No flowers with given data can be found.");
  }
}
