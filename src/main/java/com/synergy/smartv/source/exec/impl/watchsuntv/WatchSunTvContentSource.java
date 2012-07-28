package com.synergy.smartv.source.exec.impl.watchsuntv;

import com.synergy.smartv.source.api.AbstractContentSource;
import com.synergy.smartv.source.api.IContentSource;
import com.synergy.smartv.source.exec.SmarTvConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Component
public class WatchSunTvContentSource extends AbstractContentSource {
  private static Logger logger = Logger.getLogger(WatchSunTvContentSource.class.getName());
  @Autowired
  private TaskExecutor taskExecutor;
  @Autowired
  private SmarTvConfig config;
  @Value("${watchsuntv.username}")
  private String username;
  @Value("${watchsuntv.password}")
  private String password ;

  @Override
  public String getDisplayName() {
    return "WatchSunTv";
  }

  @Override
  public Collection<IContentSource> bringUpContentSource(WebDriver driver) {
    logger.info("Opening up watchsuntv.com");
    driver.get("http://www.watchsuntv.com");
    try {
      logger.info("Filling in credentials for user " + username);
      driver.findElement(By.name("amember_login")).sendKeys(username);
      driver.findElement(By.name("amember_pass")).sendKeys(password);
      logger.info("Finding the login button");
      driver.findElement(By.xpath("/html/body/div/div[4]/div/form/div/p[4]/input")).click();
      logger.info("Clicking on watch TV here link");
      driver.findElement(By.linkText("Watch TV Here")).click();
      logger.info("Getting the list of channels");
      List<WebElement> webElements = driver.findElements(By.xpath
          ("/html/body/div/div[2]/div[3]/ul/li/p/span/a[@href='javascript:void(1)']"));

      Collection<IContentSource> subContentSources = new ArrayList<IContentSource>();
      for (WebElement element : webElements) {
        subContentSources.add(new WatchSunTvChannel(element.getText(), taskExecutor, config));
      }
      return subContentSources;
    } catch (RuntimeException runtimeException) {
      logger.severe("Exception while bring up WatchSunTv Content resource. Exception " + runtimeException.toString());
      runtimeException.printStackTrace();
      return new ArrayList<IContentSource>();
    }
  }

}
