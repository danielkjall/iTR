/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.ui.html;

import java.io.PrintWriter;

import com.intiro.itr.ui.UIElement;
import com.intiro.itr.ui.UIException;
import com.intiro.itr.util.personalization.ClientInfo;

/**
 * An HTMLSection - a Section element contains html code
 * that is sent to the client.
 */
public class HtmlSection extends UIElement {

  //~ Instance/static variables ........................................................................................

  String thisClass = new String(this.getClass().getName() + ".");
  private StringBuffer section = new StringBuffer("");

  //~ Constructors .....................................................................................................

  /**
   * Constructor
   * Create a new htmlSection object with the given section content
   *
   * @param    newSection Section for the htmlSection
   * @throws   UIException If the parameter is invalid
   */
  public HtmlSection(String newSection) throws UIException {
    super();

    if (newSection == null) { throw new UIException("Section was a null value"); }

    section.append(newSection);
  }

  //~ Methods ..........................................................................................................

  /**
   * Add to section.
   *
   * @param    moreSection add content for section
   * @throws   UIException If the parameter is invalid
   */
  public void addToSection(String moreSection) throws UIException {
    if (moreSection == null) { throw new UIException("Section was a null value"); }

    section.append("<BR x=\"x\" />" + moreSection + "<BR x=\"x\" />");
  }

  /**
   * Display the section to the client
   * Once a section is displayed it cannot be displayed again
   *
   * @param    out            Outputstream to use to display to the client
   * @param    inClientInfo        ClientInfo information about the client.
   * @param    inEServiceName    the eServiceName that is to be accessed.
   * @throws   UIException If the UIElement (or it's contents) cannot be displayed
   */
  public void display(PrintWriter out, ClientInfo inClientInfo, String inEServiceName) throws UIException {
    if (!section.equals("") || section != null) {
      out.println("<BR x=\"x\" />" + section.toString() + "<BR x=\"x\" />");
    }
    else {
      throw new UIException("Section was not set when displayed");
    }

    setDisplayed();
  }
}