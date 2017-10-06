package com.intiro.itr.util.xsl;

import com.intiro.itr.util.personalization.*;

public interface XSLCarrier {

  /**
   * Return a correct webpath to the xsl when parsing on server.
   *
   * @param userProfile a UserProfile with settings.
   * @return a String, specifying the correct webpath to the xsl file.
   */
  public String handleClientXSL(UserProfile userProfile);

  /**
   * Return a correct path to the xsl when parsing on server.
   *
   * @param userProfile a UserProfile with settings.
   * @return a String, specifying the correct realpath to the xsl file.
   */
  public String handleServerXSL(UserProfile userProfile);

}
