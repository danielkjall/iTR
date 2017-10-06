package com.intiro.itr.logic.generatereport.vacation;

import com.intiro.itr.util.*;
import com.intiro.itr.util.combos.CompanyCombo;
import com.intiro.itr.util.combos.ProjectsCombo;
import com.intiro.itr.util.combos.UsersCombo;
import com.intiro.itr.util.combos.YearsCombo;
import com.intiro.itr.util.personalization.*;
import com.intiro.itr.util.xml.*;

import com.intiro.itr.util.log.IntiroLog;

public class QueryProfile extends DynamicXMLCarrier {

  static final String XML_COMPANY_END = "</company>";
  static final String XML_COMPANY_START = "<company>";
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
  protected CompanyCombo companyCombo = null;
  protected ProjectsCombo projectsCombo = null;
  protected UsersCombo usersCombo = null;
  protected YearsCombo yearsCombo = null;

  public QueryProfile(UserProfile profile) throws XMLBuilderException {
    super(profile);

    //Projects
    projectsCombo = new ProjectsCombo(userProfile);
    projectsCombo.load();
    projectsCombo.setStartEndTags(XML_PROJECT_START, XML_PROJECT_END);

    //Project codes
    companyCombo = new CompanyCombo(userProfile);
    companyCombo.load();
    companyCombo.setStartEndTags(XML_COMPANY_START, XML_COMPANY_END);

    //Users
    usersCombo = new UsersCombo(userProfile, true);
    usersCombo.load(getUserProfile().getUserId());
    usersCombo.setStartEndTags(XML_USER_START, XML_USER_END);

    //Months
    ITRCalendar now = new ITRCalendar();
    yearsCombo = new YearsCombo(userProfile, false);
    yearsCombo.load("" + now.getYear());
    yearsCombo.setStartEndTags(XML_YEAR_START, XML_YEAR_END);
  }
  //~ Methods ..........................................................................................................

  /**
   * This is the method that will produce the XML. It will fill the xmlDoc with XML.
   *
   * @param xmlDoc a StringBuffer to be filled with xml.
   */
  public void toXML(StringBuffer xmlDoc)
          throws Exception {
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
    } else { //Employees can only see report on them self.
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

    //Company
    companyCombo.toXML(xmlDoc);

    //Year
    yearsCombo.toXML(xmlDoc);

    //Get end of document
    builder.getEndOfDocument(xmlDoc);

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer): xmlDoc = " + xmlDoc.toString());
    }
  }
}
