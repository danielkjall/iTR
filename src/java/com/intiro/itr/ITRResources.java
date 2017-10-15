package com.intiro.itr;

import com.intiro.itr.db.DBConstants;
import com.intiro.itr.db.DBQueriesConfig;
import com.intiro.itr.db.InvocationHandlerSetting;
import com.intiro.itr.util.StringRecordset;

public class ITRResources {

  //DEBUG
  public final static boolean DEBUG = true;
  public final static String ERROR_HANDLER = "error_handler";
  public final static String HTTP_PROTOCOL_STRING = "http://";
  public final static String ITR_ACTIVITY_EDITOR = "activityEditor";
  public final static String ITR_EMAIL_EDITOR = "emailEditor";
  public final static String ITR_MODIFIED_ACTIVITY = "modifiedActivity";
  public final static String ITR_MODIFIED_COMPANY = "modifiedCompany";
  public final static String ITR_MODIFIED_CONTACT = "modifiedContact";
  public final static String ITR_MODIFIED_PROJECT = "modifiedProject";
  public final static String ITR_MODIFIED_PROJECTMEMBERS = "modifiedProjectMembers";
  public final static String ITR_MODIFIED_USER = "modifiedUser";
  public final static String ITR_PHONE_NUMBER_EDITOR = "phoneEditor";
  public final static String ITR_PROJECTACTIVITIES_EDITOR = "modifiedProjectActivities";
  public final static String ITR_PROJECTMEMBERS_EDITOR = "modifiedProjectMembers";
  public final static String ITR_USER_PROFILE = "user_profile";
  public final static String ITR_WEEK_REPORT = "week_report";
  public final static String ITR_WEEK_REPORTS = "week_reports";
  public final static String LOGIN_MESSAGE = "login_message";

  //CONSTANTS FOR SESSION
  public static final String MESSAGE_TO_LOGOUT_USER = "message_to_logout_user";
  public static final String ITR_PERIOD_END_TYPE = "1";

  //SETTINGS
  private static String webITRRoot = null;
  private static String periodEndType = "1";
  private static String itrSkin = null;
  private static String language = "en";
  private static String languageCode = "1";
  private static String protocol = null;
  private static String realITRRoot = null;

  public static String getDefaultFileSeparator() {
    return System.getProperty("file.separator");
  }

  public static void setDefaultLanguage(String lang) {
    language = lang;
  }

  public static String getDefaultLanguage() {
    return language;
  }

  public static void setDefaultLanguageCode(String code) {
    languageCode = code;
  }

  public static String getDefaultLanguageCode() {
    return languageCode;
  }

  public static void setDefaultProtocolForITR(String prot) {
    protocol = prot;
  }

  public static String getDefaultProtocolForITR() {
    return protocol;
  }

  public static void setDefaultRealITRRootDir(String root) {
    realITRRoot = root;
  }

  public static String getDefaultRealITRRootDir() {
    return realITRRoot;
  }

  public static void setDefaultSkin(String skin) {
    itrSkin = skin;
  }

  public static String getDefaultSkin() {
    return itrSkin;
  }

  public static void setDefaultWebITRRootDir(String root) {
    webITRRoot = root;
  }

  public static String getDefaultWebITRRootDir() {
    return webITRRoot;
  }

  public static void setDefaultPeriodEndType(String type) {
    periodEndType = type;
  }

  public static String getDefaultPeriodEndType() {
    return periodEndType;
  }

  public void load() {
    try {
      String cacheKey = getClass().getName() + ".getSettings";
      String statisticKey = getClass().getName() + ".load";
      int cacheTime = 3600*10 ;
      InvocationHandlerSetting s = InvocationHandlerSetting.create(cacheKey, cacheTime, statisticKey);
      StringRecordset rs = DBQueriesConfig.getProxy(s).getSettings();

      if (!rs.getEOF()) {
        setDefaultRealITRRootDir(rs.getField(DBConstants.SETTINGS_REALROOTDIR));
        setDefaultWebITRRootDir(rs.getField(DBConstants.SETTINGS_WEBROOTDIR));
        setDefaultLanguageCode(rs.getField(DBConstants.LANGUAGE_CODE));
        setDefaultLanguage(rs.getField(DBConstants.LANGUAGE_NAME));
        setDefaultProtocolForITR(rs.getField(DBConstants.SETTINGS_PROTCOL));
        setDefaultSkin(rs.getField(DBConstants.SKIN_PATH));
        setDefaultPeriodEndType(rs.getField(DBConstants.PERIOD_END_TYPE));
        rs.moveNext();
      }

      rs.close();
    } catch (Exception e) {
      System.out.println("ERROR: ITRResources.getInstance(): " + e.getMessage());
      e.printStackTrace();
    }
  }
}
