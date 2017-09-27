/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.util.xml;

import java.io.Serializable;
import java.io.StringReader;
import java.util.Enumeration;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.intiro.itr.db.DBExecute;
import com.intiro.itr.db.DBQueries;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.toolbox.log.IntiroLog;

public class DynamicXMLCarrier extends XMLElement implements Serializable {

  //~ Instance/static variables ........................................................................................

  protected DBExecute dbExecute = null;
  protected DBQueries dbQuery = null;

  //~ Constructors .....................................................................................................

  /**
   * Constructor I.
   * Use this one if you want to use the database
   */
  public DynamicXMLCarrier(UserProfile profile) throws XMLBuilderException {
    super(profile);
    dbQuery = new DBQueries(profile);
    dbExecute = new DBExecute(profile);
  }

  /**
   * Constructor II.
   * Use this one if you want to use the database
   */
  public DynamicXMLCarrier() throws XMLBuilderException {
    super();
    dbQuery = new DBQueries();
    dbExecute = new DBExecute();
  }

  //~ Methods ..........................................................................................................

  /**
   * This method return the Document held by this class.
   * It uses toXML(xmlDoc) to make a Document.
   *
   * @exception    Exception, if something goes wrong.
   */
  public synchronized Document getDocument() throws Exception {
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
  }

  /**
   * Override the Object.toString() method.
   * Good for debugging.
   * @return a debugging String, xmlformatted.
   */
  public String toString() {
    StringBuffer retval = new StringBuffer();

    try {
      toXML(retval);
    } catch (Exception e) {
      if (IntiroLog.ce()) {
        IntiroLog.criticalError(getClass(), getClass().getName() + ".toString(): " + e.getMessage());
      }
    }

    return retval.toString();
  }

  /**
   * This is the method that will produce the XML.
   * It will fill the xmlDoc with XML.
   * @param    xmlDoc a StringBuffer to be filled with xml.
   */
  public void toXML(StringBuffer xmlDoc) throws Exception {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer): entered");
    }
    if (contents.size() == 0) { throw new XMLBuilderException(getClass().getName() + ".toXML(StringBuffer): No contents have been set."); }

    XMLElement oneElement = null;

    for (Enumeration e = contents.elements(); e.hasMoreElements();) {
      oneElement = (XMLElement) e.nextElement();
      oneElement.toXML(xmlDoc);
    }
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer): xmlDoc = " + xmlDoc.toString());
    }
  }
}