package com.intiro.itr.util.statistics;

public class ItrStatistic {

  private String status;
  private String action;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public ItrStatistic(String action, String status) {
    this.action = action;
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof ItrStatistic && ((ItrStatistic) o).hashCode() == this.hashCode();
  }

  @Override
  public int hashCode() {
    return (this.action + "|" + this.status).hashCode();
  }
}
