/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.util.personalization;

import java.util.Vector;

import com.intiro.itr.db.DBConstants;
import com.intiro.itr.db.DBExecute;
import com.intiro.itr.db.DBQueries;
import com.intiro.itr.logic.email.Email;
import com.intiro.itr.logic.phone.PhoneNumber;
import com.intiro.itr.util.ITRCalendar;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.toolbox.log.IntiroLog;

public class UserProfile {

  //~ Instance/static variables ........................................................................................

  static final String XML_ACTIVATEDDATE_END = "</activateddate>";
  static final String XML_ACTIVATEDDATE_START = "<activateddate>";
  static final String XML_ACTIVATED_END = "</activated>";
  static final String XML_ACTIVATED_START = "<activated>";
  static final String XML_COMPANYID_END = "</companyid>";
  static final String XML_COMPANYID_START = "<companyid>";
  static final String XML_COMPANY_END = "</company>";
  static final String XML_COMPANY_START = "<company>";
  static final String XML_CONNECTED_DEFAULT_PROJ_END = "</condefproj>";
  static final String XML_CONNECTED_DEFAULT_PROJ_START = "<condefproj>";
  static final String XML_CREATEDATE_END = "</createdate>";
  static final String XML_CREATEDATE_START = "<createdate>";
  static final String XML_DEACTIVATEDDATE_END = "</deactivateddate>";
  static final String XML_DEACTIVATEDDATE_START = "<deactivateddate>";
  static final String XML_DEFAULTDAYS_END = "</defaultdays>";
  static final String XML_DEFAULTDAYS_START = "<defaultdays>";
  static final String XML_FIRSTNAME_END = "</firstname>";
  static final String XML_FIRSTNAME_START = "<firstname>";
  static final String XML_LANGUAGEID_END = "</languageid>";
  static final String XML_LANGUAGEID_START = "<languageid>";
  static final String XML_LANGUAGE_END = "</language>";
  static final String XML_LANGUAGE_START = "<language>";
  static final String XML_LASTNAME_END = "</lastname>";
  static final String XML_LASTNAME_START = "<lastname>";
  static final String XML_LOGINID_END = "</loginid>";
  static final String XML_LOGINID_START = "<loginid>";
  static final String XML_MONEYHOURS_END = "</moneyh>";
  static final String XML_MONEYHOURS_START = "<moneyh>";
  static final String XML_REMAININGDAYS_END = "</remainingdays>";
  static final String XML_REMAININGDAYS_START = "<remainingdays>";
  static final String XML_REPORT_APPROVERID_END = "</approverid>";
  static final String XML_REPORT_APPROVERID_START = "<approverid>";
  static final String XML_SAVEDDAYS_END = "</saveddays>";
  static final String XML_SAVEDDAYS_START = "<saveddays>";
  static final String XML_SKINID_END = "</moneyh>";
  static final String XML_SKINID_START = "<moneyh>";
  static final String XML_SKIN_END = "</skin>";
  static final String XML_SKIN_START = "<skin>";
  static final String XML_TOTALHOURS_END = "</totalh>";
  static final String XML_TOTALHOURS_START = "<totalh>";
  static final String XML_USEDDAYS_END = "</useddays>";
  static final String XML_USEDDAYS_START = "<useddays>";
  static final String XML_USERID_END = "</userid>";
  static final String XML_USERID_START = "<userid>";
  static final String XML_USER_END = "</user>";
  static final String XML_USER_START = "<user>";
  static final String XML_VACATIONHOURS_END = "</vacationh>";
  static final String XML_VACATIONHOURS_START = "<vacationh>";
  boolean activated = false;
  boolean isLoggedIn = false;
  private ITRCalendar activatedDate = null;
  private ClientInfo clientInfo = null;
  private String companyId = null;
  private String companyName = null;
  private ITRCalendar createdDate = null;
  private ITRCalendar deActivatedDate = null;
  private int defaultVacationDays = 0;
  private Vector emails = null;
  private String firstName = null;
  //private boolean isConnectedToDefaultProjects = false;
  private String language = null;
  private String languageCode = null;
  private String languageId = null;
  private String lastName = null;
  private String loginId = null;
  private double overtimeMoneyHours = 0;
  private double overtimeVacationHours = 0;
  private String password = null;
  private Vector phones = null;
  private String reportApproverId = null;
  private Role role = new Role();
  private int savedVacationDays = 0;
  private String skin = null;
  private String skinId = null;
  private int usedVacationDays = 0;
  private String userId = null;

  //~ Constructors .....................................................................................................

  /**
   * Constructor I for UserProfile.
   */
  public UserProfile() {
    ITRCalendar now = new ITRCalendar();
    setCreatedDate(now);
    setDeActivatedDate(now);
    setActivatedDate(now);
  }

  //~ Methods ..........................................................................................................

  public void setActivated(String status) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".setActivated(): status = " + status);
    }
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".setActivated(): before changing, getActivated() = " + getActivated());
    }
    //if status =   1 then it is true or equals true.
    if ((status != null && status.trim().equals("1")) || (status != null && status.trim().equalsIgnoreCase("True"))) {
      activated = true;
    }
    else {
      activated = false;
    }
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".setActivated(): after changing, getActivated() = " + getActivated());
    }
  }

  public boolean getActivated() {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".getActivated(): " + activated);
    }

    return activated;
  }

  /*
   public void setConnectedToDefaultProjects(String status) {
   //if status =    1 then it is true or equals    true.
   if( (status != null && status.trim().equals("1")) || (status != null && status.trim().equalsIgnoreCase("True")) )    {
   isConnectedToDefaultProjects = true;
   }
   }
   public boolean getConnectedToDefaultProjects() {
   return isConnectedToDefaultProjects;
   }
   */
  public void setActivatedDate(ITRCalendar date) {
    if (date != null) {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".setActivatedDate(): " + date);
      }

      activatedDate = date.cloneCalendar();
    }
  }

  public ITRCalendar getActivatedDate() {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".getActivatedDate(): " + activatedDate);
    }

    return activatedDate.cloneCalendar();
  }

  public void setClientInfo(ClientInfo clientInfo) {
    this.clientInfo = clientInfo;
  }

  public ClientInfo getClientInfo() {
    return clientInfo;
  }

  /**
   *    Sets the CompanyId of the logged on    user.
   *
   *    @param        CompanyId, an String specifying    the    CompanyId.
   */
  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  /**
   *    Gets the CompanyId of the logged on    user.
   *
   *    @return        a String, specifying the CompanyId.
   */
  public String getCompanyId() {
    return companyId;
  }

  /**
   *    Sets the CompanyName of    the    logged on user.
   *
   *    @param        CompanyName, an    String specifying the CompanyName.
   */
  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  /**
   *    Gets the CompanyName of    the    logged on user.
   *
   *    @return        a String, specifying the CompanyName.
   */
  public String getCompanyName() {
    return companyName;
  }

  public void setCreatedDate(ITRCalendar date) {
    if (date != null) {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".setCreatedDate(): " + date);
      }

      createdDate = date.cloneCalendar();
    }
  }

  public ITRCalendar getCreatedDate() {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".getCreatedDate(): " + createdDate);
    }

    return createdDate.cloneCalendar();
  }

  public void setDeActivatedDate(ITRCalendar date) {
    if (date != null) {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".setDeActivatedDate(): " + date);
      }

      deActivatedDate = date.cloneCalendar();
    }
  }

  public ITRCalendar getDeActivatedDate() {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".getDeActivatedDate(): " + deActivatedDate);
    }

    return deActivatedDate.cloneCalendar();
  }

  /**
   *    Sets the vacationDays of the logged    on user.
   *
   *    @param        vacationDays, an String    specifying the number of vacation days.
   */
  public void setDefaultVacationDays(int days) {
    this.defaultVacationDays = days;
  }

  public int getDefaultVacationDays() {
    return defaultVacationDays;
  }

  /**
   *    Gets a specific email for userprofile.
   *
   *    @return        an Email.
   */
  public Email getEmail(int index) {
    Email oneEmail = null;

    if (emails != null) {
      oneEmail = (Email) emails.get(index);
    }

    return oneEmail;
  }

  /**
   *    Gets the email of userprofile.
   *
   *    @return        a String, specifying the emailId.
   */
  public Vector getEmails() {
    if (emails != null) {
      return emails;
    } else {
      return new Vector();
    }
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguageCode(String code) {
    if (clientInfo != null) {
      clientInfo.setLanguageCode(code);
    }

    this.languageCode = code;
  }

  public String getLanguageCode() {
    String code = this.languageCode;

    if (clientInfo != null) {
      code = clientInfo.getLanguageCode();
    }

    return code;
  }

  public void setLanguageId(String languageId) {
    this.languageId = languageId;
  }

  public String getLanguageId() {
    return languageId;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getLastName() {
    return lastName;
  }

  /**
   * Get the login status.
   * @return a boolean true, if logged in, else false.
   */
  public boolean isLoggedIn() {
    return isLoggedIn;
  }

  /**
   *    Sets the LoginId of the    logged on Login.
   *
   *    @param        LoginId, an String specifying the LoginId.
   */
  public void setLoginId(String loginId) {
    this.loginId = loginId;
  }

  /**
   *    Gets the LoginId of the    logged on Login.
   *
   *    @return        a String, specifying the LoginId.
   */
  public String getLoginId() {
    return loginId;
  }

  /**
   *    Sets the overtimeHours that    the    user want in money.
   *
   *    @param        overtimeHours, an String specifying    the    number of overtime hours.
   */
  public void setOvertimeMoneyHours(double hours) {
    this.overtimeMoneyHours = hours;
  }

  /**
   *    Gets the overtimeHours that    the    user want in money.
   *
   *    @param        overtimeHours, an String specifying    the    number of overtime hours.
   */
  public double getOvertimeMoneyHours() {
    return overtimeMoneyHours;
  }

  /**
   *    Sets the overtimeHours of the logged on    user.
   *
   *    @param        overtimeHours, an String specifying    the    number of overtime hours.
   */
  public void setOvertimeVacationHours(double hours) {
    this.overtimeVacationHours = hours;
  }

  /**
   *    Gets the overtimeHours of the logged on    user.
   *
   *    @param        overtimeHours, an String specifying    the    number of overtime hours.
   */
  public double getOvertimeVacationHours() {
    return overtimeVacationHours;
  }

  /**
   *    Sets the password of the logged    on user.
   *
   *    @param        password, a string specifying the password.
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   *    Gets the password of the logged    on user.
   *
   *    @return        a String, specifying the password.
   */
  public String getPassword() {
    return password;
  }

  /**
   *    Gets a specific phoneNumber for userprofile.
   *
   *    @return        an PhoneNumber.
   */
  public PhoneNumber getPhoneNumber(int index) {
    PhoneNumber onePhone = null;

    if (phones != null) {
      onePhone = (PhoneNumber) phones.get(index);
    }

    return onePhone;
  }

  /**
   *    Gets the email of userprofile.
   *
   *    @return        a Vector, containing the userprofiles phoneNumbers.
   */
  public Vector getPhoneNumbers() {
    if (phones != null) { 
      return phones;
    } else {
      return new Vector();
    }
  }

  /**
   * Return remaining vacationdays for the user.
   */
  public int getRemainingVacationDays() {
    return (getDefaultVacationDays() + getSavedVacationDays()) - getUsedVacationDays();
  }

  public void setReportApproverId(String id) {
    this.reportApproverId = id;
  }

  public String getReportApproverId() {
    return reportApproverId;
  }

  public Role getRole() {
    return role;
  }

  /**
   * Check    if the users Role is authorized    to use the Module with supplied    moduleId.
   *
   * @param        moduleId, an int specifying    the    module to check    for.
   */
  public boolean isRoleAuthorizedForModule(int moduleId) {
    boolean retval = false;

    if (getRole() != null) {
      retval = getRole().isRoleAuthorizedForModule(moduleId);
    }

    return retval;
  }

  /**
   *    Sets the vacationDays of the logged    on user.
   *
   *    @param        vacationDays, an String    specifying the number of vacation days.
   */
  public void setSavedVacationDays(int days) {
    this.savedVacationDays = days;
  }

  public int getSavedVacationDays() {
    return savedVacationDays;
  }

  public void setSkin(String skin) {
    this.skin = skin;
  }

  public String getSkin() {
    return skin;
  }

  public void setSkinId(String skinId) {
    this.skinId = skinId;
  }

  public String getSkinId() {
    return skinId;
  }

  /**
   *    Sets the vacationDays of the logged    on user.
   *
   *    @param        vacationDays, an String    specifying the number of vacation days.
   */
  public void setUsedVacationDays(int days) {
    this.usedVacationDays = days;
  }

  public int getUsedVacationDays() {
    return usedVacationDays;
  }

  /**
   *    Sets the userId    of the logged on user.
   *
   *    @param    userId,    a string specifying    the    userId.
   */
  public void setUserId(String userId) {
    this.userId = userId;
  }

  /**
   *    Gets the userId    of the logged on user.
   *
   *    @return        a String, specifying the userId.
   */
  public String getUserId() {
    return userId;
  }

  /**
   *
   */
  public static UserProfile getUserProfile(String userId) throws XMLBuilderException {
    UserProfile retval = new UserProfile();

    try {
      retval.load(userId);
    } catch (Exception e) {
      if (IntiroLog.ce()) {
        IntiroLog.criticalError(UserProfile.class, ".load(): ERROR	FROM DATABASE, exception = ".concat(e.getMessage()));
      }

      throw new XMLBuilderException("UserProfile.load(): " + e.getMessage());
    }

    return retval;
  }

  /**
   *    Add    to overtimeHours that the user want    in money.
   *
   *    @param        hours, an String specifying    the    number of overtime hours to    be added.
   */
  public void addToOvertimeMoneyHours(double hours) {
    this.overtimeMoneyHours += hours;
  }

  /**
   *    Add    to overtimeHours of    the    logged on user.
   *
   *    @param        hours, an String specifying    the    number of overtime hours to    be added.
   */
  public void addToOvertimeVacationHours(double hours) {
    this.overtimeVacationHours += hours;
  }

  /**
   * Delete the User.
   *
   * @return boolean.  false if nothing was deleted from db
   */
  public boolean delete() throws Exception {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".load(): Entering");
    }

    boolean retVal = false;

    try {
      retVal = new DBExecute().deleteUser(Integer.parseInt(getUserId()));
    } catch (Exception e) {
      IntiroLog.info(getClass(), getClass().getName() + ".delete(): ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }

    return retVal;
  }

  /**
   * This method is used to reload    the    userprofile    after a    change has been    made.
   * @return    a boolean, true    if success,    else false.
   */
  public boolean load(int userId) throws XMLBuilderException {
    return load(Integer.toString(userId));
  }

  /**
   * This method is used to reload    the    userprofile    after a    change has been    made.
   * @return    a boolean, true    if success,    else false.
   */
  public boolean load(String userId) throws XMLBuilderException {
    DBQueries query = new DBQueries(this);
    boolean success = false;

    try {
      StringRecordset rs = query.loadUserProfile(userId);

      if (!rs.getEOF()) {
        setLoginId(rs.getField(DBConstants.USER_LOGINID));
        setPassword(rs.getField(DBConstants.USER_PASSWORD));
        setUserId(rs.getField(DBConstants.USER_ID_PK));
        setFirstName(rs.getField(DBConstants.USER_FIRSTNAME));
        setLastName(rs.getField(DBConstants.USER_LASTNAME));
        setLanguage(rs.getField("Lname"));
        setLanguageCode(rs.getField(DBConstants.LANGUAGE_CODE));
        setLanguageId(rs.getField("Lid"));
        setSkinId(rs.getField("Sid"));
        setSkin(rs.getField("Sname"));

        if (getClientInfo() != null) {
          getClientInfo().setSkin(rs.getField(DBConstants.SKIN_PATH));
        }

        setActivated(rs.getField(DBConstants.USER_ACTIVATED));
        setCompanyId(rs.getField(DBConstants.USER_COMPANYID_FK));
        setCompanyName(rs.getField("cName"));
        setDefaultVacationDays(Integer.parseInt(rs.getField(DBConstants.USER_VACATION_DEFAULT_DAYS)));
        setSavedVacationDays(Integer.parseInt(rs.getField(DBConstants.USER_VACATION_SAVED_DAYS)));
        setUsedVacationDays(Integer.parseInt(rs.getField(DBConstants.USER_VACATION_USED_DAYS)));
        setOvertimeMoneyHours(Double.parseDouble(rs.getField(DBConstants.USER_OVERTIME_MONEY_HOURS)));
        setOvertimeVacationHours(Double.parseDouble(rs.getField(DBConstants.USER_OVERTIME_VACATION_HOURS)));
        setReportApproverId(rs.getField(DBConstants.USER_REPORT_APPROVERID_FK));

        ITRCalendar activatedDate = new ITRCalendar(rs.getField(DBConstants.USER_ACTIVATED_DATE));
        setActivatedDate(activatedDate);

        ITRCalendar deActivatedDate = new ITRCalendar(rs.getField(DBConstants.USER_DEACTIVATED_DATE));
        setDeActivatedDate(deActivatedDate);

        ITRCalendar createdDate = new ITRCalendar(rs.getField(DBConstants.USER_CREATED_DATE));
        setCreatedDate(createdDate);
        role.load(getUserId());
        loadEmailsForUser();
        loadPhoneNumbersForUser();
        success = true;
      }

      rs.close();
    } catch (Exception e) {
      if (IntiroLog.ce()) {
        IntiroLog.criticalError(getClass(), getClass().getName() + ".reload():	ERROR FROM DATABASE, exception = ".concat(e.getMessage()));
      }

      throw new XMLBuilderException(getClass().getName() + ".reload(): " + e.getMessage());
    }
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".reload():	Leaving");
    }

    return success;
  }

  /**
   *    Sets the emails for the userprofile.
   */
  public void loadEmailsForUser() throws XMLBuilderException {
    if (getUserId() != null && !getUserId().equalsIgnoreCase("-1")) {
      this.emails = Email.load(Integer.parseInt(getUserId()), -1, -1);
    }
  }

  /**
   *    Sets the emails for the userprofile.
   */
  public void loadPhoneNumbersForUser() throws XMLBuilderException {
    if (getUserId() != null && !getUserId().equalsIgnoreCase("-1")) {
      this.phones = PhoneNumber.load(Integer.parseInt(getUserId()), -1, -1);
    }
  }

  public void loadRole() throws Exception {
    if (role != null) {
      role.load(getUserId());
    }
  }

   public boolean login() throws Exception {
    return login(this.loginId, this.password);
  }
  /**
   * This method is used to login a user.
   * Method uses reload(),    to load    the    userProfile.
   *
   * @see com.intiro.itr.ui.login.LoginVerifier
   * @return    a boolean, true    if success,    else false.
   */
  public boolean login(String loginId, String password) throws Exception {
    boolean retval = false;
    retval = reload(loginId, password);
    if(retval)
    {
        retval = getActivated();
    }

    return retval;
  }

  public void logout() {
    setLoginStatus(false);
  }

  /**
   * This method is used to reload the userprofile after a change has been made.
   * @return    a boolean, true    if success, else false.
   */
  public boolean reload() throws Exception {
    return reload(getLoginId(), getPassword());
  }

  /**
   * This method is used to reload the userprofile after a change has been made.
   * @return    a boolean, true    if success, else false.
   */
  public boolean reload(String loginId, String password) throws Exception {
    DBQueries query = new DBQueries(this);
    boolean success = false;

    try {
      StringRecordset rs = query.login(loginId, password);

      if (!rs.getEOF()) {
        setLoginId(loginId);
        setPassword(password);
        setUserId(rs.getField(DBConstants.USER_ID_PK));
        setFirstName(rs.getField(DBConstants.USER_FIRSTNAME));
        setLastName(rs.getField(DBConstants.USER_LASTNAME));
        setLanguage(rs.getField("Lname"));
        setLanguageCode(rs.getField(DBConstants.LANGUAGE_CODE));
        setLanguageId(rs.getField("Lid"));
        setSkinId(rs.getField("Sid"));
        setSkin(rs.getField("Sname"));

        if (getClientInfo() != null) {
          getClientInfo().setSkin(rs.getField(DBConstants.SKIN_PATH));
        }

        setActivated(rs.getField(DBConstants.USER_ACTIVATED));
        setCompanyId(rs.getField(DBConstants.USER_COMPANYID_FK));
        setCompanyName(rs.getField("cName"));
        setDefaultVacationDays(Integer.parseInt(rs.getField(DBConstants.USER_VACATION_DEFAULT_DAYS)));
        setSavedVacationDays(Integer.parseInt(rs.getField(DBConstants.USER_VACATION_SAVED_DAYS)));
        setUsedVacationDays(Integer.parseInt(rs.getField(DBConstants.USER_VACATION_USED_DAYS)));
        setOvertimeMoneyHours(Double.parseDouble(rs.getField(DBConstants.USER_OVERTIME_MONEY_HOURS)));
        setOvertimeVacationHours(Double.parseDouble(rs.getField(DBConstants.USER_OVERTIME_VACATION_HOURS)));
        setReportApproverId(rs.getField(DBConstants.USER_REPORT_APPROVERID_FK));

        ITRCalendar activatedDate = new ITRCalendar(rs.getField(DBConstants.USER_ACTIVATED_DATE));
        setActivatedDate(activatedDate);

        ITRCalendar deActivatedDate = new ITRCalendar(rs.getField(DBConstants.USER_DEACTIVATED_DATE));
        setDeActivatedDate(deActivatedDate);

        ITRCalendar createdDate = new ITRCalendar(rs.getField(DBConstants.USER_CREATED_DATE));
        setCreatedDate(createdDate);
        role.load(getUserId());
        loadEmailsForUser();
        loadPhoneNumbersForUser();
        success = true;
      }

      rs.close();
    } catch (Exception e) {
      if (IntiroLog.ce()) {
        IntiroLog.criticalError(getClass(), getClass().getName() + ".reload(): ERROR FROM DATABASE, exception = ".concat(e.getMessage()));
      }

      throw new Exception(getClass().getName() + ".reload(): " + e.getMessage());
    }

    return success;
  }

  /**
   * Save the user    profile.
   */
  public void save() throws XMLBuilderException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".save(): Entering");
    }
    if (getUserId() != null && !getUserId().equalsIgnoreCase("-1")) {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".save(): updating");
      }
      try {
        DBExecute dbExecute = new DBExecute();
        dbExecute.updateUserProfile(this);

        //update the user role connection
        if (getRole() != null) {
          getRole().setUserId(Integer.parseInt(getUserId()));
          getRole().save();
        }
      } catch (Exception e) {
        if (IntiroLog.ce()) {
          IntiroLog.criticalError(getClass(), getClass().getName() + ".save(): ERROR FROM	DATABASE, exception	= " + e.getMessage());
        }

        throw new XMLBuilderException(e.getMessage());
      }
    }
    else {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".save(): creating new");
      }
      try {
        DBQueries dbQuery = new DBQueries();
        StringRecordset rs = dbQuery.makeNewUserAndFetchId(this);

        if (!rs.getEOF()) {
          setUserId(rs.getField("maxId"));

          //create a user role connection
          if (getRole() != null) {
            getRole().setUserId(Integer.parseInt(getUserId()));
            getRole().save();
          }
        }
      } catch (Exception e) {
        if (IntiroLog.ce()) {
          IntiroLog.criticalError(getClass(), getClass().getName() + ".save(): ERROR FROM	DATABASE, exception	= " + e.getMessage());
        }

        throw new XMLBuilderException(e.getMessage());
      }
    }
  }

  /**
   *    Subtract from overtimeHours that the user want in money.
   *
   *    @param        hours, an String specifying    the    number of overtime hours to    be subtracted.
   */
  public void subtractFromOvertimeMoneyHours(double hours) {
    this.overtimeMoneyHours -= hours;
  }

  /**
   *    Subtract from overtimeHours of the user.
   *
   *    @param        hours, an String specifying    the    number of overtime hours to    be subtracted.
   */
  public void subtractFromOvertimeVacationHours(double hours) {
    this.overtimeVacationHours -= hours;
  }

  public String toString() {
    StringBuffer retval = new StringBuffer();

    try {
      toXML(retval);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return retval.toString();
  }

  /**
   * This is the method that will produce the XML.
   * It will fill the xmlDoc with XML.
   * @param    xmlDoc a StringBuffer to be filled with xml.
   */
  public void toXML(StringBuffer xmlDoc) throws Exception {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer): Entered");
    }

    xmlDoc.append(XML_USER_START);

    //User id
    xmlDoc.append(XML_USERID_START);

    if (getUserId() != null) {
      xmlDoc.append(getUserId());
    }

    xmlDoc.append(XML_USERID_END);

    //First name
    xmlDoc.append(XML_FIRSTNAME_START);

    if (getFirstName() != null) {
      xmlDoc.append(getFirstName());
    }

    xmlDoc.append(XML_FIRSTNAME_END);

    //Last name
    xmlDoc.append(XML_LASTNAME_START);

    if (getLastName() != null) {
      xmlDoc.append(getLastName());
    }

    xmlDoc.append(XML_LASTNAME_END);

    //Login id
    xmlDoc.append(XML_LOGINID_START);

    if (getLoginId() != null) {
      xmlDoc.append(getLoginId());
    }

    xmlDoc.append(XML_LOGINID_END);

    //Activated
    xmlDoc.append(XML_ACTIVATED_START);
    xmlDoc.append(getActivated());
    xmlDoc.append(XML_ACTIVATED_END);

    //Activated date
    xmlDoc.append(XML_ACTIVATEDDATE_START);

    if (getActivatedDate() != null) {
      xmlDoc.append(getActivatedDate().getCalendarInStoreFormat());
    }

    xmlDoc.append(XML_ACTIVATEDDATE_END);

    //Create date
    xmlDoc.append(XML_CREATEDATE_START);

    if (getCreatedDate() != null) {
      xmlDoc.append(getCreatedDate().getCalendarInStoreFormat());
    }

    xmlDoc.append(XML_CREATEDATE_END);

    //Deaktivated date
    xmlDoc.append(XML_DEACTIVATEDDATE_START);

    if (getDeActivatedDate() != null) {
      xmlDoc.append(getDeActivatedDate().getCalendarInStoreFormat());
    }

    xmlDoc.append(XML_DEACTIVATEDDATE_END);

    //Default vacation days
    xmlDoc.append(XML_DEFAULTDAYS_START);
    xmlDoc.append(getDefaultVacationDays());
    xmlDoc.append(XML_DEFAULTDAYS_END);

    //Saved vacation days
    xmlDoc.append(XML_SAVEDDAYS_START);
    xmlDoc.append(getSavedVacationDays());
    xmlDoc.append(XML_SAVEDDAYS_END);

    //Used vacation days
    xmlDoc.append(XML_USEDDAYS_START);
    xmlDoc.append(getUsedVacationDays());
    xmlDoc.append(XML_USEDDAYS_END);

    //Remaining vacation days
    xmlDoc.append(XML_REMAININGDAYS_START);
    xmlDoc.append(getRemainingVacationDays());
    xmlDoc.append(XML_REMAININGDAYS_END);

    //Vacation overtime hours
    xmlDoc.append(XML_VACATIONHOURS_START);
    xmlDoc.append(getOvertimeVacationHours());
    xmlDoc.append(XML_VACATIONHOURS_END);

    //Money overtime hours
    xmlDoc.append(XML_MONEYHOURS_START);
    xmlDoc.append(getOvertimeMoneyHours());
    xmlDoc.append(XML_MONEYHOURS_END);

    //Total worked overtime hours
    xmlDoc.append(XML_TOTALHOURS_START);
    xmlDoc.append(getOvertimeMoneyHours() + getOvertimeVacationHours());
    xmlDoc.append(XML_TOTALHOURS_END);

    if (emails != null) {

      //Emails
      Email oneEmail = null;

      //Loop through all the Emails
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer): emails.size() = " + emails.size());
      }

      int j = 0;

      for (int i = 0; i < emails.size(); i++) {
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer): Emails, Loop[" + i + "]");
        }

        oneEmail = (Email) emails.get(i);

        if (oneEmail != null) {
          oneEmail.toXML(xmlDoc, i, j++);
        }
      }
    }
    if (phones != null) {

      //PhoneNumbers
      PhoneNumber onePhone = null;

      //Loop through all the phone numbers
      int j = 0;

      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer): phones.size() = " + phones.size());
      }
      for (int i = 0; i < phones.size(); i++) {
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer): Phones, Loop[" + i + "]");
        }

        onePhone = (PhoneNumber) phones.get(i);

        if (onePhone != null) {
          onePhone.toXML(xmlDoc, i, j++);
        }
      }
    }

    //Languageid
    xmlDoc.append(XML_LANGUAGEID_START);

    if (getLanguageId() != null) {
      xmlDoc.append(getLanguageId());
    }

    xmlDoc.append(XML_LANGUAGEID_END);

    //Language
    xmlDoc.append(XML_LANGUAGE_START);

    if (getLanguage() != null) {
      xmlDoc.append(getLanguage());
    }

    xmlDoc.append(XML_LANGUAGE_END);

    //Skinid
    xmlDoc.append(XML_SKINID_START);

    if (getSkinId() != null) {
      xmlDoc.append(getSkinId());
    }

    xmlDoc.append(XML_SKINID_END);

    //Skin
    xmlDoc.append(XML_SKIN_START);

    if (getSkin() != null) {
      xmlDoc.append(getSkin());
    }

    xmlDoc.append(XML_SKIN_END);

    //companyid
    xmlDoc.append(XML_COMPANYID_START);

    if (getCompanyId() != null) {
      xmlDoc.append(getCompanyId());
    }

    xmlDoc.append(XML_COMPANYID_END);

    //company
    xmlDoc.append(XML_COMPANY_START);

    if (getCompanyName() != null) {
      xmlDoc.append(getCompanyName());
    }

    xmlDoc.append(XML_COMPANY_END);

    /*
     //Connected to default projects
     xmlDoc.append(XML_CONNECTED_DEFAULT_PROJ_START);
     xmlDoc.append(getConnectedToDefaultProjects());
     xmlDoc.append(XML_CONNECTED_DEFAULT_PROJ_END);
     */
    xmlDoc.append(XML_REPORT_APPROVERID_START);

    if (getReportApproverId() != null) {
      xmlDoc.append(getReportApproverId());
    }

    xmlDoc.append(XML_REPORT_APPROVERID_END);
    xmlDoc.append(XML_USER_END);

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer): Leaving");
    }
  }

  /**
   * Set the login status.
   * @param    status        a boolean setting the login    status.
   */
  protected void setLoginStatus(boolean status) {
    isLoggedIn = status;
  }
}