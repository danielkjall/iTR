package com.intiro.itr.util;

import java.util.Date;

public class StopWatch {

  private Date startTime;

  public void startTiming() {
    startTime = new Date();
  }

  public long stopTiming() {
    Date stopTime = new Date();
    long timediff = (stopTime.getTime() - startTime.getTime());
    return timediff;
  }
}
