package com.intiro.itr.logic.project;

import com.intiro.itr.db.DBConstants;
import com.intiro.itr.db.DBExecute;
import com.intiro.itr.db.DBQueriesAdmin;
import com.intiro.itr.db.InvocationHandlerSetting;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.log.IntiroLog;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProjectActivity implements Serializable {

  public static final String XML_ENDTAG_CODE = "</code>";
  public static final String XML_ENDTAG_DESC = "</description>";
  public static final String XML_ENDTAG_ID = "</id>";
  public static final String XML_ENDTAG_ITR_PROJECTCODEID = "</projectcodeid>";
  public static final String XML_ENDTAG_ITR_PROJECTID = "</projectid>";
  public static final String XML_STARTTAG_CODE = "<code>";
  public static final String XML_STARTTAG_DESC = "<description>";
  public static final String XML_STARTTAG_ID = "<id>";
  public static final String XML_STARTTAG_ITR_PROJECTCODEID = "<projectcodeid>";
  public static final String XML_STARTTAG_ITR_PROJECTID = "<projectid>";
  String code = "";
  String description;
  int id;
  int projectCodeId;
  int projectId;
  boolean stillAssigned = true;

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

  public void setProjectCodeId(int projectCodeId) {
    this.projectCodeId = projectCodeId;
  }

  public int getProjectCodeId() {
    return projectCodeId;
  }

  public void setProjectId(int projectId) {
    this.projectId = projectId;
  }

  public int getProjectId() {
    return projectId;
  }

  public void setStillAssigned(boolean stillAssigned) {
    this.stillAssigned = stillAssigned;
  }

  public boolean getStillAssigned() {
    return stillAssigned;
  }

  public static List<ProjectActivity> loadProjectActivities(int projectId) throws XMLBuilderException {
    if (IntiroLog.d()) {
      IntiroLog.detail(ProjectActivity.class, ProjectActivity.class.getName() + ".getProjectActivities(int projectId): Entering");
    }

    List<ProjectActivity> retval = new ArrayList<>();

    try {
      String cacheKey = ProjectActivity.class.getName() + ".loadProjectActivities_" + projectId;
      String statisticKey = ProjectActivity.class.getName() + ".loadProjectActivities";
      int cacheTime = 3600 * 10;
      InvocationHandlerSetting s = InvocationHandlerSetting.create(cacheKey, cacheTime, statisticKey);
      StringRecordset rs = DBQueriesAdmin.getProxy(s).loadProjectActivitiesForProject(projectId);

      while (!rs.getEOF()) {
        ProjectActivity pa = new ProjectActivity();
        pa.setId(rs.getInt("Id"));
        pa.setProjectId(rs.getInt("ITR_ProjectId"));
        pa.setProjectCodeId(rs.getInt("ITR_ProjectCodeId"));
        pa.setDescription(rs.getField(DBConstants.PROJECTCODE_DESCRIPTION));
        pa.setCode(rs.getField(DBConstants.PROJECTCODE_CODE));
        retval.add(pa);
        rs.moveNext();
      }

      rs.close();
    } catch (Exception e) {
      IntiroLog.criticalError(ProjectActivity.class, ProjectActivity.class.getName() + ".loadProjectActivities(): ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(ProjectActivity.class.getName() + ".loadProjectActivities(): " + e.getMessage());
    }
    if (IntiroLog.d()) {
      IntiroLog.detail(ProjectActivity.class, ProjectActivity.class.getName() + ".loadProjectActivities(): Leaving");
    }

    return retval;
  }

  public boolean delete() throws Exception {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".delete(): Entering");
    }

    boolean retVal = false;

    try {
      String statisticKey = getClass().getName() + ".delete";
      InvocationHandlerSetting s = InvocationHandlerSetting.create(statisticKey);
      retVal = DBExecute.getProxy(s).deleteProjectActivity(getId());
    } catch (Exception e) {
      IntiroLog.info(getClass(), getClass().getName() + ".delete(): ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }

    return retVal;
  }

  public boolean load(String id) throws XMLBuilderException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".load(): Entering");
    }

    boolean success = false;
    try {
      String cacheKey = getClass().getName() + ".load_" + id;
      String statisticKey = getClass().getName() + ".load";
      int cacheTime = 3600 * 10;
      InvocationHandlerSetting s = InvocationHandlerSetting.create(cacheKey, cacheTime, statisticKey);
      StringRecordset rs = DBQueriesAdmin.getProxy(s).loadProjectActivity(Integer.parseInt(id));

      if (!rs.getEOF()) {
        setId(rs.getInt("Id"));
        setProjectId(rs.getInt("ITR_ProjectId"));
        setProjectCodeId(rs.getInt("ITR_ProjectCodeId"));
        setDescription(rs.getField(DBConstants.PROJECTCODE_DESCRIPTION));
        setCode(rs.getField(DBConstants.PROJECTCODE_CODE));
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

    if (getId() != 0) {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".save(): updating");
      }
      try {
        DBExecute.getProxy(s).updateProjectActivity(this);
      } catch (Exception e) {
        IntiroLog.criticalError(getClass(), getClass().getName() + ".save(): ERROR FROM DATABASE, exception = " + e.getMessage());
        throw new XMLBuilderException(e.getMessage());
      }
    } else {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".save(): creating new");
      }
      try {
        StringRecordset rs = DBQueriesAdmin.getProxy(s).makeNewProjectActivityAndFetchId(this);

        if (!rs.getEOF()) {
          int tmp = rs.getInt("maxId");
          setId(tmp);
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
    retval.append(", id = ").append(getId());
    retval.append(", projectId = ").append(getProjectId());
    retval.append(", projectCodeId = ").append(getProjectCodeId());
    return retval.toString();
  }

  public void toXML(StringBuffer xmlDoc) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer xmlDoc): Entering");
    }

    xmlDoc.append(XML_STARTTAG_ID);
    xmlDoc.append(getId());
    xmlDoc.append(XML_ENDTAG_ID);
    xmlDoc.append(XML_STARTTAG_DESC);
    xmlDoc.append(getDescription());
    xmlDoc.append(XML_ENDTAG_DESC);
    xmlDoc.append(XML_STARTTAG_CODE);
    xmlDoc.append(getCode());
    xmlDoc.append(XML_ENDTAG_CODE);
    xmlDoc.append(XML_STARTTAG_ITR_PROJECTID);
    xmlDoc.append(getProjectId());
    xmlDoc.append(XML_ENDTAG_ITR_PROJECTID);
    xmlDoc.append(XML_STARTTAG_ITR_PROJECTCODEID);
    xmlDoc.append(getProjectCodeId());
    xmlDoc.append(XML_ENDTAG_ITR_PROJECTCODEID);
  }
}
