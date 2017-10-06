package com.intiro.itr.util;

import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.*;
import com.intiro.itr.util.xml.DynamicXMLCarrier;
import com.intiro.itr.util.log.IntiroLog;

public class ErrorHandler extends DynamicXMLCarrier {

  protected static final String XML_ERROR_CLASS_END = "</class>";
  protected static final String XML_ERROR_CLASS_MESSAGE_END = "</classmessage>";
  protected static final String XML_ERROR_CLASS_MESSAGE_START = "<classmessage>";
  protected static final String XML_ERROR_CLASS_START = "<class>";
  protected static final String XML_ERROR_MESSAGE_END = "</message>";
  protected static final String XML_ERROR_MESSAGE_START = "<message>";
  String error_message = "Message is missing!!";
  Exception exception = new Exception("No exception has been set for ErrorHandler");

  public ErrorHandler(UserProfile userProfile) throws XMLBuilderException {
    super(userProfile);
  }

  public void setErrorMessage(String message) {
    error_message = message;
  }

  public String getErrorMessage() {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(),
              getClass().getName() + ".getErrorMessage(): before changing, error_message = " + error_message);
    }
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(),
              getClass().getName() + ".getErrorMessage(): after changing, error_message = " + error_message);
    }

    return error_message;
  }

  public void setException(Exception exception) {
    this.exception = exception;
  }

  public Exception getException() {
    return exception;
  }

  /**
   * This is the method that will produce the XML. It will fill the xmlDoc with XML.
   *
   * @param xmlDoc a StringBuffer to be filled with xml.
   */
  public void toXML(StringBuffer xmlDoc)
          throws Exception {
    XMLBuilder builder = new XMLBuilder();

    /*Get start of document*/
    builder.getStartOfDocument(xmlDoc);

    /*Build document content*/
    xmlDoc.append(XML_ERROR_MESSAGE_START);
    xmlDoc.append(getErrorMessage());
    xmlDoc.append(XML_ERROR_MESSAGE_END);
    xmlDoc.append(XML_ERROR_CLASS_START);
    xmlDoc.append(getException());
    xmlDoc.append(XML_ERROR_CLASS_END);
    xmlDoc.append(XML_ERROR_CLASS_MESSAGE_START);
    xmlDoc.append(getException().getMessage());
    xmlDoc.append(XML_ERROR_CLASS_MESSAGE_END);

    /*Get end of document*/
    builder.getEndOfDocument(xmlDoc);
  }
}
