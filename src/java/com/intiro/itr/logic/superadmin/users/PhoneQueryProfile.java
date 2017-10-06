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

public class PhoneQueryProfile extends DynamicXMLCarrier {

  //~ Instance/static variables ........................................................................................

  static final String XML_ACTION_END = "</action>";
  static final String XML_ACTION_START = "<action>";
  static final String XML_USER_END = "</user>";
  static final String XML_USER_START = "<user>";
  protected UsersCombo usersCombo = null;
  protected String action = "users";

  /**
   * @return Returns the action.
   */
  public String getAction() {
    return action;
  }
  //~ Constructors .....................................................................................................

  public PhoneQueryProfile(UserProfile profile) throws XMLBuilderException {
    super(profile);

    //Users
    usersCombo = new UsersCombo(userProfile, true);
    usersCombo.load(getUserProfile().getUserId());
    usersCombo.setStartEndTags(XML_USER_START, XML_USER_END);
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

    // action (telling if this is a conatct or a user page)
    xmlDoc.append(XML_ACTION_START);
    xmlDoc.append("users");
    xmlDoc.append(XML_ACTION_END);

    //activ users
    usersCombo.toXML(xmlDoc);

    //Get end of document
    builder.getEndOfDocument(xmlDoc);

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer xmlDoc): xmlDoc = " + xmlDoc.toString());
    }
  }
  /**
   * @return Returns the usersCombo.
   */
  public UsersCombo getUsersCombo() {
    return usersCombo;
  }
}