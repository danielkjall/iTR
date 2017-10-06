package com.intiro.itr.util.startup;

import com.intiro.itr.util.log.IntiroLog;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;

@Singleton
@javax.ejb.Startup
public class Startup {

  @PostConstruct
  private void startup() {
    IntiroLog.info(this.getClass(), "ITR startup class is starting.....");
    Thread storeLogs = new StoreLogs();
    storeLogs.setDaemon(true);
    storeLogs.start();
    
    Thread storeStatistics = new StoreStatistics();
    storeStatistics.setDaemon( true );
    storeStatistics.start();
  }

  @PreDestroy
  private void shutdown() {
    IntiroLog.info(this.getClass(), "!!!!!!SHUTDOWN ITR!!!!!");
  }
}
