package com.synergy.smartv.source.resource;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class ContentSourcePath {
  private String rootContentSource;
  private Queue<String> contentSource = new LinkedList<String>();

  public String getRootContentSource() {
    return rootContentSource;
  }

  public Queue<String> getContentSource() {
    return contentSource;
  }

  public static ContentSourcePath parse(final String sourcePathStr) {
    ContentSourcePath sourcePath = new ContentSourcePath();
    String[] parts = sourcePathStr.split("/");
    sourcePath.rootContentSource = parts[0];
    sourcePath.contentSource.addAll(Arrays.asList(parts).subList(1, parts.length));
    return sourcePath;
  }


}
