package com.intiro.itr.logic.superadmin.projects;

import com.intiro.itr.logic.project.Project;
import com.intiro.itr.logic.project.ProjectMember;
import com.intiro.itr.util.combos.CompanyCombo;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.DynamicXMLCarrier;
import com.intiro.itr.util.xml.XMLBuilder;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.log.IntiroLog;
import java.util.List;

public class ProjectEditor extends DynamicXMLCarrier {

  static final String XML_ASSIGNED_PROJECTMEMBERS_END = "</assigned>";
  static final String XML_ASSIGNED_PROJECTMEMBERS_START = "<assigned>";
  static final String XML_COMPANY_END = "</company>";
  static final String XML_COMPANY_START = "<company>";
  static final String XML_MODE_END = "</mode>";
  static final String XML_MODE_START = "<mode>";
  static final String XML_PROJECTMEMBER_END = "</projectmember>";
  static final String XML_PROJECTMEMBER_START = "<projectmember>";
  static final String XML_PROJECT_END = "</project>";
  static final String XML_PROJECT_START = "<project>";
  static final String XML_TITLE_END = "</title>";
  static final String XML_TITLE_START = "<title>";

  //Combos
  protected CompanyCombo companyCombo = null;

  //modes
  protected boolean inAddMode = false;
  protected boolean inDeleteMode = false;
  protected boolean inEditMode = false;
  protected boolean inViewMode = false;
  protected String mode = "not set";
  protected Project oneProject = null;
  protected String projectId = "";

  //Members
  protected List<ProjectMember> projectMembers = null;
  protected String title = "";

  //~ Constructors .....................................................................................................
  public ProjectEditor(UserProfile profile, String mode, String projectId) throws XMLBuilderException {
    super(profile);

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".Constructor(): Entered");
    }
    if (mode != null) {
      if (mode.indexOf("Add") != -1) {
        inAddMode = true;
        mode = "Add";
        title = "Add";
      } else if (mode.indexOf("Edit") != -1) {
        inEditMode = true;
        mode = "Edit";
        title = "Edit";
      } else if (mode.indexOf("View") != -1) { //view mode
        inViewMode = true;
        mode = "View";
        title = "View";
      } else { //delete mode
        inDeleteMode = true;
        mode = "Delete";
        title = "Delete";
      }
    }

    //IntiroLog.detail(getClass().getName()+".doGet(): mode = ."+mode+".");
    //IntiroLog.detail(getClass().getName()+".doGet(): mode.trim() = ."+mode.trim()+".");
    this.mode = mode;
    this.projectId = projectId;

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".Constructor(): projectId = " + projectId);
    }

    //Load the project to edit, view
    Project oneProject = new Project(getUserProfile()); //TODO CHANGE CONSTRUTOR CALL

    if (inEditMode || inViewMode || inDeleteMode) {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".Constructor(): loading project with projid = " + projectId + ", inAddMode =  " + inAddMode);
      }

      oneProject.load(projectId);
    }

    this.oneProject = oneProject;

    //Company
    if (inAddMode) {
      this.companyCombo = new CompanyCombo(getUserProfile());
      companyCombo.load();
    } else {
      this.companyCombo = new CompanyCombo(getUserProfile(), false);
      companyCombo.load(oneProject.getCompanyId());
    }

    companyCombo.setStartEndTags(XML_COMPANY_START, XML_COMPANY_END);

    //Project Members
    this.projectMembers = ProjectMember.loadAssignedProjectMembers(Integer.parseInt(projectId));

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".Constructor(): Leaving");
    }
  }

  //~ Methods ..........................................................................................................
  public Project getModifiedProject() {
    return oneProject;
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

    //Get start of document
    builder.getStartOfDocument(xmlDoc);

    //Company combo
    companyCombo.toXML(xmlDoc);

    //Title
    xmlDoc.append(XML_TITLE_START);
    xmlDoc.append(title);
    xmlDoc.append(XML_TITLE_END);

    //Mode
    xmlDoc.append(XML_MODE_START);
    xmlDoc.append(mode);
    xmlDoc.append(XML_MODE_END);

    //Project Members
    xmlDoc.append(XML_ASSIGNED_PROJECTMEMBERS_START);

    try {
      ProjectMember onePM = null;

      for (int i = 0; i < projectMembers.size(); i++) {
        onePM = (ProjectMember) projectMembers.get(i);

        if (onePM != null) {
          xmlDoc.append(XML_PROJECTMEMBER_START);
          onePM.toXML(xmlDoc);
          xmlDoc.append(XML_PROJECTMEMBER_END);
        }
      }
    } catch (Exception e) {
      IntiroLog.error(getClass(), getClass().getName() + ".toXML(StringBuffer xmlDoc): The Assigned part., exception = " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }

    xmlDoc.append(XML_ASSIGNED_PROJECTMEMBERS_END);

    //Project
    xmlDoc.append(XML_PROJECT_START);

    if (getModifiedProject() != null) {
      getModifiedProject().toXML(xmlDoc);
    }

    xmlDoc.append(XML_PROJECT_END);

    //Get end of document
    builder.getEndOfDocument(xmlDoc);

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer): xmlDoc = " + xmlDoc);
    }
  }

  /**
   * @return Returns the companyCombo.
   */
  public CompanyCombo getCompanyCombo() {
    return companyCombo;
  }

  /**
   * @return Returns the inAddMode.
   */
  public boolean isInAddMode() {
    return inAddMode;
  }

  /**
   * @return Returns the inDeleteMode.
   */
  public boolean isInDeleteMode() {
    return inDeleteMode;
  }

  /**
   * @return Returns the inEditMode.
   */
  public boolean isInEditMode() {
    return inEditMode;
  }

  /**
   * @return Returns the inViewMode.
   */
  public boolean isInViewMode() {
    return inViewMode;
  }

  /**
   * @return Returns the mode.
   */
  public String getMode() {
    return mode;
  }

  /**
   * @return Returns the oneProject.
   */
  public Project getOneProject() {
    return oneProject;
  }

  /**
   * @return Returns the projectId.
   */
  public String getProjectId() {
    return projectId;
  }

  /**
   * @return Returns the projectMembers.
   */
  public List<ProjectMember> getProjectMembers() {
    return projectMembers;
  }

  /**
   * @return Returns the title.
   */
  public String getTitle() {
    return title;
  }
}
