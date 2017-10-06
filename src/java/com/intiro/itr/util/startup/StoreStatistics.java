package com.intiro.itr.util.startup;

import com.intiro.itr.db.DBExecute;
import com.intiro.itr.util.log.IntiroLog;
import com.intiro.itr.util.statistics.ItrStatistic;
import com.intiro.itr.util.statistics.ItrStatistics;
import com.intiro.itr.util.statistics.StatisticVO;
import com.intiro.itr.util.statistics.StatisticsVO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class StoreStatistics extends StartupThread {

  @Override
  public void run() {
    ItrStatistics.getInstance().setUse(true);

    while (true) {
      sleep(SLEEP_IN_SECONDS_BETWEEN_TRIES);

      StartUpClassSetting setting = loadStartUpSetting(this.getClass().getSimpleName());
      ItrStatistics.getInstance().setUse(!setting.isPaused());

      // Check if we should run:
      // 1. if paused
      // 2. if enough time has passed
      if (shouldRun(setting) == false) {
        continue;
      }

      Map<ItrStatistic, Integer> stats = ItrStatistics.getInstance().getResult();

      StatisticsVO statistics = new StatisticsVO();
      statistics.setStatistics(new ArrayList<>());

      List<StatisticVO> list = statistics.getStatistics();

      Date now = new Date();

      for (ItrStatistic stat : stats.keySet()) {
        StatisticVO vo = new StatisticVO();
        vo.setKategori(stat.getCategory());
        vo.setAction(stat.getAction());
        vo.setStatus(stat.getStatus());
        vo.setCount(stats.get(stat));
        vo.setTimestamp(now);
        list.add(vo);
      }

      try {
        DBExecute.getProxy().saveStatistics(statistics);
      } catch (Exception e) {
        // Swallow exception
        IntiroLog.warning(this.getClass(), "MsStoreStatistics: Failed to store statistics! e: " + e);
      }
    }
  }
}