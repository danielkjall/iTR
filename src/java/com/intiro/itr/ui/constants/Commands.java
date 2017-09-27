/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.ui.constants;

/**
 * This class holds all the commands that are
 * sent down to the OM-system through
 * OMSystem.setData() and OMSystem.getData().
 */
public interface Commands {
  //~ Instance/static variables ........................................................................................

  public final static String REAUTHENTICATE_MESSAGE = "No Session - reauthenticate";
  public final static String XSLFIELD_COMPANYID = "companyid";

  //UserProfileChangerActivator
  public final static String XSLFIELD_FIRSTNAME = "firstName";
  public final static String XSLFIELD_FROMDATE = "fromdate";
  public final static String XSLFIELD_LANGUAGE = "language";
  public final static String XSLFIELD_LASTNAME = "lastName";

  //LoginView
  public final static String XSLFIELD_LOGIN_ID = "loginId";
  public final static String XSLFIELD_MONTH = "month";
  public final static String XSLFIELD_NEW_LOGINID = "newLoginId";

  //PasswordChangerActivator
  public final static String XSLFIELD_NEW_PASSWORD = "newPassword";
  public final static String XSLFIELD_PASSWORD = "password";
  public final static String XSLFIELD_PROJECTCODEID = "projectcodeid";
  public final static String XSLFIELD_PROJECTID = "projectid";
  public final static String XSLFIELD_SKIN = "skin";
  public final static String XSLFIELD_TODATE = "todate";

  //ReportView
  public final static String XSLFIELD_USERID = "userid";
  public final static String XSLFIELD_YEAR = "year";
}