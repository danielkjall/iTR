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

import com.intiro.itr.util.xml.*;
import com.intiro.itr.util.xml.DynamicXMLCarrier;

import com.intiro.toolbox.log.IntiroLog;


public class ErrorHandler extends DynamicXMLCarrier {
  //~ Instance/static variables ........................................................................................

  protected static final String XML_ERROR_CLASS_END = new String( "</class>" );
  protected static final String XML_ERROR_CLASS_MESSAGE_END = new String( "</classmessage>" );
  protected static final String XML_ERROR_CLASS_MESSAGE_START = new String( "<classmessage>" );
  protected static final String XML_ERROR_CLASS_START = new String( "<class>" );
  protected static final String XML_ERROR_MESSAGE_END = new String( "</message>" );
  protected static final String XML_ERROR_MESSAGE_START = new String( "<message>" );
  String error_message = new String( "Message is missing!!" );
  Exception exception = new Exception( "No exception has been set for ErrorHandler" );

  //~ Constructors .....................................................................................................

  public ErrorHandler( UserProfile userProfile )
               throws XMLBuilderException {
    super( userProfile );
  }
  //~ Methods ..........................................................................................................

  public void setErrorMessage( String message ) {
    error_message = message;
  }


  public String getErrorMessage() {
    if ( IntiroLog.d() ) {
      IntiroLog.detail( getClass(), 
                        getClass().getName() + ".getErrorMessage(): before changing, error_message = " + error_message );
    }
    if ( IntiroLog.d() ) {
      IntiroLog.detail( getClass(), 
                        getClass().getName() + ".getErrorMessage(): after changing, error_message = " + error_message );
    }

    return error_message;
  }


  public void setException( Exception exception ) {
    this.exception = exception;
  }


  public Exception getException() {
    return exception;
  }

  /**
     * This is the method that will produce the XML.
     * It will fill the xmlDoc with XML.
         *
     * @param    xmlDoc a StringBuffer to be filled with xml.
     */
  public void toXML( StringBuffer xmlDoc )
             throws Exception {
    XMLBuilder builder = new XMLBuilder();

    /*Get start of document*/
    builder.getStartOfDocument( xmlDoc );

    /*Build document content*/
    xmlDoc.append( XML_ERROR_MESSAGE_START );
    xmlDoc.append( getErrorMessage() );
    xmlDoc.append( XML_ERROR_MESSAGE_END );
    xmlDoc.append( XML_ERROR_CLASS_START );
    xmlDoc.append( getException() );
    xmlDoc.append( XML_ERROR_CLASS_END );
    xmlDoc.append( XML_ERROR_CLASS_MESSAGE_START );
    xmlDoc.append( getException().getMessage() );
    xmlDoc.append( XML_ERROR_CLASS_MESSAGE_END );

    /*Get end of document*/
    builder.getEndOfDocument( xmlDoc );
  }
}