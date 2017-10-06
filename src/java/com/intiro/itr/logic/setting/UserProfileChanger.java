/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.logic.setting;

import com.intiro.itr.util.combos.LanguageCombo;
import com.intiro.itr.util.combos.SkinsCombo;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.DynamicXMLCarrier;
import com.intiro.itr.util.xml.XMLBuilder;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.log.IntiroLog;

public class UserProfileChanger extends DynamicXMLCarrier {

  //~ Instance/static variables ........................................................................................

  static final String XML_FIRSTNAME_END = "</firstname>";
  static final String XML_FIRSTNAME_START = "<firstname>";
  static final String XML_LANGUAGE_END = "</language>";
  static final String XML_LANGUAGE_START = "<language>";
  static final String XML_LASTNAME_END = "</lastname>";
  static final String XML_LASTNAME_START = "<lastname>";
  static final String XML_SKIN_END = "</skin>";
  static final String XML_SKIN_START = "<skin>";
  protected LanguageCombo languageCombo = null;
  protected SkinsCombo skinsCombo = null;

  //~ Constructors .....................................................................................................

  public UserProfileChanger(UserProfile profile) throws XMLBuilderException {
    super(profile);

    /*Languages*/
    this.languageCombo = new LanguageCombo(userProfile);
    languageCombo.load(userProfile.getLanguageId());

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".Constructor: creating languageCombo with languageId = " + userProfile.getLanguageId());
    }

    languageCombo.setStartEndTags(XML_LANGUAGE_START, XML_LANGUAGE_END);

    /*Skins*/
    this.skinsCombo = new SkinsCombo(userProfile);
    skinsCombo.load(userProfile.getSkinId());
    skinsCombo.setStartEndTags(XML_SKIN_START, XML_SKIN_END);
  }

  //~ Methods ..........................................................................................................

  /**
   * This is the method that will produce the XML.
   * It will fill the xmlDoc with XML.
   * @param    xmlDoc a StringBuffer to be filled with xml.
   */
  public void toXML(StringBuffer xmlDoc) throws Exception {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer): entered");
    }

    XMLBuilder builder = new XMLBuilder();

    /*Get start of document*/
    builder.getStartOfDocument(xmlDoc);

    /*First name*/
    xmlDoc.append(XML_FIRSTNAME_START);
    xmlDoc.append(userProfile.getFirstName());
    xmlDoc.append(XML_FIRSTNAME_END);

    /*Last name*/
    xmlDoc.append(XML_LASTNAME_START);
    xmlDoc.append(userProfile.getLastName());
    xmlDoc.append(XML_LASTNAME_END);

    /*Language*/
    languageCombo.toXML(xmlDoc);

    /*Skin*/
    skinsCombo.toXML(xmlDoc);

    /*Get end of document*/
    builder.getEndOfDocument(xmlDoc);
  }
}