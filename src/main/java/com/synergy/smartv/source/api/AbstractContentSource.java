package com.synergy.smartv.source.api;

import com.synergy.smartv.source.api.model.InvalidSourcePathException;
import org.openqa.selenium.WebDriver;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public abstract class AbstractContentSource implements IContentSource {
  private Map<String, IContentSource> subContentSources = null;

  @Override
  public Map<String, IContentSource> getCachedSubSources() {
    return subContentSources;
  }

  @Override
  public IContentSource findContentSourceModel(final Queue<String> contentSource, final WebDriver webDriver) {
    viewContentSource(webDriver);
    if(contentSource.isEmpty()) {
      return this;
    }

    Queue<String> sourceQueue = new LinkedList<String>(contentSource);
    String sourceName = sourceQueue.poll();
    if(subContentSources != null && subContentSources.containsKey(sourceName)) {
      return subContentSources.get(sourceName).findContentSourceModel(sourceQueue, webDriver);
    } else {
      throw new InvalidSourcePathException(sourceName, getDisplayName());
    }
  }

  @Override
  public abstract String getDisplayName();

  @Override
  public void viewContentSource(final WebDriver driver) {
    subContentSources = new HashMap<String, IContentSource>();
    Collection<IContentSource> returnedSources = bringUpContentSource(driver);
    for (IContentSource source : returnedSources) {
      subContentSources.put(source.getDisplayName(), source);
    }
  }

  protected abstract Collection<IContentSource> bringUpContentSource(final WebDriver driver);
}
