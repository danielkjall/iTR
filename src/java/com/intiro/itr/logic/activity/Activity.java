/**
 * Title:
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.logic.activity;

import java.util.Vector;

import com.intiro.itr.db.DBConstants;
import com.intiro.itr.db.DBExecute;
import com.intiro.itr.db.DBQueries;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.toolbox.log.IntiroLog;

public class Activity {

  //~ Instance/static variables ........................................................................................

  public static final String XML_ENDTAG_CODE = "</code>";
  public static final String XML_ENDTAG_DESCRIPTION = "</description>";
  public static final String XML_ENDTAG_ID = "</id>";
  public static final String XML_STARTTAG_CODE = "<code>";
  public static final String XML_STARTTAG_DESCRIPTION = "<description>";
  public static final String XML_STARTTAG_ID = "<id>";
  String code = "";
  String description = "";
  int id = -1;

  //~ Methods ..........................................................................................................

  /**
   * Setter function
   *
   * return void
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * Getter function
   *
   * return a String representing the property.
   */
  public String getCode() {
    return code;
  }

  /**
   * Setter function
   *
   * return void
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Getter function
   *
   * return a String representing the property.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Setter function
   *
   * return void
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Getter function
   *
   * return a int representing the property.
   */
  public int getId() {
    return id;
  }

  public static Vector <Activity> loadActivitiesNotInProject(int projectId) throws XMLBuilderException {
    if (IntiroLog.d()) {
      IntiroLog.detail(Activity.class, Activity.class.getName() + ".loadActivitiesNotInProject(int projectId): Entering");
    }

    DBQueries query = new DBQueries();
    Vector <Activity> retval = new Vector <Activity> ();

    try {
      StringRecordset rs = query.loadActivitiesNotInProject(projectId);

      while (!rs.getEOF()) {
        Activity pa = new Activity();
        pa.setId(rs.getInt("Id"));
        pa.setDescription(rs.getField(DBConstants.PROJECTCODE_DESCRIPTION));
        pa.setCode(rs.getField(DBConstants.PROJECTCODE_CODE));
        retval.addElement(pa);
        rs.moveNext();
      }

      rs.close();
    } catch (Exception e) {
      if (IntiroLog.ce()) {
        IntiroLog.criticalError(Activity.class, Activity.class.getName() + ".loadActivitiesNotInProject(): ERROR FROM DATABASE, exception = " + e.getMessage());
      }

      throw new XMLBuilderException(Activity.class.getName() + ".loadActivitiesNotInProject(): " + e.getMessage());
    }
    if (IntiroLog.d()) {
      IntiroLog.detail(Activity.class, Activity.class.getName() + ".loadActivitiesNotInProject(): Leaving");
    }

    return retval;
  }

  /**
   * Delete the Activity.
   *
   * @return boolean.  false if nothing was deleted from db
   */
  public boolean delete() throws Exception {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".load(): Entering");
    }

    boolean retVal = false;

    try {
      retVal = new DBExecute().deleteActivity(getId());
    } catch (Exception e) {
      IntiroLog.info(getClass(), getClass().getName() + ".delete(): ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }

    return retVal;
  }

  public boolean load(String id) throws XMLBuilderException {
    DBQueries query = new DBQueries();
    boolean success = false;

    try {
      StringRecordset rs = query.loadActivity(Integer.parseInt(id));

      if (!rs.getEOF()) {
        setId(rs.getInt("Id"));
        setDescription(rs.getField("Description"));
        setCode(rs.getField("Code"));
        success = true;
      }

      rs.close();
    } catch (Exception e) {
      if (IntiroLog.ce()) {
        IntiroLog.criticalError(getClass(), getClass().getName() + ".load(): ERROR FROM DATABASE, exception = " + e.getMessage());
      }

      throw new XMLBuilderException(getClass().getName() + ".load(): " + e.getMessage());
    }
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".load(): Leaving");
    }

    return success;
  }

  public void save() throws XMLBuilderException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".save(): Entering");
    }
    if (getId() != -1) {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".save(): updating");
      }
      try {
        DBExecute dbExecute = new DBExecute();
        dbExecute.updateActivity(this);
      } catch (Exception e) {
        if (IntiroLog.ce()) {
          IntiroLog.criticalError(getClass(), getClass().getName() + ".save(): ERROR FROM DATABASE, exception = " + e.getMessage());
        }

        throw new XMLBuilderException(e.getMessage());
      }
    }
    else {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".save(): creating new");
      }
      try {
        DBQueries dbQuery = new DBQueries();
        StringRecordset rs = dbQuery.makeActivityAndFetchId(this);

        if (!rs.getEOF()) {
          setId(rs.getInt("maxId"));
        }
      } catch (Exception e) {
        if (IntiroLog.ce()) {
          IntiroLog.criticalError(getClass(), getClass().getName() + ".save(): ERROR FROM DATABASE, exception = " + e.getMessage());
        }

        throw new XMLBuilderException(e.getMessage());
      }
    }
  }

  public String toString() {
    StringBuffer retval = new StringBuffer();
    retval.append("id = " + getId());
    retval.append("description = " + getDescription());
    retval.append("code = " + getCode());

    return retval.toString();
  }

  public void toXML(StringBuffer xmlDoc) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer xmlDoc): Entering");
    }

    xmlDoc.append(XML_STARTTAG_ID);
    xmlDoc.append(getId());
    xmlDoc.append(XML_ENDTAG_ID);
    xmlDoc.append(XML_STARTTAG_DESCRIPTION);
    xmlDoc.append(getDescription());
    xmlDoc.append(XML_ENDTAG_DESCRIPTION);
    xmlDoc.append(XML_STARTTAG_CODE);
    xmlDoc.append(getCode());
    xmlDoc.append(XML_ENDTAG_CODE);
  }
}