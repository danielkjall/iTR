package com.intiro.itr;

import com.intiro.itr.util.log.IntiroLog;
import com.intiro.itr.util.startup.StoreLogs;
import com.intiro.itr.util.startup.StoreStatistics;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public class ITRInitServlet extends javax.servlet.http.HttpServlet {

  @Override
  public void init(ServletConfig config) throws ServletException {
    IntiroLog.info(this.getClass(), "ITR startup class is starting.....");
    Thread storeLogs = new StoreLogs();
    storeLogs.setDaemon(true);
    storeLogs.start();

    Thread storeStatistics = new StoreStatistics();
    storeStatistics.setDaemon(true);
    storeStatistics.start();
  }

  @Override
  public void destroy() {
    IntiroLog.info(this.getClass(), "!!!!!!SHUTDOWN ITR!!!!!");
  }
}
