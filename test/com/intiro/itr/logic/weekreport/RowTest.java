/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intiro.itr.logic.weekreport;

import com.intiro.itr.db.DBConnect;
import com.intiro.itr.logic.project.Project;
import com.intiro.itr.util.ITRCalendar;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.XMLBuilderException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class RowTest {

  public RowTest() {
  }

  @BeforeClass
  public static void setUpClass() {
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  
  @Test
  public void test20120101() throws XMLBuilderException {
    DBConnect.testMode();
    ITRCalendar fromDate = new ITRCalendar("2012-01-01");
    ITRCalendar toDate = new ITRCalendar("2012-01-01");
    UserProfile userProfile = new UserProfile();
    userProfile.load(1);
    Row instance = new Row(userProfile, fromDate, toDate);
    SumRows sumRow = new SumRows(userProfile, fromDate, toDate);
    instance.calculateDayStatus(sumRow);
    assertEquals(1, instance.getFromDate().getWeekOfYear());
    assertEquals(1, instance.getFromDate().getWeekPart());
    assertEquals(true, instance.getFromDate().isWeekSplit());
    assertEquals(false, instance.isMonExist());
    assertEquals(false, instance.isTueExist());
    assertEquals(false, instance.isWedExist());
    assertEquals(false, instance.isThuExist());
    assertEquals(false, instance.isFriExist());
    assertEquals(false, instance.isSatExist());
    assertEquals(true, instance.isSunExist());
  }

  @Test
  public void test20130101() throws XMLBuilderException {
    DBConnect.testMode();
    ITRCalendar fromDate = new ITRCalendar("2013-01-01");
    ITRCalendar toDate = new ITRCalendar("2013-01-06");
    UserProfile userProfile = new UserProfile();
    userProfile.load(1);
    Row instance = new Row(userProfile, fromDate, toDate);
    SumRows sumRow = new SumRows(userProfile, fromDate, toDate);
    instance.calculateDayStatus(sumRow);
    assertEquals(1, instance.getFromDate().getWeekOfYear());
    assertEquals(1, instance.getFromDate().getWeekPart());
    assertEquals(true, instance.getFromDate().isWeekSplit());
    assertEquals(false, instance.isMonExist());
    assertEquals(true, instance.isTueExist());
    assertEquals(true, instance.isWedExist());
    assertEquals(true, instance.isThuExist());
    assertEquals(true, instance.isFriExist());
    assertEquals(true, instance.isSatExist());
    assertEquals(true, instance.isSunExist());
  }

  @Test
  public void test20140101() throws XMLBuilderException {
    DBConnect.testMode();
    ITRCalendar fromDate = new ITRCalendar("2014-01-01");
    ITRCalendar toDate = new ITRCalendar("2014-01-01");
    UserProfile userProfile = new UserProfile();
    userProfile.load(1);
    Row instance = new Row(userProfile, fromDate, toDate);
    SumRows sumRow = new SumRows(userProfile, fromDate, toDate);
    instance.calculateDayStatus(sumRow);
    assertEquals(false, instance.isMonExist());
    assertEquals(false, instance.isTueExist());
    assertEquals(true, instance.isWedExist());
    assertEquals(true, instance.isThuExist());
    assertEquals(true, instance.isFriExist());
    assertEquals(true, instance.isSatExist());
    assertEquals(true, instance.isSunExist());
  }

  @Test
  public void test20150101() throws XMLBuilderException {
    DBConnect.testMode();
    ITRCalendar fromDate = new ITRCalendar("2015-01-01");
    ITRCalendar toDate = new ITRCalendar("2015-01-01");
    UserProfile userProfile = new UserProfile();
    userProfile.load(1);
    Row instance = new Row(userProfile, fromDate, toDate);
    SumRows sumRow = new SumRows(userProfile, fromDate, toDate);
    instance.calculateDayStatus(sumRow);
    assertEquals(false, instance.isMonExist());
    assertEquals(false, instance.isTueExist());
    assertEquals(false, instance.isWedExist());
    assertEquals(true, instance.isThuExist());
    assertEquals(true, instance.isFriExist());
    assertEquals(true, instance.isSatExist());
    assertEquals(true, instance.isSunExist());
  }

  @Test
  public void test20160101() throws XMLBuilderException {
    DBConnect.testMode();
    ITRCalendar fromDate = new ITRCalendar("2016-01-01");
    ITRCalendar toDate = new ITRCalendar("2016-01-01");
    UserProfile userProfile = new UserProfile();
    userProfile.load(1);
    Row instance = new Row(userProfile, fromDate, toDate);
    SumRows sumRow = new SumRows(userProfile, fromDate, toDate);
    instance.calculateDayStatus(sumRow);
    assertEquals(false, instance.isMonExist());
    assertEquals(false, instance.isTueExist());
    assertEquals(false, instance.isWedExist());
    assertEquals(false, instance.isThuExist());
    assertEquals(true, instance.isFriExist());
    assertEquals(true, instance.isSatExist());
    assertEquals(true, instance.isSunExist());
  }

  @Test
  public void test20170101() throws XMLBuilderException {
    DBConnect.testMode();
    ITRCalendar fromDate = new ITRCalendar("2017-01-01");
    ITRCalendar toDate = new ITRCalendar("2017-01-01");
    UserProfile userProfile = new UserProfile();
    userProfile.load(1);
    Row instance = new Row(userProfile, fromDate, toDate);
    SumRows sumRow = new SumRows(userProfile, fromDate, toDate);
    instance.calculateDayStatus(sumRow);
    assertEquals(false, instance.isMonExist());
    assertEquals(false, instance.isTueExist());
    assertEquals(false, instance.isWedExist());
    assertEquals(false, instance.isThuExist());
    assertEquals(false, instance.isFriExist());
    assertEquals(false, instance.isSatExist());
    assertEquals(true, instance.isSunExist());
  }

  @Test
  public void test20180101() throws XMLBuilderException {
    DBConnect.testMode();
    ITRCalendar fromDate = new ITRCalendar("2018-01-01");
    ITRCalendar toDate = new ITRCalendar("2018-01-01");
    UserProfile userProfile = new UserProfile();
    userProfile.load(1);
    Row instance = new Row(userProfile, fromDate, toDate);
    SumRows sumRow = new SumRows(userProfile, fromDate, toDate);
    instance.calculateDayStatus(sumRow);
    assertEquals(true, instance.isMonExist());
    assertEquals(true, instance.isTueExist());
    assertEquals(true, instance.isWedExist());
    assertEquals(true, instance.isThuExist());
    assertEquals(true, instance.isFriExist());
    assertEquals(true, instance.isSatExist());
    assertEquals(true, instance.isSunExist());
  }
}
