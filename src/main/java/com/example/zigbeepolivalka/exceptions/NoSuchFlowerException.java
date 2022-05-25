package com.example.zigbeepolivalka.exceptions;

public class NoSuchFlowerException extends Exception {
  public NoSuchFlowerException(String message) {
    super(message);
  }

  public NoSuchFlowerException() {
    this("No flowers with given data can be found.");
  }
}
