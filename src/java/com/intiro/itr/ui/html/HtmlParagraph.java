/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 *
 * @author Daniel Kjall
 * @version 1.0
 */
package com.intiro.itr.ui.html;

import java.io.PrintWriter;
import java.util.Enumeration;

import com.intiro.itr.ui.UIElement;
import com.intiro.itr.ui.UIException;
import com.intiro.itr.util.personalization.ClientInfo;

/**
 * An HTML Paragraph - a Paragraph element contains all other elements and is what actually gets sent to the client
 *
 */
public class HtmlParagraph extends UIElement {

  //~ Instance/static variables ........................................................................................
  String thisClass = new String(this.getClass().getName() + ".");
  private String header = new String("");
  private String text = new String("");

  //~ Constructors .....................................................................................................
  /**
   * Constructor I Create a new Paragraph object with the given header and text.
   *
   * @param newHeader Header to be used in Paragraph.
   * @param newText Text to be used in Paragraph.
   * @throws UIException If the parameter is invalid
   */
  public HtmlParagraph(String newHeader, String newText) throws UIException {
    super();
    header = newHeader;
    text = newText;
  }

  /**
   * Constructor II Create a new Paragraph object with the given header and UIElement
   *
   * @param newHeader Title for the Paragraph.
   * @param newElement Element to be displayed in the Paragraph.
   * @throws UIException If the parameter is invalid
   */
  public HtmlParagraph(String newHeader, UIElement newElement) throws UIException {
    super();
    header = newHeader;
    add(newElement);
  }

  /**
   * Constructor III Create a new Paragraph object with the given UIElement.
   *
   * @param newElement Element to be displayed in the Paragraph.
   * @throws UIException If the parameter is invalid
   */
  public HtmlParagraph(UIElement newElement) throws UIException {
    super();
    add(newElement);
  }

  //~ Methods ..........................................................................................................
  /**
   * Display the Paragraph to the client Once a Paragraph is displayed it cannot be displayed again or new element added to it
   *
   * @param out Outputstream to use to display to the client
   * @param inClientInfo ClientInfo information about the client.
   * @param eServiceName the eServiceName that is to be accessed.
   * @throws UIException If the UIElement (or it's contents) cannot be displayed
   */
  public void display(PrintWriter out, ClientInfo inClientInfo, String eServiceName) throws UIException {

    //Print header if set.
    if (!header.equals("") && header != null) {
      out.println("<h3>" + header + "</h3>");
    }

    out.println("<p>");

    //Print text if set.
    if (!text.equals("") && text != null) {
      out.println(text);
    }
    //Print contents if added.
    if (contents.size() > 0) {

      for (UIElement oneElement : contents) {
        oneElement.display(out);
      }
    }

    out.println("</p>");
    setDisplayed();
  }
}
