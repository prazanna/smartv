package com.synergy.smartv.source.api.model;


public class InvalidSourcePathException extends RuntimeException {
  public InvalidSourcePathException(String subPath, String path) {
    super("Could not find " + subPath+ " under " + path);

  }
}
