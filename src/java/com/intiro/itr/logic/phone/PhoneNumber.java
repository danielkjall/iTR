/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.logic.phone;

import java.util.Vector;

import com.intiro.itr.db.DBConstants;
import com.intiro.itr.db.DBExecute;
import com.intiro.itr.db.DBQueries;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.toolbox.log.IntiroLog;

/**
 * Title:        ITR
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Intiro Development AB
 * @author Olof Altenstedt, Daniel Kjall
 * @version 1.1
 */
public class PhoneNumber {

  //~ Instance/static variables ........................................................................................

  static final String XML_COLORINDEX_END = "</colorindex>";
  static final String XML_COLORINDEX_START = "<colorindex>";
  static final String XML_INDEX_END = "</i>";
  static final String XML_INDEX_START = "<i>";
  static final String XML_PHONECONTACTID_END = "</phonecontactid>";
  static final String XML_PHONECONTACTID_START = "<phonecontactid>";
  static final String XML_PHONEDESCRIPTION_END = "</phonedesc>";
  static final String XML_PHONEDESCRIPTION_START = "<phonedesc>";
  static final String XML_PHONEID_END = "</phoneid>";
  static final String XML_PHONEID_START = "<phoneid>";
  static final String XML_PHONENUMBER_END = "</phoneno>";
  static final String XML_PHONENUMBER_START = "<phoneno>";
  static final String XML_PHONEUSERID_END = "</phoneuserid>";
  static final String XML_PHONEUSERID_START = "<phoneuserid>";
  static final String XML_PHONE_END = "</phone>";

  //Phone
  static final String XML_PHONE_START = "<phone>";
  int phoneContactId = -1;
  String phoneDescription = null;
  int phoneId = -1;
  String phoneNumber = null;
  int phoneUserId = -1;
  PhoneRegion region = new PhoneRegion();
  int regionId = -1;
  boolean remove = false;

  //~ Constructors .....................................................................................................

  /**
   * Constructor I for PhoneNumber.
   */
  public PhoneNumber() {
    //empty
  }

  //~ Methods ..........................................................................................................

  /**
   * Load the Phone numbers for the specified userId, contactId or phoneId.
   */
  public static Vector <PhoneNumber> load(int userId, int contactId, int phoneId) throws XMLBuilderException {
    Vector <PhoneNumber> retval = new Vector <PhoneNumber> ();

    try {
      if (userId == -1 && contactId == -1 && phoneId == -1) { throw new Exception("PhoneNumber.load(String userId, String contactId, String phoneId): At least one input has to be not null."); }

      StringRecordset rs = new DBQueries().getPhoneNumbers(userId, contactId, phoneId);
      String tmpContact;
      String tmpUser;

      while (!rs.getEOF()) {
        PhoneNumber pn = new PhoneNumber();
        pn.setPhoneId(Integer.parseInt(rs.getField(DBConstants.PHONE_ID_PK)));
        pn.setPhoneNumber(rs.getField(DBConstants.PHONE_PHONENUMBER));
        pn.setPhoneDescription(rs.getField(DBConstants.PHONE_DESCRIPTION));
        tmpUser = rs.getField(DBConstants.PHONE_USERID_FK);

        if (tmpUser.length() > 0) {
          pn.setPhoneUserId(Integer.parseInt(tmpUser));
        }

        tmpContact = rs.getField(DBConstants.PHONE_CONTACTID_FK);

        if (tmpContact.length() > 0) {
          pn.setPhoneContactId(Integer.parseInt(tmpContact));
        }

        pn.setPhoneRegionId(Integer.parseInt(rs.getField(DBConstants.PHONE_REGIONID_FK)));

        PhoneRegion oneRegion = new PhoneRegion();
        oneRegion.load(pn.getPhoneRegionId());
        pn.setRegion(oneRegion);
        retval.add(pn);
        rs.moveNext();
      }
    } catch (Exception e) {
      IntiroLog.info(PhoneNumber.class, ".load(String userId, String contactId, String phoneId): ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }

    return retval;
  }

  public void setNotRemoved() {
    this.remove = false;
  }

  /**
   * Sets the phoneContactId of the logged on user.
   *
   * @param        phoneContactId, an int specifying the phoneContactId.
   */
  public void setPhoneContactId(int phoneContactId) {
    this.phoneContactId = phoneContactId;
  }

  /**
   * Gets the phoneContactId of the logged on user.
   *
   * @return        an int, specifying the phoneContactId.
   */
  public int getPhoneContactId() {
    return phoneContactId;
  }

  /**
   * Sets the phoneDescription of the logged on user.
   *
   * @param        phoneDescription, an String specifying the phoneDescription.
   */
  public void setPhoneDescription(String phoneDescription) {
    this.phoneDescription = phoneDescription;
  }

  /**
   * Gets the phoneDescription of the logged on user.
   *
   * @return        a String, specifying the phoneDescription.
   */
  public String getPhoneDescription() {
    return (phoneDescription==null)?"":phoneDescription;
  }

  /**
   * Sets the phoneId of the logged on user.
   *
   * @param      phoneId, an int specifying the phoneId.
   */
  public void setPhoneId(int phoneId) {
    this.phoneId = phoneId;
  }

  /**
   * Gets the PhoneId of the logged on user.
   *
   * @return      an int, specifying the PhoneId.
   */
  public int getPhoneId() {
    return phoneId;
  }

  /**
   * Sets the phoneNumber of the logged on user.
   *
   * @param        phoneNumber, an String specifying the phoneNumber.
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * Gets the phoneNumber of the logged on user.
   *
   * @return        a String, specifying the phoneNumber.
   */
  public String getPhoneNumber() {
    return (phoneNumber==null)?"":phoneNumber;
  }

  /**
   * Sets the regionId of the  on phone number.
   *
   * @param      regionId, an int specifying the regionId.
   */
  public void setPhoneRegionId(int regionId) {
    this.regionId = regionId;
  }

  /**
   * Gets the regionId of the phone number.
   *
   * @return      an int, specifying the regionId.
   */
  public int getPhoneRegionId() {
    return regionId;
  }

  /**
   * Sets the phoneUserId of the logged on user.
   *
   * @param        phoneUserId, an int specifying the phoneUserId.
   */
  public void setPhoneUserId(int phoneUserId) {
    this.phoneUserId = phoneUserId;
  }

  /**
   * Gets the phoneUserId of the logged on user.
   *
   * @return        an int, specifying the phoneUserId.
   */
  public int getPhoneUserId() {
    return phoneUserId;
  }

  /**
   * Sets the region of the logged on user.
   *
   * @param      PhoneRegion, a PhoneRegion specifying the region.
   */
  public void setRegion(PhoneRegion region) {
    this.region = region;
  }

  /**
   * Gets the PhoneRegion.
   *
   * @return      a PhoneRegion, specifying the region.
   */
  public PhoneRegion getRegion() {
    return region;
  }

  public void setRemoved() {
    this.remove = true;
  }

  public boolean isRemoved() {
    return remove;
  }

  public PhoneNumber clonePhoneNumber() {
    PhoneNumber retval = new PhoneNumber();
    retval.setPhoneDescription(getPhoneDescription());
    retval.setPhoneNumber(getPhoneNumber());
    retval.setPhoneUserId(getPhoneUserId());
    retval.setPhoneContactId(getPhoneContactId());
    retval.setRegion(getRegion().cloneRegion());

    try {
      retval.getRegion().load(retval.getRegion().getRegionId());
    } catch (Exception e) {
      if (IntiroLog.ce()) {
        IntiroLog.criticalError(getClass(), getClass().getName() + ".clonePhoneNumber(): Could not load region: ", e);
      }
    }

    retval.setPhoneRegionId(getPhoneRegionId());

    return retval;
  }

  /**
   * Save the phone numbers for either the contact or the user.
   */
  public void save() throws Exception {
    if (getPhoneId() == -1) {
      try {
        DBQueries dbQueries = new DBQueries();
        StringRecordset rs = dbQueries.addPhoneNumberAndGetId(this);

        if (!rs.getEOF()) {
          setPhoneId(Integer.parseInt(rs.getField("maxId")));
        }
        else {
          throw new XMLBuilderException(getClass().getName() + ".save(): Could not make and find a new phone.");
        }
      } catch (Exception e) {
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".save(): ERROR FROM DATABASE, exception = " + e.getMessage());
        }

        throw new Exception(e.getMessage());
      }
    }
    else if (isRemoved() && getPhoneId() != -1) {
      try {
        DBExecute dbExecute = new DBExecute();
        dbExecute.deletePhoneNumber(this);
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
        dbExecute.updatePhoneNumber(this);
      } catch (Exception e) {
        if (IntiroLog.e()) {
          IntiroLog.error(getClass(), getClass().getName() + ".save(): ERROR FROM DATABASE, exception = " + e.getMessage());
        }

        throw new Exception(e.getMessage());
      }
    }

    //Save the region
    //getRegion().save();
  }

  public String toString() {
    StringBuffer retval = new StringBuffer();
    this.toXML(retval, -1, -1);

    return retval.toString();
  }

  public void toXML(StringBuffer xmlDoc, int index, int colorIndex) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer xmlDoc): Entering");
    }

    //PHONE
    xmlDoc.append(XML_PHONE_START);
    xmlDoc.append(XML_INDEX_START);
    xmlDoc.append(index);
    xmlDoc.append(XML_INDEX_END);
    xmlDoc.append(XML_COLORINDEX_START);
    xmlDoc.append(colorIndex);
    xmlDoc.append(XML_COLORINDEX_END);
    xmlDoc.append(XML_PHONEID_START);
    xmlDoc.append(getPhoneId());
    xmlDoc.append(XML_PHONEID_END);
    xmlDoc.append(XML_PHONENUMBER_START);

    if (getPhoneNumber() != null) {
      xmlDoc.append(getPhoneNumber());
    }

    xmlDoc.append(XML_PHONENUMBER_END);
    xmlDoc.append(XML_PHONEDESCRIPTION_START);

    if (getPhoneDescription() != null) {
      xmlDoc.append(getPhoneDescription());
    }

    xmlDoc.append(XML_PHONEDESCRIPTION_END);
    xmlDoc.append(XML_PHONEUSERID_START);
    xmlDoc.append(getPhoneUserId());
    xmlDoc.append(XML_PHONEUSERID_END);
    xmlDoc.append(XML_PHONECONTACTID_START);
    xmlDoc.append(getPhoneContactId());
    xmlDoc.append(XML_PHONECONTACTID_END);

    if (getRegion() != null) {
      getRegion().toXML(xmlDoc);
    }

    xmlDoc.append(XML_PHONE_END);
  }
}