package com.synergy.smartv.source.exec;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

@Component
public class SmarTvConfig {

  public static final String SMART_TV_HOME = "smart.tv.home";
  public static final String REPOSITORY = "repository";
  private File repositoryDir;

  @PostConstruct
  public void init() {
    String smartVHome = System.getProperty(SMART_TV_HOME);
    if(smartVHome == null) {
      throw new IllegalArgumentException(SMART_TV_HOME + " has to be specified ");
    }
    repositoryDir = new File(SMART_TV_HOME, REPOSITORY);
  }

  public File getRepositoryDir() {
    return repositoryDir;
  }
}
