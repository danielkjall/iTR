package com.intiro.itr.util.log;

public class IntiroLog {

  private static IntiroLog instance;

  public static void criticalError(Class aClass, String msg) {
    log(aClass.getName() + msg);
  }

  public static void criticalError(Class aClass, String msg, Exception e) {
    log(aClass.getName() + msg + ", e: " + e.getMessage());
  }

  public static void error(Class aClass, String msg) {
    log(aClass.getName() + msg);
  }

  public static void error(Class aClass, String msg, Exception e) {
    log(aClass.getName() + msg + ", e: " + e.getMessage());
  }
  
  public static void warning(Class aClass, String msg) {
    log(aClass.getName() + msg);
  }

  public static void info(Class aClass, String msg) {
    log(aClass.getName() + msg);
  }

  public static boolean i() {
    return true;
  }

  public static void detail(Class aClass, String msg) {
    log(aClass.getName() + msg);
  }

  public static boolean d() {
    return true;
  }

  private static void log(String msg) {
    System.out.println(msg);
  }

  public static IntiroLog getInstance() {
    if (instance == null) {
      instance = new IntiroLog();
    }
    return instance;
  }

  public static Object getLogAsStringBuffer() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  public void setLoggingLevel(int i) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  public int getLoggingLevel() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
