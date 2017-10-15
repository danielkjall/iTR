package com.intiro.itr.logic.activity;

import java.util.ArrayList;
import com.intiro.itr.db.DBConstants;
import com.intiro.itr.db.DBExecute;
import com.intiro.itr.db.DBQueriesAdmin;
import com.intiro.itr.db.InvocationHandlerSetting;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.log.IntiroLog;
import java.util.List;

public class Activity {

  public static final String XML_ENDTAG_CODE = "</code>";
  public static final String XML_ENDTAG_DESCRIPTION = "</description>";
  public static final String XML_ENDTAG_ID = "</id>";
  public static final String XML_STARTTAG_CODE = "<code>";
  public static final String XML_STARTTAG_DESCRIPTION = "<description>";
  public static final String XML_STARTTAG_ID = "<id>";
  String code = "";
  String description = "";
  int id = -1;

  public void setCode(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public static List<Activity> loadActivitiesNotInProject(int projectId) throws XMLBuilderException {
    if (IntiroLog.d()) {
      IntiroLog.detail(Activity.class, Activity.class.getName() + ".loadActivitiesNotInProject(int projectId): Entering");
    }

    ArrayList<Activity> retval = new ArrayList<>();

    try {
      String cacheKey = Activity.class.getName() + ".loadActivitiesNotInProject_" + projectId;
      String statisticKey = Activity.class.getName() + ".loadActivitiesNotInProject";
      int cacheTime = 3600 * 10;
      InvocationHandlerSetting s = InvocationHandlerSetting.create(cacheKey, cacheTime, statisticKey);
      StringRecordset rs = DBQueriesAdmin.getProxy(s).loadActivitiesNotInProject(projectId);

      while (!rs.getEOF()) {
        Activity pa = new Activity();
        pa.setId(rs.getInt("Id"));
        pa.setDescription(rs.getField(DBConstants.PROJECTCODE_DESCRIPTION));
        pa.setCode(rs.getField(DBConstants.PROJECTCODE_CODE));
        retval.add(pa);
        rs.moveNext();
      }

      rs.close();
    } catch (Exception e) {
      IntiroLog.criticalError(Activity.class, Activity.class.getName() + ".loadActivitiesNotInProject(): ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(Activity.class.getName() + ".loadActivitiesNotInProject(): " + e.getMessage());
    }
    if (IntiroLog.d()) {
      IntiroLog.detail(Activity.class, Activity.class.getName() + ".loadActivitiesNotInProject(): Leaving");
    }

    return retval;
  }

  public boolean delete() throws Exception {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".load(): Entering");
    }

    boolean retVal = false;

    try {
      String statisticKey = Activity.class.getName() + ".delete";
      InvocationHandlerSetting s = InvocationHandlerSetting.create(statisticKey);
      retVal = DBExecute.getProxy(s).deleteActivity(getId());
    } catch (Exception e) {
      IntiroLog.info(getClass(), getClass().getName() + ".delete(): ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }

    return retVal;
  }

  public boolean load(String id) throws XMLBuilderException {
    boolean success = false;

    try {
      String cacheKey = getClass().getName() + ".load_" + id;
      String statisticKey = getClass().getName() + ".load";
      int cacheTime = 3600 * 10;
      InvocationHandlerSetting s = InvocationHandlerSetting.create(cacheKey, cacheTime, statisticKey);
      StringRecordset rs = DBQueriesAdmin.getProxy(s).loadActivity(Integer.parseInt(id));

      if (!rs.getEOF()) {
        setId(rs.getInt("Id"));
        setDescription(rs.getField("Description"));
        setCode(rs.getField("Code"));
        success = true;
      }

      rs.close();
    } catch (Exception e) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".load(): ERROR FROM DATABASE, exception = " + e.getMessage());
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

    String statisticKey = getClass().getName() + ".save";
    InvocationHandlerSetting s = InvocationHandlerSetting.create(statisticKey);

    if (getId() != -1) {

      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".save(): updating");
      }

      try {
        DBExecute.getProxy(s).updateActivity(this);
      } catch (Exception e) {
        IntiroLog.criticalError(getClass(), getClass().getName() + ".save(): ERROR FROM DATABASE, exception = " + e.getMessage());
        throw new XMLBuilderException(e.getMessage());
      }
    } else {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".save(): creating new");
      }
      try {
        StringRecordset rs = DBQueriesAdmin.getProxy(s).makeActivityAndFetchId(this);

        if (!rs.getEOF()) {
          setId(rs.getInt("maxId"));
        }
      } catch (Exception e) {
        IntiroLog.criticalError(getClass(), getClass().getName() + ".save(): ERROR FROM DATABASE, exception = " + e.getMessage());
        throw new XMLBuilderException(e.getMessage());
      }
    }
  }

  @Override
  public String toString() {
    StringBuilder retval = new StringBuilder();
    retval.append("id = ").append(getId());
    retval.append("description = ").append(getDescription());
    retval.append("code = ").append(getCode());

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
