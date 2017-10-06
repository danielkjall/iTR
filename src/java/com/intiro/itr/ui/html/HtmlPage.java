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
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.intiro.itr.ITRResources;
import com.intiro.itr.ui.Page;
import com.intiro.itr.ui.UIElement;
import com.intiro.itr.ui.UIException;
import com.intiro.itr.ui.constants.URLs;
import com.intiro.itr.ui.error.NoSessionException;
import com.intiro.itr.ui.xsl.XSLFormatedArea;
import com.intiro.itr.util.personalization.ClientInfo;

/**
 * An HTML page - a page element contains all other elements and is what actually gets sent to the client
 */
public class HtmlPage extends Page implements URLs {

  //~ Instance/static variables ........................................................................................
  protected String bgColor = null;
  protected String cSSClass = null;
  protected ArrayList<String> javaScripts = new ArrayList<String>(5);
  protected String leftMargin = null;
  protected String marginHeigth = null;
  protected String marginWidth = null;
  protected String onLoadCall = null;
  protected String picture = null;
  protected ArrayList<String> scriptsInHeader = new ArrayList<String>(5);
  protected String textFont = null;
  protected String title = new String("No Title");
  protected String topMargin = null;
  protected ArrayList<XSLFormatedArea> xslScriptsInHeader = new ArrayList<XSLFormatedArea>(5);
  String thisClass = new String(this.getClass().getName() + ".");

  //~ Constructors .....................................................................................................
  /**
   * <font color="#FF0000"> <b>WARNING!</b></font>
   * Use this constructor only from LoginView because it doesn't require a session.
   */
  public HtmlPage(String newTitle) throws UIException {
    super();
    setName(newTitle);
    setTitle(newTitle);
  }

  /**
   * Constructor
   *
   * @throws UIException If the superclass constructor fails
   */
  public HtmlPage(HttpServletRequest request) throws UIException, NoSessionException {
    super();
    setName(request.getRequestURI());

    //Check if the user has a session
    if (request.getSession(false) == null) {
      throw new NoSessionException("No session was found.");
    }
  }

  /**
   * Constructor Create a new page object with the given title
   *
   * @param newTitle Title for the page
   * @throws UIException If the parameter is invalid
   */
  public HtmlPage(String newTitle, HttpServletRequest request) throws UIException, NoSessionException {
    super();
    setName(request.getRequestURI());

    //Check if the user has a session
    if (request.getSession(false) == null) {
      throw new NoSessionException("No session was found.");
    }

    setTitle(newTitle);
  }

  //~ Methods ..........................................................................................................
  /**
   * Set the background color for the page
   *
   * @param newColor Color for the background of this page
   * @throws UIException If the parameter is invalid
   */
  public synchronized void setBGColor(String newColor) throws UIException {
    String myName = new String(thisClass + "setBGColor(String)");

    if (newColor == null) {
      throw new UIException(myName + ":Background color cannot be null");
    }

    bgColor = newColor;
  }

  /**
   * Set background pictue.
   *
   * @param newBackground, background for the page
   */
  public synchronized void setBackground(String picture) {
    if (picture != null) {
      this.picture = picture;
    }
  }

  public synchronized void setCssClass(String cssClass) throws UIException {
    String myName = new String(thisClass + "setCssClass(String)");

    if (cssClass == null) {
      throw new UIException(myName + ":cssClass can not be null");
    }

    this.cSSClass = cssClass;
  }

  public synchronized void setLeftMargin(String newLeftMargin) throws UIException {
    String myName = new String(thisClass + "setLeftMargin(String)");

    if (newLeftMargin == null) {
      throw new UIException(myName + ":Left margin cannot be null");
    }

    leftMargin = newLeftMargin;
  }

  public synchronized void setMarginHeigth(String newHeigth) throws UIException {
    String myName = new String(thisClass + "setMarginHeigth(String)");

    if (newHeigth == null) {
      throw new UIException(myName + ":Margin heigth cannot be null");
    }

    marginHeigth = newHeigth;
  }

  public synchronized void setMarginWidth(String newWidth) throws UIException {
    String myName = new String(thisClass + "setMarginWidth(String)");

    if (newWidth == null) {
      throw new UIException(myName + ":Margin width cannot be null");
    }

    marginWidth = newWidth;
  }

  public synchronized void setOnLoadCall(String callScript) throws UIException {
    String myName = new String(thisClass + "setOnLoadScript(String)");

    if (callScript == null) {
      throw new UIException(myName + ":OnLoad can not be null");
    }

    onLoadCall = callScript;
  }

  public synchronized void setTextFont(String newText) throws UIException {
    String myName = new String(thisClass + "setTextFont(String)");

    if (newText == null) {
      throw new UIException(myName + ":Text font cannot be null");
    }

    textFont = newText;
  }

  /**
   * Set a new title for this page
   *
   * @param newTitle, Title for the page
   */
  public synchronized void setTitle(String newTitle) {
    if (newTitle != null) {
      title = newTitle;
    }
  }

  public synchronized void setTopMargin(String newTopMargin) throws UIException {
    String myName = new String(thisClass + "setBGColor(String)");

    if (newTopMargin == null) {
      throw new UIException(myName + ":Background color cannot be null");
    }

    topMargin = newTopMargin;
  }

  /**
   * Add a new element to the page
   *
   * @param newElement Element to add
   * @throws UIException If the parameter is invalid
   */
  public synchronized void add(UIElement newElement) throws UIException {
    String myName = new String(thisClass + "add(UIElement)");

    if (newElement instanceof HtmlPage) {
      throw new UIException(myName + ":Can't add a Page to Page " + getName());
    }

    super.add(newElement);
  }

  /**
   * Add a reference to a JavaScript on the page.
   *
   * @param scriptURL, where to find the script.
   */
  public synchronized void addJavaScriptReference(String scriptURL) {
    if (scriptURL.length() != 0 && scriptURL != null) {
      javaScripts.add(ITRResources.getDefaultProtocolForITR() + ITRResources.getDefaultWebITRRootDir() + JAVASCRIPT_DIR + scriptURL);
    }
  }

  /**
   * Add a xsl with script in the header of the page.
   *
   * @param xslFormatedArea, where to find the script.
   */
  public synchronized void addScriptToHeader(XSLFormatedArea xslFormatedArea) {
    if (xslFormatedArea != null) {
      xslScriptsInHeader.add(xslFormatedArea);
    }
  }

  /**
   * Add a String containing the script to put in the head of the page.
   *
   * @param script, a String holding the script.
   */
  public synchronized void addScriptToHeader(String script) {
    if (script != null) {
      scriptsInHeader.add(script);
    }
  }

  /**
   * Display the page to the client Once a page is displayed it cannot be displayed again or new element added to it
   *
   * @param out Outputstream to use to display to the client
   * @param inClientInfo ClientInfo information about the client.
   * @param inEServiceName the eServiceName that is to be accessed.
   * @throws UIException If the UIElement (or it's contents) cannot be displayed
   */
  public void display(PrintWriter out, ClientInfo inClientInfo, String inEServiceName) throws UIException {
    String myName = new String(thisClass + "display(PrintWriter)");

    if (contents.size() == 0) {
      throw new UIException("<p>" + myName + ":Page " + getName() + " has no contents");
    }

    out.println("<HTML>");
    out.println("<HEAD>");
    out.println("<TITLE>" + title + "</TITLE>");
    out.println("<META NAME=\"generator\" CONTENT=\"ITR\">");
    out.println("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"../include/ITR.CSS\">");

    //Add JavaScripts to the page if there are any.
    if (javaScripts != null && !javaScripts.isEmpty()) {

      for (String oneScriptRef : javaScripts) {
        out.print("<SCRIPT type=\"text/javascript\" src=\"");
        out.println(oneScriptRef + "\"></SCRIPT>");
      }
    }
    //Add XSLFormatedArea to the head of the page.
    if (xslScriptsInHeader != null && !xslScriptsInHeader.isEmpty()) {
      for (XSLFormatedArea oneXSLArea : xslScriptsInHeader) {
        oneXSLArea.display(out);
      }
    }

    //Add Script to the head of the page.
    if (scriptsInHeader != null && !scriptsInHeader.isEmpty()) {
      for (String oneScript : scriptsInHeader) {
        out.println(oneScript);
      }
    }

    out.println("</HEAD>");
    out.print("<BODY");

    if (cSSClass != null) {
      out.print(" CLASS=\"" + cSSClass + "\"");
    }
    if (cSSID != null) {
      out.print(" ID=\"" + cSSID + "\"");
    }
    if (bgColor != null) {
      out.println(" BGCOLOR=\"" + bgColor + "\"");
    }
    if (marginHeigth != null) {
      out.println(" MARGINHEIGHT=\"" + marginHeigth + "\"");
    }
    if (marginWidth != null) {
      out.println(" MARGINWIDTH=\"" + marginWidth + "\"");
    }
    if (leftMargin != null) {
      out.println(" LEFTMARGIN=\"" + leftMargin + "\"");
    }
    if (textFont != null) {
      out.println(" TEXT=\"" + textFont + "\"");
    }
    if (topMargin != null) {
      out.println(" TOPMARGIN=\"" + topMargin + "\"");
    }
    if (picture != null) {
      out.println(" BACKGROUND=\"" + picture + "\"");
    }
    if (onLoadCall != null) {
      out.println(" ONLOAD=\"" + onLoadCall + "\"");
    }

    out.println(">");

    for (UIElement oneElement : contents) {
      oneElement.display(out);
    }

    out.println("</BODY>");
    out.println("</HTML>");
    setDisplayed();
    out.flush();
  }
}
