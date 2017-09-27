/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.ui;

public class UIException extends Exception {
  //~ Constructors .....................................................................................................

  /**
     * Constructor
     */
  public UIException() {
    super();
  }
  /**
     * Constructor
     *
     * @param    s Error message
     */
  public UIException( String s ) {
    super( s );
  }
}