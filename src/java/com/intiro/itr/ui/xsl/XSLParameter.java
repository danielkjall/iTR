/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.ui.xsl;

import java.lang.String;


/** XSLparameter is used in conjunction with XSLFormattedAreas to
 * pass parameters to the xsl-transformation of a XML-document.
 */
public class XSLParameter {
  //~ Instance/static variables ........................................................................................

  private String name;
  private String value;

  //~ Constructors .....................................................................................................

  /**
     * Constructor
     */
  public XSLParameter() {
    //empty
  }
  //~ Methods ..........................................................................................................

  /**
     * Set the name of the parameter.
     * @param name,    a String representing the name of parameter.
     */
  public synchronized void setName( String name ) {
    this.name = name;
  }

  /**
     * Returns the name of the parameter.
     */
  public synchronized String getName() {
    return name;
  }

  /**
     * Set the value of the parameter.
     * @param newValue, value of parameter.
     */
  public synchronized void setValue( String value ) {
    this.value = value;
  }

  /**
     * Returns the value of the parameter.
     */
  public synchronized String getValue() {
    return value;
  }
}