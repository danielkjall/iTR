/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intiro.itr.logic.weekreport;

import com.intiro.itr.util.ITRCalendar;
import java.util.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author daniel
 */
public class WeekReportTest {

    public WeekReportTest() {
    }

  @BeforeClass
  public static void setUpClass() throws Exception {
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
  }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

  /**
   * Test of setEditRow method, of class WeekReport.
   */
  @Test
  public void testSetEditRow() {
    System.out.println("setEditRow");
    EditRow editRow = null;
    WeekReport instance = null;
    instance.setEditRow(editRow);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of getEditRow method, of class WeekReport.
   */
  @Test
  public void testGetEditRow() {
    System.out.println("getEditRow");
    WeekReport instance = null;
    EditRow expResult = null;
    EditRow result = instance.getEditRow();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of getFromDate method, of class WeekReport.
   */
  @Test
  public void testGetFromDate() {
    System.out.println("getFromDate");
    WeekReport instance = null;
    ITRCalendar expResult = null;
    ITRCalendar result = instance.getFromDate();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of isLoaded method, of class WeekReport.
   */
  @Test
  public void testIsLoaded() {
    System.out.println("isLoaded");
    WeekReport instance = null;
    boolean expResult = false;
    boolean result = instance.isLoaded();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of getRow method, of class WeekReport.
   */
  @Test
  public void testGetRow() {
    System.out.println("getRow");
    int index = 0;
    WeekReport instance = null;
    Row expResult = null;
    Row result = instance.getRow(index);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of getRows method, of class WeekReport.
   */
  @Test
  public void testGetRows() {
    System.out.println("getRows");
    WeekReport instance = null;
    Vector expResult = null;
    Vector result = instance.getRows();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of isSaved method, of class WeekReport.
   */
  @Test
  public void testIsSaved() {
    System.out.println("isSaved");
    WeekReport instance = null;
    boolean expResult = false;
    boolean result = instance.isSaved();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of setSubmitErrorOccurred method, of class WeekReport.
   */
  @Test
  public void testSetSubmitErrorOccurred() {
    System.out.println("setSubmitErrorOccurred");
    boolean status = false;
    WeekReport instance = null;
    instance.setSubmitErrorOccurred(status);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of isSubmittedFromDB method, of class WeekReport.
   */
  @Test
  public void testIsSubmittedFromDB() {
    System.out.println("isSubmittedFromDB");
    WeekReport instance = null;
    boolean expResult = false;
    boolean result = instance.isSubmittedFromDB();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of isSubmittedFromUser method, of class WeekReport.
   */
  @Test
  public void testIsSubmittedFromUser() {
    System.out.println("isSubmittedFromUser");
    WeekReport instance = null;
    boolean expResult = false;
    boolean result = instance.isSubmittedFromUser();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of getSumRows method, of class WeekReport.
   */
  @Test
  public void testGetSumRows() {
    System.out.println("getSumRows");
    WeekReport instance = null;
    SumRows expResult = null;
    SumRows result = instance.getSumRows();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of getToDate method, of class WeekReport.
   */
  @Test
  public void testGetToDate() {
    System.out.println("getToDate");
    WeekReport instance = null;
    ITRCalendar expResult = null;
    ITRCalendar result = instance.getToDate();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of setWeekComment method, of class WeekReport.
   */
  @Test
  public void testSetWeekComment() {
    System.out.println("setWeekComment");
    String comment = "";
    WeekReport instance = null;
    instance.setWeekComment(comment);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of getWeekComment method, of class WeekReport.
   */
  @Test
  public void testGetWeekComment() {
    System.out.println("getWeekComment");
    WeekReport instance = null;
    String expResult = "";
    String result = instance.getWeekComment();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of setWeekCommentId method, of class WeekReport.
   */
  @Test
  public void testSetWeekCommentId() {
    System.out.println("setWeekCommentId");
    int id = 0;
    WeekReport instance = null;
    instance.setWeekCommentId(id);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of getWeekCommentId method, of class WeekReport.
   */
  @Test
  public void testGetWeekCommentId() {
    System.out.println("getWeekCommentId");
    WeekReport instance = null;
    int expResult = 0;
    int result = instance.getWeekCommentId();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of setWeekReportId method, of class WeekReport.
   */
  @Test
  public void testSetWeekReportId() {
    System.out.println("setWeekReportId");
    int id = 0;
    WeekReport instance = null;
    instance.setWeekReportId(id);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of getWeekReportId method, of class WeekReport.
   */
  @Test
  public void testGetWeekReportId() {
    System.out.println("getWeekReportId");
    WeekReport instance = null;
    int expResult = 0;
    int result = instance.getWeekReportId();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of addRow method, of class WeekReport.
   */
  @Test
  public void testAddRow() {
    System.out.println("addRow");
    Row newRow = null;
    WeekReport instance = null;
    instance.addRow(newRow);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of approve method, of class WeekReport.
   */
  @Test
  public void testApprove() throws Exception {
    System.out.println("approve");
    WeekReport instance = null;
    instance.approve();
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of checkIfOkToSubmit method, of class WeekReport.
   */
  @Test
  public void testCheckIfOkToSubmit() {
    System.out.println("checkIfOkToSubmit");
    WeekReport instance = null;
    boolean expResult = false;
    boolean result = instance.checkIfOkToSubmit();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of clear method, of class WeekReport.
   */
  @Test
  public void testClear() throws Exception {
    System.out.println("clear");
    WeekReport instance = null;
    instance.clear();
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of equals method, of class WeekReport.
   */
  @Test
  public void testEquals() {
    System.out.println("equals");
    WeekReport oneWeekReport = null;
    WeekReport instance = null;
    boolean expResult = false;
    boolean result = instance.equals(oneWeekReport);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of load method, of class WeekReport.
   */
  @Test
  public void testLoad() throws Exception {
    System.out.println("load");
    WeekReport instance = null;
    instance.load();
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of loadWeekWithLatestSubmittedWeek method, of class WeekReport.
   */
  @Test
  public void testLoadWeekWithLatestSubmittedWeek() throws Exception {
    System.out.println("loadWeekWithLatestSubmittedWeek");
    WeekReport instance = null;
    instance.loadWeekWithLatestSubmittedWeek();
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of reject method, of class WeekReport.
   */
  @Test
  public void testReject() throws Exception {
    System.out.println("reject");
    WeekReport instance = null;
    instance.reject();
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of removeRow method, of class WeekReport.
   */
  @Test
  public void testRemoveRow() {
    System.out.println("removeRow");
    int index = 0;
    WeekReport instance = null;
    instance.removeRow(index);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of save method, of class WeekReport.
   */
  @Test
  public void testSave() throws Exception {
    System.out.println("save");
    WeekReport instance = null;
    instance.save();
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of saveErrorOccurred method, of class WeekReport.
   */
  @Test
  public void testSaveErrorOccurred() {
    System.out.println("saveErrorOccurred");
    WeekReport instance = null;
    boolean expResult = false;
    boolean result = instance.saveErrorOccurred();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of submit method, of class WeekReport.
   */
  @Test
  public void testSubmit() throws Exception {
    System.out.println("submit");
    WeekReport instance = null;
    instance.submit();
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of submitErrorOccurred method, of class WeekReport.
   */
  @Test
  public void testSubmitErrorOccurred() {
    System.out.println("submitErrorOccurred");
    WeekReport instance = null;
    boolean expResult = false;
    boolean result = instance.submitErrorOccurred();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of toString method, of class WeekReport.
   */
  @Test
  public void testToString() {
    System.out.println("toString");
    WeekReport instance = null;
    String expResult = "";
    String result = instance.toString();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of toXML method, of class WeekReport.
   */
  @Test
  public void testToXML() throws Exception {
    System.out.println("toXML");
    StringBuffer xmlDoc = null;
    WeekReport instance = null;
    instance.toXML(xmlDoc);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of toXMLSummary method, of class WeekReport.
   */
  @Test
  public void testToXMLSummary() throws Exception {
    System.out.println("toXMLSummary");
    StringBuffer xmlDoc = null;
    int weekIndex = 0;
    WeekReport instance = null;
    instance.toXMLSummary(xmlDoc, weekIndex);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of setSumRows method, of class WeekReport.
   */
  @Test
  public void testSetSumRows() {
    System.out.println("setSumRows");
    SumRows sumRows = null;
    WeekReport instance = null;
    instance.setSumRows(sumRows);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of calculateDaysStatus method, of class WeekReport.
   */
  @Test
  public void testCalculateDaysStatus() {
    System.out.println("calculateDaysStatus");
    WeekReport instance = null;
    instance.calculateDaysStatus();
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of setLoaded method, of class WeekReport.
   */
  @Test
  public void testSetLoaded() {
    System.out.println("setLoaded");
    WeekReport instance = null;
    instance.setLoaded();
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of getOvertimeMoneyHoursForWeek method, of class WeekReport.
   */
  @Test
  public void testGetOvertimeMoneyHoursForWeek() {
    System.out.println("getOvertimeMoneyHoursForWeek");
    WeekReport instance = null;
    double expResult = 0.0;
    double result = instance.getOvertimeMoneyHoursForWeek();
    assertEquals(expResult, result, 0.0);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of getOvertimeVacationHoursForWeek method, of class WeekReport.
   */
  @Test
  public void testGetOvertimeVacationHoursForWeek() {
    System.out.println("getOvertimeVacationHoursForWeek");
    WeekReport instance = null;
    double expResult = 0.0;
    double result = instance.getOvertimeVacationHoursForWeek();
    assertEquals(expResult, result, 0.0);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of setRows method, of class WeekReport.
   */
  @Test
  public void testSetRows() {
    System.out.println("setRows");
    Vector<Row> rows = null;
    WeekReport instance = null;
    instance.setRows(rows);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of setSaveErrorOccrred method, of class WeekReport.
   */
  @Test
  public void testSetSaveErrorOccrred() {
    System.out.println("setSaveErrorOccrred");
    WeekReport instance = null;
    instance.setSaveErrorOccrred();
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of setSaved method, of class WeekReport.
   */
  @Test
  public void testSetSaved() {
    System.out.println("setSaved");
    WeekReport instance = null;
    instance.setSaved();
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of setSubmittedFromDB method, of class WeekReport.
   */
  @Test
  public void testSetSubmittedFromDB() {
    System.out.println("setSubmittedFromDB");
    String status = "";
    WeekReport instance = null;
    instance.setSubmittedFromDB(status);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of setSubmittedFromUser method, of class WeekReport.
   */
  @Test
  public void testSetSubmittedFromUser() {
    System.out.println("setSubmittedFromUser");
    WeekReport instance = null;
    instance.setSubmittedFromUser();
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of getMode method, of class WeekReport.
   */
  @Test
  public void testGetMode() {
    System.out.println("getMode");
    WeekReport instance = null;
    String expResult = "";
    String result = instance.getMode();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of setMode method, of class WeekReport.
   */
  @Test
  public void testSetMode() {
    System.out.println("setMode");
    String mode = "";
    WeekReport instance = null;
    instance.setMode(mode);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

}