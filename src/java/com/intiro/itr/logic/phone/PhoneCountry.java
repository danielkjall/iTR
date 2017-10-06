package com.intiro.itr.logic.phone;

import com.intiro.itr.db.DBConstants;
import com.intiro.itr.db.DBQueries;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.cache.ItrCache;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.log.IntiroLog;
import java.util.HashMap;
import java.util.Map;

public class PhoneCountry {

  public static final String CACHE_ALL_PHONECOUNTRY = "CACHE_ALL_PHONECOUNTRY";
  private static final String XML_PHONECOUNTRYCODE_END = "</countrycode>";
  private static final String XML_PHONECOUNTRYCODE_START = "<countrycode>";
  private static final String XML_PHONECOUNTRYID_END = "</countryid>";
  private static final String XML_PHONECOUNTRYID_START = "<countryid>";
  private static final String XML_PHONECOUNTRYNAME_END = "</countryname>";
  private static final String XML_PHONECOUNTRYNAME_START = "<countryname>";
  private static final String XML_PHONECOUNTRY_END = "</phonecountry>";
  private static final String XML_PHONECOUNTRY_START = "<phonecountry>";
  private static final String XML_PHONEREGION_END = "</phoneregion>";
  private static final String XML_PHONEREGION_START = "<phoneregion>";
  private String countryCode = null;
  private int countryId = -1;
  private String countryName = null;
  private int phoneContactId = -1;
  private String phoneDescription = null;
  private int phoneId = -1;
  private String phoneNumber = null;
  private int phoneUserId = -1;
  private String regionCode = null;
  private int regionId = -1;
  private String regionName = null;
  private boolean remove = false;

  /**
   * Constructor I for PhoneNumber.
   */
  public PhoneCountry() {
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryId(int countryId) {
    this.countryId = countryId;
  }

  public int getCountryId() {
    return countryId;
  }

  public void setCountryName(String countryName) {
    this.countryName = countryName;
  }

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
   *
   * @param countryId
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public void load(int countryId) throws XMLBuilderException {
    try {
      if (countryId == -1) {
        throw new Exception(getClass().getName() + ".load(int countryId): At least one input has to be not null.");
      }

      StringRecordset rs = DBQueries.getProxy().getPhoneCountry(countryId);

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
      IntiroLog.error(getClass(), getClass().getName() + ".load(int countryId): ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }
  }

  public static Map<Integer, PhoneCountry> loadAllPhoneCountries() throws XMLBuilderException {
    Map<Integer, PhoneCountry> retval = new HashMap<>();
    try {

      Map<Integer, PhoneCountry> cached = ItrCache.get(CACHE_ALL_PHONECOUNTRY);
      if (cached != null) {
        return cached;
      }

      StringRecordset rs = DBQueries.getProxy().getPhoneCountry(-1);

      if (!rs.getEOF()) {
        PhoneCountry pc = new PhoneCountry();
        pc.setCountryCode(rs.getField(DBConstants.PHONECOUNTRYCODE_COUNTRYCODE));
        pc.setCountryName(rs.getField(DBConstants.PHONECOUNTRYCODE_COUNTRYNAME));
        pc.setCountryId(Integer.parseInt(rs.getField(DBConstants.PHONECOUNTRYCODE_ID_PK)));
        retval.put(pc.getCountryId(), pc);
        rs.moveNext();

        if (IntiroLog.d()) {
          IntiroLog.detail(PhoneCountry.class, PhoneCountry.class.getName() + ".loadAllPhoneCountries()");
        }
      }
    } catch (Exception e) {
      IntiroLog.error(PhoneCountry.class, PhoneCountry.class.getName() + ".loadAllPhoneCountries(): ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }

    final int TenHours = 1 * 60 * 60 * 10;
    ItrCache.put(CACHE_ALL_PHONECOUNTRY, retval, TenHours);
    return retval;
  }

  public void save() throws Exception {
  }

  @Override
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
