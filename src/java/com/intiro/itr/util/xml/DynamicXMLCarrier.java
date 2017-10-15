package com.intiro.itr.util.xml;

import java.io.Serializable;
//import java.io.StringReader;
//import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
//import org.xml.sax.InputSource;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.log.IntiroLog;

public class DynamicXMLCarrier extends XMLElement implements Serializable {

  /**
   * Constructor I. Use this one if you want to use the database
   *
   * @param profile
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public DynamicXMLCarrier(UserProfile profile) throws XMLBuilderException {
    super(profile);
  }

  /**
   * Constructor II. Use this one if you want to use the database
   *
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public DynamicXMLCarrier() throws XMLBuilderException {
    super();
  }

  //~ Methods ..........................................................................................................
  /**
   * This method return the Document held by this class. It uses toXML(xmlDoc) to make a Document.
   *
   * @return
   * @exception Exception, if something goes wrong.
   */
  @Override
  public synchronized Document getDocument() throws Exception {
    /*
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".getDocument(): getDocument entered");
    }

    DOMParser parser = new DOMParser();

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".getDocument(): DOM parser created");
    }

    StringBuffer sb = new StringBuffer();
    toXML(sb);

    StringReader stringStream = new StringReader(sb.toString());
    parser.parse(new InputSource(stringStream));

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".getDocument(): XML String parsed");
    }

    Document doc = parser.getDocument();

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".getDocument(): Root element fetched");
    }

    return doc;
    */
    return null;
  }

  /**
   * Override the Object.toString() method. Good for debugging.
   *
   * @return a debugging String, xmlformatted.
   */
  @Override
  public String toString() {
    StringBuffer retval = new StringBuffer();

    try {
      toXML(retval);
    } catch (Exception e) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".toString(): " + e.getMessage());
    }

    return retval.toString();
  }

  /**
   * This is the method that will produce the XML. It will fill the xmlDoc with XML.
   *
   * @param xmlDoc a StringBuffer to be filled with xml.
   * @throws java.lang.Exception
   */
  @Override
  public void toXML(StringBuffer xmlDoc) throws Exception {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer): entered");
    }
    if (contents.isEmpty()) {
      throw new XMLBuilderException(getClass().getName() + ".toXML(StringBuffer): No contents have been set.");
    }

    for (XMLElement oneElement : contents) {
      oneElement.toXML(xmlDoc);
    }
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer): xmlDoc = " + xmlDoc.toString());
    }
  }
}
