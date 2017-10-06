/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.logic.superadmin.contacts;

import com.intiro.itr.logic.contacts.Contacts;
import com.intiro.itr.util.combos.CompanyCombo;
import com.intiro.itr.util.combos.UsersCombo;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.DynamicXMLCarrier;
import com.intiro.itr.util.xml.XMLBuilder;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.log.IntiroLog;

public class ContactEdit extends DynamicXMLCarrier {

  // ~ Instance/static variables ........................................................................................

  static final String XML_COMPANY_END = "</company>";

  static final String XML_COMPANY_START = "<company>";

  static final String XML_KNOWN_BY_USER_END = "</knownbyuser>";

  static final String XML_KNOWN_BY_USER_START = "<knownbyuser>";

  static final String XML_MODE_END = "</mode>";

  static final String XML_MODE_START = "<mode>";

  static final String XML_TITLE_END = "</title>";

  static final String XML_TITLE_START = "<title>";

  // Combos
  protected CompanyCombo companyCombo = null;

  protected String contactId = "";

  // modes
  protected boolean inAddMode = false;

  protected boolean inDeleteMode = false;

  protected boolean inEditMode = false;

  protected boolean inViewMode = false;

  protected UsersCombo knownByUserCombo = null;

  protected String mode = "not set";

  protected Contacts oneContact = null;

  protected String title = "";

  // ~ Constructors .....................................................................................................

  public ContactEdit(UserProfile profile, String mode, String contactId) throws XMLBuilderException {
    super(profile);

    if (mode != null) {
      if (mode.indexOf("Add") != -1) { // Add mode
        inAddMode = true;
        mode = "Add";
        title = "Add";
      } else if (mode.indexOf("Edit") != -1) { // Edit mode
        inEditMode = true;
        mode = "Edit";
        title = "Edit";
      } else if (mode.indexOf("View") != -1) { // View mode
        inViewMode = true;
        mode = "View";
        title = "View";
      } else if (mode.indexOf("Delete") != -1) { // Delete mode
        inDeleteMode = true;
        mode = "Delete";
        title = "Delete";
      } else { // Default mode is set to View for safety.
        inViewMode = true;
        mode = "View";
        title = "View";
      }
    }

    this.mode = mode;
    this.contactId = contactId;

    // Load the user to edit, view, delete
    Contacts oneContact = new Contacts();

    if (inEditMode || inViewMode || inDeleteMode) {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): loading user with contactId = " + contactId + ", mode =  " + this.mode);
      }

      oneContact.load(contactId);
      oneContact.loadEmails();
      oneContact.loadPhoneNumbers();
    }

    this.oneContact = oneContact;

    // Company
    if (inAddMode) {
      this.companyCombo = new CompanyCombo(getUserProfile(), true);
      companyCombo.load();
    } else {
      this.companyCombo = new CompanyCombo(getUserProfile(), false);
      companyCombo.load("" + oneContact.getITR_CompanyId());
    }

    companyCombo.setStartEndTags(XML_COMPANY_START, XML_COMPANY_END);

    // Known by user
    if (inAddMode) {
      this.knownByUserCombo = new UsersCombo(getUserProfile(), true, true);
      knownByUserCombo.load();
    } else {
      this.knownByUserCombo = new UsersCombo(getUserProfile(), false, true);
      knownByUserCombo.load("" + oneContact.getKnownByUser_Id());
    }

    knownByUserCombo.setStartEndTags(XML_KNOWN_BY_USER_START, XML_KNOWN_BY_USER_END);
  }

  // ~ Methods ..........................................................................................................

  public Contacts getModifiedContact() {
    return oneContact;
  }

  /**
   * This is the method that will produce the XML. It will fill the xmlDoc with XML.
   * 
   * @param xmlDoc
   *          a StringBuffer to be filled with xml.
   */
  public void toXML(StringBuffer xmlDoc) throws Exception {
    XMLBuilder builder = new XMLBuilder();
    builder.getStartOfDocument(xmlDoc);

    // Contact
    oneContact.toXML(xmlDoc);

    // Company combo
    companyCombo.toXML(xmlDoc);

    // Known by combo
    knownByUserCombo.toXML(xmlDoc);

    // Title
    xmlDoc.append(XML_TITLE_START);
    xmlDoc.append(title);
    xmlDoc.append(XML_TITLE_END);

    // Mode
    xmlDoc.append(XML_MODE_START);
    xmlDoc.append(mode);
    xmlDoc.append(XML_MODE_END);

    /* Get end of document */
    builder.getEndOfDocument(xmlDoc);
  }

  /**
   * @return Returns the companyCombo.
   */
  public CompanyCombo getCompanyCombo() {
    return companyCombo;
  }

  /**
   * @return Returns the contactId.
   */
  public String getContactId() {
    return contactId;
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
   * @return Returns the knownByUserCombo.
   */
  public UsersCombo getKnownByUserCombo() {
    return knownByUserCombo;
  }

  /**
   * @return Returns the mode.
   */
  public String getMode() {
    return mode;
  }

  /**
   * @return Returns the oneContact.
   */
  public Contacts getOneContact() {
    return oneContact;
  }

  /**
   * @return Returns the title.
   */
  public String getTitle() {
    return title;
  }
}