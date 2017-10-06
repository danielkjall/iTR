/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.logic;

import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.DynamicXMLCarrier;
import com.intiro.itr.util.xml.XMLBuilder;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.log.IntiroLog;

public class UserDataTag extends DynamicXMLCarrier {

  //~ Instance/static variables ........................................................................................

  static final String XML_COMPANY_END = "</company>";
  static final String XML_COMPANY_START = "<company>";
  static final String XML_NAME_END = "</name>";
  static final String XML_NAME_START = "<name>";
  static final String XML_ROLE_END = "</role>";
  static final String XML_ROLE_START = "<role>";

  //~ Constructors .....................................................................................................

  public UserDataTag(UserProfile profile) throws XMLBuilderException {
    super(profile);
  }

  //~ Methods ..........................................................................................................

  /**
   * This is the method that will produce the XML.
   * It will fill the xmlDoc with XML.
   * @param    xmlDoc a StringBuffer to be filled with xml.
   */
  public void toXML(StringBuffer xmlDoc) throws Exception {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer): entered");
    }

    XMLBuilder builder = new XMLBuilder();

    /*Get start of document*/
    builder.getStartOfDocument(xmlDoc);

    /*Name*/
    xmlDoc.append(XML_NAME_START);
    xmlDoc.append(userProfile.getFirstName() + " " + userProfile.getLastName());
    xmlDoc.append(XML_NAME_END);

    /*Company*/
    xmlDoc.append(XML_COMPANY_START);
    xmlDoc.append(userProfile.getCompanyName());
    xmlDoc.append(XML_COMPANY_END);

    /*Role*/
    xmlDoc.append(XML_ROLE_START);
    xmlDoc.append(userProfile.getRole().getName());
    xmlDoc.append(XML_ROLE_END);

    /*Get end of document*/
    builder.getEndOfDocument(xmlDoc);
  }
}