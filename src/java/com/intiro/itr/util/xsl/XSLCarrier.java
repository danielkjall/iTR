/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.util.xsl;

import com.intiro.itr.util.personalization.*;


public interface XSLCarrier {
  //~ Methods ..........................................................................................................

  /**
     * Return a correct webpath to the xsl when parsing on server.
     * @param userProfile        a UserProfile with settings.
     * @return a String, specifying the correct webpath to the xsl file.
     */
  public String handleClientXSL( UserProfile userProfile );


  /**
     * Return a correct path to the xsl when parsing on server.
     *
     * @param userProfile        a UserProfile with settings.
     * @return a String, specifying the correct realpath to the xsl file.
     */
  public String handleServerXSL( UserProfile userProfile );

}