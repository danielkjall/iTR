/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.ui.error;

/**
 * Thrown when a user tries to add parameters to a xml
 * document that lacks a head
 */
public class NoXMLDocumentHeadException extends Exception {
  //~ Constructors .....................................................................................................

  public NoXMLDocumentHeadException() {
    super();
  }

  public NoXMLDocumentHeadException( String s ) {
    super( s );
  }
}