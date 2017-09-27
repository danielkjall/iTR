/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.logic;

import java.util.ArrayList;

import com.intiro.itr.db.DBConstants;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.ColumnProperties;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.xml.XMLInnerTable;

/**
 * This class represents a table.
 * It is abstract so it need to be subclasses to be used by a specific innertable.
 */
public class ProjectSubCodesTable extends XMLInnerTable {

  //~ Instance/static variables ........................................................................................

  static final String XML_SUB_CODES_END = "</subcodes>";

  /*XML tags*/
  static final String XML_SUB_CODES_START = "<subcodes>";
  protected String projectId = null;

  //~ Constructors .....................................................................................................

  /**
   * Constructor I.
   * Construct a ProjectsTable.
   * Load it with a StringRecordset with the load() method.
   *
   * @param        profile                a UserProfile.
   * @exception    XMLBuilderException    thrown if something goes wrong.
   */
  public ProjectSubCodesTable(UserProfile profile, String projectId) throws XMLBuilderException {
    super(profile);
    this.projectId = projectId;
    setStartEndTags(XML_SUB_CODES_START, XML_SUB_CODES_END);
  }

  //~ Methods ..........................................................................................................

  /**
   * Get columnProperties.
   * This method is called when fetching information from the database.
   * This method should return an ArrayList with ColumnProperties.
   */
  public ArrayList <ColumnProperties> getColumnProperties() throws XMLBuilderException {

    /*Create the columnproperties*/
    ColumnProperties prop1 = new ColumnProperties(DBConstants.PROJECTCODE_ID_PK, DBConstants.PROJECTCODE_CODE);
    ColumnProperties prop2 = new ColumnProperties(DBConstants.PROJECTCODE_DESCRIPTION);

    /*Add them to arraylist*/
    ArrayList <ColumnProperties> colProps = new ArrayList <ColumnProperties> ();
    colProps.add(prop1);
    colProps.add(prop2);

    /*Set columnProperties = colProps*/
    columnProperties = colProps;

    /*Return colProps*/
    return colProps;
  }

  /**
   * Return the recordset for this ProjectSubCodesTable.
   *
   * @exception    throws XMLBuilderException if something goes wrong.
   */
  public StringRecordset getResultset() throws XMLBuilderException {
    return getResultset(projectId);
  }

  private StringRecordset getResultset(String projId) throws XMLBuilderException {
    StringRecordset retval = null;

    try {
      retval = dbQuery.getProjectCodesForProject(Integer.parseInt(projId));
    } catch (Exception e) {
      throw new XMLBuilderException(e.getMessage());
    }

    return retval;
  }
}