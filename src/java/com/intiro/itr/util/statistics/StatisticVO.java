package com.intiro.itr.util.statistics;

import java.util.Date;

public class StatisticVO {

  private String action;
  private String status;
  private int hits;
  private Date timestamp;

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public int getHits() {
    return hits;
  }

  public void setHits(int count) {
    this.hits = count;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }
}
