/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.util;

import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.DynamicXMLCarrier;
import com.intiro.itr.util.xml.XMLBuilder;
import com.intiro.itr.util.xml.XMLBuilderException;

public abstract class ResultDisplayer extends DynamicXMLCarrier {
  // ~ Instance/static variables ........................................................................................

  static final String XML_MESSAGE_END = "</message>";

  static final String XML_MESSAGE_START = "<message>";

  protected boolean success = false;

  // ~ Constructors .....................................................................................................

  public ResultDisplayer(UserProfile profile) throws XMLBuilderException {
    super(profile);
  }

  // ~ Methods ..........................................................................................................

  /**
   * This the method that retrives the message to use for failure.
   */
  public String getFailureMessage() {
    return "Failure, contact administator!";
  }

  /**
   * This the method that retrives the message to use for success.
   */
  public String getSuccessMessage() {
    return "Sucess!";
  }

  /**
   * This is the method that will produce the XML. It will fill the xmlDoc with XML.
   * 
   * @param xmlDoc
   *          a StringBuffer to be filled with xml.
   */
  public void toXML(StringBuffer xmlDoc) throws Exception {

    XMLBuilder builder = new XMLBuilder();

    /* Get start of document */
    builder.getStartOfDocument(xmlDoc);

    /* Build document content */
    xmlDoc.append(XML_MESSAGE_START);

    if (success) {
      xmlDoc.append(getSuccessMessage());
    } else {
      xmlDoc.append(getFailureMessage());
    }

    xmlDoc.append(XML_MESSAGE_END);

    /* Get end of document */
    builder.getEndOfDocument(xmlDoc);
  }

  /**
   * @return Returns the success.
   */
  public boolean isSuccess() {
    return success;
  }
}