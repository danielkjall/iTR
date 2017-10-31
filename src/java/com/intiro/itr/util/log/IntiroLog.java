package com.intiro.itr.util.log;

import java.util.logging.Level;
import java.util.logging.Logger;

public class IntiroLog {

  private static final Logger LOG = Logger.getLogger(IntiroLog.class.getName());

  private static IntiroLog instance;

  public static void criticalError(Class aClass, String msg) {
    log(aClass.getName() + msg, Level.SEVERE);
  }

  public static void criticalError(Class aClass, String msg, Exception e) {
    log(aClass.getName() + msg + ", e: " + e.getMessage(), Level.SEVERE);
  }

  public static void error(Class aClass, String msg) {
    log(aClass.getName() + msg, Level.SEVERE);
  }

  public static void error(Class aClass, String msg, Exception e) {
    log(aClass.getName() + msg + ", e: " + e.getMessage(), Level.SEVERE);
  }

  public static void warning(Class aClass, String msg) {
    log(aClass.getName() + msg, Level.WARNING);
  }

  public static void info(Class aClass, String msg) {
    log(aClass.getName() + msg, Level.INFO);
  }

  public static boolean i() {
    return true;
  }

  public static void detail(Class aClass, String msg) {
    log(aClass.getName() + msg, Level.FINE);
  }

  public static boolean d() {
    return true;
  }

  private static void log(String msg, Level level) {
    if (LOG.getLevel() == Level.ALL) {
      LOG.info(msg);
    }
    LOG.log(level, msg);

  }

  public static IntiroLog getInstance() {
    if (instance == null) {
      instance = new IntiroLog();
    }
    return instance;
  }

  public static Object getLogAsStringBuffer() {
    return "getLogAsStringBuffer";
  }

  public void setLoggingLevel(int i) {
    
  }

  public int getLoggingLevel() {
   return 1;
  }
}
