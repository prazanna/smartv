package com.synergy.smartv.source.exec.impl.einthusan;

import com.synergy.smartv.source.api.AbstractContentSource;
import com.synergy.smartv.source.api.IContentSource;
import com.synergy.smartv.source.exec.impl.watchsuntv.WatchSunTvChannel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Component
public class EinthusanContentSource extends AbstractContentSource{
  private static Logger logger = Logger.getLogger(EinthusanContentSource.class.getName());

  @Autowired
  private TaskExecutor taskExecutor;

  @Override
  public String getDisplayName() {
    return "Einthusan";
  }

  @Override
  protected Collection<IContentSource> bringUpContentSource(final WebDriver driver) {
    logger.info("Bringing up http://www.einthusan.com/movies/index.php?lang=tamil");
    driver.get("http://www.einthusan.com/movies/index.php?lang=tamil");
    try {
      logger.info("Getting the list of movies from einthusan");
      List<WebElement> webElements = driver.findElements(By.cssSelector("a.movie-title"));
      Collection<IContentSource> subContentSources = new ArrayList<IContentSource>();
      for (WebElement element : webElements) {
        subContentSources.add(new EinthusanMovie(element.getText(), taskExecutor));
      }
      return subContentSources;
    } catch (RuntimeException runtimeException) {
      logger.severe("Exception while bring up Einthusans Content resource. Exception " + runtimeException.toString());
      runtimeException.printStackTrace();
      return new ArrayList<IContentSource>();
    }
  }
}
