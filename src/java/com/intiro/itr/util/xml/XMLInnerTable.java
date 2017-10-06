package com.intiro.itr.util.xml;

import java.util.ArrayList;

import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.log.IntiroLog;
import java.util.List;

/**
 * This class represents a table. It is abstract so it need to be subclasses to be used by a specific innertable.
 *
 */
public abstract class XMLInnerTable extends DynamicXMLCarrier {

  //~ Instance/static variables ........................................................................................
  static final String XML_TD_DISPLAY_END = "</display>";
  static final String XML_TD_DISPLAY_START = "<display>";
  static final String XML_TD_END = "</td>";
  static final String XML_TD_INDEX_END = "</tdi>";
  static final String XML_TD_INDEX_START = "<tdi>";
  static final String XML_TD_START = "<td>";
  static final String XML_TD_VALUE_END = "</value>";
  static final String XML_TD_VALUE_START = "<value>";
  static final String XML_TR_END = "</tr>";
  static final String XML_TR_INDEX_END = "</tri>";
  static final String XML_TR_INDEX_START = "<tri>";

  /*XML tags*/
  static final String XML_TR_START = "<tr>";
  protected List<ColumnProperties> columnProperties = null;
  protected String nameEnd = null;
  protected String nameStart = null;
  protected StringRecordset rs = null;

  //~ Constructors .....................................................................................................
  /**
   * Constructor I. Construct a XMLInnerTable. You must set the column properties with the method setColumnProperties(). Load it with a
   * StringRecordset with the load() method.
   *
   * @param profile a UserProfile.
   * @exception XMLBuilderException thrown if something goes wrong.
   */
  public XMLInnerTable(UserProfile profile) throws XMLBuilderException {
    super(profile);
  }

  /**
   * Constructor II. Construct a XMLInnertable. Load it with a StringRecordset with the load() method.
   *
   * @param profile a UserProfile.
   * @param columnProperties a ArrayList with ColumnProperties.
   * @exception XMLBuilderException thrown if something goes wrong.
   */
  public XMLInnerTable(UserProfile profile, ArrayList columnProperties) throws XMLBuilderException {
    super(profile);
    this.columnProperties = columnProperties;
  }

  //~ Methods ..........................................................................................................
  /**
   * Set columnProperties.
   *
   * @param columnProperties
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public void setColumnProperties(List<ColumnProperties> columnProperties) throws XMLBuilderException {
    this.columnProperties = columnProperties;
  }

  /**
   * Get columnProperties. This method is called when fetching information from the database. This method should return an ArrayList with
   * ColumnProperties.
   *
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public List<ColumnProperties> getColumnProperties() throws XMLBuilderException {
    if (columnProperties == null || columnProperties.isEmpty()) {
      throw new XMLBuilderException(getClass().getName() + ".getColumnProperties(): columnProperties was not set. Override this method or use method setColumnProperties() to set the columnProperties.");
    }

    return columnProperties;
  }

  /**
   * Set start and end tag on document.
   *
   * @param nameStart, a String with the start tag of this innertable.
   * @param nameEnd, a String with the end tag of this innertable.
   */
  public void setStartEndTags(String nameStart, String nameEnd) throws XMLBuilderException {
    this.nameStart = nameStart;
    this.nameEnd = nameEnd;
  }

  /**
   * Load the inner table.
   *
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public void load() throws XMLBuilderException {
    rs = getResultset();
  }

  /**
   * Make xml of the combobox.
   *
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  @Override
  public void toXML(StringBuffer xmlDoc) throws XMLBuilderException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(): Entering");
    }
    if (rs == null) {
      throw new XMLBuilderException(getClass().getName() + ".toXML(): Resultset has not been loaded, DEVELOPER ERROR!!!! call the method load(). ");
    }

    /*Make the columnProperties correctly set before using it.*/
    columnProperties = getColumnProperties();

    if (nameStart != null) {
      xmlDoc.append(nameStart);
    }

    int trIndex = 0;

    try {
      while (!rs.getEOF()) {
        xmlDoc.append(XML_TR_START);

        /*tr index*/
        xmlDoc.append(XML_TR_INDEX_START);
        xmlDoc.append(trIndex);
        xmlDoc.append(XML_TR_INDEX_END);

        for (int tdIndex = 0; tdIndex < columnProperties.size(); tdIndex++) {

          /*Retrive the ColumnProperties for this row*/
          ColumnProperties tdProp = columnProperties.get(tdIndex);

          /*td start*/
          xmlDoc.append(XML_TD_START);

          /*td index*/
          xmlDoc.append(XML_TD_INDEX_START);
          xmlDoc.append(tdIndex);
          xmlDoc.append(XML_TD_INDEX_END);

          /*td value*/
          xmlDoc.append(XML_TD_VALUE_START);
          xmlDoc.append(rs.getField(tdProp.getValue()));
          xmlDoc.append(XML_TD_VALUE_END);

          /*td display value*/
          if (tdProp.getDisplay() != null) {
            xmlDoc.append(XML_TD_DISPLAY_START);
            xmlDoc.append(rs.getField(tdProp.getDisplay()));
            xmlDoc.append(XML_TD_DISPLAY_END);
          }

          /*td end */
          xmlDoc.append(XML_TD_END);
        }

        /*End tr*/
        xmlDoc.append(XML_TR_END);
        trIndex++;
        rs.moveNext();
      }
    } catch (Exception e) {
      throw new XMLBuilderException(e.getMessage());
    }
    if (nameEnd != null) {
      xmlDoc.append(nameEnd);
    }
  }

  /**
   * Return the resultset for this InnerTable.
   *
   * @return
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  protected abstract StringRecordset getResultset() throws XMLBuilderException;
}
