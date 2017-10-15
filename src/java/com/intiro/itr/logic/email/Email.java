package com.intiro.itr.logic.email;

import java.util.ArrayList;

import com.intiro.itr.db.DBConstants;
import com.intiro.itr.db.DBExecute;
import com.intiro.itr.db.DBQueriesAdmin;
import com.intiro.itr.db.DBQueriesConfig;
import com.intiro.itr.db.InvocationHandlerSetting;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.log.IntiroLog;
import java.util.HashMap;
import java.util.Map;

public class Email {

  public static final String CACHE_ALL_EMAILS = "CACHE_ALL_EMAILS";
  private static final String XML_COLORINDEX_END = "</colorindex>";
  private static final String XML_COLORINDEX_START = "<colorindex>";
  private static final String XML_EMAILADDRESS_END = "</emailaddress>";
  private static final String XML_EMAILADDRESS_START = "<emailaddress>";
  private static final String XML_EMAILCONTACTID_END = "</emailcontactid>";
  private static final String XML_EMAILCONTACTID_START = "<emailcontactid>";
  private static final String XML_EMAILDESC_END = "</emaildesc>";
  private static final String XML_EMAILDESC_START = "<emaildesc>";
  private static final String XML_EMAILID_END = "</emailid>";
  private static final String XML_EMAILID_START = "<emailid>";
  private static final String XML_EMAILUSERID_END = "</emailuserid>";
  private static final String XML_EMAILUSERID_START = "<emailuserid>";
  private static final String XML_EMAIL_END = "</email>";
  private static final String XML_EMAIL_START = "<email>";
  private static final String XML_INDEX_END = "</i>";
  private static final String XML_INDEX_START = "<i>";
  private String address = null;
  private int contactId = -1;
  private String description = null;
  private int emailId = -1;
  private boolean remove = false;
  private int userId = -1;

  public Email() {
  }

  public void setAddress(String addr) {
    this.address = addr;
  }

  public String getAddress() {
    return (address == null) ? "" : address;
  }

  public void setContactId(int contactId) {
    this.contactId = contactId;
  }

  public int getContactId() {
    return contactId;
  }

  public void setDescription(String desc) {
    this.description = desc;
  }

  public String getDescription() {
    return (description == null) ? "" : description;
  }

  public void setId(int Id) {
    this.emailId = Id;
  }

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

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getUserId() {
    return userId;
  }

  /**
   * Load the emails for the specified userId. The userId can be a Contact id as well.
   *
   * @param userId
   * @param contactId
   * @param emailId
   * @return
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public static ArrayList<Email> load(int userId, int contactId, int emailId) throws XMLBuilderException {
    ArrayList<Email> retval = new ArrayList<>();

    try {
      if (userId == -1 && contactId == -1 && emailId == -1) {
        throw new Exception("Email.load(String userId, String contactId, String emailId): At least one input has to be not null.");
      }

      String cacheKey = Email.class.getName() + ".load_" + userId + "_" + contactId + "_" + emailId;
      String statisticKey = Email.class.getName() + ".load";
      int cacheTime = 3600 * 10;
      InvocationHandlerSetting s = InvocationHandlerSetting.create(cacheKey, cacheTime, statisticKey);
      StringRecordset rs = DBQueriesConfig.getProxy(s).getEmails(userId, contactId, emailId);
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
      IntiroLog.error(Email.class, "Email.load(String userId, String contactId, String emailId): ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }

    return retval;
  }

  public static Map<Integer, ArrayList<Email>> loadAllEmails() throws XMLBuilderException {
    Map<Integer, ArrayList<Email>> retval = new HashMap<>();

    try {
      String cacheKey = Email.class.getName() + ".getAllEmails";
      String statisticKey = Email.class.getName() + ".loadAllEmails";
      int cacheTime = 3600 * 10;
      InvocationHandlerSetting s = InvocationHandlerSetting.create(cacheKey, cacheTime, statisticKey);
      StringRecordset rs = DBQueriesConfig.getProxy(s).getAllEmails();
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

        Integer key = em.getUserId();
        if (key != null) {
          if (retval.containsKey(key) == false) {
            retval.put(key, new ArrayList<>());
          }
          retval.get(key).add(em);
        }

        rs.moveNext();
      }
    } catch (Exception e) {
      IntiroLog.error(Email.class, "loadAllEmails(): ERROR FROM DATABASE, exception = " + e.getMessage());
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
   *
   * @throws java.lang.Exception
   */
  public void save() throws Exception {
    if (getId() == -1) {
      try {
        String statisticKey = getClass().getName() + ".save";
        InvocationHandlerSetting s = InvocationHandlerSetting.create(statisticKey);
        StringRecordset rs = DBQueriesAdmin.getProxy(s).addEmailAndGetId(this);

        if (!rs.getEOF()) {
          setId(Integer.parseInt(rs.getField("maxId")));
        } else {
          throw new XMLBuilderException(getClass().getName() + ".save(): Could not make and find a new email.");
        }
      } catch (Exception e) {
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".save(): ERROR FROM DATABASE, exception = " + e.getMessage());
        }

        throw new Exception(e.getMessage());
      }
    } else if (remove && getId() != -1) {
      try {
        String statisticKey = getClass().getName() + ".save";
        InvocationHandlerSetting s = InvocationHandlerSetting.create(statisticKey);
        DBExecute.getProxy(s).deleteEmail(this);
      } catch (Exception e) {
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".save(): ERROR FROM DATABASE, exception = " + e.getMessage());
        }

        throw new Exception(e.getMessage());
      }
    } else {
      try {
        String statisticKey = getClass().getName() + ".save";
        InvocationHandlerSetting s = InvocationHandlerSetting.create(statisticKey);
        DBExecute.getProxy(s).updateEmail(this);
      } catch (Exception e) {
        IntiroLog.error(getClass(), getClass().getName() + ".save(): ERROR FROM DATABASE, exception = " + e.getMessage());
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
