package com.synergy.smartv.source.exec.impl.watchsuntv;

import com.synergy.smartv.source.api.AbstractContentSource;
import com.synergy.smartv.source.api.IContentSource;
import com.synergy.smartv.source.exec.SikuliExecutor;
import com.synergy.smartv.source.exec.SmarTvConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.core.task.TaskExecutor;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

public class WatchSunTvChannel extends AbstractContentSource {
  private static Logger logger = Logger.getLogger(WatchSunTvChannel.class.getName());
  public static final String WATCHSUNTV_SIKULI = "watchsuntv.sikuli";

  private String name;
  private TaskExecutor taskExecutor;
  private SmarTvConfig config;

  public WatchSunTvChannel(String name, final TaskExecutor taskExecutor, final SmarTvConfig config) {
    this.name = name;
    this.taskExecutor = taskExecutor;
    this.config = config;
  }

  @Override
  public String getDisplayName() {
    return name;
  }

  @Override
  public Collection<IContentSource> bringUpContentSource(final WebDriver driver) {
    logger.info("Click on "+name);
    driver.findElement(By.partialLinkText(name)).click();
    logger.info("Executing Sikuli script to full screen video source");
    taskExecutor.execute(new SikuliExecutor(config.getRepositoryDir(),
        WATCHSUNTV_SIKULI, logger));
    logger.info("Returning empty subContentSource");
    return new ArrayList<IContentSource>();
  }
}
