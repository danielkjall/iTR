package com.intiro.itr.db.vo;

import com.intiro.itr.db.DBConstants;
import com.intiro.itr.util.StringRecordset;

public class CalendarWeekVO {

  private int id;
  private int weekNo;
  private int weekPart;
  private String fromDate;
  private String toDate;
  private double mo;
  private double tu;
  private double we;
  private double th;
  private double fr;
  private double sa;
  private double su;

  public CalendarWeekVO() {

  }

  public int getId() {
    return id;
  }

  public int getWeekNo() {
    return weekNo;
  }

  public int getWeekPart() {
    return weekPart;
  }

  public String getFromDate() {
    return fromDate;
  }

  public String getToDate() {
    return toDate;
  }

  public double getMo() {
    return mo;
  }

  public double getTu() {
    return tu;
  }

  public double getWe() {
    return we;
  }

  public double getTh() {
    return th;
  }

  public double getFr() {
    return fr;
  }

  public double getSa() {
    return sa;
  }

  public double getSu() {
    return su;
  }

  public void loadFromRS(StringRecordset rs) throws Exception {
    
    id = rs.getInt(DBConstants.CALENDARWEEK_ID_PK);
    fromDate = rs.getField(DBConstants.CALENDARWEEK_FROM_DATE).substring(0, 10);
    toDate = rs.getField(DBConstants.CALENDARWEEK_TO_DATE).substring(0, 10);
    weekNo = rs.getInt(DBConstants.CALENDARWEEK_WEEKNO);
    weekPart = rs.getInt(DBConstants.CALENDARWEEK_WEEKPART);
    mo = Double.parseDouble(rs.getField(DBConstants.CALENDARWEEK_EXPECTEDHOURSMO));
    tu = Double.parseDouble(rs.getField(DBConstants.CALENDARWEEK_EXPECTEDHOURSTU));
    we = Double.parseDouble(rs.getField(DBConstants.CALENDARWEEK_EXPECTEDHOURSWE));
    th = Double.parseDouble(rs.getField(DBConstants.CALENDARWEEK_EXPECTEDHOURSTH));
    fr = Double.parseDouble(rs.getField(DBConstants.CALENDARWEEK_EXPECTEDHOURSFR));
    sa = Double.parseDouble(rs.getField(DBConstants.CALENDARWEEK_EXPECTEDHOURSSA));
    su = Double.parseDouble(rs.getField(DBConstants.CALENDARWEEK_EXPECTEDHOURSSU));
  }
}