package com.synergy.smartv.source.exec;

import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class SikuliExecutor implements Runnable {
  private final File repository;
  private final String sikuliScriptName;
  private Logger logger;

  public SikuliExecutor(File repository, String sikuliScriptName, final Logger logger) {
    this.repository = repository;
    this.sikuliScriptName = sikuliScriptName;
    this.logger = logger;
    logger.info("Setting repository directory as " + repository.getAbsolutePath());
  }

  @Override
  public void run() {
    ProcessBuilder processBuilder = new ProcessBuilder();
    processBuilder.directory(repository);
    processBuilder.command("java", "-jar", "sikuli-script.jar", sikuliScriptName);
    processBuilder.redirectErrorStream(true);
    logger.info("Executing " + processBuilder.toString());


    Process process;
    try {
      process = processBuilder.start();
      writeProcessOutput(process, logger);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void writeProcessOutput(final Process process, final Logger logger) throws IOException {
    InputStreamReader tempReader = new InputStreamReader(
        new BufferedInputStream(process.getInputStream()));
    BufferedReader reader = new BufferedReader(tempReader);
    while (true) {
      String line = reader.readLine();
      if (line == null) {
        break;
      }
      logger.info(line);
    }
  }
}
