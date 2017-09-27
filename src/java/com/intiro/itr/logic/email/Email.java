/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.logic.email;

import java.util.Vector;

import com.intiro.itr.db.DBConstants;
import com.intiro.itr.db.DBExecute;
import com.intiro.itr.db.DBQueries;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.toolbox.log.IntiroLog;

public class Email {

  //~ Instance/static variables ........................................................................................

  static final String XML_COLORINDEX_END = "</colorindex>";
  static final String XML_COLORINDEX_START = "<colorindex>";
  static final String XML_EMAILADDRESS_END = "</emailaddress>";
  static final String XML_EMAILADDRESS_START = "<emailaddress>";
  static final String XML_EMAILCONTACTID_END = "</emailcontactid>";
  static final String XML_EMAILCONTACTID_START = "<emailcontactid>";
  static final String XML_EMAILDESC_END = "</emaildesc>";
  static final String XML_EMAILDESC_START = "<emaildesc>";
  static final String XML_EMAILID_END = "</emailid>";
  static final String XML_EMAILID_START = "<emailid>";
  static final String XML_EMAILUSERID_END = "</emailuserid>";
  static final String XML_EMAILUSERID_START = "<emailuserid>";
  static final String XML_EMAIL_END = "</email>";
  static final String XML_EMAIL_START = "<email>";
  static final String XML_INDEX_END = "</i>";
  static final String XML_INDEX_START = "<i>";
  String address = null;
  int contactId = -1;
  String description = null;
  int emailId = -1;
  boolean remove = false;
  int userId = -1;

  //~ Constructors .....................................................................................................

  public Email() {
    //empty
  }

  //~ Methods ..........................................................................................................

  /**
   * Sets the email address.
   *
   * @param   a String containing the address
   */
  public void setAddress(String addr) {
    this.address = addr;
  }

  /**
   * Get the email address.
   *
   * @return   an String containing the email address.
   */
  public String getAddress() {
    return (address==null) ? "":address;
  }

  /**
   * Sets the contact Id.
   *
   * @param   an int containing the contact Id
   */
  public void setContactId(int contactId) {
    this.contactId = contactId;
  }

  /**
   * Get the contact Id.
   *
   * @return   an int containing the contact Id.
   */
  public int getContactId() {
    return contactId;
  }

  /**
   * Sets the email description.
   *
   * @param   a String containing the description
   */
  public void setDescription(String desc) {
    this.description = desc;
  }

  /**
   * Get the email description.
   *
   * @return   an String containing the email description.
   */
  public String getDescription() {
    return (description==null) ? "":description;
  }

  /**
   * Sets the email Id.
   *
   * @param   an int containing the Id
   */
  public void setId(int Id) {
    this.emailId = Id;
  }

  /**
   * Get the email Id.
   *
   * @return   an int containing the email Id.
   */
  public int getId() {
    return emailId;
  }

  public void setNotRemoved() {
    this.remove = false;
  }

  public void setRemoved() {
    this.remove = true;
  }

  public boolean isRemoved() {
    return remove;
  }

  /**
   * Sets the user Id.
   *
   * @param   an int containing the user Id
   */
  public void setUserId(int userId) {
    this.userId = userId;
  }

  /**
   * Get the user Id.
   *
   * @return   an int containing the user Id.
   */
  public int getUserId() {
    return userId;
  }

  /**
   * Load the emails for the specified userId. The userId can be a Contact id as well.
   */
  public static Vector <Email> load(int userId, int contactId, int emailId) throws XMLBuilderException {
    Vector <Email> retval = new Vector <Email> ();

    try {
      if (userId == -1 && contactId == -1 && emailId == -1) { throw new Exception("Email.load(String userId, String contactId, String emailId): At least one input has to be not null."); }

      StringRecordset rs = new DBQueries().getEmails(userId, contactId, emailId);
      String tmpContact;
      String tmpUser;

      while (!rs.getEOF()) {
        Email em = new Email();
        em.setId(Integer.parseInt(rs.getField(DBConstants.EMAIL_ID_PK)));
        em.setAddress(rs.getField(DBConstants.EMAIL_EMAIL));
        em.setDescription(rs.getField(DBConstants.EMAIL_DESCRIPTION));
        tmpContact = rs.getField(DBConstants.EMAIL_CONTACTID);

        if (tmpContact.length() > 0) {
          em.setContactId(Integer.parseInt(tmpContact));
        }

        tmpUser = rs.getField(DBConstants.EMAIL_USERID_FK);

        if (tmpUser.length() > 0) {
          em.setUserId(Integer.parseInt(tmpUser));
        }

        retval.add(em);
        rs.moveNext();
      }
    } catch (Exception e) {
      if (IntiroLog.e()) {
        IntiroLog.error(Email.class, "Email.load(String userId, String contactId, String emailId): ERROR FROM DATABASE, exception = " + e.getMessage());
      }

      throw new XMLBuilderException(e.getMessage());
    }

    return retval;
  }

  public Email cloneEmail() {
    Email retval = new Email();
    retval.setDescription(getDescription());
    retval.setAddress(getAddress());
    retval.setContactId(getContactId());
    retval.setUserId(getUserId());

    return retval;
  }

  /**
   * Save the emails for either the contact or the user.
   */
  public void save() throws Exception {
    if (getId() == -1) {
      try {
        DBQueries dbQueries = new DBQueries();
        StringRecordset rs = dbQueries.addEmailAndGetId(this);

        if (!rs.getEOF()) {
          setId(Integer.parseInt(rs.getField("maxId")));
        }
        else {
          throw new XMLBuilderException(getClass().getName() + ".save(): Could not make and find a new email.");
        }
      } catch (Exception e) {
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".save(): ERROR FROM DATABASE, exception = " + e.getMessage());
        }

        throw new Exception(e.getMessage());
      }
    }
    else if (remove && getId() != -1) {
      try {
        DBExecute dbExecute = new DBExecute();
        dbExecute.deleteEmail(this);
      } catch (Exception e) {
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".save(): ERROR FROM DATABASE, exception = " + e.getMessage());
        }

        throw new Exception(e.getMessage());
      }
    }
    else {
      try {
        DBExecute dbExecute = new DBExecute();
        dbExecute.updateEmail(this);
      } catch (Exception e) {
        if (IntiroLog.e()) {
          IntiroLog.error(getClass(), getClass().getName() + ".save(): ERROR FROM DATABASE, exception = " + e.getMessage());
        }

        throw new Exception(e.getMessage());
      }
    }
  }

  public void toXML(StringBuffer xmlDoc, int index, int colorIndex) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer xmlDoc): Entering");
    }

    //EMAIL
    xmlDoc.append(XML_EMAIL_START);
    xmlDoc.append(XML_INDEX_START);
    xmlDoc.append(index);
    xmlDoc.append(XML_INDEX_END);
    xmlDoc.append(XML_COLORINDEX_START);
    xmlDoc.append(colorIndex);
    xmlDoc.append(XML_COLORINDEX_END);
    xmlDoc.append(XML_EMAILID_START);
    xmlDoc.append(getId());
    xmlDoc.append(XML_EMAILID_END);
    xmlDoc.append(XML_EMAILADDRESS_START);

    if (getAddress() != null) {
      xmlDoc.append(getAddress());
    }

    xmlDoc.append(XML_EMAILADDRESS_END);
    xmlDoc.append(XML_EMAILDESC_START);

    if (getDescription() != null) {
      xmlDoc.append(getDescription());
    }

    xmlDoc.append(XML_EMAILDESC_END);
    xmlDoc.append(XML_EMAILCONTACTID_START);
    xmlDoc.append(getContactId());
    xmlDoc.append(XML_EMAILCONTACTID_END);
    xmlDoc.append(XML_EMAILUSERID_START);
    xmlDoc.append(getUserId());
    xmlDoc.append(XML_EMAILUSERID_END);
    xmlDoc.append(XML_EMAIL_END);
  }
}