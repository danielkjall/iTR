/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Olof Altenstedt
 * @version       1.0
 */
package com.intiro.itr.logic.generatereport.monthly;

import com.intiro.itr.util.ITRCalendar;
import com.intiro.itr.util.combos.MonthsCombo;
import com.intiro.itr.util.combos.ProjectCodesCombo;
import com.intiro.itr.util.combos.ProjectsCombo;
import com.intiro.itr.util.combos.UsersCombo;
import com.intiro.itr.util.combos.YearsCombo;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.DynamicXMLCarrier;
import com.intiro.itr.util.xml.XMLBuilder;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.log.IntiroLog;

public class QueryProfile extends DynamicXMLCarrier {

  //~ Instance/static variables ........................................................................................

  static final String XML_MONTH_END = "</month>";
  static final String XML_MONTH_START = "<month>";
  static final String XML_PROJECTCODE_END = "</projectcode>";
  static final String XML_PROJECTCODE_START = "<projectcode>";
  static final String XML_PROJECT_END = "</project>";
  static final String XML_PROJECT_START = "<project>";
  static final String XML_ROLE_END = "</role>";
  static final String XML_ROLE_START = "<role>";
  static final String XML_USERID_END = "</userid>";
  static final String XML_USERID_START = "<userid>";
  static final String XML_USERNAME_END = "</username>";
  static final String XML_USERNAME_START = "<username>";
  static final String XML_USER_END = "</user>";
  static final String XML_USER_START = "<user>";
  static final String XML_YEAR_END = "</year>";
  static final String XML_YEAR_START = "<year>";
  protected ProjectCodesCombo codesCombo = null;
  protected MonthsCombo monthsCombo = null;
  protected ProjectsCombo projectsCombo = null;
  protected UsersCombo usersCombo = null;
  protected YearsCombo yearsCombo = null;

  //~ Constructors .....................................................................................................

  public QueryProfile(UserProfile profile) throws XMLBuilderException {
    super(profile);

    //Projects
    projectsCombo = new ProjectsCombo(userProfile, "All projects");
    projectsCombo.load();
    projectsCombo.setStartEndTags(XML_PROJECT_START, XML_PROJECT_END);

    //Project codes
    codesCombo = new ProjectCodesCombo(userProfile, "All project codes");
    codesCombo.load();
    codesCombo.setStartEndTags(XML_PROJECTCODE_START, XML_PROJECTCODE_END);

    //Users
    usersCombo = null;
    if(getUserProfile().getRole().isAdmin() || getUserProfile().getRole().isSuperAdmin())
      usersCombo = new UsersCombo(userProfile, "All users", true);
    else
      usersCombo = new UsersCombo(userProfile, false, true);
    usersCombo.load(getUserProfile().getUserId(), false);
    usersCombo.setStartEndTags(XML_USER_START, XML_USER_END);

    //Months
    monthsCombo = new MonthsCombo(userProfile, "All months");
    monthsCombo.load("null");
    monthsCombo.setStartEndTags(XML_MONTH_START, XML_MONTH_END);

    ITRCalendar now = new ITRCalendar();
    yearsCombo = new YearsCombo(userProfile, false);
    yearsCombo.load("" + now.getYear());
    yearsCombo.setStartEndTags(XML_YEAR_START, XML_YEAR_END);
  }

  //~ Methods ..........................................................................................................

  /**
   * This is the method that will produce the XML.
   * It will fill the xmlDoc with XML.
   * @param    xmlDoc a StringBuffer to be filled with xml.
   */
  public void toXML(StringBuffer xmlDoc) throws Exception {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer): entered");
    }

    XMLBuilder builder = new XMLBuilder();

    /*Get start of document*/
    builder.getStartOfDocument(xmlDoc);

    /*Users role*/
    xmlDoc.append(XML_ROLE_START);
    xmlDoc.append(getUserProfile().getRole().getName());
    xmlDoc.append(XML_ROLE_END);

    //User
    if (getUserProfile().getRole().isAdmin() || getUserProfile().getRole().isSuperAdmin()) {
      usersCombo.toXML(xmlDoc);
    }
    else { //Employees can only see report on them self.
      xmlDoc.append(XML_USER_START);
      xmlDoc.append(XML_USERID_START);
      xmlDoc.append(getUserProfile().getUserId());
      xmlDoc.append(XML_USERID_END);
      xmlDoc.append(XML_USERNAME_START);
      xmlDoc.append(getUserProfile().getFirstName() + " " + getUserProfile().getLastName());
      xmlDoc.append(XML_USERNAME_END);
      xmlDoc.append(XML_USER_END);
    }

    //Project
    projectsCombo.toXML(xmlDoc);

    //Project codes
    codesCombo.toXML(xmlDoc);

    //Year
    yearsCombo.toXML(xmlDoc);

    //Months
    monthsCombo.toXML(xmlDoc);

    /*Get end of document*/
    builder.getEndOfDocument(xmlDoc);

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer): xmlDoc = " + xmlDoc.toString());
    }
  }
  /**
   * @return Returns the codesCombo.
   */
  public ProjectCodesCombo getCodesCombo() {
    return codesCombo;
  }
  /**
   * @return Returns the monthsCombo.
   */
  public MonthsCombo getMonthsCombo() {
    return monthsCombo;
  }
  /**
   * @return Returns the projectsCombo.
   */
  public ProjectsCombo getProjectsCombo() {
    return projectsCombo;
  }
  /**
   * @return Returns the usersCombo.
   */
  public UsersCombo getUsersCombo() {
    return usersCombo;
  }
  /**
   * @return Returns the yearsCombo.
   */
  public YearsCombo getYearsCombo() {
    return yearsCombo;
  }
}