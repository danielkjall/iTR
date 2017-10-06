package com.intiro.itr.logic;

import java.util.ArrayList;
import com.intiro.itr.db.DBConstants;
import com.intiro.itr.db.DBQueries;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.ColumnProperties;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.xml.XMLInnerTable;

/**
 * This class represents a table. It is abstract so it need to be subclasses to be used by a specific innertable.
 *
 */
public class ProjectsTable extends XMLInnerTable {

  static final String XML_PROJECTS_END = "</projects>";
  static final String XML_PROJECTS_START = "<projects>";

  /**
   * Constructor I. Construct a ProjectsTable. Load it with a StringRecordset with the load() method.
   *
   * @param profile a UserProfile.
   * @exception XMLBuilderException thrown if something goes wrong.
   */
  public ProjectsTable(UserProfile profile) throws XMLBuilderException {
    super(profile);
    setStartEndTags(XML_PROJECTS_START, XML_PROJECTS_END);
  }

  /**
   * Get columnProperties. This method is called when fetching information from the database. This method should return an ArrayList with
   * ColumnProperties.
   *
   * @return
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  @Override
  public ArrayList<ColumnProperties> getColumnProperties() throws XMLBuilderException {

    /*Create the columnproperties*/
    ColumnProperties prop1 = new ColumnProperties(DBConstants.PROJECT_ID_PK, DBConstants.PROJECT_MAINCODE);
    ColumnProperties prop2 = new ColumnProperties(DBConstants.PROJECT_NAME);
    ColumnProperties prop3 = new ColumnProperties(DBConstants.PROJECT_DESCRIPTION);

    /*Add them to arraylist*/
    ArrayList<ColumnProperties> colProps = new ArrayList<>();
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
   * @return
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  @Override
  public StringRecordset getResultset() throws XMLBuilderException {
    StringRecordset retval = null;

    try {
      retval = DBQueries.getProxy().getProjectsForUser(Integer.parseInt(getUserProfile().getUserId()));
    } catch (Exception e) {
      throw new XMLBuilderException(e.getMessage());
    }

    return retval;
  }
}
