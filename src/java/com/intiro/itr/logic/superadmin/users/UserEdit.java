/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.logic.superadmin.users;

import com.intiro.itr.util.combos.CompanyCombo;
import com.intiro.itr.util.combos.LanguageCombo;
import com.intiro.itr.util.combos.RolesCombo;
import com.intiro.itr.util.combos.SkinsCombo;
import com.intiro.itr.util.combos.UsersCombo;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.DynamicXMLCarrier;
import com.intiro.itr.util.xml.XMLBuilder;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.log.IntiroLog;

public class UserEdit extends DynamicXMLCarrier {

  /**
   * @return Returns the approverCombo.
   */
  public UsersCombo getApproverCombo() {
    return approverCombo;
  }
  /**
   * @return Returns the companyCombo.
   */
  public CompanyCombo getCompanyCombo() {
    return companyCombo;
  }
  /**
   * @return Returns the inAddMode.
   */
  public boolean isInAddMode() {
    return inAddMode;
  }
  /**
   * @return Returns the inDeleteMode.
   */
  public boolean isInDeleteMode() {
    return inDeleteMode;
  }
  /**
   * @return Returns the inEditMode.
   */
  public boolean isInEditMode() {
    return inEditMode;
  }
  /**
   * @return Returns the inViewMode.
   */
  public boolean isInViewMode() {
    return inViewMode;
  }
  /**
   * @return Returns the languageCombo.
   */
  public LanguageCombo getLanguageCombo() {
    return languageCombo;
  }
  /**
   * @return Returns the mode.
   */
  public String getMode() {
    return mode;
  }
  /**
   * @return Returns the oneUser.
   */
  public UserProfile getOneUser() {
    return oneUser;
  }
  /**
   * @return Returns the rolesCombo.
   */
  public RolesCombo getRolesCombo() {
    return rolesCombo;
  }
  /**
   * @return Returns the skinsCombo.
   */
  public SkinsCombo getSkinsCombo() {
    return skinsCombo;
  }
  /**
   * @return Returns the title.
   */
  public String getTitle() {
    return title;
  }
  /**
   * @return Returns the userId.
   */
  public String getUserId() {
    return userId;
  }
  //~ Instance/static variables ........................................................................................

  static final String XML_APPROVER_END = "</approver>";
  static final String XML_APPROVER_START = "<approver>";
  static final String XML_COMPANY_END = "</company>";
  static final String XML_COMPANY_START = "<company>";
  static final String XML_LANGUAGE_END = "</language>";
  static final String XML_LANGUAGE_START = "<language>";
  static final String XML_MODE_END = "</mode>";
  static final String XML_MODE_START = "<mode>";
  static final String XML_ROLE_END = "</role>";
  static final String XML_ROLE_START = "<role>";
  static final String XML_SKIN_END = "</skin>";
  static final String XML_SKIN_START = "<skin>";
  static final String XML_TITLE_END = "</title>";
  static final String XML_TITLE_START = "<title>";
  protected UsersCombo approverCombo = null;
  protected CompanyCombo companyCombo = null;

  //modes
  protected boolean inAddMode = false;
  protected boolean inDeleteMode = false;
  protected boolean inEditMode = false;
  protected boolean inViewMode = false;

  //Combos
  protected LanguageCombo languageCombo = null;
  protected String mode = "not set";
  protected UserProfile oneUser = null;
  protected RolesCombo rolesCombo = null;
  protected SkinsCombo skinsCombo = null;
  protected String title = "";
  protected String userId = "";

  //~ Constructors .....................................................................................................

  public UserEdit(UserProfile profile, String mode, String userId) throws XMLBuilderException {
    super(profile);

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".Constructor(): Entered");
    }
    if (mode != null) {
      if (mode.indexOf("Add") != -1) {
        inAddMode = true;
        mode = "Add";
        title = "Add";
      }
      else if (mode.indexOf("Edit") != -1 || mode.indexOf("Activate") != -1) {
        inEditMode = true;
        mode = "Edit";
        title = "Edit";
      }
      else if (mode.indexOf("View") != -1) { //view mode
        inViewMode = true;
        mode = "View";
        title = "View";
      }
      else { //delete mode
        inDeleteMode = true;
        mode = "Delete";
        title = "Delete";
      }
    }

    //IntiroLog.detail(getClass().getName()+".doGet(): mode = ."+mode+".");
    //IntiroLog.detail(getClass().getName()+".doGet(): mode.trim() = ."+mode.trim()+".");
    this.mode = mode;
    this.userId = userId;

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): userId = " + userId);
    }

    //Load the user to edit, view
    UserProfile oneUser = new UserProfile();

    if (inEditMode || inViewMode || inDeleteMode) {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): loading user with userid = " + userId + ", inAddMode =  " + inAddMode);
      }

      oneUser.load(userId);
    }

    this.oneUser = oneUser;

    //Languages
    if (inAddMode) {
      this.languageCombo = new LanguageCombo(oneUser, true);
      languageCombo.load();
    }
    else {
      this.languageCombo = new LanguageCombo(oneUser, false);
      languageCombo.load(oneUser.getLanguageId());
    }

    languageCombo.setStartEndTags(XML_LANGUAGE_START, XML_LANGUAGE_END);

    //Skins
    if (inAddMode) {
      this.skinsCombo = new SkinsCombo(oneUser, true);
      skinsCombo.load();
    }
    else {
      this.skinsCombo = new SkinsCombo(oneUser, false);
      skinsCombo.load(oneUser.getSkinId());
    }

    skinsCombo.setStartEndTags(XML_SKIN_START, XML_SKIN_END);

    //Company
    if (inAddMode) {
      this.companyCombo = new CompanyCombo(oneUser, true);
      companyCombo.load();
    }
    else {
      this.companyCombo = new CompanyCombo(oneUser, false);
      companyCombo.load(oneUser.getCompanyId());
    }

    companyCombo.setStartEndTags(XML_COMPANY_START, XML_COMPANY_END);

    //Approver
    if (inAddMode) {
      this.approverCombo = new UsersCombo(oneUser, true, true);
      approverCombo.load();
    }
    else {
      this.approverCombo = new UsersCombo(oneUser, false, true);
      approverCombo.load(oneUser.getReportApproverId());
    }

    approverCombo.setStartEndTags(XML_APPROVER_START, XML_APPROVER_END);

    //Roles
    if (inAddMode) {
      this.rolesCombo = new RolesCombo(true);
      rolesCombo.load();
    }
    else {
      this.rolesCombo = new RolesCombo(false);
      rolesCombo.load(String.valueOf(oneUser.getRole().getRoleId()));
    }

    rolesCombo.setStartEndTags(XML_ROLE_START, XML_ROLE_END);

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".Constructor(): Leaving");
    }
  }

  //~ Methods ..........................................................................................................

  public UserProfile getModifiedUser() {
    return oneUser;
  }

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

    //User
    oneUser.toXML(xmlDoc);

    //Language
    languageCombo.toXML(xmlDoc);

    //Skin
    skinsCombo.toXML(xmlDoc);

    //Company combo
    companyCombo.toXML(xmlDoc);

    //Approver combo
    approverCombo.toXML(xmlDoc);

    //Roles combo
    rolesCombo.toXML(xmlDoc);

    //Title
    xmlDoc.append(XML_TITLE_START);
    xmlDoc.append(title);
    xmlDoc.append(XML_TITLE_END);

    //Mode
    xmlDoc.append(XML_MODE_START);
    xmlDoc.append(mode);
    xmlDoc.append(XML_MODE_END);

    /*Get end of document*/
    builder.getEndOfDocument(xmlDoc);

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer): xmlDoc = " + xmlDoc);
    }
  }
}