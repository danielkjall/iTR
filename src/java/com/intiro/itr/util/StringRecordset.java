/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 *
 * @author Daniel Kjall
 * @version 1.0
 */
package com.intiro.itr.util;

import com.intiro.itr.util.log.IntiroLog;

import java.sql.*;
import java.text.SimpleDateFormat;

import java.util.*;

/**
 * This class acts as an ADORecordset, but is implemented as strings and ArrayLists
 */
public class StringRecordset {

  //~ Instance/static variables ........................................................................................
  /**
   * Delimiter that is used to separate fields in import/export from/to StringRecordset
   */
  public static final String FIELD_DELIMITER = "|";

  /**
   * Delimiter that is used to separate rows in import/export from/to StringRecordset
   */
  public static final String ROW_DELIMITER = "^";
  private int actualRow = 0;
  private List<String> headerVec = new ArrayList<String>();
  private List<List<String>> rowVec = new ArrayList<List<String>>();

  //~ Constructors .....................................................................................................
  /**
   * Constructor I for StringRecordset.
   *
   * Import a Java ResultSet
   *
   * @param rs A java.sql.Recordset
   * @exception throws Exception if an exception is encountered
   */
  public StringRecordset(ResultSet rs) throws Exception {
    ResultSetMetaData rsMeta = null;
    rsMeta = rs.getMetaData();

    int fieldCount = rsMeta.getColumnCount();

    for (int i = 1; i <= fieldCount; i++) {
      headerVec.add(rsMeta.getColumnLabel(i));
    }
    while (rs.next()) {
      List<String> oneRowVec = new ArrayList<>();

      for (int j = 1; j <= fieldCount; j++) {
        String value = rs.getString(j);
        
        if(rsMeta.getColumnType(j) == java.sql.Types.DATE || rsMeta.getColumnType(j) == java.sql.Types.TIMESTAMP) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
            value = sdf.format(rs.getDate(j));
        }

        // Check if the database value is null
        if (rs.wasNull() || value.equalsIgnoreCase("null")) {
          oneRowVec.add("");
        } else {
          oneRowVec.add(value);
        }
      }

      rowVec.add(oneRowVec);
    }
  }

  //~ Methods ..........................................................................................................
  /**
   * Returns the specified field value for the current row.
   *
   * @param ix the field index
   * @return an boolean
   * @throws java.lang.Exception
   */
  public boolean getBoolean(int ix) throws Exception {
    boolean val = true;

    try {
      val = Boolean.getBoolean(getField(ix));
    } catch (Exception e) {
      IntiroLog.error(getClass(), getClass().getName() + ".getBoolean(): Could not convert " + getField(ix) + " into a boolean. Exception = " + e);
    }
    return val;
  }

  /**
   * Get the specified field value for the current row.
   *
   * @param ix the field name
   * @return an boolean
   * @throws java.lang.Exception
   */
  public boolean getBoolean(String ix) throws Exception {
    boolean val = true;

    try {
      val = Boolean.getBoolean(getField(ix));
    } catch (Exception e) {
      IntiroLog.error(getClass(), getClass().getName() + ".getBoolean(): Could not convert " + getField(ix) + " into a boolean. Exception = " + e);
    }

    return val;
  }

  /**
   * Returns the specified field value for the current row.
   *
   * @param ix the field index
   * @return an double
   * @throws java.lang.Exception
   */
  public double getDouble(int ix) throws Exception {
    double val = 0;

    try {
      val = Double.parseDouble(getField(ix));
    } catch (Exception e) {
      IntiroLog.error(getClass(), getClass().getName() + ".getDouble(): Could not convert " + getField(ix) + " into a double. Exception = " + e);
    }

    return val;
  }

  /**
   * Get the specified field value for the current row.
   *
   * @param ix the field name
   * @return an double
   * @throws java.lang.Exception
   */
  public double getDouble(String ix) throws Exception {
    double val = 0;

    try {
      val = Double.parseDouble(getField(ix));
    } catch (Exception e) {
      IntiroLog.error(getClass(), getClass().getName() + ".getDouble(): Could not convert " + getField(ix) + " into a double. Exception = " + e);
    }

    return val;
  }

  /**
   * Return true if we are on the last row.
   * <P>
   *
   * @return true, if end of row
   */
  public boolean getEOF() {
    boolean eof = false;

    if (actualRow >= rowVec.size()) {
      eof = true;
    }

    return eof;
  }

  /**
   * Sets the specified field value for the current row.
   * <P>
   *
   * @param ix the field name
   * @param value the value to set
   * @throws java.lang.Exception
   */
  public void setField(String ix, String value) throws Exception {
    List<String> v = getActualRow();
    boolean found = false;
    int i = 0;

    for (i = 0; i < headerVec.size(); i++) {
      if (ix.equalsIgnoreCase(headerVec.get(i))) {
        found = true;
        break;
      }
    }
    if (!found) {
      String e = "Field [" + ix + "] not found! Valid fields are ";

      for (int j = 0; j < fieldCount(); j++) {
        e += "[" + headerVec.get(j) + "] ";
      }

      throw new Exception(e);
    }

    v.add(i, value);
  }

  /**
   * Sets the specified field value for the current row.
   * <P>
   *
   * @param ix the field index
   * @param value the value to set
   * @throws java.lang.Exception
   */
  public void setField(int ix, String value) throws Exception {
    List<String> v = getActualRow();
    v.add(ix, value);
  }

  /**
   * Sets the specified field value for a specific row.
   * <P>
   *
   * @param row the row number
   * @param field
   * @param value the value to set
   * @throws java.lang.Exception
   */
  public void setField(int row, int field, String value) throws Exception {

    // Check if there are any rows
    if (rowCount() == 0) {
      throw new Exception("0 rows in Recordset");
    }
    // Check if row exist
    if (row > rowCount()) {
      throw new Exception("Row " + row + " doesnt exist in Recordset");
    }

    List<String> v = rowVec.get(row);
    v.add(field, value);
  }

  /**
   * Get the specified field value for the current row.
   * <P>
   *
   * @param ix the field name
   * @return a String containing the field value
   * @throws java.lang.Exception
   */
  public String getField(String ix) throws Exception {
    List<String> v = getActualRow();
    boolean found = false;
    int i = 0;

    for (i = 0; i < headerVec.size(); i++) {
      if (ix.equalsIgnoreCase(headerVec.get(i))) {
        found = true;
        break;
      }
    }
    if (!found) {
      String e = "Field [" + ix + "] not found! Valid fields are ";

      for (int j = 0; j < fieldCount(); j++) {
        e += "[" + headerVec.get(j) + "] ";
      }

      throw new Exception(e);
    }

    return v.get(i);
  }

  /**
   * Returns the specified field value for the current row.
   * <P>
   *
   * @param ix the field index
   * @return a String containing the field value
   * @throws java.lang.Exception
   */
  public String getField(int ix) throws Exception {
    List<String> v = getActualRow();

    return v.get(ix);
  }

  /**
   * Returns the specified field value for a specific row.
   * <P>
   *
   * @param row the row number
   * @param field
   * @return a String containing the field value
   * @throws java.lang.Exception
   */
  public String getField(int row, int field) throws Exception {
    // Check if there are any rows
    if (rowCount() == 0) {
      throw new Exception("0 rows in Recordset");
    }
    // Check if row exist
    if (row > rowCount()) {
      throw new Exception("Row " + row + " doesnt exist in Recordset");
    }

    List<String> v = rowVec.get(row);

    return v.get(field);
  }

  /**
   * Gets the specific header name.
   * <P>
   *
   * @param ix the field index
   * @return a String, if succeeded
   * @throws java.lang.Exception
   */
  public String getHeaderName(int ix) throws Exception {
    return headerVec.get(ix);
  }

  ///////////////////////////////////////////////////////////
  //////////////////  Convienient methods  //////////////////
  ///////////////////////////////////////////////////////////
  /**
   * Returns the specified field value for the current row.
   *
   * @param ix the field index
   * @return an ITRCalendar
   * @throws java.lang.Exception
   */
  public ITRCalendar getITRDate(int ix) throws Exception {
    ITRCalendar val = new ITRCalendar();

    try {
      val.loadCalendarWithDate(getField(ix));
    } catch (Exception e) {
      IntiroLog.error(getClass(), getClass().getName() + ".getITRDate(): Could not convert " + getField(ix) + " into an ITRCalendar. Exception = " + e);
    }

    return val;
  }

  /**
   * Get the specified field value for the current row.
   *
   * @param ix the field name
   * @return an ITRCalendar
   * @throws java.lang.Exception
   */
  public ITRCalendar getITRDate(String ix) throws Exception {
    ITRCalendar val = new ITRCalendar();

    try {
      val.loadCalendarWithDate(getField(ix));
    } catch (Exception e) {
      IntiroLog.error(getClass(), getClass().getName() + ".getITRDate(): Could not convert " + getField(ix) + " into an ITRCalendar. Exception = " + e);
    }

    return val;
  }

  /**
   * Returns the specified field value for the current row.
   *
   * @param ix the field index
   * @return an int
   * @throws java.lang.Exception
   */
  public int getInt(int ix) throws Exception {
    int val = 0;

    try {
      val = Integer.parseInt(getField(ix));
    } catch (Exception e) {
      IntiroLog.error(getClass(), getClass().getName() + ".getInt(): Could not convert " + getField(ix) + " into an int. Exception = " + e);
    }

    return val;
  }

  /**
   * Get the specified field value for the current row.
   *
   * @param ix the field name
   * @return an int
   * @throws java.lang.Exception
   */
  public int getInt(String ix) throws Exception {
    int val = 0;

    try {
      val = Integer.parseInt(getField(ix));
    } catch (Exception e) {
      IntiroLog.error(getClass(), getClass().getName() + ".getInt(): Could not convert " + getField(ix) + " into an int. Exception = " + e);
    }

    return val;
  }

  /**
   * Adds a new column to the Recordset.
   * <P>
   * @param columnName A column name
   * @throws java.lang.Exception
   */
  public void addColumn(String columnName) throws Exception {
    List<String> vec;
    headerVec.add(columnName);

    for (int i = 0; i < rowVec.size(); i++) {
      vec = rowVec.get(i);
      vec.add("");
    }
  }

  /**
   * Adds a new row consisting of the specified columns in the array
   * <P>
   * @param row A new row formatted as a String array
   * @throws java.lang.Exception
   */
  public void addNew(String[] row) throws Exception {
    List<String> oneRowVec = new ArrayList<>();
    oneRowVec.addAll(Arrays.asList(row));
    rowVec.add(oneRowVec);
  }

  /**
   * Close the recordset. Should always be done after you used the StringRecordset
   */
  public void close() {
    moveFirst();
    headerVec.clear();
    rowVec.clear();
  }

  /**
   * Returns the numbers of fields.
   * <P>
   *
   * @return the number of fields
   */
  public int fieldCount() {
    return headerVec.size();
  }

  /**
   * Moves to the first row in the recordset
   */
  public void moveFirst() {
    actualRow = 0;
  }

  /**
   * Moves to the next row in the recordset
   */
  public void moveNext() {
    actualRow++;
  }

  /**
   * Returns the numbers of rows
   * <P>
   * @return the number of rows
   */
  public int rowCount() {
    return rowVec.size();
  }

  /**
   * Export the recordset to a importable string format.
   * <P>
   *
   * @return a String, if succeeded
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    // Creates header row
    for (int i = 0; i < headerVec.size(); i++) {
      sb.append(headerVec.get(i)).append(FIELD_DELIMITER);
    }

    sb.append(ROW_DELIMITER);

    // Creates the recordset
    for (int i = 0; i < rowVec.size(); i++) {
      List<String> fieldVec = rowVec.get(i);
      int fields = fieldVec.size();

      for (int j = 0; j < fields; j++) {
        sb.append(fieldVec.get(j)).append(FIELD_DELIMITER);
      }
      if (i < (rowVec.size() - 1)) {
        sb.append(ROW_DELIMITER);
      }
    }

    return sb.toString();
  }

  /**
   * Returns current row.
   * <P>
   *
   * @return a ArrayList, if succeeded
   */
  private List<String> getActualRow() throws Exception {

    // Check if there are any rows
    if (rowCount() == 0) {
      throw new Exception("0 rows in Recordset");
    }
    // Check if the actual row exist
    if (actualRow > rowCount()) {
      throw new Exception("Row " + actualRow + " doesnt exist in Recordset");
    }

    return rowVec.get(actualRow);
  }
}
