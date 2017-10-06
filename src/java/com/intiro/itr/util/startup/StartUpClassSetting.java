package com.intiro.itr.util.startup;

public class StartUpClassSetting {

  private boolean paused = true;
  private int sleepInterval = 60;
  private String startupClass = "NOT_SET";

  public boolean isPaused() {
    return paused;
  }

  public void setPaused(boolean paused) {
    this.paused = paused;
  }

  public int getSleepInterval() {
    return sleepInterval;
  }

  public void setSleepInterval(int sleepInterval) {
    this.sleepInterval = sleepInterval;
  }

  public String getStartupClass() {
    return startupClass;
  }

  public void setStartupClass(String startupClass) {
    this.startupClass = startupClass;
  }

}
