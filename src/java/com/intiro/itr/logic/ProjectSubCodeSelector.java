/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.logic;

import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.DynamicXMLCarrier;
import com.intiro.itr.util.xml.XMLBuilder;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.toolbox.log.IntiroLog;

public class ProjectSubCodeSelector extends DynamicXMLCarrier {

  //~ Instance/static variables ........................................................................................

  protected final static String XML_PROJID_END = "</projid>";
  protected final static String XML_PROJID_START = "<projid>";
  protected final static String XML_PROJNAME_END = "</projname>";
  protected final static String XML_PROJNAME_START = "<projname>";
  protected final static String XML_USERNAME_END = "</username>";
  protected final static String XML_USERNAME_START = "<username>";
  protected ProjectSubCodesTable projTable = null;

  //~ Constructors .....................................................................................................

  public ProjectSubCodeSelector(UserProfile profile, String projectId) throws XMLBuilderException {
    super(profile);

    /*Make a ProjectsTable*/
    projTable = new ProjectSubCodesTable(userProfile, projectId);

    /*Load the projTable from database*/
    projTable.load();
  }

  //~ Methods ..........................................................................................................

  /**
   * This is the method that will produce the XML.
   * It will fill the xmlDoc with XML.
   *
   * @param    xmlDoc a StringBuffer to be filled with xml.
   */
  public void toXML(StringBuffer xmlDoc) throws Exception {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer): entered");
    }

    XMLBuilder builder = new XMLBuilder();

    /*Get start of document*/
    builder.getStartOfDocument(xmlDoc);
    xmlDoc.append(XML_USERNAME_START);
    xmlDoc.append(userProfile.getFirstName() + " " + userProfile.getLastName());
    xmlDoc.append(XML_USERNAME_END);
    projTable.toXML(xmlDoc);

    /*Get end of document*/
    builder.getEndOfDocument(xmlDoc);

    if (IntiroLog.e()) {
      IntiroLog.error(getClass(), getClass().getName() + ".toXML(StringBuffer): xmlDoc = " + xmlDoc.toString());
    }
  }
  /**
   * @return Returns the projTable.
   */
  public ProjectSubCodesTable getProjTable() {
    return projTable;
  }
}