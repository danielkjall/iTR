/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.util.xml;

/**
 * This class represents properties on a column used in the XMLInnerTable.
 *
 * @see com.intiro.itr.util.xml.XMLInnerTable
 */
public class ColumnProperties {

  //~ Instance/static variables ........................................................................................

  protected String bgColor = null;
  protected String display = null;
  protected String value = null;

  //~ Constructors .....................................................................................................

  /**
   * Constructor I.
   *
   * @param    value      a String used to retrieve the value from the StringRecordset.
   */
  public ColumnProperties(String value) {
    super();
    this.value = value;
  }

  /**
   * Constructor II.
   *
   * @param    value      a String used to retrieve the value from the StringRecordset.
   * @param    display      a String used to retrieve the value to be displayed from the StringRecordset.
   */
  public ColumnProperties(String value, String display) {
    super();
    this.value = value;
    this.display = display;
  }

  /**
   * Constructor III.
   *
   * @param    value      a String used to retrieve the value from the StringRecordset.
   * @param    display      a String used to retrieve the value to be displayed from the StringRecordset.
   * @param    bgColor      a String specifying the color to set on this column.
   */
  public ColumnProperties(String value, String display, String bgColor) {
    super();
    this.value = value;
    this.display = display;
    this.bgColor = bgColor;
  }

  //~ Methods ..........................................................................................................

  /**
   * Get background color.
   */
  public String getBGColor() {
    return bgColor;
  }

  /**
   * Get display.
   */
  public String getDisplay() {
    return display;
  }

  /**
   * Get value.
   */
  public String getValue() {
    return value;
  }
}