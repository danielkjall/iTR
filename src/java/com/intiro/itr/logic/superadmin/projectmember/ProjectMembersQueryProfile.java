/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Olof Altenstedt
 * @version       1.0
 */
package com.intiro.itr.logic.superadmin.projectmember;

import com.intiro.itr.util.combos.ProjectsCombo;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.DynamicXMLCarrier;
import com.intiro.itr.util.xml.XMLBuilder;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.log.IntiroLog;

public class ProjectMembersQueryProfile extends DynamicXMLCarrier {

  //~ Instance/static variables ........................................................................................

  static final String XML_PROJECTS_END = "</project>";
  static final String XML_PROJECTS_START = "<project>";
  protected ProjectsCombo projectsCombo = null;

  //~ Constructors .....................................................................................................

  public ProjectMembersQueryProfile(UserProfile profile) throws XMLBuilderException {
    super(profile);

    //Project combo
    projectsCombo = new ProjectsCombo(userProfile);
    projectsCombo.load();
    projectsCombo.setStartEndTags(XML_PROJECTS_START, XML_PROJECTS_END);
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
    projectsCombo.toXML(xmlDoc);

    //Get end of document
    builder.getEndOfDocument(xmlDoc);

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer xmlDoc): xmlDoc = " + xmlDoc.toString());
    }
  }
  /**
   * @return Returns the projectsCombo.
   */
  public ProjectsCombo getProjectsCombo() {
    return projectsCombo;
  }
}