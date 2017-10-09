package com.intiro.itr.db.vo;

import com.intiro.itr.db.DBConstants;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.log.IntiroLog;
import com.intiro.itr.util.xml.XMLBuilderException;

public class ProjectPropertyVO {

  private String projectId;
  private String projectDescription;
  private String projectCode;
  private String projectName;
  private String fromDate;
  private String toDate;
  private String companyId;
  private String activated;
  private String adminProject;
  private String contract;
  private String technique;

  public String getProjectId() {
    return projectId;
  }

  public String getProjectDescription() {
    return projectDescription;
  }

  public String getProjectCode() {
    return projectCode;
  }

  public String getProjectName() {
    return projectName;
  }

  public String getFromDate() {
    return fromDate;
  }

  public String getToDate() {
    return toDate;
  }

  public String getCompanyId() {
    return companyId;
  }

  public String getActivated() {
    return activated;
  }

  public String getAdminProject() {
    return adminProject;
  }

  public String getContract() {
    return contract;
  }

  public String getTechnique() {
    return technique;
  }

  public void loadFromRS(StringRecordset rs) throws XMLBuilderException {
    try {
      projectId = rs.getField(DBConstants.PROJECT_ID_PK);
      projectDescription = rs.getField(DBConstants.PROJECT_DESCRIPTION);
      String pc = rs.getField(DBConstants.PROJECT_MAINCODE);
      if (projectCode != null) {
        projectCode = pc;
      }
      projectName = rs.getField(DBConstants.PROJECT_NAME);
      fromDate = rs.getField(DBConstants.PROJECT_FROMDATE);
      toDate = rs.getField(DBConstants.PROJECT_TODATE);
      companyId = rs.getField(DBConstants.PROJECT_COMPANYID_FK);
      activated = rs.getField(DBConstants.PROJECT_ACTIVE);
      adminProject = rs.getField(DBConstants.PROJECT_ADMINPROJECT);
      contract = rs.getField(DBConstants.PROJECT_CONTRACT);
      technique = rs.getField(DBConstants.PROJECT_TECHNIQUE);
    } catch (Exception e) {
      IntiroLog.error(getClass(), getClass().getName() + ".loadFromRS(String): ERROR FROM DATABASE, Collecting Project data. exception = " + e.getMessage());
    }
  }
}
