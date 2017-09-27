/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.util.xml;

import org.w3c.dom.*;


/**
 * The interface among xml carriers.
 */
public interface XMLCarrier {
  //~ Methods ..........................................................................................................

  /**
     * This method return the Document held by this class.
     * It uses toXML(xmlDoc) to make a Document.
     *
     * @exception    Exception, if something goes wrong.
     */
  public Document getDocument()
                       throws Exception;


  /**
     * This is the method that will produce the XML.
     * It will fill the xmlDoc with XML.
     * @param    xmlDoc a StringBuffer to be filled with xml.
     *
     */
  public void toXML( StringBuffer xmlDoc )
             throws Exception;

}