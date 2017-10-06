package com.intiro.itr.logic.superadmin.projects;

import java.util.ArrayList;
import com.intiro.itr.logic.activity.Activity;
import com.intiro.itr.logic.project.Project;
import com.intiro.itr.logic.project.ProjectActivity;
import com.intiro.itr.util.combos.CompanyCombo;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.DynamicXMLCarrier;
import com.intiro.itr.util.xml.XMLBuilder;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.log.IntiroLog;
import java.util.List;

public class ProjectActivitiesEditor extends DynamicXMLCarrier {

  static final String XML_ACTIVITY_END = "</activity>";
  static final String XML_ACTIVITY_START = "<activity>";
  static final String XML_ASSIGNED_ACTIVITIES_END = "</assigned>";
  static final String XML_ASSIGNED_ACTIVITIES_START = "<assigned>";
  static final String XML_AVAILABLE_ACTIVITIES_END = "</available>";
  static final String XML_AVAILABLE_ACTIVITIES_START = "<available>";
  static final String XML_COMPANY_END = "</company>";
  static final String XML_COMPANY_START = "<company>";
  static final String XML_PROJECTID_END = "</projectid>";
  static final String XML_PROJECTID_START = "<projectid>";
  static final String XML_PROJECT_END = "</project>";
  static final String XML_PROJECT_START = "<project>";
  static final String XML_TITLE_END = "</title>";
  static final String XML_TITLE_START = "<title>";
  protected List<ProjectActivity> assignedActivities = new ArrayList<>();
  protected List<Activity> availableActivities = new ArrayList<>();
  protected CompanyCombo companyCombo = null;
  protected Project oneProject = null;
  protected int projectId = -1;
  protected String title = "Edit Activities";

  public ProjectActivitiesEditor(UserProfile profile, int projectId) throws XMLBuilderException {
    super(profile);
    this.projectId = projectId;

    //Load the project to edit, view
    this.oneProject = new Project(getUserProfile()); //TODO CHANGE CONSTRUCTOR CALL
    this.oneProject.load(projectId);

    //Company
    this.companyCombo = new CompanyCombo(getUserProfile(), false);
    companyCombo.load(oneProject.getCompanyId());
    companyCombo.setStartEndTags(XML_COMPANY_START, XML_COMPANY_END);
    this.assignedActivities = ProjectActivity.loadProjectActivities(projectId);
    this.availableActivities = Activity.loadActivitiesNotInProject(projectId);
  }

  //~ Methods ..........................................................................................................
  /**
   * Set Activity.
   */
  public void setActivity(ProjectActivity onePA) {
    Activity oneA = new Activity();

    if (oneA.getCode() != null) {
      oneA.setCode(onePA.getCode());
    }

    oneA.setDescription(onePA.getDescription());

    /* TODO: add the entry sorted on code */
    availableActivities.add(oneA);
  }

  /**
   * Get Activity.
   */
  public Activity getActivity(int id) {

    //Activity
    Activity oneA = null;

    //Loop through all the Activity
    for (int i = 0; i < availableActivities.size(); i++) {
      oneA = availableActivities.get(i);

      if (oneA != null && oneA.getId() == id) {
        return oneA;
      }
    }

    return null;
  }

  public Project getModifiedProject() {
    return oneProject;
  }

  /**
   * Get ProjectActivities.
   */
  public List<ProjectActivity> getProjectActivities() {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".getProjectActivities(): Entering");
    }

    return assignedActivities;
  }

  /**
   * Set ProjectActivity.
   */
  public void setProjectActivity(Activity oneA) {
    ProjectActivity onePA = new ProjectActivity();
    onePA.setCode(oneA.getCode());
    onePA.setDescription(oneA.getDescription());
    onePA.setProjectCodeId(oneA.getId());
    onePA.setProjectId(this.getProjectId());

    /* TODO: add the entry sorted on code */
    assignedActivities.add(onePA);
  }

  /**
   * Get ProjectActivity.
   */
  public ProjectActivity getProjectActivity(int id) {

    //ProjectActivity
    ProjectActivity onePA = null;

    //Loop through all the ProjectActivity
    for (int i = 0; i < assignedActivities.size(); i++) {
      onePA = assignedActivities.get(i);

      if (onePA != null && onePA.getId() == id && onePA.getStillAssigned()) {
        return onePA;
      }
    }

    return null;
  }

  /**
   * Setter function
   *
   * return void
   */
  public void setProjectId(int projectId) {
    this.projectId = projectId;
  }

  /**
   * Getter function
   *
   * return an int representing the property.
   */
  public int getProjectId() {
    return projectId;
  }

  /**
   * Remove ProjectActivities. Enter the available ids here.
   */
  public void moveFromAssignedToAvailable(int[] projectCodeIds) {
    ProjectActivity onePA = null;

    for (int i = 0; i < projectCodeIds.length; i++) {
      onePA = getProjectActivity(projectCodeIds[i]);

      if (onePA != null) {
        setActivity(onePA);
        onePA.setStillAssigned(false);

        //                assignedActivities.remove(onePA);
      }
    }
  }

  /**
   * Remove Activities. Enter the assigned ids here.
   */
  public void moveFromAvailableToAssigned(int[] projectCodesIds) {
    Activity oneA = null;

    for (int i = 0; i < projectCodesIds.length; i++) {
      oneA = getActivity(projectCodesIds[i]);

      if (oneA != null) {
        setProjectActivity(oneA);
        availableActivities.remove(oneA);
      }
    }
  }

  /**
   * Save project activities.
   */
  public void save() throws XMLBuilderException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".save(): entered");
    }
    try {
      ProjectActivity onePA = null;

      for (int i = 0; i < assignedActivities.size(); i++) {
        onePA = assignedActivities.get(i);

        if (onePA != null) {
          if (onePA.getStillAssigned()) {
            onePA.save();
          } else {
            onePA.delete();
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
    xmlDoc.append(getTitle());
    xmlDoc.append(XML_TITLE_END);

    //Company combo
    companyCombo.toXML(xmlDoc);

    //Project
    xmlDoc.append(XML_PROJECT_START);

    if (getModifiedProject() != null) {
      getModifiedProject().toXML(xmlDoc);
    }

    xmlDoc.append(XML_PROJECT_END);

    /*Assigned activities*/

 /* This is created in the Project.toXML() class instead!
     xmlDoc.append(XML_ASSIGNED_ACTIVITIES_START);
     try {
     ProjectActivity onePA = null;
     for(int i=0; i < assignedActivities.size(); i++) {
     onePA = (ProjectActivity)assignedActivities.get(i);
     if(onePA != null) {
     xmlDoc.append(XML_ACTIVITY_START);
     onePA.toXML(xmlDoc);
     xmlDoc.append(XML_ACTIVITY_END);
     }
     }
     } catch(Exception e) {
     if(IntiroLog.e()) IntiroLog.error(getClass(), getClass().getName()+".toXML(StringBuffer xmlDoc): The Available part. ERROR FROM DATABASE, exception = " + e.getMessage());
     throw new XMLBuilderException(e.getMessage());
     }
     xmlDoc.append(XML_ASSIGNED_ACTIVITIES_END);
     */

 /*Available activities*/
    xmlDoc.append(XML_AVAILABLE_ACTIVITIES_START);

    try {
      Activity oneA = null;

      for (int i = 0; i < availableActivities.size(); i++) {
        oneA = availableActivities.get(i);

        if (oneA != null) {
          xmlDoc.append(XML_ACTIVITY_START);
          oneA.toXML(xmlDoc);
          xmlDoc.append(XML_ACTIVITY_END);
        }
      }
    } catch (Exception e) {
      IntiroLog.error(getClass(), getClass().getName() + ".toXML(StringBuffer xmlDoc): The Assigned part. ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }

    xmlDoc.append(XML_AVAILABLE_ACTIVITIES_END);

    /*Get end of document*/
    builder.getEndOfDocument(xmlDoc);

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer): xmlDoc = " + xmlDoc.toString());
    }
  }

  /**
   * @return Returns the assignedActivities.
   */
  public List<ProjectActivity> getAssignedActivities() {
    return assignedActivities;
  }

  /**
   * @return Returns the availableActivities.
   */
  public List<Activity> getAvailableActivities() {
    return availableActivities;
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

  /**
   * @return Returns the title.
   */
  public String getTitle() {
    return title;
  }
}
