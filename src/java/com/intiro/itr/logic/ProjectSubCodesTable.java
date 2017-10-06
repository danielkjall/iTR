package com.intiro.itr.logic;

import java.util.ArrayList;
import com.intiro.itr.db.DBConstants;
import com.intiro.itr.db.DBQueries;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.ColumnProperties;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.xml.XMLInnerTable;
import java.util.List;

/**
 * This class represents a table. It is abstract so it need to be subclasses to be used by a specific innertable.
 */
public class ProjectSubCodesTable extends XMLInnerTable {

  static final String XML_SUB_CODES_END = "</subcodes>";
  static final String XML_SUB_CODES_START = "<subcodes>";
  protected String projectId = null;

  /**
   * Constructor I. Construct a ProjectsTable. Load it with a StringRecordset with the load() method.
   *
   * @param profile a UserProfile.
   * @param projectId
   * @exception XMLBuilderException thrown if something goes wrong.
   */
  public ProjectSubCodesTable(UserProfile profile, String projectId) throws XMLBuilderException {
    super(profile);
    this.projectId = projectId;
    setStartEndTags(XML_SUB_CODES_START, XML_SUB_CODES_END);
  }

  /**
   * Get columnProperties. This method is called when fetching information from the database. This method should return an ArrayList with
   * ColumnProperties.
   *
   * @return
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  @Override
  public List<ColumnProperties> getColumnProperties() throws XMLBuilderException {

    /*Create the columnproperties*/
    ColumnProperties prop1 = new ColumnProperties(DBConstants.PROJECTCODE_ID_PK, DBConstants.PROJECTCODE_CODE);
    ColumnProperties prop2 = new ColumnProperties(DBConstants.PROJECTCODE_DESCRIPTION);

    /*Add them to arraylist*/
    ArrayList<ColumnProperties> colProps = new ArrayList<>();
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
   * @return
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  @Override
  public StringRecordset getResultset() throws XMLBuilderException {
    return getResultset(projectId);
  }

  private StringRecordset getResultset(String projId) throws XMLBuilderException {
    StringRecordset retval = null;

    try {
      retval = DBQueries.getProxy().getProjectCodesForProject(Integer.parseInt(projId));
    } catch (Exception e) {
      throw new XMLBuilderException(e.getMessage());
    }

    return retval;
  }
}
