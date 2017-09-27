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
 * Thrown when a user tries to access a servlet without
 * having a session. A session is created once user has
 * authenticated.
 */
public class NoSessionException extends Exception {
  //~ Constructors .....................................................................................................

  public NoSessionException() {
    super();
  }

  public NoSessionException( String s ) {
    super( s );
  }
}