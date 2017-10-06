package com.intiro.itr.logic.project;

import com.intiro.itr.db.*;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.log.IntiroLog;
import java.util.ArrayList;
import java.util.List;

public class ProjectMember {

  public static final String XML_ENDTAG_ACTIVE = "</active>";
  public static final String XML_ENDTAG_FIRSTNAME = "</firstname>";
  public static final String XML_ENDTAG_ID = "</id>";
  public static final String XML_ENDTAG_ITR_PROJECTID = "</itr_projectid>";
  public static final String XML_ENDTAG_ITR_USERID = "</itr_userid>";
  public static final String XML_ENDTAG_LASTNAME = "</lastname>";
  public static final String XML_ENDTAG_LOGINID = "</loginid>";
  public static final String XML_ENDTAG_PROJECTADMIN = "</projectadmin>";
  public static final String XML_ENDTAG_RATE = "</rate>";
  public static final String XML_STARTTAG_ACTIVE = "<active>";
  public static final String XML_STARTTAG_FIRSTNAME = "<firstname>";
  public static final String XML_STARTTAG_ID = "<id>";
  public static final String XML_STARTTAG_ITR_PROJECTID = "<itr_projectid>";
  public static final String XML_STARTTAG_ITR_USERID = "<itr_userid>";
  public static final String XML_STARTTAG_LASTNAME = "<lastname>";
  public static final String XML_STARTTAG_LOGINID = "<loginid>";
  public static final String XML_STARTTAG_PROJECTADMIN = "<projectadmin>";
  public static final String XML_STARTTAG_RATE = "<rate>";
  protected boolean active;
  protected String firstName = "";
  protected int iTR_ProjectId;
  protected int iTR_UserId;
  protected int id;
  protected String lastName = "";
  protected String loginId = "";
  protected boolean projectAdmin;
  protected int rate;
  boolean stillAssigned = true;

  public void setActive(boolean active) {
    this.active = active;
  }

  public boolean getActive() {
    return active;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setITR_ProjectId(int iTR_ProjectId) {
    this.iTR_ProjectId = iTR_ProjectId;
  }

  public int getITR_ProjectId() {
    return iTR_ProjectId;
  }

  public void setITR_UserId(int iTR_UserId) {
    this.iTR_UserId = iTR_UserId;
  }

  public int getITR_UserId() {
    return iTR_UserId;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLoginId(String loginId) {
    this.loginId = loginId;
  }

  public String getLoginId() {
    return loginId;
  }

  public void setProjectAdmin(boolean projectAdmin) {
    this.projectAdmin = projectAdmin;
  }

  public boolean getProjectAdmin() {
    return projectAdmin;
  }

  public void setRate(int rate) {
    this.rate = rate;
  }

  public int getRate() {
    return rate;
  }

  public void setStillAssigned(boolean stillAssigned) {
    this.stillAssigned = stillAssigned;
  }

  public boolean getStillAssigned() {
    return stillAssigned;
  }

  public boolean delete() throws Exception {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".delete(): Entering");
    }

    boolean retVal = false;

    try {
      retVal = DBExecute.getProxy().deleteProjectMember(this.getId());
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
      StringRecordset rs = DBQueries.getProxy().loadProjectMember(Integer.parseInt(id));

      if (!rs.getEOF()) {
        setId(rs.getInt("Id"));
        setITR_ProjectId(rs.getInt("ITR_ProjectId"));
        setITR_UserId(rs.getInt("ITR_UserId"));
        setRate(rs.getInt("Rate"));

        String tmp = rs.getField("Active");

        if ((tmp != null && tmp.trim().equals("1")) || (tmp != null && tmp.trim().equalsIgnoreCase("true"))) {
          this.setActive(true);
        } else {
          this.setActive(false);
        }

        tmp = rs.getField("ProjectAdmin");

        if ((tmp != null && tmp.trim().equals("1")) || (tmp != null && tmp.trim().equalsIgnoreCase("true"))) {
          this.setProjectAdmin(true);
        } else {
          this.setProjectAdmin(false);
        }

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

  public static List<ProjectMember> loadAssignedProjectMembers(int projectId) throws XMLBuilderException {
    if (IntiroLog.d()) {
      IntiroLog.detail(ProjectMember.class,
              ProjectMember.class.getName() + ".loadAssignedProjectMembers(int projectId): Entering");
    }

    List<ProjectMember> retval = new ArrayList<>();

    try {
      StringRecordset rs = DBQueries.getProxy().loadAssignedProjectMembers(projectId);

      while (!rs.getEOF()) {
        ProjectMember pm = new ProjectMember();
        pm.setId(rs.getInt("Id"));
        pm.setITR_ProjectId(rs.getInt("ITR_ProjectId"));
        pm.setITR_UserId(rs.getInt("ITR_UserId"));
        pm.setRate(rs.getInt("Rate"));

        String tmp = rs.getField("Active");

        if ((tmp != null && tmp.trim().equals("1")) || (tmp != null && tmp.trim().equalsIgnoreCase("true"))) {
          pm.setActive(true);
        } else {
          pm.setActive(false);
        }

        tmp = rs.getField("ProjectAdmin");

        if ((tmp != null && tmp.trim().equals("1")) || (tmp != null && tmp.trim().equalsIgnoreCase("true"))) {
          pm.setProjectAdmin(true);
        } else {
          pm.setProjectAdmin(false);
        }

        pm.setFirstName(rs.getField("FirstName"));
        pm.setLastName(rs.getField("LastName"));
        pm.setLoginId(rs.getField("LoginId"));
        retval.add(pm);
        rs.moveNext();
      }

      rs.close();
    } catch (Exception e) {
      IntiroLog.criticalError(ProjectMember.class, ProjectMember.class.getName() + ".loadAssignedProjectMembers(): ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(ProjectMember.class.getName() + ".loadAssignedProjectMembers(): " + e.getMessage());
    }
    if (IntiroLog.d()) {
      IntiroLog.detail(ProjectMember.class, ProjectMember.class.getName() + ".loadAssignedProjectMembers(): Leaving");
    }

    return retval;
  }

  public static List<ProjectMember> loadAvailableProjectMembers(int projectId) throws XMLBuilderException {
    if (IntiroLog.d()) {
      IntiroLog.detail(ProjectMember.class, ProjectMember.class.getName() + ".loadAvailableProjectMembers(int projectId): Entering");
    }

    List<ProjectMember> retval = new ArrayList<>();

    try {
      StringRecordset rs = DBQueries.getProxy().loadAvailableProjectMembers(projectId);

      while (!rs.getEOF()) {
        ProjectMember pm = new ProjectMember();
        pm.setITR_ProjectId(projectId);
        pm.setITR_UserId(rs.getInt("Id"));
        pm.setRate(0);
        pm.setActive(true);
        pm.setProjectAdmin(false);
        pm.setFirstName(rs.getField("FirstName"));
        pm.setLastName(rs.getField("LastName"));
        pm.setLoginId(rs.getField("LoginId"));
        retval.add(pm);
        rs.moveNext();
      }

      rs.close();
    } catch (Exception e) {
      IntiroLog.criticalError(ProjectMember.class, ProjectMember.class.getName() + ".loadAvailableProjectMembers(): ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(ProjectMember.class.getName() + ".loadAvailableProjectMembers(): " + e.getMessage());
    }
    if (IntiroLog.d()) {
      IntiroLog.detail(ProjectMember.class, ProjectMember.class.getName() + ".loadAvailableProjectMembers(): Leaving");
    }

    return retval;
  }

  public boolean save()
          throws XMLBuilderException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".save(): Entering");
    }
    if (getId() != 0) {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".save(): updating");
      }
      try {
        DBExecute.getProxy().updateProjectMember(this);
      } catch (Exception e) {
        IntiroLog.criticalError(getClass(), getClass().getName() + ".save(): ERROR FROM DATABASE, exception = " + e.getMessage());
        throw new XMLBuilderException(e.getMessage());
      }
    } else {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".save(): creating new");
      }
      try {
        StringRecordset rs = DBQueries.getProxy().makeNewProjectMemberAndFetchId(this);

        if (!rs.getEOF()) {
          setId(rs.getInt("maxId"));
        }
      } catch (Exception e) {
        IntiroLog.criticalError(getClass(), getClass().getName() + ".save(): ERROR FROM DATABASE, exception = " + e.getMessage());
        throw new XMLBuilderException(e.getMessage());
      }
    }

    return true;
  }

  @Override
  public String toString() {
    StringBuilder retval = new StringBuilder();
    retval.append("id = ").append(getId()).append(", ");
    retval.append("iTR_ProjectId = ").append(getITR_ProjectId()).append(", ");
    retval.append("iTR_UserId = ").append(getITR_UserId()).append(", ");
    retval.append("rate = ").append(getRate()).append(", ");
    retval.append("active = ").append(getActive()).append(", ");
    retval.append("projectAdmin = ").append(getProjectAdmin()).append(", ");
    retval.append("firstName = ").append(getFirstName()).append(", ");
    retval.append("lastName = ").append(getLastName()).append(", ");
    retval.append("loginId = ").append(getLoginId()).append(" ");

    return retval.toString();
  }

  public boolean toXML(StringBuffer xmlDoc) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer xmlDoc): Entering");
    }

    xmlDoc.append(XML_STARTTAG_ID);
    xmlDoc.append(getId());
    xmlDoc.append(XML_ENDTAG_ID);
    xmlDoc.append(XML_STARTTAG_ITR_PROJECTID);
    xmlDoc.append(getITR_ProjectId());
    xmlDoc.append(XML_ENDTAG_ITR_PROJECTID);
    xmlDoc.append(XML_STARTTAG_ITR_USERID);
    xmlDoc.append(getITR_UserId());
    xmlDoc.append(XML_ENDTAG_ITR_USERID);
    xmlDoc.append(XML_STARTTAG_RATE);
    xmlDoc.append(getRate());
    xmlDoc.append(XML_ENDTAG_RATE);
    xmlDoc.append(XML_STARTTAG_ACTIVE);
    xmlDoc.append(getActive());
    xmlDoc.append(XML_ENDTAG_ACTIVE);
    xmlDoc.append(XML_STARTTAG_PROJECTADMIN);
    xmlDoc.append(getProjectAdmin());
    xmlDoc.append(XML_ENDTAG_PROJECTADMIN);
    xmlDoc.append(XML_STARTTAG_FIRSTNAME);
    xmlDoc.append(getFirstName());
    xmlDoc.append(XML_ENDTAG_FIRSTNAME);
    xmlDoc.append(XML_STARTTAG_LASTNAME);
    xmlDoc.append(getLastName());
    xmlDoc.append(XML_ENDTAG_LASTNAME);
    xmlDoc.append(XML_STARTTAG_LOGINID);
    xmlDoc.append(getLoginId());
    xmlDoc.append(XML_ENDTAG_LOGINID);

    return true;
  }
}
