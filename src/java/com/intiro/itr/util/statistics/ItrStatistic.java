package com.intiro.itr.util.statistics;

public class ItrStatistic {

  private String status;
  private String methodCalled;
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

  public String getMethodCalled() {
    return methodCalled;
  }

  public void setMethodCalled(String methodCalled) {
    this.methodCalled = methodCalled;
  }

  public ItrStatistic(String action, String methodCalled, String status) {
    this.action = action;
    this.methodCalled = methodCalled;
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof ItrStatistic && ((ItrStatistic) o).hashCode() == this.hashCode();
  }

  @Override
  public int hashCode() {
    return (this.action + "|" + this.status + "|" + this.methodCalled).hashCode();
  }
}
