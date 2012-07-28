package com.synergy.smartv.source;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.net.URL;
import java.security.ProtectionDomain;
import java.util.logging.Logger;

/**
 * SmartVRunner starts up embedded JETTY server and loads the web.xml
 * which inturn loads the spring context
 */
public class SmartVRunner {
  private static Logger logger = Logger.getLogger(SmartVRunner.class.getName());
  private static final int DEFAULT_PORT = 8080;

  public static void main(String[] args) throws Exception {
    int port = getServerPort(args);

    // Start up the jetty server
    Server server = new Server(port);
    ProtectionDomain domain = SmartVRunner.class.getProtectionDomain();
    URL location = domain.getCodeSource().getLocation();
    WebAppContext appContext = new WebAppContext();
    appContext.setContextPath("/");
    appContext.setDescriptor(location.toExternalForm() + "/web.xml");
    appContext.setServer(server);
    appContext.setWar(location.toExternalForm());
    System.out.println(location.toExternalForm());
    System.out.println("starting jetty server");
    appContext.setInitParameter("useFileMappedBuffer", "false");
    server.setHandler(appContext);
    server.start();
    server.join();
  }

  /**
   * Gets the server port
   *
   * @param args Arguments passed to the program
   * @return retrieve the server port
   */
  private static int getServerPort(final String[] args) throws ParseException {
    int port = DEFAULT_PORT;
    Options options = new Options();
    options.addOption("p", "port", true, "HTTP Port to run on");
    CommandLineParser parser = new PosixParser();
    CommandLine cmd = parser.parse(options, args);
    if (cmd.hasOption("port")) {
      try {
        port = Integer.parseInt(cmd.getOptionValue("port"));
      } catch (NumberFormatException e) {
        logger.severe("First argument should be the port number. Cannot convert " + args[0] + " to a port number");
        System.exit(-1);
      }
    }
    logger.info("HTTP server started on " + port);
    return port;
  }

}
