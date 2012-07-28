package com.synergy.smartv.source.api;

import org.openqa.selenium.WebDriver;

import java.util.Collection;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;

/**
 * represents the interface for a content source
 * All content sources should implement this interface and should be @component
 * this would auto register itself with the ContentSourceResource
 *
 * @author Prasanna
 * @version 1.0
 */
public interface IContentSource {

  Map<String, IContentSource> getCachedSubSources();

  /**
   * @return display name of the content source
   */
  String getDisplayName();

  /**
   * Launch the browser and bring up the content source on the screen.
   * If this content source has more content sources, then a list of
   * <code>IContentSource</code> is returned otherwise an empty collection is returned
   *
   * @see IContentSource
   * @param driver WebDriver to launch the browser in
   * @return Collection of ContentSourceList within this source
   */
  void viewContentSource(final WebDriver driver);

  IContentSource findContentSourceModel(Queue<String> contentSource, final WebDriver webDriver);
}
