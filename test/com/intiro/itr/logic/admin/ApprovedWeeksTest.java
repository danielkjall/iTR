package com.intiro.itr.logic.admin;

import com.intiro.itr.db.DBConnect;
import com.intiro.itr.util.personalization.UserProfile;
import java.util.Map;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ApprovedWeeksTest {

  public ApprovedWeeksTest() {
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
  public void testLoad() throws Exception {
    DBConnect.testMode();
    Map<String, UserProfile> mapUserProfiles = UserProfile.loadAllUserProfiles();
    String year = "2017";
    String userId = "";
    ApprovedWeeks instance = new ApprovedWeeks(mapUserProfiles.get("1"), "2017");
    int expResult = 977;
    ArrayList result = instance.load(year, userId);
    assertEquals(expResult, result.size());
  }

  @Test
  public void testLoadPerformance() throws Exception {
    DBConnect.testMode();
    Map<String, UserProfile> mapUserProfiles = UserProfile.loadAllUserProfiles();
    String year = "2017";
    ApprovedWeeks instance = new ApprovedWeeks(mapUserProfiles.get("1"), "2017");
    int expResult = 977;
    ArrayList result = instance.loadPerformance(year);
    assertEquals(expResult, result.size());
  }
}
