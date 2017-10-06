package com.intiro.itr.logic.superadmin.projectmember;

import java.util.ArrayList;
import com.intiro.itr.logic.project.Project;
import com.intiro.itr.logic.project.ProjectMember;
import com.intiro.itr.util.combos.CompanyCombo;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.DynamicXMLCarrier;
import com.intiro.itr.util.xml.XMLBuilder;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.log.IntiroLog;
import java.util.List;

public class ProjectMembersEditor extends DynamicXMLCarrier {

  static final String XML_ASSIGNED_PROJECTMEMBERS_END = "</assigned>";
  static final String XML_ASSIGNED_PROJECTMEMBERS_START = "<assigned>";
  static final String XML_AVAILABLE_PROJECTMEMBERS_END = "</available>";
  static final String XML_AVAILABLE_PROJECTMEMBERS_START = "<available>";
  static final String XML_COMPANY_END = "</company>";
  static final String XML_COMPANY_START = "<company>";
  static final String XML_PROJECTID_END = "</projectid>";
  static final String XML_PROJECTID_START = "<projectid>";
  static final String XML_PROJECTMEMBER_END = "</projectmember>";
  static final String XML_PROJECTMEMBER_START = "<projectmember>";
  static final String XML_PROJECT_END = "</project>";
  static final String XML_PROJECT_START = "<project>";
  static final String XML_TITLE_END = "</title>";
  static final String XML_TITLE_START = "<title>";
  protected boolean active = true;
  protected List<ProjectMember> assignedMembers = new ArrayList<>();
  protected List<ProjectMember> availableMembers = new ArrayList<>();
  protected CompanyCombo companyCombo = null;
  protected Project oneProject = null;
  protected boolean projectAdmin = false;
  protected int projectId = -1;
  protected int rate = 0;
  protected String title = "Edit Members";

  /**
   * @return Returns the title.
   */
  public String getTitle() {
    return title;
  }

  public ProjectMembersEditor(UserProfile profile, int projectId) throws XMLBuilderException {
    super(profile);
    this.projectId = projectId;

    //Load the project to edit, view
    this.oneProject = new Project(getUserProfile()); //TODO CHANGE CONSTRUCTOR CALL
    this.oneProject.load(projectId);

    //Company
    this.companyCombo = new CompanyCombo(getUserProfile(), false);
    companyCombo.load(oneProject.getCompanyId());
    companyCombo.setStartEndTags(XML_COMPANY_START, XML_COMPANY_END);

    //Project Members
    this.assignedMembers = ProjectMember.loadAssignedProjectMembers(projectId);
    this.availableMembers = ProjectMember.loadAvailableProjectMembers(projectId);
  }

  //~ Methods ..........................................................................................................
  /**
   * Setter function return void
   */
  public void setActive(boolean active) {
    this.active = active;
  }

  /**
   * Getter function return a boolean representing the property.
   */
  public boolean getActive() {
    return active;
  }

  /**
   * Get Assigned ProjectMembers.
   */
  public List<ProjectMember> getAssignedProjectActivities() {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".getAssignedProjectActivities(): Entering");
    }

    return assignedMembers;
  }

  /**
   * Set Assigned ProjectMember.
   */
  public void setAssignedProjectMember(ProjectMember onePM) {
    onePM.setITR_ProjectId(this.getProjectId());
    onePM.setRate(this.getRate());
    onePM.setActive(this.getActive());
    onePM.setProjectAdmin(this.getProjectAdmin());
    assignedMembers.add(onePM);
  }

  /**
   * Get Assigned ProjectMember.
   */
  public ProjectMember getAssignedProjectMember(int id) {
    ProjectMember onePM = null;

    //Loop through all the ProjectMembers
    for (int i = 0; i < assignedMembers.size(); i++) {
      onePM = assignedMembers.get(i);

      if (onePM != null && onePM.getId() == id && onePM.getStillAssigned()) {
        return onePM;
      }
    }

    return null;
  }

  /**
   * Set Available ProjectMember.
   */
  public void setAvailableMember(ProjectMember onePM) {
    availableMembers.add(onePM);
  }

  /**
   * Get Available ProjectMember.
   */
  public ProjectMember getAvailableProjectMember(int id) {
    ProjectMember onePM = null;

    for (int i = 0; i < availableMembers.size(); i++) {
      onePM = availableMembers.get(i);

      if (onePM != null && onePM.getITR_UserId() == id) {
        return onePM;
      }
    }

    return null;
  }

  public Project getModifiedProject() {
    return oneProject;
  }

  /**
   * Setter function return void
   */
  public void setProjectAdmin(boolean projectAdmin) {
    this.projectAdmin = projectAdmin;
  }

  /**
   * Getter function return a boolean representing the property.
   */
  public boolean getProjectAdmin() {
    return projectAdmin;
  }

  /**
   * Setter function return void
   */
  public void setProjectId(int projectId) {
    this.projectId = projectId;
  }

  /**
   * Getter function return an int representing the property.
   */
  public int getProjectId() {
    return projectId;
  }

  /**
   * Setter function return void
   */
  public void setRate(int rate) {
    this.rate = rate;
  }

  /**
   * Getter function return an int representing the property.
   */
  public int getRate() {
    return rate;
  }

  /**
   * Remove Assigned ProjectMembers. Enter the available ids here.
   */
  public void moveFromAssignedToAvailable(int[] availablePMIds) {
    ProjectMember onePM = null;

    for (int i = 0; i < availablePMIds.length; i++) {
      onePM = getAssignedProjectMember(availablePMIds[i]);

      if (onePM != null) {
        setAvailableMember(onePM);
        onePM.setStillAssigned(false);
        //assignedMembers.remove(onePM);
      }
    }
  }

  /**
   * Remove Available Activities. Enter the assigned ids here.
   */
  public void moveFromAvailableToAssigned(int[] assignedPMIds) {
    ProjectMember onePM = null;

    for (int i = 0; i < assignedPMIds.length; i++) {
      onePM = getAvailableProjectMember(assignedPMIds[i]);

      if (onePM != null) {
        setAssignedProjectMember(onePM);
        availableMembers.remove(onePM);
      }
    }
  }

  /**
   * Save project Members.
   */
  public void save() throws XMLBuilderException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".save(): entered");
    }
    try {
      ProjectMember onePM = null;

      for (int i = 0; i < assignedMembers.size(); i++) {
        onePM = assignedMembers.get(i);

        if (onePM != null) {
          if (onePM.getStillAssigned()) {
            onePM.save();
          } else {
            onePM.delete();
          }
        }
      }
    } catch (Exception e) {
      IntiroLog.error(getClass(), getClass().getName() + ".save(): ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }
  }

  /**
   * This is the method that will produce the XML. It will fill the xmlDoc with XML.
   *
   * @param xmlDoc a StringBuffer to be filled with xml.
   */
  public void toXML(StringBuffer xmlDoc) throws Exception {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer): entered");
    }

    XMLBuilder builder = new XMLBuilder();

    /*Get start of document*/
    builder.getStartOfDocument(xmlDoc);

    //Title
    xmlDoc.append(XML_TITLE_START);
    xmlDoc.append(title);
    xmlDoc.append(XML_TITLE_END);

    //Company combo
    companyCombo.toXML(xmlDoc);

    //Project
    xmlDoc.append(XML_PROJECT_START);

    if (getModifiedProject() != null) {
      getModifiedProject().toXML(xmlDoc);
    }

    xmlDoc.append(XML_PROJECT_END);

    /*Assigned members*/
    // This is created in the Project.toXML() class instead!
    xmlDoc.append(XML_ASSIGNED_PROJECTMEMBERS_START);

    try {
      ProjectMember onePM = null;

      for (int i = 0; i < assignedMembers.size(); i++) {
        onePM = assignedMembers.get(i);

        if (onePM != null) {
          xmlDoc.append(XML_PROJECTMEMBER_START);
          onePM.toXML(xmlDoc);
          xmlDoc.append(XML_PROJECTMEMBER_END);
        }
      }
    } catch (Exception e) {
      IntiroLog.error(getClass(), getClass().getName() + ".toXML(StringBuffer xmlDoc): The Assigned part. ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }

    xmlDoc.append(XML_ASSIGNED_PROJECTMEMBERS_END);

    /*Available members*/
    // This is created in the Project.toXML() class instead!
    xmlDoc.append(XML_AVAILABLE_PROJECTMEMBERS_START);

    try {
      ProjectMember onePM = null;

      for (int i = 0; i < availableMembers.size(); i++) {
        onePM = availableMembers.get(i);

        if (onePM != null) {
          xmlDoc.append(XML_PROJECTMEMBER_START);
          onePM.toXML(xmlDoc);
          xmlDoc.append(XML_PROJECTMEMBER_END);
        }
      }
    } catch (Exception e) {
      IntiroLog.error(getClass(), getClass().getName() + ".toXML(StringBuffer xmlDoc): The Available part. ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }

    xmlDoc.append(XML_AVAILABLE_PROJECTMEMBERS_END);

    /*Get end of document*/
    builder.getEndOfDocument(xmlDoc);

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer): xmlDoc = " + xmlDoc.toString());
    }
  }

  /**
   * @return Returns the assignedMembers.
   */
  public List<ProjectMember> getAssignedMembers() {
    return assignedMembers;
  }

  /**
   * @return Returns the availableMembers.
   */
  public List<ProjectMember> getAvailableMembers() {
    return availableMembers;
  }
  
  /**
   * @return Returns the companyCombo.
   */
  public CompanyCombo getCompanyCombo() {
    return companyCombo;
  }

  /**
   * @return Returns the oneProject.
   */
  public Project getOneProject() {
    return oneProject;
  }
}
