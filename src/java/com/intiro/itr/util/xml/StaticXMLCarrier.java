package com.intiro.itr.util.xml;

import java.io.File;
import java.io.RandomAccessFile;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import com.intiro.itr.ITRResources;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.log.IntiroLog;

public class StaticXMLCarrier extends XMLElement {

  //~ Instance/static variables ........................................................................................
  private StringBuffer xmlFile = new StringBuffer();

  //~ Constructors .....................................................................................................
  /**
   * Constructor I.
   */
  public StaticXMLCarrier(String xmlFile, UserProfile userProfile) throws XMLBuilderException {
    super(userProfile);
    this.xmlFile.append(xmlFile);
  }

  //~ Methods ..........................................................................................................
  /**
   * This method return the Document held by this class. It uses toXML(xmlDoc) to make a Document.
   *
   * @exception Exception, if something goes wrong.
   */
  public synchronized Document getDocument() throws Exception {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".getDocument(String xmlFile): getDocument entered");
    }

    DOMParser parser = new DOMParser();

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".getDocument(): DOM parser created");
    }
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".getDocument(): xmlFile = " + ITRResources.getDefaultRealITRRootDir() + xmlFile);
    }

    parser.parse(ITRResources.getDefaultRealITRRootDir() + xmlFile);

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".getDocument(): XML file parsed");
    }

    Document doc = parser.getDocument();

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".getDocument(): Root element fetched");
    }

    return doc;
  }

  public StringBuffer getXMLFilePath() {
    return xmlFile;
  }

  /**
   * Override the Object.toString() method. Good for debugging.
   *
   * @return a debugging String, xmlformatted.
   */
  public String toString() {
    StringBuffer retval = new StringBuffer();

    try {
      toXML(retval);
    } catch (Exception e) {
      IntiroLog.error(getClass(), getClass().getName() + ".toString(): " + e.getMessage());
    }

    return retval.toString();
  }

  /**
   * This is the method that will produce the XML. It will fill the xmlDoc with XML.
   *
   * @param xmlDoc a StringBuffer to be filled with xml.
   */
  public void toXML(StringBuffer xmlDoc) throws Exception {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".getDocumentAsSB(String xmlFile): Entering");
    }

    File docFile = new File(ITRResources.getDefaultRealITRRootDir() + xmlFile);

    //If file exist we can get content into xmlDoc.
    if (docFile.exists()) {
      try {
        RandomAccessFile in = null;

        try {
          in = new RandomAccessFile(docFile, "r");

          String c;

          while ((c = in.readLine()) != null) {
            xmlDoc.append(c);
          }
        } finally {

          //Close docFile
          if (in != null) {
            in.close();
          }
        }
      } catch (Exception e) {
        IntiroLog.error(getClass(), getClass().getName() + ".toXML(): " + e.getMessage());
      }
    }
  }
}
