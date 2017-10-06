package com.intiro.itr.util.personalization;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.intiro.itr.ITRResources;
import com.intiro.itr.util.log.IntiroLog;

public class ClientInfo {

  private String browserVersion = null;
  private String charEnch = null;
  private boolean isExplorer = false;
  private boolean isNetscape = false;
  private boolean isWap = false;
  private String languageCode = null;
  private String opSystem = null;
  private String referer = null;
  private String skin = null;
  private String userAgent = null;

  public ClientInfo() {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ". Constructor");
    }
  }

  //~ Methods ..........................................................................................................
  /**
   * Method returns the client browsers version number.
   *
   * @return a String containing the version number.
   */
  public String getBrowserVersion() {
    if (browserVersion != null) {
      return browserVersion.trim();
    } else {
      IntiroLog.warning(getClass(), getClass().getName() + ".getBrowserVersion() returns null -> the browser client might not be handled properly by the ClientInfo ");
      return browserVersion;
    }
  }

  public String getCharEnch() {
    if (charEnch != null) {
      return charEnch.trim();
    } else {
      IntiroLog.warning(getClass(), getClass().getName() + ".getCharEnch() returns null -> the browser client might not be handled properly by the ClientInfo ");
      return charEnch;
    }
  }

  public String getContentType() {
    String retval = "";

    //String xml      = "text/xml";
    String html = "text/html";

    //String wml      = "text/wml";
    //UNTIL WE SUPPORT CLIENT PARSING, ALWAYS RETURN html
    retval = html;

    /*
     if(canClientParse())
     retval = xml;
     else if(isWap())
     retval = wml;
     else
     retval = html;
     */
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".getContentType(): Setting contentType = " + retval);
    }

    return retval;
  }

  public boolean isExplorer() {
    return isExplorer;
  }

  public void setLanguageCode(String code) {
    if (code != null) {
      this.languageCode = code;
    }
  }

  public String getLanguageCode() {
    if (languageCode != null) {
      return languageCode.trim();
    } else {
      IntiroLog.warning(getClass(), getClass().getName() + ".getLanguage() is null -> the browser client might not be handled properly by the ClientInfo, returning the default language code ");
      return ITRResources.getDefaultLanguageCode();
    }
  }

  public boolean isNetscape() {
    return isNetscape;
  }

  public String getOpSystem() {
    if (opSystem != null) {
      return opSystem.trim();
    } else {
      IntiroLog.warning(getClass(), getClass().getName() + ".getOpSystem() returns null -> the browser client might not be handled properly by the ClientInfo ");
      return opSystem;
    }
  }

  public String getReferer() {
    if (referer != null) {
      return referer.trim();
    } else {
      IntiroLog.warning(getClass(), getClass().getName() + ".getReferer() returns null -> the browser client might not be handled properly by the ClientInfo ");
      return referer;
    }
  }

  public void setSkin(String skin) {
    if (skin != null) {
      this.skin = skin;
    }
  }

  public String getSkin() {
    if (skin != null && !(skin.equalsIgnoreCase("null"))) {
      return skin.trim();
    } else {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".getSkin() is null -> the user have no skin set, returning the default skin");
      }
      return ITRResources.getDefaultSkin();
    }
  }

  /**
   * Method returns the user agent string.
   *
   * @return a String containing user agent information.
   */
  public String getUserAgent() {
    if (browserVersion != null) {
      return userAgent.trim();
    } else {
      IntiroLog.warning(getClass(), getClass().getName() + ".getUserAgent() returns null -> the browser client might not be handled properly by the ClientInfo ");
      return userAgent;
    }
  }

  public boolean isWap() {
    return isWap;
  }

  public boolean canClientParse() {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".canClientParse(): = " + ((isNetscape() || isExplorer()) && Double.parseDouble(getBrowserVersion()) > 5.0));
    }

    return ((isNetscape() || isExplorer()) && Double.parseDouble(getBrowserVersion()) > 5.0);
  }

  /**
   * Initializes the ClientInfo with information about the client.
   *
   * @param inRequest a HttpServletRequest.
   * @param inResponse a HttpServletResponse.
   */
  public void init(HttpServletRequest inRequest, HttpServletResponse inResponse) {
    userAgent = inRequest.getHeader("user-agent");
    languageCode = inRequest.getHeader("accept-language");

    if (languageCode == null) {
      languageCode = inRequest.getLocale().getLanguage();
    }

    referer = inRequest.getHeader("referer");
    charEnch = inRequest.getCharacterEncoding();
    setBrowserProp(userAgent);
  }

  @Override
  public String toString() {
    StringBuilder retval = new StringBuilder();
    retval.append("Referer = ").append(getReferer());
    retval.append(", getBrowserVersion = ").append(getBrowserVersion());
    retval.append(", getLanguageCode = ").append(getLanguageCode());
    retval.append(", getUserAgent = ").append(getUserAgent());
    retval.append(", isExplorer = ").append(isExplorer());
    retval.append(", isNetscape = ").append(isNetscape());
    retval.append(", getOpSystem = ").append(getOpSystem());

    return retval.toString();
  }

  private void setBrowserProp(String agent) {
    //Mozilla/4.0 (compatible; MSIE 4.01; Windows NT)
    int browserPos;

    //Explorer or Netscape
    if (agent.contains("Mozilla")) {

      //Microsoft Internet Explorer
      if ((browserPos = agent.indexOf("MSIE")) != -1) {
        isExplorer = true;

        if ((agent.lastIndexOf(";") != -1) && (agent.lastIndexOf(")") != -1)) {
          opSystem = agent.substring(agent.lastIndexOf(";"), agent.lastIndexOf(")"));
        } else {
          return;
        }
        if ((agent.contains("MSIE")) && (agent.lastIndexOf(";") != -1)) {
          browserVersion = agent.substring(agent.indexOf("MSIE") + 4, agent.lastIndexOf(";"));
        } else {
          return;
        }
      } //Netscape Navigator
      else if (((browserPos = agent.indexOf("/")) != -1) && (agent.contains("["))) {
        isNetscape = true;
        browserVersion = agent.substring(browserPos + 1, agent.indexOf("["));

        if ((agent.contains("(")) && (agent.contains(";"))) {
          opSystem = agent.substring(agent.indexOf("("), agent.indexOf(";"));
        }
      }
    } //Handheld device, WAP
    else {
      isWap = true;
    }

    return;
  }
}