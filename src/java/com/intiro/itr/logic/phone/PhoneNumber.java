package com.intiro.itr.logic.phone;

import java.util.ArrayList;

import com.intiro.itr.db.DBConstants;
import com.intiro.itr.db.DBExecute;
import com.intiro.itr.db.DBQueries;
import com.intiro.itr.db.DbExecuteInterface;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.cache.ItrCache;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.log.IntiroLog;
import java.util.HashMap;
import java.util.Map;

public class PhoneNumber {

  public static final String CACHE_ALL_PHONENUMBER = "CACHE_ALL_PHONENUMBER";
  private static final String XML_COLORINDEX_END = "</colorindex>";
  private static final String XML_COLORINDEX_START = "<colorindex>";
  private static final String XML_INDEX_END = "</i>";
  private static final String XML_INDEX_START = "<i>";
  private static final String XML_PHONECONTACTID_END = "</phonecontactid>";
  private static final String XML_PHONECONTACTID_START = "<phonecontactid>";
  private static final String XML_PHONEDESCRIPTION_END = "</phonedesc>";
  private static final String XML_PHONEDESCRIPTION_START = "<phonedesc>";
  private static final String XML_PHONEID_END = "</phoneid>";
  private static final String XML_PHONEID_START = "<phoneid>";
  private static final String XML_PHONENUMBER_END = "</phoneno>";
  private static final String XML_PHONENUMBER_START = "<phoneno>";
  private static final String XML_PHONEUSERID_END = "</phoneuserid>";
  private static final String XML_PHONEUSERID_START = "<phoneuserid>";
  private static final String XML_PHONE_END = "</phone>";
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

  /**
   * Constructor I for PhoneNumber.
   */
  public PhoneNumber() {
  }

  public static Map<Integer, ArrayList<PhoneNumber>> loadAllPhoneNumbers() throws XMLBuilderException {
    Map<Integer, ArrayList<PhoneNumber>> retval = new HashMap<Integer, ArrayList<PhoneNumber>>();
    try {
      Map<Integer, ArrayList<PhoneNumber>> cached = ItrCache.get(CACHE_ALL_PHONENUMBER);
      if (cached != null) {
        return cached;
      }

      Map<Integer, PhoneRegion> mapPhoneRegions = PhoneRegion.loadAllPhoneRegions();

      StringRecordset rs = DBQueries.getProxy().getAllPhoneNumbers();
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
        pn.setRegion(mapPhoneRegions.get(pn.getPhoneRegionId()));
        if (tmpUser.length() > 0) {
          Integer key = Integer.parseInt(tmpUser);
          if (retval.containsKey(key) == false) {
            retval.put(key, new ArrayList<>());
          }
          retval.get(key).add(pn);
        }
        rs.moveNext();
      }
    } catch (Exception e) {
      IntiroLog.info(PhoneNumber.class, ".loadAllPhoneNumbers(): ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }

    final int TenHours = 1 * 60 * 60 * 10;
    ItrCache.put(CACHE_ALL_PHONENUMBER, retval, TenHours);

    return retval;
  }

  /**
   * Load the Phone numbers for the specified userId, contactId or phoneId.
   *
   * @param userId
   * @param phoneId
   * @param contactId
   * @return
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public static ArrayList<PhoneNumber> load(int userId, int contactId, int phoneId) throws XMLBuilderException {
    ArrayList<PhoneNumber> retval = new ArrayList<>();

    try {
      if (userId == -1 && contactId == -1 && phoneId == -1) {
        throw new Exception("PhoneNumber.load(String userId, String contactId, String phoneId): At least one input has to be not null.");
      }

      StringRecordset rs = DBQueries.getProxy().getPhoneNumbers(userId, contactId, phoneId);
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

  public void setPhoneContactId(int phoneContactId) {
    this.phoneContactId = phoneContactId;
  }

  public int getPhoneContactId() {
    return phoneContactId;
  }

  public void setPhoneDescription(String phoneDescription) {
    this.phoneDescription = phoneDescription;
  }

  public String getPhoneDescription() {
    return (phoneDescription == null) ? "" : phoneDescription;
  }

  public void setPhoneId(int phoneId) {
    this.phoneId = phoneId;
  }

  public int getPhoneId() {
    return phoneId;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getPhoneNumber() {
    return (phoneNumber == null) ? "" : phoneNumber;
  }

  public void setPhoneRegionId(int regionId) {
    this.regionId = regionId;
  }

  public int getPhoneRegionId() {
    return regionId;
  }

  public void setPhoneUserId(int phoneUserId) {
    this.phoneUserId = phoneUserId;
  }

  public int getPhoneUserId() {
    return phoneUserId;
  }

  public void setRegion(PhoneRegion region) {
    this.region = region;
  }

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
      IntiroLog.criticalError(getClass(), getClass().getName() + ".clonePhoneNumber(): Could not load region: ", e);
    }

    retval.setPhoneRegionId(getPhoneRegionId());

    return retval;
  }

  /**
   * Save the phone numbers for either the contact or the user.
   *
   * @throws java.lang.Exception
   */
  public void save() throws Exception {
    if (getPhoneId() == -1) {
      try {
        StringRecordset rs = DBQueries.getProxy().addPhoneNumberAndGetId(this);

        if (!rs.getEOF()) {
          setPhoneId(Integer.parseInt(rs.getField("maxId")));
        } else {
          throw new XMLBuilderException(getClass().getName() + ".save(): Could not make and find a new phone.");
        }
      } catch (Exception e) {
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".save(): ERROR FROM DATABASE, exception = " + e.getMessage());
        }

        throw new Exception(e.getMessage());
      }
    } else if (isRemoved() && getPhoneId() != -1) {
      try {
        DBExecute.getProxy().deletePhoneNumber(this);
      } catch (Exception e) {
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".save(): ERROR FROM DATABASE, exception = " + e.getMessage());
        }

        throw new Exception(e.getMessage());
      }
    } else {
      try {
        DBExecute.getProxy().updatePhoneNumber(this);
      } catch (Exception e) {
        IntiroLog.error(getClass(), getClass().getName() + ".save(): ERROR FROM DATABASE, exception = " + e.getMessage());
        throw new Exception(e.getMessage());
      }
    }

    //Save the region
    //getRegion().save();
  }

  @Override
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
