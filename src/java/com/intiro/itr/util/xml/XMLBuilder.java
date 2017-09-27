/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.util.xml;

public class XMLBuilder {

  //~ Instance/static variables ........................................................................................

  /*Declaration*/
  static final String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>";
  static final String XML_EXCEPTION_CLASS_END = "</exception_class>";

  /*Exception class*/
  static final String XML_EXCEPTION_CLASS_START = "<exception_class>";
  static final String XML_EXCEPTION_MESSAGE_END = "</exception_message>";

  /*Exception message*/
  static final String XML_EXCEPTION_MESSAGE_START = "<exception_message>";
  static final String XML_EXCEPTION_OCCURED_END = "</exception_occured>";

  /*Exception occured*/
  static final String XML_EXCEPTION_OCCURED_START = "<exception_occured>";
  static final String XML_RESPONSE_END = "</response>";

  /*Response*/
  static final String XML_RESPONSE_START = "<response version=\"1.0\">";

  //~ Constructors .....................................................................................................

  public XMLBuilder() {
    //empty
  }
  //~ Methods ..........................................................................................................

  /**
   * Return the start of the document.
   */
  public void getEndOfDocument( StringBuffer xmlDoc ) {
    xmlDoc.append( XML_RESPONSE_END );
  }

  /**
   * Add head to document.
   */
  public void getStartOfDocument( StringBuffer xmlDoc ) {
    createStartOfDocument( xmlDoc );
  }

  /**
   * Create the head of the document.
   */
  protected void createStartOfDocument( StringBuffer xmlDoc ) {
    xmlDoc.append( XML_DECLARATION + XML_RESPONSE_START );
    xmlDoc.append( XML_EXCEPTION_OCCURED_START );
    xmlDoc.append( "false" );
    xmlDoc.append( XML_EXCEPTION_OCCURED_END );
  }
}