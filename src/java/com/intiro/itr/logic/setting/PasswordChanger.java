/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.logic.setting;

import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.DynamicXMLCarrier;
import com.intiro.itr.util.xml.XMLBuilder;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.log.IntiroLog;

public class PasswordChanger extends DynamicXMLCarrier {

  //~ Instance/static variables ........................................................................................

  static final String XML_LOGINID_END = "</loginid>";
  static final String XML_LOGINID_START = "<loginid>";

  //~ Constructors .....................................................................................................

  public PasswordChanger(UserProfile profile) throws XMLBuilderException {
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

    /*Login id*/
    xmlDoc.append(XML_LOGINID_START);
    xmlDoc.append(userProfile.getLoginId());
    xmlDoc.append(XML_LOGINID_END);

    /*Get end of document*/
    builder.getEndOfDocument(xmlDoc);
  }
}