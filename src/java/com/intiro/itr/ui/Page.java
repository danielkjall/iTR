/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.ui;

import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.intiro.itr.ui.error.NoSessionException;

/**
 * A page - a page element contains all other elements and is what
 * actually gets sent to the client
 */
public class Page extends UIElement {

  //~ Instance/static variables ........................................................................................

  String thisClass = new String(this.getClass().getName() + ".");

  //~ Constructors .....................................................................................................

  /**
   * Constructor I
   * <font color="#FF0000"> <b>WARNING!</b></font>
   * Use this constructor only from LoginView and LogView, because they
   * doesn't require a session.
   *
   * @see itr.ui.login.LoginView
   * @see itr.ui.LogView
   */
  public Page() throws UIException {
    super("");
  }

  /**
   * Constructor II
   *
   * @throws    UIException If the superclass constructor fails
   */
  public Page(HttpServletRequest request) throws UIException, NoSessionException {
    setName(request.getRequestURI());

    //Check if the user has a session
    if (request.getSession(false) == null) { throw new NoSessionException(); }
  }

  //~ Methods ..........................................................................................................

  /**
   * Display the page to the client
   * Once a page is displayed it cannot be displayed again or new
   * element added to it
   *
   * @param    out            Outputstream to use to display to the client
   * @throws   UIException If the UIElement (or it's contents) cannot be displayed
   */
  public void display(PrintWriter out) throws UIException {
    String myName = new String(thisClass + "display(PrintWriter)");

    if (contents.size() == 0) { throw new UIException("<p>" + myName + ":Page " + getName() + " has no contents"); }

    UIElement oneElement = null;

    for (Enumeration e = contents.elements(); e.hasMoreElements();) {
      oneElement = (UIElement) e.nextElement();
      oneElement.display(out);
    }

    setDisplayed();
    out.flush();
  }
}