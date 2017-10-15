package com.intiro.itr.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
//import org.apache.xalan.xslt.XSLTInputSource;
//import org.apache.xalan.xslt.XSLTProcessor;
//import org.apache.xalan.xslt.XSLTProcessorFactory;
//import org.apache.xalan.xslt.XSLTResultTarget;
import org.xml.sax.SAXException;
import com.intiro.itr.ui.UIException;
import com.intiro.itr.ui.constants.URLs;
import com.intiro.itr.ui.xsl.XSLParameter;
import com.intiro.itr.util.xml.XMLElement;
import com.intiro.itr.util.xsl.XSLCarrier;
import com.intiro.itr.util.log.IntiroLog;
import java.util.List;

/**
 * This class transforms the given xml and xsl.
 *
 */
public class Transform implements URLs {

  public Transform() { // Constructor
  }

  public void transform(XMLElement xmlElement, XSLCarrier xslCarrier, ArrayList parameters, PrintWriter out) throws Exception {

    // checkForErrors.
    checkForErrors(xmlElement, xslCarrier);

    // IntiroLog.getInstance().setLoggingLevel(6);
    // Perform client or server parsing.
    // UNTIL WE SUPPORT CLIENT PARSING ONLY PERFORM SERVER TRANSFORMATION

    /*
     * if(clientInfo.canClientParse()) clientTransformation(xmlElement, xslCarrier.handleClientXSL(xmlElement.getUserProfile()), parameters, out); else
     */
    serverTransformation(xmlElement, xslCarrier.handleServerXSL(xmlElement.getUserProfile()), parameters, out);

    // IntiroLog.getInstance().setLoggingLevel(1);
  }

  /**
   * Check for errors in inparameters too the transform metods in this object.
   *
   * @param xmlElement
   * @param xslCarrier
   * @exception UIException is thrown if error is detected.
   */
  protected void checkForErrors(XMLElement xmlElement, XSLCarrier xslCarrier) throws UIException {
    if (xmlElement == null) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".checkForErrors(): xmlElement was null");
      throw new UIException("xmlElement was null in " + getClass().getName() + ".checkForErrors()");
    } else if (xslCarrier == null) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".checkForErrors(): xslCarrier was null");
      throw new UIException("xslCarrier was null in " + getClass().getName() + ".checkForErrors()");
    }
  }

  protected void clientTransformation(XMLElement inXMLElement, String inWebPathToXSLFile, ArrayList inParameters, PrintWriter inOut) throws SAXException, UIException, ParserConfigurationException, IOException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".clientTransformation(): Entering");
    }

    // Add parameters to the stylesheet if there are any.

    /*
     * TODO if ((parameters != null) && !parameters.isEmpty()) { IntiroLog.detail(getClass().getName()+".transform(): XSL Parameters exists and will be added"); XSLParameter
     * oneParameter = null; for (Enumeration e = parameters.elements(); e.hasMoreElements(); ) { oneParameter = (XSLParameter) e.nextElement();
     * processor.setStylesheetParam(oneParameter.getName(),oneParameter.getValue()); IntiroLog.detail(getClass().getName()+".transform(): Stylesheet parameter set"); }
     * IntiroLog.detail(getClass().getName()+".transform(): All parameters added"); }
     */

 /*
     * //ADD webPathToXSLFile to xml file String xslRef = " <?xml-stylesheet type=\"text/xsl\" href=\""+webPathToXSLFile+"\"?>";
     * xmlAsString.insert(xmlAsString.toString().indexOf("?>")+2, xslRef); IntiroLog.detail("xml to browser = "+xmlAsString.toString()); out.println(xmlAsString.toString());
     * IntiroLog.detail(getClass().getName()+".clientTransformation(): XML has been sent to browser.");
     */
  }

  protected void serverTransformation(XMLElement xmlElement, String realPathToXSL, List<XSLParameter> parameters, PrintWriter out) throws Exception {
    /*
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".serverTransformation(): Creating XSLTProcessor");
    }

    // Have the XSLTProcessorFactory obtain an interface to a new XSLTProcessor object.
    XSLTProcessor processor = XSLTProcessorFactory.getProcessor(new org.apache.xalan.xpath.xdom.XercesLiaison());

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".serverTransformation(): XSLTProcessor created");
    }
    // Add parameters to the stylesheet if there are any.
    if ((parameters != null) && !parameters.isEmpty()) {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".transform(): XSL Parameters exists and will be added");
      }

      for (XSLParameter oneParameter : parameters) {
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".transform(): Setting Stylesheet name = " + oneParameter.getName() + ", value = " + oneParameter.getValue());
        }

        processor.setStylesheetParam(oneParameter.getName(), oneParameter.getValue());

        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".transform(): Stylesheet parameter set");
        }
      }
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".transform(): All parameters added");
      }
    }

    processor.process(new XSLTInputSource(xmlElement.getDocument()), new XSLTInputSource(realPathToXSL), new XSLTResultTarget(out));
    processor.reset();

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".serverTransform(): XML/XSL transformation done.");
    }
     */
  }

}
