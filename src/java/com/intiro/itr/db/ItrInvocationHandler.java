package com.intiro.itr.db;

import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.cache.ItrCache;
import com.intiro.itr.util.cache.LockHelper;
import com.intiro.itr.util.logger.ItrLogger;
import com.intiro.itr.util.logger.ItrLogEntry;
import com.intiro.itr.util.statistics.ItrStatistics;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ItrInvocationHandler implements InvocationHandler {

  private static ItrInvocationHandler m_instance;
  private static final Object LOCK = new Object();
  private final InvocationHandled m_invocationHandled;
  private static Set<String> methodsNotTriggeringCleanCache = new HashSet<>();

  protected ItrInvocationHandler(InvocationHandled invocationHandled) {
    m_invocationHandled = invocationHandled;
    methodsNotTriggeringCleanCache.add("DbExecuteInterface.makeNewUserWeekId");
    methodsNotTriggeringCleanCache.add("DbExecuteInterface.deleteRow");
    methodsNotTriggeringCleanCache.add("DbExecuteInterface.insertRow");
    methodsNotTriggeringCleanCache.add("DbExecuteInterface.saveLog");
    methodsNotTriggeringCleanCache.add("DbExecuteInterface.updateApprovedInWeek");
    methodsNotTriggeringCleanCache.add("DbExecuteInterface.updateRow");
    methodsNotTriggeringCleanCache.add("DbExecuteInterface.updateSubmitInWeek");
    methodsNotTriggeringCleanCache.add("DbExecuteInterface.saveStatistics");
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

    //Special fall, vi anropar metoden och returnerar direkt.
    if (interfaceNameAndMethodName.startsWith("DBQueriesAdminInterface")
            || (interfaceNameAndMethodName.startsWith("DbExecuteInterface") && !methodsNotTriggeringCleanCache.contains(interfaceNameAndMethodName))) {
      ItrCache.clear();
    }

    InvocationHandlerSetting setting = m_invocationHandled.getCallSetup();

    if (!setting.supportCaching()) {
      return fetchFromSystem(method, args, setting);
    }

    String cacheKey = setting.getCacheKey();
    Object cachedObject = ItrCache.get(cacheKey);
    if (cachedObject != null) {
      if (setting.supportStatistic()) {
        ItrStatistics.getInstance().addGeneric(setting.getAction(), interfaceNameAndMethodName, ItrStatistics.STATUS_CACHED_VALUE);
      }
      if (cachedObject instanceof StringRecordset) {
        ((StringRecordset) cachedObject).moveFirst();
      }
      return cachedObject;
    }

    // Fetch lock for cacheKey. Every type of cached object has its own lock.
    Object lock = LockHelper.getLockingObject(cacheKey);
    synchronized (lock) {
      // for the second, third... threads waiting, we try fetch again
      // from cache.
      cachedObject = ItrCache.get(cacheKey);

      // if cachedObject is found write to log
      if (cachedObject != null) {
        if (setting.supportStatistic()) {
          ItrStatistics.getInstance().addGeneric(setting.getAction(), interfaceNameAndMethodName, ItrStatistics.STATUS_CACHED_VALUE);
        }
        if (cachedObject instanceof StringRecordset) {
          ((StringRecordset) cachedObject).moveFirst();
        }
        return cachedObject;
      }

      Object result = fetchFromSystem(method, args, setting);

      // Put object into cache for next caller.
      if (result != null) {
        ItrCache.put(setting.getCacheKey(), result, setting.getCacheTimeInSeconds());
      }
      return result;
    }
  }

  private Object fetchFromSystem(Method method, Object[] args, InvocationHandlerSetting setting) throws Throwable {
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
      ItrStatistics.getInstance().addGeneric(setting.getAction(), interfaceNameAndMethodName, ItrStatistics.STATUS_SUCCESS);
    } catch (InvocationTargetException ex) {
      long afterCallInMs = System.currentTimeMillis();
      ItrLogEntry entry = getLogEntry(interfaceNameAndMethodName, args, beforeCallInMs, afterCallInMs, timestamp);
      entry.setError(ex.getCause());
      ItrLogger.getInstance().log(entry);
      ItrStatistics.getInstance().addGeneric(setting.getAction(), interfaceNameAndMethodName, ItrStatistics.STATUS_FAILED);
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
    String cacheKey = "getServiceString_properties";
    Map<String, String> map = ItrCache.get(cacheKey);
    if (map == null) {
      map = new DBQueriesConfig().getProperties();
      if (map != null) {
        ItrCache.put(cacheKey, map, 60 * 15);
      }
    }
    return map.get("com.intiro.itr.logger.blacklist");
  }
}
