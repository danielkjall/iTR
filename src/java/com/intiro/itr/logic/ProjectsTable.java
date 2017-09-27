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
 *
 */
public class ProjectsTable extends XMLInnerTable {

  //~ Instance/static variables ........................................................................................

  static final String XML_PROJECTS_END = "</projects>";

  /*XML tags*/
  static final String XML_PROJECTS_START = "<projects>";

  //~ Constructors .....................................................................................................

  /**
   * Constructor I.
   * Construct a ProjectsTable.
   * Load it with a StringRecordset with the load() method.
   *
   * @param        profile                a UserProfile.
   * @exception    XMLBuilderException    thrown if something goes wrong.
   */
  public ProjectsTable(UserProfile profile) throws XMLBuilderException {
    super(profile);
    setStartEndTags(XML_PROJECTS_START, XML_PROJECTS_END);
  }

  //~ Methods ..........................................................................................................

  /**
   * Get columnProperties.
   * This method is called when fetching information from the database.
   * This method should return an ArrayList with ColumnProperties.
   */
  public ArrayList <ColumnProperties> getColumnProperties() throws XMLBuilderException {

    /*Create the columnproperties*/
    ColumnProperties prop1 = new ColumnProperties(DBConstants.PROJECT_ID_PK, DBConstants.PROJECT_MAINCODE);
    ColumnProperties prop2 = new ColumnProperties(DBConstants.PROJECT_NAME);
    ColumnProperties prop3 = new ColumnProperties(DBConstants.PROJECT_DESCRIPTION);

    /*Add them to arraylist*/
    ArrayList <ColumnProperties> colProps = new ArrayList <ColumnProperties> ();
    colProps.add(prop1);
    colProps.add(prop2);
    colProps.add(prop3);

    /*Set columnProperties = colProps*/
    columnProperties = colProps;

    /*Return colProps*/
    return colProps;
  }

  /**
   * Return the recordset for this ProjectsTable.
   *
   * @exception    throws XMLBuilderException if something goes wrong.
   */
  public StringRecordset getResultset() throws XMLBuilderException {
    StringRecordset retval = null;

    try {
      retval = dbQuery.getProjectsForUser(Integer.parseInt(getUserProfile().getUserId()));
    } catch (Exception e) {
      throw new XMLBuilderException(e.getMessage());
    }

    return retval;
  }
}