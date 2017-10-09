package com.intiro.itr.util.startup;

import com.intiro.itr.db.DBExecute;
import com.intiro.itr.util.log.IntiroLog;
import com.intiro.itr.util.logger.ItrLogEntry;
import com.intiro.itr.util.logger.ItrLogger;
import com.intiro.itr.util.logger.LoggerVO;
import java.util.ArrayList;
import java.util.List;

public class StoreLogs extends StartupThread {

  @Override
  public void run() {
    ItrLogger.getInstance().setUse(true);

    sleep(SLEEP_IN_SECONDS_BEFORE_FIRST_TRY);
    
    while (true) {
      sleep(SLEEP_IN_SECONDS_BETWEEN_TRIES);

      String className = this.getClass().getSimpleName();
      StartUpClassSetting setting = loadStartUpSetting(className);
      ItrLogger.getInstance().setUse(!setting.isPaused());

      // Check if we should run:
      // 1. if paused
      // 2. if enough time has passed
      if (shouldRun(setting) == false) {
        continue;
      }

      List<ItrLogEntry> logEntries = ItrLogger.getInstance().fetchAndClearEntries();

      List<LoggerVO> toSave = new ArrayList<>();
      for (ItrLogEntry entry : logEntries) {
        LoggerVO vo = entry.getVO();
        toSave.add(vo);
      }

      /**
       * Send them to database
       */
      try {
        DBExecute.getProxy().saveLog(toSave);
      } catch (Exception e) {
        // Swallow exception
        IntiroLog.warning(this.getClass(), ", Failed to save log! e: " + e);
      }
    }
  }
}
