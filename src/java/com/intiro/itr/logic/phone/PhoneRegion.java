package com.intiro.itr.logic.phone;

import com.intiro.itr.db.DBConstants;
import com.intiro.itr.db.DBExecute;
import com.intiro.itr.db.DBQueriesConfig;
import com.intiro.itr.db.InvocationHandlerSetting;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.cache.ItrCache;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.log.IntiroLog;
import java.util.HashMap;
import java.util.Map;

public class PhoneRegion {

  public static final String CACHE_ALL_PHONEREGION = "CACHE_ALL_PHONEREGION";
  private static final String XML_PHONEREGIONCODE_END = "</regioncode>";
  private static final String XML_PHONEREGIONCODE_START = "<regioncode>";
  private static final String XML_PHONEREGIONID_END = "</regionid>";
  private static final String XML_PHONEREGIONID_START = "<regionid>";
  private static final String XML_PHONEREGIONNAME_END = "</regionname>";
  private static final String XML_PHONEREGIONNAME_START = "<regionname>";
  private static final String XML_PHONEREGION_END = "</phoneregion>";
  private static final String XML_PHONEREGION_START = "<phoneregion>";
  private PhoneCountry country = new PhoneCountry();
  private int countryId = -1;
  private String regionCode = null;
  private int regionId = -1;
  private String regionName = null;
  private boolean remove = false;

  /**
   * Constructor I for PhoneRegion.
   */
  public PhoneRegion() {
  }

  public void setCountry(PhoneCountry country) {
    this.country = country;
  }

  public PhoneCountry getCountry() {
    return country;
  }

  public void setCountryId(int countryId) {
    this.countryId = countryId;
  }

  public int getCountryId() {
    return countryId;
  }

  public void setRegionCode(String regionCode) {
    this.regionCode = regionCode;
  }

  public String getRegionCode() {
    return regionCode;
  }

  public void setRegionId(int regionId) {
    this.regionId = regionId;
  }

  public int getRegionId() {
    return regionId;
  }

  public void setRegionName(String regionName) {
    this.regionName = regionName;
  }

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
      IntiroLog.criticalError(getClass(), getClass().getName() + ".cloneRegion(): Could not load country: ", e);
    }

    return retval;
  }

  /**
   * Load the Phone region for the specified regionid or countryId.
   *
   * @param regionId
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public void load(int regionId) throws XMLBuilderException {
    try {
      if (regionId == -1) {
        throw new Exception(getClass().getName() + ".load(int regionId): At least one input has to be not null.");
      }
      String cacheKey = getClass().getName() + ".load_" + regionId;
      String statisticKey = getClass().getName() + ".load";
      int cacheTime = 3600 * 10;
      InvocationHandlerSetting s = InvocationHandlerSetting.create(cacheKey, cacheTime, statisticKey);
      StringRecordset rs = DBQueriesConfig.getProxy(s).getPhoneRegion(regionId);

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
      IntiroLog.error(getClass(), getClass().getName() + ".load(int regionId): ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }
  }

  public static Map<Integer, PhoneRegion> loadAllPhoneRegions() throws XMLBuilderException {
    Map<Integer, PhoneRegion> retval = new HashMap<>();
    try {
      Map<Integer, PhoneCountry> mapPhoneCountries = PhoneCountry.loadAllPhoneCountries();
      String cacheKey = PhoneRegion.class.getName() + ".loadAllPhoneRegions";
      String statisticKey = PhoneRegion.class.getName() + ".loadAllPhoneRegions";
      int cacheTime = 3600 * 10;
      InvocationHandlerSetting s = InvocationHandlerSetting.create(cacheKey, cacheTime, statisticKey);
      StringRecordset rs = DBQueriesConfig.getProxy(s).getAllPhoneRegions();

      while (!rs.getEOF()) {
        PhoneRegion pr = new PhoneRegion();
        pr.setRegionCode(rs.getField(DBConstants.PHONEREGIONCODE_REGIONCODE));
        pr.setRegionName(rs.getField(DBConstants.PHONEREGIONCODE_REGIONNAME));
        pr.setRegionId(Integer.parseInt(rs.getField(DBConstants.PHONEREGIONCODE_ID_PK)));
        pr.setCountryId(Integer.parseInt(rs.getField(DBConstants.PHONEREGIONCODE_PHONECOUNTRYCODEID_FK)));
        pr.setCountry(mapPhoneCountries.get(pr.getCountryId()));
        retval.put(pr.getRegionId(), pr);

        rs.moveNext();
      }
      if (IntiroLog.d()) {
        IntiroLog.detail(PhoneRegion.class, PhoneRegion.class.getName() + ".loadAllPhoneRegions()");
      }
    } catch (Exception e) {
      IntiroLog.error(PhoneRegion.class, PhoneRegion.class.getName() + ".loadAllPhoneRegions(): ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }

    return retval;
  }

  /**
   * Save the phone numbers for either the contact or the user.
   *
   * @throws java.lang.Exception
   */
  public void save() throws Exception {

    try {
      String statisticKey = getClass().getName() + ".save";
      InvocationHandlerSetting s = InvocationHandlerSetting.create(statisticKey);

      DBExecute.getProxy(s).updatePhoneRegion(this);
    } catch (Exception e) {
      IntiroLog.error(getClass(), getClass().getName() + ".save(): ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new Exception(e.getMessage());
    }

    //}
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
