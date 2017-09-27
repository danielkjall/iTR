/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.ui.xsl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Vector;

import com.intiro.itr.ui.UIElement;
import com.intiro.itr.ui.UIException;
import com.intiro.itr.ui.constants.URLs;
import com.intiro.itr.util.Transform;
import com.intiro.itr.util.xml.XMLElement;
import com.intiro.itr.util.xsl.DefaultXSLCarrier;
import com.intiro.itr.util.xsl.XSLCarrier;
import com.intiro.toolbox.log.IntiroLog;

public class XSLFormatedArea extends UIElement implements URLs {

  //~ Instance/static variables ........................................................................................

  //private String header = new String("");
  //private String text = new String("");
  //private String thisClass = this.getClass().getName();
  private XMLElement xmlElement = null;
  private XSLCarrier xslCarrier = null;
  private Vector <XSLParameter> xslParameters = new Vector <XSLParameter> (5);

  //~ Constructors .....................................................................................................

  /**
   * Constructor I
   * Create a XSLArea with the parameters
   *
   * @param    xmlElement        the XMLElement to use with this xsl.
   * @param     xslFile        the file your are going to use.
   *                            Your xslFile will be handled by the DefaultXSLCarrier.
   *
   * @throws   UIException If the parameter is invalid
   */
  public XSLFormatedArea(XMLElement xmlElement, String xslFile) throws UIException {
    super();
    this.xmlElement = xmlElement;
    xslCarrier = new DefaultXSLCarrier(xslFile);
  }

  /**
   * Constructor II
   * Create a XSLArea
   *
   * @param    xmlElement        the XMLElement to use with this xsl.
   * @param     xslCarrier        the XSLCarrier that holds and formates the xsl.
   *
   * @throws   UIException If the parameter is invalid
   */
  public XSLFormatedArea(XMLElement xmlElement, XSLCarrier xslCarrier) throws UIException {
    super();
    this.xmlElement = xmlElement;
    this.xslCarrier = xslCarrier;
  }

  //~ Methods ..........................................................................................................

  /**
   * Return a nice String from the XSLFormatedArea.
   */
  public synchronized String getLogString() throws UIException {
    PrintWriter out = new PrintWriter(new StringWriter());
    display(out);

    return out.toString();
  }

  /** Add an XSLParameter to this XSL area
   */
  public synchronized void addParameter(String name, String value) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".addParameter(): name = " + name + ", value = " + value);
    }

    XSLParameter parameter = new XSLParameter();
    parameter.setName(name);
    parameter.setValue(value);
    xslParameters.addElement(parameter);
  }

  /**
   * Display the XLSArea
   *
   * @param    out            Outputstream to use to display to the client
   * @param    clientInfo        ClientInfo information about the client.
   * @param    eServiceName    the eServiceName that is to be accessed.
   * @throws   UIException If the UIElement (or it's contents) cannot be displayed
   */
  public void display(PrintWriter out) throws UIException {
    try {
      Transform trans = new Transform();

      if (xmlElement != null) {
        trans.transform(xmlElement, xslCarrier, xslParameters, out);
      }
      else {
        throw new UIException("xmlDoc or XMLFile xxxxx has not been set.");
      }
    } catch (Exception e) {
      if (IntiroLog.ce()) {
        IntiroLog.criticalError(getClass(), getClass().getName() + "..display(): " + e.getClass().getName(), e);
      }

      throw new UIException(e.getMessage());
    }

    setDisplayed();
  }
}