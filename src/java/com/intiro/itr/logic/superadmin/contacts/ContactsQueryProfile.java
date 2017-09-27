/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.logic.superadmin.contacts;

import com.intiro.itr.util.combos.ContactCombo;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.DynamicXMLCarrier;
import com.intiro.itr.util.xml.XMLBuilder;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.toolbox.log.IntiroLog;

public class ContactsQueryProfile extends DynamicXMLCarrier {

  //~ Instance/static variables ........................................................................................

  static final String XML_ACTION_END = "</action>";
  static final String XML_ACTION_START = "<action>";
  static final String XML_CONTACT_END = "</contact>";
  static final String XML_CONTACT_START = "<contact>";
  protected ContactCombo contactCombo = null;

  //~ Constructors .....................................................................................................

  public ContactsQueryProfile(UserProfile profile) throws XMLBuilderException {
    super(profile);
    contactCombo = new ContactCombo(userProfile);
    contactCombo.load();
    contactCombo.setStartEndTags(XML_CONTACT_START, XML_CONTACT_END);
  }

  //~ Methods ..........................................................................................................

  /**
   * This is the method that will produce the XML.
   * It will fill the xmlDoc with XML.
   * @param    xmlDoc a StringBuffer to be filled with xml.
   */
  public void toXML(StringBuffer xmlDoc) throws Exception {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer xmlDoc): entered");
    }

    XMLBuilder builder = new XMLBuilder();

    //Get start of document
    builder.getStartOfDocument(xmlDoc);

    // action (telling if this is a conatct or a user page)
    xmlDoc.append(XML_ACTION_START);
    xmlDoc.append("contacts");
    xmlDoc.append(XML_ACTION_END);

    // Contact combo
    contactCombo.toXML(xmlDoc);

    //Get end of document
    builder.getEndOfDocument(xmlDoc);

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer xmlDoc): xmlDoc = " + xmlDoc.toString());
    }
  }
  /**
   * @return Returns the contactCombo.
   */
  public ContactCombo getContactCombo() {
    return contactCombo;
  }
}