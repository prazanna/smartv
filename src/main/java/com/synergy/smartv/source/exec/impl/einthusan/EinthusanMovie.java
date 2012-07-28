package com.synergy.smartv.source.exec.impl.einthusan;

import com.synergy.smartv.source.api.AbstractContentSource;
import com.synergy.smartv.source.api.IContentSource;
import com.synergy.smartv.source.exec.SikuliExecutor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.core.task.TaskExecutor;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

public class EinthusanMovie extends AbstractContentSource {
  private static Logger logger = Logger.getLogger(EinthusanMovie.class.getName());

  private final String name;
  private final TaskExecutor taskExecutor;
  private static final String EINTHUSAN_SIKULI = "einthusan.sikuli";

  public EinthusanMovie(final String name, final TaskExecutor taskExecutor) {
    this.name = name;
    this.taskExecutor = taskExecutor;
  }

  @Override
  public String getDisplayName() {
    return name;
  }

  @Override
  protected Collection<IContentSource> bringUpContentSource(final WebDriver driver) {
    logger.info("Click on "+name);
    driver.findElement(By.partialLinkText(name)).click();
    logger.info("Executing Sikuli script to full screen video source");
    taskExecutor.execute(new SikuliExecutor(new File(new File(System.getProperty("smart.tv.home")), "repository"),
        EINTHUSAN_SIKULI, logger));
    logger.info("Returning empty subContentSource");
    return new ArrayList<IContentSource>();
  }
}
