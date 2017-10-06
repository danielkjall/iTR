/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.logic.superadmin.users;

import com.intiro.itr.util.combos.UsersCombo;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.DynamicXMLCarrier;
import com.intiro.itr.util.xml.XMLBuilder;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.log.IntiroLog;

public class UserQueryProfile extends DynamicXMLCarrier {

  //~ Instance/static variables ........................................................................................

  static final String XML_ACTIVATED_USER_END = "</activateduser>";
  static final String XML_ACTIVATED_USER_START = "<activateduser>";
  static final String XML_DEACTIVATED_USER_END = "</deactivateduser>";
  static final String XML_DEACTIVATED_USER_START = "<deactivateduser>";
  protected UsersCombo activatedUsersCombo = null;
  protected UsersCombo deActivatedUsersCombo = null;

  //~ Constructors .....................................................................................................

  public UserQueryProfile(UserProfile profile) throws XMLBuilderException {
    super(profile);

    //Activated Users
    activatedUsersCombo = new UsersCombo(userProfile, true);
    activatedUsersCombo.load(getUserProfile().getUserId());
    activatedUsersCombo.setStartEndTags(XML_ACTIVATED_USER_START, XML_ACTIVATED_USER_END);

    //DeActivated Users
    deActivatedUsersCombo = new UsersCombo(userProfile, false);
    deActivatedUsersCombo.load(getUserProfile().getUserId());
    deActivatedUsersCombo.setStartEndTags(XML_DEACTIVATED_USER_START, XML_DEACTIVATED_USER_END);
  }

  //~ Methods ..........................................................................................................

  /**
   * This is the method that will produce the XML.
   * It will fill the xmlDoc with XML.
   * @param    xmlDoc a StringBuffer to be filled with xml.
   */
  public void toXML(StringBuffer xmlDoc) throws Exception {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer xmlDoc): entered");
    }

    XMLBuilder builder = new XMLBuilder();

    //Get start of document
    builder.getStartOfDocument(xmlDoc);

    //activ users
    activatedUsersCombo.toXML(xmlDoc);

    //deactivated users
    deActivatedUsersCombo.toXML(xmlDoc);

    //Get end of document
    builder.getEndOfDocument(xmlDoc);

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer xmlDoc): xmlDoc = " + xmlDoc.toString());
    }
  }
  /**
   * @return Returns the activatedUsersCombo.
   */
  public UsersCombo getActivatedUsersCombo() {
    return activatedUsersCombo;
  }
  /**
   * @return Returns the deActivatedUsersCombo.
   */
  public UsersCombo getDeActivatedUsersCombo() {
    return deActivatedUsersCombo;
  }
}