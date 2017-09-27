/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.logic.superadmin.users;

import java.util.Vector;

import com.intiro.itr.logic.contacts.Contacts;
import com.intiro.itr.logic.email.Email;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.DynamicXMLCarrier;
import com.intiro.itr.util.xml.XMLBuilder;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.toolbox.log.IntiroLog;

public class EmailEditor extends DynamicXMLCarrier {

  //~ Instance/static variables ........................................................................................

  static final String XML_FIRSTNAME_END = "</firstname>";
  static final String XML_FIRSTNAME_START = "<firstname>";
  static final String XML_LASTNAME_END = "</lastname>";
  static final String XML_LASTNAME_START = "<lastname>";
  static final String XML_MODEDESC_END = "</modedesc>";
  static final String XML_MODEDESC_START = "<modedesc>";
  static final String XML_MODE_END = "</mode>";
  static final String XML_MODE_START = "<mode>";
  static final String XML_NEWEMAIL_END = "</newemail>";
  static final String XML_NEWEMAIL_START = "<newemail>";
  static final String XML_USERID_END = "</userid>";
  static final String XML_USERID_START = "<userid>";
  static final String XML_USER_END = "</user>";
  static final String XML_USER_START = "<user>";
  protected Vector <Email> emails = new Vector <Email> ();
  Email newEmail = new Email();
  int userId = -1;
  private boolean editingRow = false;
  private String firstName = "";
  private String lastName = "";
  private String mode = "users";
  private String modeDesc = "";

  //~ Constructors .....................................................................................................

  public EmailEditor(UserProfile profile, int userId, String mode) throws XMLBuilderException {
    super(profile);

    if (mode.equals("contacts")) {
      Contacts tmpContact = new Contacts();
      tmpContact.load(String.valueOf(userId));
      firstName = tmpContact.getFirstName();
      lastName = tmpContact.getLastName();
      modeDesc = "contacts";
    }
    else {
      UserProfile tmpProfile = new UserProfile();
      tmpProfile.load(userId);
      firstName = tmpProfile.getFirstName();
      lastName = tmpProfile.getLastName();
      modeDesc = "user";
    }

    this.userId = userId;
    this.mode = mode;

    if (mode.equals("contacts")) {
      this.emails = Email.load(-1, userId, -1);
    }
    else {
      this.emails = Email.load(userId, -1, -1);
    }
  }

  //~ Methods ..........................................................................................................

  /**
   * Set editingRow.
   */
  public void setEditingRow(boolean editingRow) {
    this.editingRow = editingRow;
  }

  /**
   * Get editingRow.
   */
  public boolean getEditingRow() {
    return editingRow;
  }

  /**
   * Get Email.
   */
  public Email getEmail(int index) {
    return emails.get(index);
  }

  /**
   * Get Emails.
   */
  public Vector getEmails() {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".getEmails(): Entering");
    }

    return emails;
  }

  /**
   * set new Email.
   */
  public void setNewEmail(Email newEmail) {
    this.newEmail = newEmail;
  }

  /**
   * Get new Email.
   */
  public Email getNewEmail() {
    return newEmail;
  }

  /**
   * Add Email.
   */
  public void addEmail() {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".addEmail(): Entering");
    }
    if (mode.equals("contacts")) {
      newEmail.setContactId(userId);
    }
    else {
      newEmail.setUserId(userId);
    }

    Email oneEmail = null;

    if (this.getEditingRow()) {
      for (int i = 0; i < emails.size(); i++) {
        oneEmail = emails.get(i);

        if ((oneEmail != null) && (oneEmail.getId() == newEmail.getId())) {
          oneEmail.setNotRemoved();
        }
      }
    }
    else {
      emails.add(newEmail.cloneEmail());
    }

    newEmail = new Email();
  }

  /**
   * Remove Email.
   */
  public void removeEmail(int index) {
    getEmail(index).setRemoved();
  }

  /**
   * Save emails.
   */
  public void save() throws XMLBuilderException {
    try {
      Email oneEmail = null;

      for (int i = 0; i < emails.size(); i++) {
        oneEmail = emails.get(i);

        if (oneEmail != null) {
          oneEmail.save();
        }
      }
    } catch (Exception e) {
      if (IntiroLog.e()) {
        IntiroLog.error(getClass(), getClass().getName() + ".save(String weekId): ERROR FROM DATABASE, exception = " + e.getMessage());
      }

      throw new XMLBuilderException(e.getMessage());
    }
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

    //New email
    xmlDoc.append(XML_NEWEMAIL_START);

    if (getNewEmail() != null) {
      getNewEmail().toXML(xmlDoc, -1, -1);
    }

    xmlDoc.append(XML_NEWEMAIL_END);

    /*User data*/
    xmlDoc.append(XML_USER_START);

    /*User id*/
    xmlDoc.append(XML_USERID_START);
    xmlDoc.append(userId);
    xmlDoc.append(XML_USERID_END);

    /*First name*/
    xmlDoc.append(XML_FIRSTNAME_START);
    xmlDoc.append(firstName);
    xmlDoc.append(XML_FIRSTNAME_END);

    /*Last name*/
    xmlDoc.append(XML_LASTNAME_START);
    xmlDoc.append(lastName);
    xmlDoc.append(XML_LASTNAME_END);
    xmlDoc.append(XML_USER_END);

    /*Mode Description*/
    xmlDoc.append(XML_MODEDESC_START);
    xmlDoc.append(modeDesc);
    xmlDoc.append(XML_MODEDESC_END);

    /*mode*/
    xmlDoc.append(XML_MODE_START);
    xmlDoc.append(mode);
    xmlDoc.append(XML_MODE_END);

    //Emails
    Email oneEmail = null;

    //Loop through all the Emails
    int j = 0;

    for (int i = 0; i < emails.size(); i++) {
      oneEmail = emails.get(i);

      if (oneEmail != null && !oneEmail.isRemoved()) {
        oneEmail.toXML(xmlDoc, i, j++);
      }
      else if (oneEmail != null) {
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".toXML(): oneEmail.isRemoved() = " + oneEmail.isRemoved());
        }
      }
    }

    /*Get end of document*/
    builder.getEndOfDocument(xmlDoc);

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer): xmlDoc = " + xmlDoc.toString());
    }
  }

  /**
   * Set Emails.
   */
  void setEmails(Vector <Email> emails) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".setEmails(Vector emails): Entering");
    }

    this.emails = emails;
  }
  /**
   * @return Returns the firstName.
   */
  public String getFirstName() {
    return firstName;
  }
  /**
   * @return Returns the lastName.
   */
  public String getLastName() {
    return lastName;
  }
  /**
   * @return Returns the mode.
   */
  public String getMode() {
    return mode;
  }
  /**
   * @return Returns the modeDesc.
   */
  public String getModeDesc() {
    return modeDesc;
  }
  /**
   * @return Returns the userId.
   */
  public int getUserId() {
    return userId;
  }
}