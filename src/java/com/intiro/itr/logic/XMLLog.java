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
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.toolbox.log.IntiroLog;

public class XMLLog extends DynamicXMLCarrier {

  //~ Constructors .....................................................................................................

  public XMLLog(UserProfile userProfile) throws XMLBuilderException {
    super(userProfile);
  }

  //~ Methods ..........................................................................................................

  /**
   * This is the method that will produce the XML.
   * It will fill the xmlDoc with XML.
   * @param    xmlDoc a StringBuffer to be filled with xml.
   */
  public void toXML(StringBuffer xmlDoc) {
    try {
      xmlDoc.append(IntiroLog.getLogAsStringBuffer().toString());
    } catch (Exception e) {
      System.out.println(getClass().getName() + "toXML(): ERROR when getting info from Log." + e.getMessage());
    }
  }
}