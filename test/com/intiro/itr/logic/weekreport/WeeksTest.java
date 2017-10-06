/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intiro.itr.logic.weekreport;

import com.intiro.itr.util.personalization.UserProfile;
import java.util.ArrayList;
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
public class WeeksTest {

  public WeeksTest() {
  }
  UserProfile userProfileSmall = null;
  UserProfile userProfileBig = null;

  @BeforeClass
  public static void setUpClass() throws Exception {
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
  }

  @Before
  public void setUp() throws Exception {
    loadUserProfileSmall();
    loadUserProfileBig();
  }

  @After
  public void tearDown() {
  }

  /**
   * Test of load method, of class Weeks.
   */
  @Test
  public void testLoadSubmittedSmallUser() throws Exception {
    System.out.println("testLoadSubmittedSmallUser");

    String mode = "submitted";
    Weeks weeks = new Weeks(userProfileSmall, mode);
    weeks.load("");

    assertEquals(3, weeks.getWeekReports().size());
  }

  /**
   * Test of load method, of class Weeks.
   */
  @Test
  public void testLoadSubmittedBigUser() throws Exception {
    System.out.println("testLoadSubmittedBigUser");

    String mode = "submitted";
    Weeks weeks = new Weeks(userProfileBig, mode);
    weeks.load("");

    assertEquals(481, weeks.getWeekReports().size());
  }

  @Test
  public void testLoadSubmittedPerformanceBigUser() throws Exception {
    System.out.println("testLoadSubmittedPerformanceBigUser");

    String mode = "submitted";
    Weeks weeks = new Weeks(userProfileBig, mode);
    weeks.load("2009");

    assertEquals(63, weeks.getWeekReports().size());
  }

  @Test
  public void testLoadSubmittedPerformanceSmallUser() throws Exception {
    System.out.println("testLoadSubmittedPerformanceSmallUser");

    String mode = "submitted";
    Weeks weeks = new Weeks(userProfileSmall, mode);
    weeks.load("");

    assertEquals(3, weeks.getWeekReports().size());
  }

  private void loadUserProfileSmall() throws Exception {
    userProfileSmall = new UserProfile();
    userProfileSmall.setPassword("a");
    userProfileSmall.setLoginId("a");
    userProfileSmall.login();
  }

  private void loadUserProfileBig() throws Exception {
    userProfileBig = new UserProfile();
    userProfileBig.setPassword("lsnrd1");
    userProfileBig.setLoginId("daniel");
    userProfileBig.login();
  }

  /**
   * Test of toXML method, of class Weeks.
   */
  @Test
  public void testToXML() throws Exception {
    System.out.println("toXML");
  }

  /**
   * Test of getWeekReports method, of class Weeks.
   */
  @Test
  public void testGetWeekReports() {
    System.out.println("getWeekReports");
  }




    @Test
  public void testLoadTodoBigUser() throws Exception {
    System.out.println("testLoadTodoBigUser");

    String mode = "todo";
    Weeks weeks = new Weeks(userProfileBig, mode);
    weeks.load("");

    assertEquals(3, weeks.getWeekReports().size());
  }

  @Test
  public void testLoadTodoPerformanceBigUser() throws Exception {
    System.out.println("testLoadTodoPerformanceBigUser");

    String mode = "todo";
    Weeks weeks = new Weeks(userProfileBig, mode);
    weeks.load("");

    assertEquals(3, weeks.getWeekReports().size());
  }

}
