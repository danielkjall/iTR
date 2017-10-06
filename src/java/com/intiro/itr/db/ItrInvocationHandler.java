package com.intiro.itr.db;

import com.intiro.itr.util.logger.ItrLogger;
import com.intiro.itr.util.logger.ItrLogEntry;
import com.intiro.itr.util.statistics.ItrStatistics;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

public class ItrInvocationHandler implements InvocationHandler {

  private static ItrInvocationHandler m_instance;
  private static final Object LOCK = new Object();
  private InvocationHandled m_invocationHandled;

  protected ItrInvocationHandler(InvocationHandled invocationHandled) {
    m_invocationHandled = invocationHandled;
  }

  public static ItrInvocationHandler getInstance(InvocationHandled invocationHandled) {
    if (m_instance != null) {
      return m_instance;
    }
    synchronized (LOCK) {
      m_instance = new ItrInvocationHandler(invocationHandled);
    }
    return m_instance;
  }

  private String getInterfaceName(Method method) throws Throwable {
    return method.getDeclaringClass().getSimpleName();
  }

  private String getMethodName(Method method) throws Throwable {
    return getInterfaceName(method) + "." + method.getName();
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    String interfaceNameAndMethodName = getMethodName(method);

    // Special fall, vi anropar metoden och returnerar direkt.
    //if ("MsDAO.setCallSetup".equals(interfaceNameAndMethodName)) {
    //  return method.invoke(m_invocationHandled, args);
    //}
    Object result = fetchFromSystem(method, args);
    return result;
  }

  private Object fetchFromSystem(Method method, Object[] args) throws Throwable {
    String interfaceName = getInterfaceName(method);
    String interfaceNameAndMethodName = getMethodName(method);

    boolean doLog = checkDoLog(interfaceName);
    Object result;
    long beforeCallInMs = System.currentTimeMillis();
    Date timestamp = new Date();
    try {
      result = method.invoke(m_invocationHandled, args);
      long afterCallInMs = System.currentTimeMillis();

      if (doLog) {
        ItrLogEntry entry = getLogEntry(interfaceNameAndMethodName, args, beforeCallInMs, afterCallInMs, timestamp);
        entry.setOut(result);
        ItrLogger.getInstance().log(entry);
      }
      ItrStatistics.getInstance().addGeneric(interfaceName, interfaceName, "SUCCESS");
    } catch (InvocationTargetException ex) {

      long afterCallInMs = System.currentTimeMillis();
      ItrLogEntry entry = getLogEntry(interfaceNameAndMethodName, args, beforeCallInMs, afterCallInMs, timestamp);
      entry.setError(ex.getCause());
      ItrLogger.getInstance().log(entry);
      ItrStatistics.getInstance().addGeneric(interfaceName, interfaceName, "FAILED");
      throw ex;
    }

    return result;
  }

  private ItrLogEntry getLogEntry(String key, Object[] parameters, long beforeCallInMs, long afterCallInMs, Date timestamp) {
    String aktor = "";
    String sessionId = "";
    long durationInMs = afterCallInMs - beforeCallInMs;

    ItrLogEntry entry = new ItrLogEntry();
    entry.setAnropstidInMs((int) durationInMs);
    entry.setAktor(aktor);
    entry.setIn(parameters);
    entry.setKey(key);
    entry.setSessionId(sessionId);
    entry.setTimestamp(timestamp);

    return entry;
  }

  private boolean checkDoLog(String interfaceName) throws Exception {

    //StringRecordset settings = m_queries.getSettings();
    if (m_invocationHandled == null) {
      return false;
    }

    String servicesStr = "";

    try {
      servicesStr = getServiceString();
      if (servicesStr == null) {
        return false;
      }
    } catch (Exception e) {
      return false;
    }

    String[] services = servicesStr.replaceAll("\\s+", "").split(",");
    return !java.util.Arrays.asList(services).contains(interfaceName);

  }

  private String getServiceString() throws Exception {
    /*
    StringRecordset rs = m_queries.getSettings();
    while (!rs.getEOF()) {
      if(rs.getField(ix))
    }

    settings.getField("com.intiro.itr.logger");
     */
    return null;
  }
}
