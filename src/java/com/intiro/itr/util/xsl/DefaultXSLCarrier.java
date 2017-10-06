package com.intiro.itr.util.xsl;

import java.io.File;
import com.intiro.itr.ITRResources;
import com.intiro.itr.util.personalization.ClientInfo;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.log.IntiroLog;

public class DefaultXSLCarrier implements XSLCarrier {

  private StringBuffer sbXSLFile = null;
  private String xslFile = null;

  public DefaultXSLCarrier(String xslFile) {
    this.xslFile = xslFile;
    sbXSLFile = new StringBuffer(xslFile);
  }

  /**
   * Return a correct webpath to the xsl when parsing on server.
   *
   * @param userProfile a UserProfile with settings.
   * @return a String, specifying the correct webpath to the xsl file.
   */
  @Override
  public String handleClientXSL(UserProfile userProfile) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".handleClientXSL(): Entering");
    }
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".handleClientXSL(): xslFile to be worked on = " + xslFile);
    }
    //If no xsl is specified use the start xsl for the applikation.
    if (xslFile == null || xslFile.equalsIgnoreCase("")) {
      xslFile = "START";
    }

    setLanguageCodePrefixAndSkin(userProfile);

    //START BUILDING WEBPATH TO THE XSL.
    StringBuffer webPathToXSL = new StringBuffer();
    webPathToXSL.append(ITRResources.HTTP_PROTOCOL_STRING).append(ITRResources.getDefaultWebITRRootDir());

    //END BUILDING PATH TO XSL
    IntiroLog.detail(getClass(), getClass().getName() + ".handleClientXSL(): Returning xslFile = " + webPathToXSL + sbXSLFile.toString());

    return webPathToXSL + sbXSLFile.toString();
  }

  /**
   * Return a correct path to the xsl when parsing on server.
   *
   * @param userProfile a UserProfile with settings.
   * @return a String, specifying the correct realpath to the xsl file.
   */
  public String handleServerXSL(UserProfile userProfile) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".handleServerXSL(): Entering");
    }
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".handleServerXSL(): xslFile to be worked on = " + xslFile);
    }

    setLanguageCodePrefixAndSkin(userProfile);

    //START BUILDING WEBPATH TO THE XSL.
    StringBuffer realPathToXSL = new StringBuffer();
    realPathToXSL.append(ITRResources.getDefaultRealITRRootDir());

    //END BUILDING PATH TO XSL
    IntiroLog.detail(getClass(), getClass().getName() + ".handleServerXSL(): Returning xslFile = " + realPathToXSL + sbXSLFile.toString());

    return realPathToXSL + sbXSLFile.toString();
  }

  /**
   * Used to set the correct language prefix on the xsl file and uses the correct skin.
   *
   * @param userProfile a UserProfile with settings for the user.
   */
  protected void setLanguageCodePrefixAndSkin(UserProfile userProfile) {

    //START SETTING LANGUAGE CODE PREFIX
    String languagePrefix = null;

    if (userProfile.getClientInfo() != null) {
      IntiroLog.detail(getClass(), getClass().getName() + ".getClientInfo() != null ");

      languagePrefix = userProfile.getClientInfo().getLanguageCode();

      if (languagePrefix == null) {
        languagePrefix = ITRResources.getDefaultLanguageCode();
      }
      //if .xsl is missing add it.
      if (!xslFile.contains(".xsl")) {
        sbXSLFile.append(".xsl");
      }

      sbXSLFile.insert(xslFile.lastIndexOf("/") + 1, languagePrefix + "_");
      insertSkin(userProfile.getClientInfo());

      //Build the path for the xsl file.
      StringBuilder checkFile = new StringBuilder();
      checkFile.append(ITRResources.getDefaultRealITRRootDir());

      //add the language specific xslfile
      checkFile.append(sbXSLFile.toString());

      IntiroLog.detail(getClass(), getClass().getName() + ".setLanguageCodePrefixAndSkin(): Checking if file = " + sbXSLFile.toString() + " exists");
      //Check if the language xsl exists. If not switch back to default language xsl.
      if (!(new File(checkFile.toString()).exists())) {
        IntiroLog.warning(getClass(), getClass().getName() + ".setLanguageCodePrefixAndSkin(): Specified language xsl does not exist, using default xsl instead. failed path = " + checkFile.toString());

        //add default language code
        languagePrefix = ITRResources.getDefaultLanguageCode();

        //clear sbXSLFile
        sbXSLFile.setLength(0);
        sbXSLFile.append(xslFile);

        //if .xsl is missing add it.
        if (xslFile.indexOf(".xsl") == -1) {
          sbXSLFile.append(".xsl");
        }

        sbXSLFile.insert(xslFile.lastIndexOf("/") + 1, languagePrefix + "_");
        insertSkin(userProfile.getClientInfo());
      }
    } else { //if(userProfile.getClientInfo() != null)
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".setLanguageCodePrefixAndSkin(): userProfile.getClientInfo() = " + userProfile.getClientInfo());
      }

      sbXSLFile.insert(xslFile.lastIndexOf("/") + 1, languagePrefix + "_");
    }
  }

  /**
   * Used to set the correct skin-xsl file.
   *
   * @param clientInfo a ClientInfo created with settings from the users browser.
   */
  protected void insertSkin(ClientInfo clientInfo) {
    String frames = "/frames/";

    IntiroLog.detail(getClass(), getClass().getName() + ".insertSkin(): xslFile.indexOf(frames) = " + xslFile.indexOf(frames) + " + " + frames + " = " + xslFile.indexOf(frames) + frames);

    if (xslFile.indexOf(frames) != -1) {
      sbXSLFile.insert(xslFile.lastIndexOf(frames) + frames.length(), clientInfo.getSkin() + "/");
    } else {
      IntiroLog.warning(getClass(), getClass().getName() + ".insertSkin(): was called but " + frames + " was not contained in xslFile = " + xslFile);
    }
  }
}
