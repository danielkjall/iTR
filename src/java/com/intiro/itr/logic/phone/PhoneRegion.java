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
 * @author Daniel Kjall
 * @version 1.1
 */
public class PhoneRegion {

  //~ Instance/static variables ........................................................................................

  static final String XML_PHONEREGIONCODE_END = "</regioncode>";
  static final String XML_PHONEREGIONCODE_START = "<regioncode>";
  static final String XML_PHONEREGIONID_END = "</regionid>";
  static final String XML_PHONEREGIONID_START = "<regionid>";
  static final String XML_PHONEREGIONNAME_END = "</regionname>";
  static final String XML_PHONEREGIONNAME_START = "<regionname>";
  static final String XML_PHONEREGION_END = "</phoneregion>";
  static final String XML_PHONEREGION_START = "<phoneregion>";
  PhoneCountry country = new PhoneCountry();
  int countryId = -1;
  String regionCode = null;
  int regionId = -1;
  String regionName = null;
  boolean remove = false;

  //~ Constructors .....................................................................................................

  /**
   * Constructor I for PhoneRegion.
   */
  public PhoneRegion() {
    //empty
  }

  //~ Methods ..........................................................................................................

  /**
   * Sets the country of the  on phone number.
   *
   * @param      country, a PhoneCountry specifying the country.
   */
  public void setCountry(PhoneCountry country) {
    this.country = country;
  }

  /**
   * Gets the country of the phone number.
   *
   * @return      a PhoneCountry, specifying the country.
   */
  public PhoneCountry getCountry() {
    return country;
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
   * Sets the regionCode of the logged on user.
   *
   * @param      regionCode, an String specifying the regionCode.
   */
  public void setRegionCode(String regionCode) {
    this.regionCode = regionCode;
  }

  /**
   * Gets the regionCode of the logged on user.
   *
   * @return      a String, specifying the regionCode.
   */
  public String getRegionCode() {
    return regionCode;
  }

  /**
   * Sets the regionId of the  on phone number.
   *
   * @param      regionId, an int specifying the regionId.
   */
  public void setRegionId(int regionId) {
    this.regionId = regionId;
  }

  /**
   * Gets the regionId of the phone number.
   *
   * @return      an int, specifying the regionId.
   */
  public int getRegionId() {
    return regionId;
  }

  /**
   * Sets the regionName of the logged on user.
   *
   * @param      regionName, an String specifying the regionName.
   */
  public void setRegionName(String regionName) {
    this.regionName = regionName;
  }

  /**
   * Gets the regionName of the logged on user.
   *
   * @return      a String, specifying the regionName.
   */
  public String getRegionName() {
    return regionName;
  }

  public void setRemoved() {
    this.remove = true;
  }

  public boolean isRemoved() {
    return remove;
  }

  public PhoneRegion cloneRegion() {
    PhoneRegion retval = new PhoneRegion();
    retval.setRegionCode(getRegionCode());
    retval.setRegionName(getRegionName());
    retval.setRegionId(getRegionId());
    retval.setCountryId(getCountryId());
    retval.setCountry(getCountry().clonePhoneCountry());

    try {
      retval.getCountry().load(retval.getCountry().getCountryId());
    } catch (Exception e) {
      if (IntiroLog.ce()) {
        IntiroLog.criticalError(getClass(), getClass().getName() + ".cloneRegion(): Could not load country: ", e);
      }
    }

    return retval;
  }

  /**
   * Load the Phone region for the specified regionid or countryId.
   */
  public void load(int regionId) throws XMLBuilderException {
    try {
      if (regionId == -1) { throw new Exception(getClass().getName() + ".load(int regionId): At least one input has to be not null."); }

      StringRecordset rs = new DBQueries().getPhoneRegion(regionId);

      if (!rs.getEOF()) {
        setRegionCode(rs.getField(DBConstants.PHONEREGIONCODE_REGIONCODE));
        setRegionName(rs.getField(DBConstants.PHONEREGIONCODE_REGIONNAME));
        setRegionId(regionId);
        setCountryId(Integer.parseInt(rs.getField(DBConstants.PHONEREGIONCODE_PHONECOUNTRYCODEID_FK)));

        PhoneCountry oneCountry = new PhoneCountry();
        oneCountry.load(getCountryId());
        setCountry(oneCountry);
        rs.moveNext();
      }
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".load(int regionId): toString() = " + toString());
      }
    } catch (Exception e) {
      if (IntiroLog.e()) {
        IntiroLog.error(getClass(), getClass().getName() + ".load(int regionId): ERROR FROM DATABASE, exception = " + e.getMessage());
      }

      throw new XMLBuilderException(e.getMessage());
    }
  }

  /**
   * Save the phone numbers for either the contact or the user.
   */
  public void save() throws Exception {

    /*
     if(getRegionId() == -1) {
     try {
     DBQueries dbQueries = new DBQueries();
     StringRecordset rs = dbQueries.addPhoneRegion(this);
     if(!rs.getEOF()) {
     setRegionId( Integer.parseInt(rs.getField("maxId")) );
     }
     else {
     throw new XMLBuilderException(getClass().getName()+".save(): Could not make and find a new phone region.");
     }
     } catch(Exception e) {
     IntiroLog.detail(getClass().getName()+"save(): ERROR FROM DATABASE, exception = " + e.getMessage());
     throw new Exception(e.getMessage());
     }
     }
     else if(isRemoved() && getRegionId() != -1) {
     try {
     DBExecute dbExecute = new DBExecute();
     dbExecute.deletePhoneRegion(this);
     } catch(Exception e) {
     IntiroLog.detail(getClass().getName()+".save(): ERROR FROM DATABASE, exception = " + e.getMessage());
     throw new Exception(e.getMessage());
     }
     }
     else {*/
    try {
      DBExecute dbExecute = new DBExecute();
      dbExecute.updatePhoneRegion(this);
    } catch (Exception e) {
      if (IntiroLog.e()) {
        IntiroLog.error(getClass(), getClass().getName() + ".save(): ERROR FROM DATABASE, exception = " + e.getMessage());
      }

      throw new Exception(e.getMessage());
    }

    //}
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

    //PHONE REGION
    xmlDoc.append(XML_PHONEREGION_START);
    xmlDoc.append(XML_PHONEREGIONCODE_START);

    if (getRegionCode() != null) {
      xmlDoc.append(getRegionCode());
    }

    xmlDoc.append(XML_PHONEREGIONCODE_END);
    xmlDoc.append(XML_PHONEREGIONNAME_START);

    if (getRegionName() != null) {
      xmlDoc.append(getRegionName());
    }

    xmlDoc.append(XML_PHONEREGIONNAME_END);
    xmlDoc.append(XML_PHONEREGIONID_START);
    xmlDoc.append(getRegionId());
    xmlDoc.append(XML_PHONEREGIONID_END);

    if (getCountry() != null) {
      getCountry().toXML(xmlDoc);
    }

    xmlDoc.append(XML_PHONEREGION_END);
  }
}