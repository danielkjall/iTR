/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.logic.superadmin.activity;

import com.intiro.itr.util.combos.ProjectCodesCombo;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.DynamicXMLCarrier;
import com.intiro.itr.util.xml.XMLBuilder;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.log.IntiroLog;

public class ActivityQueryProfile extends DynamicXMLCarrier {

  //~ Instance/static variables ........................................................................................

  static final String XML_ACTIVITY_END = "</activity>";
  static final String XML_ACTIVITY_START = "<activity>";
  protected ProjectCodesCombo activityCombo = null;

  //~ Constructors .....................................................................................................

  public ActivityQueryProfile(UserProfile profile) throws XMLBuilderException {
    super(profile);

    //Activity combo
    activityCombo = new ProjectCodesCombo(userProfile);
    activityCombo.load();
    activityCombo.setStartEndTags(XML_ACTIVITY_START, XML_ACTIVITY_END);
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

    //deactivated users
    activityCombo.toXML(xmlDoc);

    //Get end of document
    builder.getEndOfDocument(xmlDoc);

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer xmlDoc): xmlDoc = " + xmlDoc.toString());
    }
  }
  /**
   * @return Returns the activityCombo.
   */
  public ProjectCodesCombo getActivityCombo() {
    return activityCombo;
  }
}