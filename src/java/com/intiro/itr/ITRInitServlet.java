/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr;

import org.glassfish.api.Startup;

/**
 * This servlet is called when Tomcat is started.
 * It is used to start up iTR.
 * It calls ITRResources which loads the properties from a file and
 * then creates the connection pools.
 *
 * @see ITRResources
 */
public class ITRInitServlet implements org.glassfish.api.Startup {

  //~ Methods ..........................................................................................................

  public String getServletInfo() {
    return "itrInitServlet";
  }

  @Override
  public Lifecycle getLifecycle() {


    for (Startup.Lifecycle c : Startup.Lifecycle.values())
      System.out.println("skriver upp: "+c);

    return Startup.Lifecycle.START;

//    long start = new Date().getTime();
//    long stop = 0;
//    double sum = 0;

//    try {
//
//      /*
//       TODO:
//       The servlet is called four times, I don't no why.
//       Might be the placement of web.xml file. should be placed in web-inf not in conf.
//       */
//
//      /*Load ITRResources*/
//      ITRResources resources = new ITRResources();
//      resources.load();
//
//      /*Count the time to start "boot" the itr*/
//      stop = new Date().getTime();
//      sum = (stop - start) / 1000;
//
//      if (IntiroLog.d()) {
//        IntiroLog.detail(getClass(), getClass().getName() + ".init(): Finish the init of the itr: It took " + sum + " seconds");
//      }
//    } catch (Exception e) {
//      System.out.println("ERROR i startup class: " + e.getMessage());
//    }
  }
}