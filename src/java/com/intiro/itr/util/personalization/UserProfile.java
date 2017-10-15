package com.intiro.itr.util.personalization;

import java.util.ArrayList;

import com.intiro.itr.db.DBConstants;
import com.intiro.itr.db.DBExecute;
import com.intiro.itr.db.DBQueriesAdmin;
import com.intiro.itr.db.DBQueriesUser;
import com.intiro.itr.db.InvocationHandlerSetting;
import com.intiro.itr.logic.email.Email;
import com.intiro.itr.logic.phone.PhoneNumber;
import com.intiro.itr.util.ITRCalendar;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.log.IntiroLog;
import java.util.HashMap;
import java.util.Map;

public class UserProfile {

  public static final String CACHE_ALL_USERPROFILES = "CACHE_ALL_USERPROFILES";
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
  private ArrayList<Email> emails = null;
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
  private ArrayList<PhoneNumber> phones = null;
  private String reportApproverId = null;
  private Role role = new Role();
  private int savedVacationDays = 0;
  private String skin = null;
  private String skinId = null;
  private int usedVacationDays = 0;
  private String userId = null;

  /**
   * Constructor I for UserProfile.
   */
  public UserProfile() {
    ITRCalendar now = new ITRCalendar();
    setCreatedDate(now);
    setDeActivatedDate(now);
    setActivatedDate(now);
  }

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
    } else {
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

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public String getCompanyId() {
    return companyId;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

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

  public void setDefaultVacationDays(int days) {
    this.defaultVacationDays = days;
  }

  public int getDefaultVacationDays() {
    return defaultVacationDays;
  }

  public Email getEmail(int index) {
    Email oneEmail = null;

    if (emails != null) {
      oneEmail = (Email) emails.get(index);
    }

    return oneEmail;
  }

  public ArrayList<Email> getEmails() {
    if (emails != null) {
      return emails;
    } else {
      return new ArrayList<>();
    }
  }

  public void setEmails(ArrayList<Email> _emails) {
    emails = _emails;
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

  public boolean isLoggedIn() {
    return isLoggedIn;
  }

  public void setLoginId(String loginId) {
    this.loginId = loginId;
  }

  public String getLoginId() {
    return loginId;
  }

  public void setOvertimeMoneyHours(double hours) {
    this.overtimeMoneyHours = hours;
  }

  public double getOvertimeMoneyHours() {
    return overtimeMoneyHours;
  }

  public void setOvertimeVacationHours(double hours) {
    this.overtimeVacationHours = hours;
  }

  public double getOvertimeVacationHours() {
    return overtimeVacationHours;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPassword() {
    return password;
  }

  public PhoneNumber getPhoneNumber(int index) {
    PhoneNumber onePhone = null;

    if (phones != null) {
      onePhone = phones.get(index);
    }

    return onePhone;
  }

  public ArrayList<PhoneNumber> getPhoneNumbers() {
    if (phones != null) {
      return phones;
    } else {
      return new ArrayList<>();
    }
  }

  public void setPhoneNumbers(ArrayList<PhoneNumber> phones) {
    this.phones = phones;
  }

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

  public boolean isRoleAuthorizedForModule(int moduleId) {
    boolean retval = false;

    if (getRole() != null) {
      retval = getRole().isRoleAuthorizedForModule(moduleId);
    }

    return retval;
  }

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

  public void setUsedVacationDays(int days) {
    this.usedVacationDays = days;
  }

  public int getUsedVacationDays() {
    return usedVacationDays;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserId() {
    return userId;
  }

  public static UserProfile getUserProfile(String userId) throws XMLBuilderException {
    UserProfile retval = new UserProfile();

    try {
      retval.load(userId);
    } catch (Exception e) {
      IntiroLog.criticalError(UserProfile.class, ".load(): ERROR	FROM DATABASE, exception = ".concat(e.getMessage()));
      throw new XMLBuilderException("UserProfile.load(): " + e.getMessage());
    }

    return retval;
  }

  public void addToOvertimeMoneyHours(double hours) {
    this.overtimeMoneyHours += hours;
  }

  public void addToOvertimeVacationHours(double hours) {
    this.overtimeVacationHours += hours;
  }

  public boolean delete() throws Exception {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".load(): Entering");
    }

    boolean retVal = false;

    try {
      String statisticKey = getClass().getName() + ".delete";
      InvocationHandlerSetting s = InvocationHandlerSetting.create(statisticKey);
      retVal = DBExecute.getProxy(s).deleteUser(Integer.parseInt(getUserId()));
    } catch (Exception e) {
      IntiroLog.info(getClass(), getClass().getName() + ".delete(): ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }

    return retVal;
  }

  public boolean load(int userId) throws XMLBuilderException {
    return load(Integer.toString(userId));
  }

  public boolean load(String userId) throws XMLBuilderException {
    boolean success = false;

    try {
      String cacheKey = getClass().getName() + ".load_" + userId;
      String statisticKey = getClass().getName() + ".load";
      int cacheTime = 3600 * 10;
      InvocationHandlerSetting s = InvocationHandlerSetting.create(cacheKey, cacheTime, statisticKey);
      StringRecordset rs = DBQueriesUser.getProxy(s).loadUserProfile(userId);

      if (!rs.getEOF()) {
        mapRsToObject(this, rs);
        role.load(getUserId());
        loadEmailsForUser();
        loadPhoneNumbersForUser();
        success = true;
      }

      rs.close();
    } catch (Exception e) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".load():	ERROR FROM DATABASE, exception = ".concat(e.getMessage()));
      throw new XMLBuilderException(getClass().getName() + ".load(): " + e.getMessage());
    }
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".load():	Leaving");
    }

    return success;
  }

  public static Map<String, UserProfile> loadAllUserProfiles() throws XMLBuilderException {
    Map<String, UserProfile> retval = new HashMap<>();

    try {
      Map<Integer, ArrayList<Email>> mapEmails = Email.loadAllEmails();
      Map<Integer, ArrayList<PhoneNumber>> mapPhonenumbers = PhoneNumber.loadAllPhoneNumbers();

      String cacheKey = UserProfile.class.getName() + ".loadAllUserProfiles";
      String statisticKey = UserProfile.class.getName() + ".loadAllUserProfiles";
      int cacheTime = 3600 * 10;
      InvocationHandlerSetting s = InvocationHandlerSetting.create(cacheKey, cacheTime, statisticKey);
      StringRecordset rs = DBQueriesUser.getProxy(s).loadUserProfile(null);
      while (!rs.getEOF()) {
        UserProfile up = new UserProfile();
        mapRsToObject(up, rs);

        Integer key = Integer.parseInt(up.getUserId());
        if (mapEmails.containsKey(key)) {
          up.setEmails(mapEmails.get(key));
        }

        if (mapPhonenumbers.containsKey(key)) {
          up.setPhoneNumbers(mapPhonenumbers.get(key));
        }

        retval.put(up.getUserId(), up);
      }

      rs.close();
    } catch (Exception e) {
      IntiroLog.criticalError(UserProfile.class, UserProfile.class.getName() + ".loadAllUserProfiles():	ERROR FROM DATABASE, exception = ".concat(e.getMessage()));
      throw new XMLBuilderException(UserProfile.class.getName() + ".loadAllUserProfiles(): " + e.getMessage());
    }
    if (IntiroLog.d()) {
      IntiroLog.detail(UserProfile.class, UserProfile.class.getName() + ".loadAllUserProfiles():	Leaving");
    }

    return retval;
  }

  private static void mapRsToObject(UserProfile up, StringRecordset rs) throws XMLBuilderException, Exception {
    up.setLoginId(rs.getField(DBConstants.USER_LOGINID));
    up.setPassword(rs.getField(DBConstants.USER_PASSWORD));
    up.setUserId(rs.getField(DBConstants.USER_ID_PK));
    up.setFirstName(rs.getField(DBConstants.USER_FIRSTNAME));
    up.setLastName(rs.getField(DBConstants.USER_LASTNAME));
    up.setLanguage(rs.getField("Lname"));
    up.setLanguageCode(rs.getField(DBConstants.LANGUAGE_CODE));
    up.setLanguageId(rs.getField("Lid"));
    up.setSkinId(rs.getField("Sid"));
    up.setSkin(rs.getField("Sname"));

    if (up.getClientInfo() != null) {
      up.getClientInfo().setSkin(rs.getField(DBConstants.SKIN_PATH));
    }

    up.setActivated(rs.getField(DBConstants.USER_ACTIVATED));
    up.setCompanyId(rs.getField(DBConstants.USER_COMPANYID_FK));
    up.setCompanyName(rs.getField("cName"));
    up.setDefaultVacationDays(Integer.parseInt(rs.getField(DBConstants.USER_VACATION_DEFAULT_DAYS)));
    up.setSavedVacationDays(Integer.parseInt(rs.getField(DBConstants.USER_VACATION_SAVED_DAYS)));
    up.setUsedVacationDays(Integer.parseInt(rs.getField(DBConstants.USER_VACATION_USED_DAYS)));
    up.setOvertimeMoneyHours(Double.parseDouble(rs.getField(DBConstants.USER_OVERTIME_MONEY_HOURS)));
    up.setOvertimeVacationHours(Double.parseDouble(rs.getField(DBConstants.USER_OVERTIME_VACATION_HOURS)));
    up.setReportApproverId(rs.getField(DBConstants.USER_REPORT_APPROVERID_FK));

    ITRCalendar activatedDate = new ITRCalendar(rs.getField(DBConstants.USER_ACTIVATED_DATE));
    up.setActivatedDate(activatedDate);

    ITRCalendar deActivatedDate = new ITRCalendar(rs.getField(DBConstants.USER_DEACTIVATED_DATE));
    up.setDeActivatedDate(deActivatedDate);

    ITRCalendar createdDate = new ITRCalendar(rs.getField(DBConstants.USER_CREATED_DATE));
    up.setCreatedDate(createdDate);
    rs.moveNext();
  }

  public void loadEmailsForUser() throws XMLBuilderException {
    if (getUserId() != null && !getUserId().equalsIgnoreCase("-1")) {
      this.emails = Email.load(Integer.parseInt(getUserId()), -1, -1);
    }
  }

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
   * This method is used to login a user. Method uses reload(), to load the userProfile.
   *
   * @param loginId
   * @param password
   * @throws java.lang.Exception
   * @see com.intiro.itr.ui.login.LoginVerifier
   * @return a boolean, true if success, else false.
   */
  public boolean login(String loginId, String password) throws Exception {
    boolean retval = false;
    retval = reload(loginId, password);
    if (retval) {
      retval = getActivated();
    }

    return retval;
  }

  public void logout() {
    setLoginStatus(false);
  }

  /**
   * This method is used to reload the userprofile after a change has been made.
   *
   * @return a boolean, true if success, else false.
   * @throws java.lang.Exception
   */
  public boolean reload() throws Exception {
    return reload(getLoginId(), getPassword());
  }

  /**
   * This method is used to reload the userprofile after a change has been made.
   *
   * @param loginId
   * @param password
   * @return a boolean, true if success, else false.
   * @throws java.lang.Exception
   */
  public boolean reload(String loginId, String password) throws Exception {
    boolean success = false;

    try {
      String cacheKey = getClass().getName() + ".reload_" + loginId + "_" + password;
      String statisticKey = getClass().getName() + ".reload";
      int cacheTime = 3600 * 10;
      InvocationHandlerSetting s = InvocationHandlerSetting.create(cacheKey, cacheTime, statisticKey);
      StringRecordset rs = DBQueriesUser.getProxy(s).login(loginId, password);

      if (!rs.getEOF()) {
        mapRsToObject(this, rs);
        role.load(getUserId());
        loadEmailsForUser();
        loadPhoneNumbersForUser();
        success = true;
      }

      rs.close();
    } catch (Exception e) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".reload(): ERROR FROM DATABASE, exception = ".concat(e.getMessage()));
      throw new Exception(getClass().getName() + ".reload(): " + e.getMessage());
    }

    return success;
  }

  /**
   * Save the user profile.
   *
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public void save() throws XMLBuilderException {
    if (getUserId() != null && !getUserId().equalsIgnoreCase("-1")) {
      update();
    } else {
      insert();
    }
  }

  private void insert() throws XMLBuilderException {
    try {
      String statisticKey = getClass().getName() + ".insert";
      InvocationHandlerSetting s = InvocationHandlerSetting.create(statisticKey);
      StringRecordset rs = DBQueriesAdmin.getProxy(s).makeNewUserAndFetchId(this);

      if (!rs.getEOF()) {
        setUserId(rs.getField("maxId"));

        //create a user role connection
        if (getRole() != null) {
          getRole().setUserId(Integer.parseInt(getUserId()));
          getRole().save();
        }
      }
    } catch (Exception e) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".save(): ERROR FROM	DATABASE, exception	= " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }
  }

  private void update() throws XMLBuilderException {
    try {
      String statisticKey = getClass().getName() + ".update";
      InvocationHandlerSetting s = InvocationHandlerSetting.create(statisticKey);
      DBExecute.getProxy(s).updateUserProfile(this);

      //update the user role connection
      if (getRole() != null) {
        getRole().setUserId(Integer.parseInt(getUserId()));
        getRole().save();
      }
    } catch (Exception e) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".save(): ERROR FROM	DATABASE, exception	= " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }
  }

  /**
   * Subtract from overtimeHours that the user want in money.
   *
   * @param hours, an String specifying the number of overtime hours to be subtracted.
   */
  public void subtractFromOvertimeMoneyHours(double hours) {
    this.overtimeMoneyHours -= hours;
  }

  /**
   * Subtract from overtimeHours of the user.
   *
   * @param hours, an String specifying the number of overtime hours to be subtracted.
   */
  public void subtractFromOvertimeVacationHours(double hours) {
    this.overtimeVacationHours -= hours;
  }

  @Override
  public String toString() {
    StringBuffer retval = new StringBuffer();

    try {
      toXML(retval);
    } catch (Exception e) {
      // swallow
    }

    return retval.toString();
  }

  /**
   * This is the method that will produce the XML. It will fill the xmlDoc with XML.
   *
   * @param xmlDoc a StringBuffer to be filled with xml.
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
   *
   * @param status a boolean setting the login status.
   */
  protected void setLoginStatus(boolean status) {
    isLoggedIn = status;
  }

  private void setRole(Role _role) {
    this.role = _role;
  }
}
