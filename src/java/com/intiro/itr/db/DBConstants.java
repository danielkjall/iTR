/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.db;

public interface DBConstants {
  //~ Instance/static variables ........................................................................................

  public static final String CALENDARWEEK_EXPECTEDHOURSFR = "ExpectedHoursFr";
  public static final String CALENDARWEEK_EXPECTEDHOURSMO = "ExpectedHoursMo";
  public static final String CALENDARWEEK_EXPECTEDHOURSSA = "ExpectedHoursSa";
  public static final String CALENDARWEEK_EXPECTEDHOURSSU = "ExpectedHoursSu";
  public static final String CALENDARWEEK_EXPECTEDHOURSSUM = "ExpectedHoursSum";
  public static final String CALENDARWEEK_EXPECTEDHOURSTH = "ExpectedHoursTh";
  public static final String CALENDARWEEK_EXPECTEDHOURSTU = "ExpectedHoursTu";
  public static final String CALENDARWEEK_EXPECTEDHOURSWE = "ExpectedHoursWe";
  public static final String CALENDARWEEK_FROM_DATE = "FromDate";
  public static final String CALENDARWEEK_ID_PK = "Id";
  public static final String CALENDARWEEK_TO_DATE = "ToDate";
  public static final String CALENDARWEEK_WEEKNO = "WeekNo";
  public static final String CALENDARWEEK_WEEKPART = "WeekPart";
  public static final String COMMA = ", ";
  public static final String COMPANYSTRUCTURE_CHILDID_PK = "ITR_CompanyId_Child";
  public static final String COMPANYSTRUCTURE_ID_PK = "Id";
  public static final String COMPANYSTRUCTURE_PARENTID_PK = "ITR_CompanyId_Parent";
  public static final String COMPANYUNITS_COMPANYID_FK = "ITR_CompanyId";
  public static final String COMPANYUNITS_FATHER_UNITSID_FK = "ITR_Father_UnitsId";
  public static final String COMPANYUNITS_ID_PK = "Id";
  public static final String COMPANYUNITS_INVOICEID_FK = "ITR_InvoiceId";
  public static final String COMPANYUNITS_NAME = "Name";
  public static final String COMPANY_ID_PK = "Id";
  public static final String COMPANY_INVOICEADDRESS_ROW1 = "InvoiceAdress_Row1";
  public static final String COMPANY_INVOICEADDRESS_ROW2 = "InvoiceAdress_Row2";
  public static final String COMPANY_INVOICEADDRESS_ROW3 = "InvoiceAdress_Row3";
  public static final String COMPANY_NAME = "Name";
  public static final String COMPANY_VISITADDRESS_ROW1 = "VisitAdress_Row1";
  public static final String COMPANY_VISITADDRESS_ROW2 = "VisitAdress_Row2";
  public static final String COMPANY_VISITADDRESS_ROW3 = "VisitAdress_Row3";
  public static final String EMAIL_CONTACTID = "ContactId";
  public static final String EMAIL_DESCRIPTION = "Description";
  public static final String EMAIL_EMAIL = "Email";
  public static final String EMAIL_ID_PK = "Id";
  public static final String EMAIL_USERID_FK = "ITR_UserId";
  public static final String ENTRYROW_FR_HOURS = "HoursFr";
  public static final String ENTRYROW_HOURS_SUM = "HoursSum";
  public static final String ENTRYROW_ID_PK = "Id";
  public static final String ENTRYROW_MO_HOURS = "HoursMo";
  public static final String ENTRYROW_PROJECTCODEID_FK = "ITR_ProjectCodeId";
  public static final String ENTRYROW_PROJECTID_FK = "ITR_ProjectId";
  public static final String ENTRYROW_SA_HOURS = "HoursSa";
  public static final String ENTRYROW_SU_HOURS = "HoursSu";
  public static final String ENTRYROW_TH_HOURS = "HoursTh";
  public static final String ENTRYROW_TIMETYPEID_FK = "ITR_TimeTypeId";
  public static final String ENTRYROW_TU_HOURS = "HoursTu";
  public static final String ENTRYROW_USERID_FK = "ITR_UserId";
  public static final String ENTRYROW_USERWEEKID_FK = "ITR_UserWeekId";
  public static final String ENTRYROW_WE_HOURS = "HoursWe";
  public static final String FALSE_ACCESS = " False ";
  public static final String LANGUAGE_CODE = "Code";
  public static final String LANGUAGE_ID_PK = "Id";
  public static final String LANGUAGE_NAME = "Name";
  public static final String MODULE_ID_PK = "Id";
  public static final String MODULE_MODULE = "Module";
  public static final String MODULE_ROLESID_FK = "ITR_RolesId";
  public static final String PHONECOUNTRYCODE_COUNTRYCODE = "CountryCode";
  public static final String PHONECOUNTRYCODE_COUNTRYNAME = "CountryName";
  public static final String PHONECOUNTRYCODE_ID_PK = "Id";
  public static final String PHONEREGIONCODE_ID_PK = "Id";
  public static final String PHONEREGIONCODE_PHONECOUNTRYCODEID_FK = "PhoneCountryCodeId";
  public static final String PHONEREGIONCODE_REGIONCODE = "RegionCode";
  public static final String PHONEREGIONCODE_REGIONNAME = "RegionName";
  public static final String PHONE_CONTACTID_FK = "ContactId";
  public static final String PHONE_DESCRIPTION = "Description";
  public static final String PHONE_ID_PK = "Id";
  public static final String PHONE_PHONENUMBER = "PhoneNumber";
  public static final String PHONE_REGIONID_FK = "RegionId";
  public static final String PHONE_USERID_FK = "ITR_UserId";
  public static final String PROJECTCODES_ID_PK = "Id";
  public static final String PROJECTCODES_PROJECTCODEID_FK = "ITR_ProjectCodeId";
  public static final String PROJECTCODES_PROJECTID_FK = "ITR_ProjectId";
  public static final String PROJECTCODE_CODE = "Code";
  public static final String PROJECTCODE_DESCRIPTION = "Description";
  public static final String PROJECTCODE_ID_PK = "Id";
  public static final String PROJECTMEMBERS_ID_PK = "Id";
  public static final String PROJECTMEMBERS_PROJECTADMIN = "ProjectAdmin";
  public static final String PROJECTMEMBERS_PROJECTID_FK = "ITR_ProjectId";
  public static final String PROJECTMEMBERS_RATE = "Rate";
  public static final String PROJECTMEMBERS_USERID_FK = "ITR_UserId";
  public static final String PROJECT_ACTIVE = "Active";
  public static final String PROJECT_ADMINPROJECT = "AdminProject";
  public static final String PROJECT_COMPANYID_FK = "ITR_CompanyId";
  public static final String PROJECT_CONTRACT = "Contract";
  public static final String PROJECT_DESCRIPTION = "Description";
  public static final String PROJECT_FROMDATE = "FromDate";
  public static final String PROJECT_ID_PK = "Id";
  public static final String PROJECT_MAINCODE = "MainCode";
  public static final String PROJECT_NAME = "Name";
  public static final String PROJECT_TECHNIQUE = "Technique";
  public static final String PROJECT_TODATE = "ToDate";
  public static final String ROLES_ID_PK = "Id";
  public static final String ROLES_NAME = "Name";
  public static final String SETTINGS_ID_PK = "Id";
  public static final String SETTINGS_LANGUAGEID_FK = "ITR_languageId";
  public static final String SETTINGS_PROTCOL = "WebProtocol";
  public static final String SETTINGS_REALROOTDIR = "RealRootDir";
  public static final String SETTINGS_SKINID_FK = "ITR_SkinId";
  public static final String SETTINGS_WEBROOTDIR = "WebRootDir";

  /*Common characters*/
  public static final String SINGLE_QUOTE = "'";
  public static final String SKIN_DESCRIPTION = "Description";
  public static final String SKIN_ID_PK = "Id";
  public static final String SKIN_NAME = "Name";
  public static final String SKIN_PATH = "Path";
  public static final String SKIN_THUMBNAILPATH = "ThumbnailPath";
  
  public static final String PERIOD_END_TYPE = "PeriodEndType";

  /*ITR_CALENDARWEEK table*/
  public static final String TABLE_CALENDARWEEK = "ITR_CalendarWeek";
  public static final String TABLE_CALENDARWEEK_DOT = "ITR_CalendarWeek.";

  /*ITR_COMPANY table*/
  public static final String TABLE_COMPANY = "ITR_Company";

  /*ITR_COMPANYSTRUCTURE table*/
  public static final String TABLE_COMPANYSTRUCTURE = "ITR_CompanyStructure";
  public static final String TABLE_COMPANYSTRUCTURE_DOT = "ITR_CompanyStructure.";

  /*ITR_COMPLEMENTS table*/
  public static final String TABLE_COMPANYUNITS = "ITR_CompanyUnits";
  public static final String TABLE_COMPANYUNITS_DOT = "ITR_CompanyUnits.";
  public static final String TABLE_COMPANY_DOT = "ITR_Company.";

  /*EMAIL table*/
  public static final String TABLE_EMAIL = "Email";
  public static final String TABLE_EMAIL_DOT = "Email.";

  /*ITR_ENTRYROW table*/
  public static final String TABLE_ENTRYROW = "ITR_EntryRow";
  public static final String TABLE_ENTRYROW_DOT = "ITR_EntryRow.";

  /*ITR_LANGUAGE table*/
  public static final String TABLE_LANGUAGE = "ITR_Language";
  public static final String TABLE_LANGUAGE_DOT = "ITR_Language.";

  /*ITR_MODULE table*/
  public static final String TABLE_MODULE = "ITR_Module";
  public static final String TABLE_MODULE_DOT = "ITR_Module.";

  /*PHONE table*/
  public static final String TABLE_PHONE = "Phone";

  /*PHONECOUNTRYCODE table*/
  public static final String TABLE_PHONECOUNTRYCODE = "PhoneCountryCode";
  public static final String TABLE_PHONECOUNTRYCODE_DOT = "PhoneCountryCode.";

  /*PHONEREGIONCODE table*/
  public static final String TABLE_PHONEREGIONCODE = "PhoneRegionCode";
  public static final String TABLE_PHONEREGIONCODE_DOT = "PhoneRegionCode.";
  public static final String TABLE_PHONE_DOT = "Phone.";

  /*ITR_PROJECT table*/
  public static final String TABLE_PROJECT = "ITR_Project";

  /*ITR_PROJECTCODE table*/
  public static final String TABLE_PROJECTCODE = "ITR_ProjectCode";

  /*ITR_PROJECTCODES table*/
  public static final String TABLE_PROJECTCODES = "ITR_PROJECTCODES";
  public static final String TABLE_PROJECTCODES_DOT = "ITR_PROJECTCODES.";
  public static final String TABLE_PROJECTCODE_DOT = "ITR_ProjectCode.";

  /*ITR_PROJECTMEMBERS table*/
  public static final String TABLE_PROJECTMEMBERS = "ITR_ProjectMembers";
  public static final String TABLE_PROJECTMEMBERS_DOT = "ITR_ProjectMembers.";
  public static final String TABLE_PROJECT_DOT = "ITR_Project.";

  /*ITR_ROLES table*/
  public static final String TABLE_ROLES = "ITR_Roles";
  public static final String TABLE_ROLES_DOT = "ITR_Roles.";

  /*ITR_SETTINGS table*/
  public static final String TABLE_SETTINGS = "ITR_Settings";
  public static final String TABLE_SETTINGS_DOT = "ITR_Settings.";

  /*ITR_SKIN table*/
  public static final String TABLE_SKIN = "ITR_Skin";
  public static final String TABLE_SKIN_DOT = "ITR_Skin.";

  /*ITR_TIMETYPE table*/
  public static final String TABLE_TIMETYPE = "ITR_TimeType";
  public static final String TABLE_TIMETYPE_DOT = "ITR_TimeType.";

  /*ITR_USER table*/
  public static final String TABLE_USER = "ITR_User";

  /*ITR_USERROLES table*/
  public static final String TABLE_USERROLES = "ITR_UserRoles";
  public static final String TABLE_USERROLES_DOT = "ITR_UserRoles.";

  /*ITR_USERWEEK table*/
  public static final String TABLE_USERWEEK = "ITR_UserWeek";
  public static final String TABLE_USERWEEK_DOT = "ITR_UserWeek.";
  public static final String TABLE_USER_DOT = "ITR_User.";
  public static final String TIMETYPE_DESCRIPTION = "Description";
  public static final String TIMETYPE_ID_PK = "Id";
  public static final String TIMETYPE_TYPE = "Type";
  public static final String TRUE_ACCESS = " True ";
  public static final String USERROLES_ID_PK = "Id";
  public static final String USERROLES_ROLESID_FK = "ITR_RolesId";
  public static final String USERROLES_USERID_FK = "ITR_UserId";
  public static final String USERWEEK_APPROVED = "Approved";
  public static final String USERWEEK_APPROVEDDATE = "ApprovedDate";
  public static final String USERWEEK_CALENDARWEEK_ID_FK = "ITR_CalendarWeekId";
  public static final String USERWEEK_COMMENT = "Comment";
  public static final String USERWEEK_ID_PK = "Id";
  public static final String USERWEEK_SUBMITTED = "Submitted";
  public static final String USERWEEK_SUBMITTEDDATE = "SubmittedDate";
  public static final String USER_ACTIVATED = "Activated";
  public static final String USER_ACTIVATED_DATE = "ActivatedDate";
  public static final String USER_ADMIN = "Admin";
  public static final String USER_COMPANYID_FK = "ITR_CompanyId";
  public static final String USER_CREATED_DATE = "CreatedDate";
  public static final String USER_DEACTIVATED_DATE = "DeactivatedDate";
  public static final String USER_FIRSTNAME = "FirstName";
  public static final String USER_ID_PK = "Id";
  public static final String USER_LANGUAGEID_FK = "ITR_LanguageId";
  public static final String USER_LASTNAME = "LastName";
  public static final String USER_LOGINID = "LoginId";
  public static final String USER_OVERTIME_MONEY_HOURS = "MoneyOvertimeHours";
  public static final String USER_OVERTIME_VACATION_HOURS = "VacationOvertimeHours";
  public static final String USER_PASSWORD = "Password";
  public static final String USER_REPORT_APPROVERID_FK = "ReportApproverId";
  public static final String USER_SKINID_FK = "ITR_SkinId";
  public static final String USER_VACATION_DEFAULT_DAYS = "DefaultVacationDays";
  public static final String USER_VACATION_SAVED_DAYS = "SavedVacationDays";
  public static final String USER_VACATION_USED_DAYS = "UsedVacationDays";
}