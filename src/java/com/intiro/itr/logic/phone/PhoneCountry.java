/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.logic.phone;

import com.intiro.itr.db.DBConstants;
import com.intiro.itr.db.DBQueries;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.toolbox.log.IntiroLog;

/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
public class PhoneCountry {

  //~ Instance/static variables ........................................................................................

  static final String XML_PHONECOUNTRYCODE_END = "</countrycode>";
  static final String XML_PHONECOUNTRYCODE_START = "<countrycode>";
  static final String XML_PHONECOUNTRYID_END = "</countryid>";
  static final String XML_PHONECOUNTRYID_START = "<countryid>";
  static final String XML_PHONECOUNTRYNAME_END = "</countryname>";
  static final String XML_PHONECOUNTRYNAME_START = "<countryname>";
  static final String XML_PHONECOUNTRY_END = "</phonecountry>";
  static final String XML_PHONECOUNTRY_START = "<phonecountry>";
  static final String XML_PHONEREGION_END = "</phoneregion>";
  static final String XML_PHONEREGION_START = "<phoneregion>";
  String countryCode = null;
  int countryId = -1;
  String countryName = null;
  int phoneContactId = -1;
  String phoneDescription = null;
  int phoneId = -1;
  String phoneNumber = null;
  int phoneUserId = -1;
  String regionCode = null;
  int regionId = -1;
  String regionName = null;
  boolean remove = false;

  //~ Constructors .....................................................................................................

  /**
   * Constructor I for PhoneNumber.
   */
  public PhoneCountry() {
    //empty
  }

  //~ Methods ..........................................................................................................

  /**
   * Sets the countryCode of the logged on user.
   *
   * @param      countryCode, an String specifying the countryCode.
   */
  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  /**
   * Gets the countryCode of the logged on user.
   *
   * @return      a String, specifying the countryCode.
   */
  public String getCountryCode() {
    return countryCode;
  }

  /**
   * Sets the countryId of the  on phone number.
   *
   * @param      countryId, an int specifying the countryId.
   */
  public void setCountryId(int countryId) {
    this.countryId = countryId;
  }

  /**
   * Gets the countryId of the phone number.
   *
   * @return      an int, specifying the countryId.
   */
  public int getCountryId() {
    return countryId;
  }

  /**
   * Sets the countryName of the logged on user.
   *
   * @param      countryName, an String specifying the countryName.
   */
  public void setCountryName(String countryName) {
    this.countryName = countryName;
  }

  /**
   * Gets the countryName of the logged on user.
   *
   * @return      a String, specifying the countryName.
   */
  public String getCountryName() {
    return countryName;
  }

  public void setRemoved() {
    this.remove = true;
  }

  public boolean isRemoved() {
    return remove;
  }

  public PhoneCountry clonePhoneCountry() {
    PhoneCountry retval = new PhoneCountry();
    retval.setCountryCode(getCountryCode());
    retval.setCountryName(getCountryName());
    retval.setCountryId(getCountryId());

    return retval;
  }

  /**
   * Load the Phone numbers for the specified userId, contactId or phoneId.
   */
  public void load(int countryId) throws XMLBuilderException {
    try {
      if (countryId == -1) { throw new Exception(getClass().getName() + ".load(int countryId): At least one input has to be not null."); }

      StringRecordset rs = new DBQueries().getPhoneCountry(countryId);

      if (!rs.getEOF()) {
        setCountryCode(rs.getField(DBConstants.PHONECOUNTRYCODE_COUNTRYCODE));
        setCountryName(rs.getField(DBConstants.PHONECOUNTRYCODE_COUNTRYNAME));
        setCountryId(countryId);
        rs.moveNext();

        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".load(int countryId): toString() = " + toString());
        }
      }
    } catch (Exception e) {
      if (IntiroLog.e()) {
        IntiroLog.error(getClass(), getClass().getName() + ".load(int countryId): ERROR FROM DATABASE, exception = " + e.getMessage());
      }

      throw new XMLBuilderException(e.getMessage());
    }
  }

  /**
   * Save the phone country.
   */
  public void save() throws Exception {

    //not implemented yet
  }

  public String toString() {
    StringBuffer retval = new StringBuffer();
    this.toXML(retval);

    return retval.toString();
  }

  public void toXML(StringBuffer xmlDoc) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer xmlDoc): Entering");
    }

    //PHONE COUNTRY
    xmlDoc.append(XML_PHONECOUNTRY_START);
    xmlDoc.append(XML_PHONECOUNTRYCODE_START);

    if (getCountryCode() != null) {
      xmlDoc.append(getCountryCode());
    }

    xmlDoc.append(XML_PHONECOUNTRYCODE_END);
    xmlDoc.append(XML_PHONECOUNTRYNAME_START);

    if (getCountryName() != null) {
      xmlDoc.append(getCountryName());
    }

    xmlDoc.append(XML_PHONECOUNTRYNAME_END);
    xmlDoc.append(XML_PHONECOUNTRYID_START);
    xmlDoc.append(getCountryId());
    xmlDoc.append(XML_PHONECOUNTRYID_END);
    xmlDoc.append(XML_PHONECOUNTRY_END);
  }
}