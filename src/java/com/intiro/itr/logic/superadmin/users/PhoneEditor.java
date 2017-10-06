package com.intiro.itr.logic.superadmin.users;

import java.util.ArrayList;
import com.intiro.itr.logic.contacts.Contacts;
import com.intiro.itr.logic.phone.PhoneNumber;
import com.intiro.itr.util.combos.PhoneCountryCombo;
import com.intiro.itr.util.combos.PhoneRegionCombo;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.DynamicXMLCarrier;
import com.intiro.itr.util.xml.XMLBuilder;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.log.IntiroLog;

public class PhoneEditor extends DynamicXMLCarrier {

  /**
   * @return Returns the countryCombo.
   */
  public PhoneCountryCombo getCountryCombo() {
    return countryCombo;
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
   * @return Returns the phones.
   */
  public ArrayList<PhoneNumber> getPhones() {
    return phones;
  }

  /**
   * @return Returns the regionCombo.
   */
  public PhoneRegionCombo getRegionCombo() {
    return regionCombo;
  }

  /**
   * @return Returns the userId.
   */
  public int getUserId() {
    return userId;
  }
  //~ Instance/static variables ........................................................................................

  static final String XML_FIRSTNAME_END = "</firstname>";
  static final String XML_FIRSTNAME_START = "<firstname>";
  static final String XML_LASTNAME_END = "</lastname>";
  static final String XML_LASTNAME_START = "<lastname>";
  static final String XML_MODEDESC_END = "</modedesc>";
  static final String XML_MODEDESC_START = "<modedesc>";
  static final String XML_MODE_END = "</mode>";
  static final String XML_MODE_START = "<mode>";
  static final String XML_NEWPHONE_END = "</newphone>";
  static final String XML_NEWPHONE_START = "<newphone>";
  static final String XML_PHONECOUNTRY_END = "</country>";
  static final String XML_PHONECOUNTRY_START = "<country>";
  static final String XML_PHONEREGION_END = "</region>";
  static final String XML_PHONEREGION_START = "<region>";
  static final String XML_USERID_END = "</userid>";
  static final String XML_USERID_START = "<userid>";
  static final String XML_USER_END = "</user>";
  static final String XML_USER_START = "<user>";
  protected PhoneCountryCombo countryCombo = null;
  protected ArrayList<PhoneNumber> phones = new ArrayList<PhoneNumber>();

  //Combo
  protected PhoneRegionCombo regionCombo = null;
  PhoneNumber newPhoneNumber = new PhoneNumber();
  int userId = -1;
  private boolean editingRow = false;
  private String firstName = "";
  private String lastName = "";
  private String mode = "users";
  private String modeDesc = "";

  //~ Constructors .....................................................................................................
  public PhoneEditor(UserProfile profile, int userId, String mode) throws XMLBuilderException {
    super(profile);

    if (mode.equals("contacts")) {
      Contacts tmpContact = new Contacts();
      tmpContact.load(String.valueOf(userId));
      firstName = tmpContact.getFirstName();
      lastName = tmpContact.getLastName();
      modeDesc = "contact";
    } else {
      UserProfile tmpProfile = new UserProfile();
      tmpProfile.load(userId);
      firstName = tmpProfile.getFirstName();
      lastName = tmpProfile.getLastName();
      modeDesc = "user";
    }

    this.userId = userId;
    this.mode = mode;

    if (mode.equals("contacts")) {
      this.phones = PhoneNumber.load(-1, userId, -1);
    } else {
      this.phones = PhoneNumber.load(userId, -1, -1);
    }
    try {

      //Region combo
      regionCombo = new PhoneRegionCombo();
      regionCombo.load();

      //regionCombo.setSelectedValue(String.valueOf(getPhoneRegionId()));
      regionCombo.setStartEndTags(XML_PHONEREGION_START, XML_PHONEREGION_END);

      //Country combo
      countryCombo = new PhoneCountryCombo();
      countryCombo.load();
      countryCombo.setStartEndTags(XML_PHONECOUNTRY_START, XML_PHONECOUNTRY_END);

      //countryCombo.setSelectedValue(String.valueOf(getPhoneCountryId()));
    } catch (XMLBuilderException e) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".Constructor(): Could not create combos. ", e);
      e.printStackTrace();
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
   * set new PhoneNumber.
   */
  public void setNewPhoneNumber(PhoneNumber newPhoneNumber) {
    this.newPhoneNumber = newPhoneNumber;
    loadCombos();
  }

  /**
   * Get new PhoneNumber.
   */
  public PhoneNumber getNewPhoneNumber() {
    return newPhoneNumber;
  }

  /**
   * Get Phone number.
   */
  public PhoneNumber getPhoneNumber(int index) {
    return phones.get(index);
  }

  /**
   * Get Phone numbers.
   */
  public ArrayList getPhoneNumbers() {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".getPhoneNumbers(): Entering");
    }

    return phones;
  }

  /**
   * Add PhoneNumber.
   */
  public void addPhoneNumber() {
    if (mode.equals("contacts")) {
      newPhoneNumber.setPhoneContactId(userId);
    } else {
      newPhoneNumber.setPhoneUserId(userId);
    }

    PhoneNumber onePhoneNumber = null;

    if (this.getEditingRow()) {
      for (int i = 0; i < phones.size(); i++) {
        onePhoneNumber = phones.get(i);

        if ((onePhoneNumber != null) && (onePhoneNumber.getPhoneId() == newPhoneNumber.getPhoneId())) {
          onePhoneNumber.setNotRemoved();
        }
      }
    } else {
      phones.add(newPhoneNumber.clonePhoneNumber());
    }

    newPhoneNumber = new PhoneNumber();
    unLoadCombos();
  }

  /**
   * Remove PhoneNumber.
   */
  public void removePhoneNumber(int index) {
    getPhoneNumber(index).setRemoved();
  }

  /**
   * Save emails.
   */
  public void save() throws XMLBuilderException {
    try {
      PhoneNumber onePhone = null;

      for (int i = 0; i < phones.size(); i++) {
        onePhone = phones.get(i);

        if (onePhone != null) {
          onePhone.save();
        }
      }
    } catch (Exception e) {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".save(): ERROR FROM DATABASE, exception = " + e.getMessage());
      }

      throw new XMLBuilderException(e.getMessage());
    }
  }

  /**
   * This is the method that will produce the XML. It will fill the xmlDoc with XML.
   *
   * @param xmlDoc a StringBuffer to be filled with xml.
   */
  public void toXML(StringBuffer xmlDoc) throws Exception {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer): entered");
    }

    XMLBuilder builder = new XMLBuilder();

    //Get start of document
    builder.getStartOfDocument(xmlDoc);

    //New phone
    xmlDoc.append(XML_NEWPHONE_START);

    if (getNewPhoneNumber() != null) {
      getNewPhoneNumber().toXML(xmlDoc, -1, -1);
    }

    xmlDoc.append(XML_NEWPHONE_END);

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

    //PhoneNumbers
    PhoneNumber onePhone = null;

    //Loop through all the phone numbers
    int j = 0;

    for (int i = 0; i < phones.size(); i++) {
      onePhone = phones.get(i);

      if (onePhone != null && !onePhone.isRemoved()) {
        onePhone.toXML(xmlDoc, i, j++);
      }
    }

    regionCombo.toXML(xmlDoc);
    countryCombo.toXML(xmlDoc);

    //Get end of document
    builder.getEndOfDocument(xmlDoc);

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer xmlDoc): xmlDoc = " + xmlDoc);
    }
  }

  /**
   * Set Phone numbers.
   */
  void setPhoneNumbers(ArrayList<PhoneNumber> phones) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".setPhoneNumbers(ArrayList phones): Entering");
    }

    this.phones = phones;
  }

  void loadCombos() {
    regionCombo.setSelectedValue(String.valueOf(getNewPhoneNumber().getRegion().getRegionId()));
    countryCombo.setSelectedValue(String.valueOf(getNewPhoneNumber().getRegion().getCountry().getCountryId()));
  }

  void unLoadCombos() {
    regionCombo.setSelectedValue("null");
    countryCombo.setSelectedValue("null");
  }
}
