package com.intiro.itr.util.startup;

import com.intiro.itr.db.DBQueriesConfig;
import com.intiro.itr.db.InvocationHandlerSetting;
import com.intiro.itr.util.ItrUtil;
import com.intiro.itr.util.cache.ItrCache;
import com.intiro.itr.util.log.IntiroLog;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Set;

public abstract class StartupThread extends Thread {

  protected Date latestRun = new Date();
  protected static final int SLEEP_IN_SECONDS_BETWEEN_TRIES = 5;
  protected static final int SLEEP_IN_SECONDS_BEFORE_FIRST_TRY = 60;
  private static final Object LOCK = new Object();
  private static final String STARTUPCLASS_EXECUTION_PAUS_FOR_DEFAULT = "com.intiro.itr.startup.execution.paus.default";
  private static final String STARTUPCLASS_EXECUTION_SLEEP_INTERVAL_FOR_DEFAULT = "com.intiro.itr.startup.sleep.interval.default";
  private static final String STARTUPCLASS_EXECUTION_PAUS_FOR_PREFIX = "com.intiro.itr.startup.execution.paus.prefix.";
  private static final String STARTUPCLASS_EXECUTION_SLEEP_INTERVAL_FOR_PREFIX = "com.intiro.itr.startup.sleep.interval.";

  protected boolean shouldRun(StartUpClassSetting setting) {
    boolean retval = false;
    try {
      if (setting == null) {
        return retval;
      }
      if (setting.isPaused()) {
        return retval;
      }
      if (shouldRunBasedOnTimeSinceLastRun(setting) == false) {
        return retval;
      }

      retval = true;
      latestRun = new Date();
    } catch (Exception e) {
      // Svälj felet, men logga
      IntiroLog.warning(this.getClass(), "Något gick fel vid uppläsning av settings för startupclass: " + setting.getStartupClass());
      retval = false;
    }

    return retval;
  }

  private boolean shouldRunBasedOnTimeSinceLastRun(StartUpClassSetting setting) {

    Calendar timeForNextRun = GregorianCalendar.getInstance();
    timeForNextRun.setTime(latestRun);
    timeForNextRun.add(Calendar.SECOND, setting.getSleepInterval());

    Calendar now = GregorianCalendar.getInstance();

    return now.after(timeForNextRun);
  }

  protected StartUpClassSetting loadStartUpSetting(String startupClass) {
    StartUpClassSetting retval = new StartUpClassSetting();
    retval.setStartupClass(startupClass);

    try {
      Map<String, String> map;

      String cacheKey = getClass().getName() + ".getProperties";
      String statisticKey = getClass().getName() + ".load";
      int cacheTime = 3600 * 10;
      InvocationHandlerSetting s = InvocationHandlerSetting.create(cacheKey, cacheTime, statisticKey);
      map = DBQueriesConfig.getProxy(s).getProperties();

      if (map == null || map.isEmpty()) {
        return retval;
      }

      // Set values to StartUpClassSetting
      Set<String> keys = map.keySet();
      // 1. Set default values
      for (String key : keys) {
        String keyOfInterest = STARTUPCLASS_EXECUTION_PAUS_FOR_DEFAULT;
        if (key.equals(keyOfInterest)) {
          extractPaused(retval, map, key);
        }

        keyOfInterest = STARTUPCLASS_EXECUTION_SLEEP_INTERVAL_FOR_DEFAULT;
        if (key.equals(keyOfInterest)) {
          extractSleepInterval(retval, map, key);
        }
      }

      // 2. Set values specific for startupClass
      for (String key : keys) {
        String keyOfInterest = STARTUPCLASS_EXECUTION_PAUS_FOR_PREFIX + startupClass;
        if (key.equals(keyOfInterest)) {
          extractPaused(retval, map, key);
        }

        keyOfInterest = STARTUPCLASS_EXECUTION_SLEEP_INTERVAL_FOR_PREFIX + startupClass;
        if (key.equals(keyOfInterest)) {
          extractSleepInterval(retval, map, key);
        }
      }

    } catch (Exception e) {
      // Swallow exception
      IntiroLog.warning(this.getClass(), "Failed getting setting to set MsStartUpclassSettings! e:" + e.getMessage());
    }

    return retval;
  }

  private void extractSleepInterval(StartUpClassSetting retval, Map<String, String> map, String key) {
    int sleepInSeconds = 60;
    try {
      sleepInSeconds = Integer.parseInt(map.get(key));
    } catch (NumberFormatException e) {
      // Swallow
    }
    retval.setSleepInterval(sleepInSeconds);
  }

  private void extractPaused(StartUpClassSetting retval, Map<String, String> map, String key) {
    boolean paused = false;
    try {
      paused = ItrUtil.getBooleanFromDBValue(map.get(key));
    } catch (Exception e) {
      // Swallow
    }
    retval.setPaused(paused);
  }

  protected void sleep(int timeInSeconds) {
    try {
      Thread.sleep(timeInSeconds * 1000);
    } catch (InterruptedException ex) {
      IntiroLog.warning(this.getClass(), "Failed to sleep! e: " + ex.getMessage());
    }
  }

}
