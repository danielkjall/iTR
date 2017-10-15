package com.intiro.itr.db;

import com.intiro.itr.util.ItrUtil;

public class InvocationHandlerSetting {

  private String m_cacheKey;
  private int m_cacheTimeInSeconds;
  private String m_statisticAction;

  public String getCacheKey() {
    return m_cacheKey;
  }

  public void setCacheKey(String cacheKey) {
    this.m_cacheKey = cacheKey;
  }

  public int getCacheTimeInSeconds() {
    return m_cacheTimeInSeconds;
  }

  public void setCacheTimeInSeconds(int cacheTime) {
    this.m_cacheTimeInSeconds = cacheTime;
  }

  public void setStatisticAction(String statisticAction) {
    this.m_statisticAction = statisticAction;
  }

  public String getAction() {
    return m_statisticAction;
  }

  public static InvocationHandlerSetting create() {
    InvocationHandlerSetting retval = new InvocationHandlerSetting();
    return retval;
  }

  public static InvocationHandlerSetting create(String statistikAction) {
    InvocationHandlerSetting retval = new InvocationHandlerSetting();
    retval.setStatisticAction(statistikAction);
    return retval;
  }

  public static InvocationHandlerSetting create(String cachekey, int cacheTimeKeyInSeconds, String statistikAction) {
    InvocationHandlerSetting retval = new InvocationHandlerSetting();
    retval.setCacheKey(cachekey);
    retval.setCacheTimeInSeconds(cacheTimeKeyInSeconds);
    retval.setStatisticAction(statistikAction);
    return retval;
  }

  public boolean supportCaching() {
    return ItrUtil.isStrContainingValue(m_cacheKey);
  }

  public boolean supportStatistic() {
    return ItrUtil.isStrContainingValue(this.m_statisticAction);
  }
}
