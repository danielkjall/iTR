package com.intiro.itr.ui.xsl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import com.intiro.itr.ui.UIElement;
import com.intiro.itr.ui.UIException;
import com.intiro.itr.ui.constants.URLs;
import com.intiro.itr.util.Transform;
import com.intiro.itr.util.xml.XMLElement;
import com.intiro.itr.util.xsl.DefaultXSLCarrier;
import com.intiro.itr.util.xsl.XSLCarrier;
import com.intiro.itr.util.log.IntiroLog;

public class XSLFormatedArea extends UIElement implements URLs {

  private XMLElement xmlElement = null;
  private XSLCarrier xslCarrier = null;
  private ArrayList<XSLParameter> xslParameters = new ArrayList<>(5);

  /**
   * Constructor I Create a XSLArea with the parameters
   *
   * @param xmlElement the XMLElement to use with this xsl.
   * @param xslFile the file your are going to use. Your xslFile will be handled by the DefaultXSLCarrier.
   *
   * @throws UIException If the parameter is invalid
   */
  public XSLFormatedArea(XMLElement xmlElement, String xslFile) throws UIException {
    super();
    this.xmlElement = xmlElement;
    xslCarrier = new DefaultXSLCarrier(xslFile);
  }

  /**
   * Constructor II Create a XSLArea
   *
   * @param xmlElement the XMLElement to use with this xsl.
   * @param xslCarrier the XSLCarrier that holds and formates the xsl.
   *
   * @throws UIException If the parameter is invalid
   */
  public XSLFormatedArea(XMLElement xmlElement, XSLCarrier xslCarrier) throws UIException {
    super();
    this.xmlElement = xmlElement;
    this.xslCarrier = xslCarrier;
  }

  public synchronized String getLogString() throws UIException {
    PrintWriter out = new PrintWriter(new StringWriter());
    display(out);
    return out.toString();
  }

  public synchronized void addParameter(String name, String value) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".addParameter(): name = " + name + ", value = " + value);
    }

    XSLParameter parameter = new XSLParameter();
    parameter.setName(name);
    parameter.setValue(value);
    xslParameters.add(parameter);
  }

  /**
   * Display the XLSArea
   *
   * @param out Outputstream to use to display to the client
   * @throws UIException If the UIElement (or it's contents) cannot be displayed
   */
  @Override
  public void display(PrintWriter out) throws UIException {
    try {
      Transform trans = new Transform();

      if (xmlElement != null) {
        trans.transform(xmlElement, xslCarrier, xslParameters, out);
      } else {
        throw new UIException("xmlDoc or XMLFile xxxxx has not been set.");
      }
    } catch (Exception e) {
      IntiroLog.criticalError(getClass(), getClass().getName() + "..display(): " + e.getClass().getName(), e);
      throw new UIException(e.getMessage());
    }

    setDisplayed();
  }
}
