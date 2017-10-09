package com.intiro.itr.logic.project;

import java.util.ArrayList;

import com.intiro.itr.db.DBConstants;
import com.intiro.itr.db.DBExecute;
import com.intiro.itr.db.DBQueries;
import com.intiro.itr.db.vo.ProjectPropertyVO;
import com.intiro.itr.util.ITRCalendar;
import com.intiro.itr.util.ItrUtil;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.DynamicXMLCarrier;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.log.IntiroLog;
import java.util.List;

public class Project extends DynamicXMLCarrier {

  protected static final String XML_ACTIVITY_END = "</activity>";
  protected static final String XML_ACTIVITY_START = "<activity>";
  protected static final String XML_ASSIGNED_ACTIVITIES_END = "</assigned>";
  protected static final String XML_ASSIGNED_ACTIVITIES_START = "<assigned>";
  protected static final String XML_ROW_ACTIVATED_END = "</activated>";
  protected static final String XML_ROW_ACTIVATED_START = "<activated>";
  protected static final String XML_ROW_ADMINPROJ_END = "</adminproj>";
  protected static final String XML_ROW_ADMINPROJ_START = "<adminproj>";
  protected static final String XML_ROW_COMPANY_END = "</companyid>";
  protected static final String XML_ROW_COMPANY_START = "<companyid>";
  protected static final String XML_ROW_CONTRACT_END = "</contract>";
  protected static final String XML_ROW_CONTRACT_START = "<contract>";
  protected static final String XML_ROW_FROMDATE_END = "</fromdate>";
  protected static final String XML_ROW_FROMDATE_START = "<fromdate>";
  protected static final String XML_ROW_PROJACT_END = "</projectactivity>";
  // Project Activity
  protected static final String XML_ROW_PROJACT_START = "<projectactivity>";
  protected static final String XML_ROW_PROJCODE_END = "</projcode>";
  protected static final String XML_ROW_PROJCODE_START = "<projcode>";
  protected static final String XML_ROW_PROJDESC_END = "</projdesc>";
  protected static final String XML_ROW_PROJDESC_START = "<projdesc>";
  protected static final String XML_ROW_PROJID_END = "</projid>";
  protected static final String XML_ROW_PROJID_START = "<projid>";
  protected static final String XML_ROW_PROJNAME_END = "</projname>";
  protected static final String XML_ROW_PROJNAME_START = "<projname>";
  protected static final String XML_ROW_PROJSUBCODE_END = "</subcode>";
  protected static final String XML_ROW_PROJSUBCODE_START = "<subcode>";
  protected static final String XML_ROW_PROJSUBID_END = "</subid>";
  // Row
  protected static final String XML_ROW_PROJSUBID_START = "<subid>";
  protected static final String XML_ROW_PROJTECH_END = "</projtech>";
  protected static final String XML_ROW_PROJTECH_START = "<projtech>";
  protected static final String XML_ROW_TODATE_END = "</todate>";
  protected static final String XML_ROW_TODATE_START = "<todate>";
  protected boolean activated = true;
  protected boolean adminProject = false;
  protected List<ProjectActivity> assignedActivities = null;
  protected String companyId = "";
  protected boolean contract = false;
  protected ITRCalendar fromDate = null;
  protected ProjectActivity projAct = new ProjectActivity();
  protected String projCode = "";
  protected String projDesc = "";
  protected String projId = "-1";
  protected String projName = "";
  protected String technique = "";
  //Not connected to database yet: TODO connect to database
  protected int fixedPrice = 0;
  protected ITRCalendar toDate = null;

  public Project(UserProfile userProfile) throws XMLBuilderException {
    super(userProfile);
  }

  public void setActivated(String status) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".setActivated(): Entering");
    }
    if (status.equalsIgnoreCase("true") || status.equalsIgnoreCase("1")) {
      activated = true;
    } else {
      activated = false;
    }
  }

  public boolean getActivated() {
    return activated;
  }

  public void setAdminProject(String status) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".setAdminProject(String status):");
    }
    if (status.equalsIgnoreCase("true") || status.equalsIgnoreCase("1")) {
      adminProject = true;
    } else {
      adminProject = false;
    }
  }

  public boolean getAdminProject() {
    return adminProject;
  }

  public void setCompanyId(String id) {
    companyId = id;
  }

  public String getCompanyId() {
    return companyId;
  }

  public void setContract(String status) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".setContract(): Entering");
    }
    if (status.equalsIgnoreCase("true") || status.equalsIgnoreCase("1")) {
      contract = true;
    } else {
      contract = false;
    }
  }

  public boolean getContract() {
    return contract;
  }

  public void setFromDate(ITRCalendar fromDate) {

    // if(IntiroLog.d()) if(IntiroLog.d()) IntiroLog.detail(getClass(), getClass(), getClass().getName()+".setFromDate(): fromDate = "+fromDate);
    this.fromDate = fromDate;
  }

  public void setFromDate(String fromDateString) {

    // if(IntiroLog.d()) if(IntiroLog.d()) IntiroLog.detail(getClass(), getClass(), getClass().getName()+".setFromDate(String): fromDateString = "+fromDateString);
    ITRCalendar fromDate = new ITRCalendar(fromDateString);
    this.fromDate = fromDate;
  }

  public ITRCalendar getFromDate() {
    return fromDate;
  }

  public void setProjectActivities(ArrayList assignedActivities) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".setProjectActivities(): assignedActivities = " + assignedActivities.toString());
    }

    this.assignedActivities = assignedActivities;
  }

  public List<ProjectActivity> getProjectActivities() {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".getProjectActivities(): assignedActivities = " + assignedActivities.toString());
    }

    return this.assignedActivities;
  }

  public void setProjectActivity(ProjectActivity projAct) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".setProjectActivity(): projAct = " + projAct.toString());
    }

    this.projAct = projAct;
  }

  public ProjectActivity getProjectActivity() {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".getProjectActivityc(): projAct = " + this.projAct.toString());
    }

    return this.projAct;
  }

  public void setProjectActivityId(String projActId) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".setProjectActivityId(): projActId = " + projActId);
    }
    // this.projAct.setId(Integer.parseInt(projActId));
    try {
      this.projAct.load(projActId);
    } catch (Exception e) {
      // empty
    }
  }

  public String getProjectActivityId() {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".setProjectActivity(): getProjectActivityId = " + String.valueOf(projAct.getId()));
    }

    return String.valueOf(projAct.getId());
  }

  public void setProjectCode(String code) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".setProjectCode(): Code = " + code);
    }

    projCode = code;
  }

  public String getProjectCode() {
    return projCode;
  }

  public void setProjectDesc(String desc) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".setProjectDesc(): desc = " + desc);
    }

    projDesc = desc;
  }

  public String getProjectDesc() {
    return projDesc;
  }

  public void setProjectId(String id) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".setProjectId(): Id = " + id);
    }

    projId = id;
  }

  public String getProjectId() {
    return projId;
  }

  public void setProjectName(String name) {
    projName = name;
  }

  public String getProjectName() {
    return projName;
  }

  public void setTechnique(String tech) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".setTechnique(String tech):");
    }

    technique = tech;
  }

  public String getTechnique() {
    return technique;
  }

  public void setToDate(ITRCalendar toDate) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".setToDate(): toDate = " + toDate);
    }

    this.toDate = toDate;
  }

  public void setToDate(String toDateString) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".setToDate(String): toDateString = " + toDateString);
    }

    ITRCalendar toDate = new ITRCalendar(toDateString);
    this.toDate = toDate;
  }

  public ITRCalendar getToDate() {
    return toDate;
  }

  public Project cloneProject() {
    Project retval = null;

    try {
      retval = new Project(userProfile);

      // retval.setProjectSubCode(getProjectSubCode());
      // retval.setProjectSubId(getProjectSubId());
      retval.setProjectActivityId(getProjectActivityId());

      if (getProjectCode() != null) {
        retval.setProjectCode(getProjectCode());
      }

      retval.setProjectId(getProjectId());
      retval.setProjectDesc(getProjectDesc());
      retval.setProjectName(getProjectName());
      retval.setCompanyId(getCompanyId());

      if (getFromDate() != null) {
        retval.setFromDate(getFromDate().cloneCalendar());
      }
      if (getToDate() != null) {
        retval.setToDate(getToDate().cloneCalendar());
      }

      retval.setTechnique(getTechnique());
      retval.setActivated(String.valueOf(getActivated()));
      retval.setAdminProject(String.valueOf(getAdminProject()));
      retval.setContract(String.valueOf(getContract()));
    } catch (XMLBuilderException e) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".cloneProject(): " + e.getMessage());
    }

    return retval;
  }

  public boolean delete() throws Exception {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".delete(): Entering");
    }

    boolean retVal = false;

    try {
      retVal = DBExecute.getProxy().deleteProject(Integer.parseInt(getProjectId()));
    } catch (Exception e) {
      IntiroLog.info(getClass(), getClass().getName() + ".delete(): ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }

    return retVal;
  }

  public void load(int projId) throws XMLBuilderException {
    load(String.valueOf(projId));
  }

  public void load(String projId) throws XMLBuilderException {
    try {
      ProjectPropertyVO vo = DBQueries.getProxy().getProjectProperty(projId);

      if (vo != null && ItrUtil.isStrContainingValue(vo.getProjectId())) {
        setProjectId(vo.getProjectId());
        setProjectDesc(vo.getProjectDescription());
        setProjectCode(vo.getProjectCode());
        setProjectName(vo.getProjectName());
        setFromDate(vo.getFromDate());
        setToDate(vo.getToDate());
        setCompanyId(vo.getCompanyId());
        setActivated(vo.getActivated());
        setAdminProject(vo.getAdminProject());
        setContract(vo.getContract());
        setTechnique(vo.getTechnique());
      }
    } catch (Exception e) {
      IntiroLog.error(getClass(), getClass().getName() + ".load(String): ERROR FROM DATABASE, Collecting Project data. exception = " + e.getMessage());
      throw new XMLBuilderException(getClass().getName() + ".load(String): " + e.getMessage());
    }
    try {
      this.assignedActivities = ProjectActivity.loadProjectActivities(Integer.parseInt(projId));
    } catch (Exception e) {
      IntiroLog.error(getClass(), getClass().getName() + ".load(String): ERROR FROM DATABASE, Collecting Project Activity data. exception = " + e.getMessage());
      throw new XMLBuilderException(getClass().getName() + ".load(String): " + e.getMessage());
    }
  }

  public void save() throws XMLBuilderException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".save(): Entering");
    }
    if (getProjectId() != null && !getProjectId().equalsIgnoreCase("-1")) {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".save(): updating");
      }
      try {
        DBExecute.getProxy().updateProject(this);
      } catch (Exception e) {
        IntiroLog.criticalError(getClass(), getClass().getName() + ".save(): ERROR FROM DATABASE, exception = " + e.getMessage());
        throw new XMLBuilderException(e.getMessage());
      }
    } else { // Make a new project
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".save(): creating new");
      }
      try {
        StringRecordset rs = DBQueries.getProxy().makeProjectAndFetchId(this);

        if (!rs.getEOF()) {
          setProjectId(rs.getField("maxId"));
        }
      } catch (Exception e) {
        IntiroLog.criticalError(getClass(), getClass().getName() + ".save(): ERROR FROM	DATABASE, exception	= " + e.getMessage());
        throw new XMLBuilderException(e.getMessage());
      }
    }
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    toString(sb);

    return sb.toString();
  }

  public void toString(StringBuffer sb) {

    /* project ide */
    sb.append("[projId = ");
    sb.append(projId);

    // project code
    sb.append(", projCode = ");
    sb.append(projCode);

    // Project Activity
    sb.append(", projActId = ");
    sb.append(getProjectActivityId());

    /*
     * // project sub Id sb.append(", projSubId = "); sb.append(projSubId); // project sub code sb.append(", projSubCode = "); sb.append(projSubCode);
     */
    // project description
    sb.append(", projDesc = ");
    sb.append(projDesc);

    /* project name */
    sb.append(", projName = ");
    sb.append(projName);

    /* activated */
    sb.append(", activated = ");
    sb.append(activated);

    /* contract */
    sb.append(", contract = ");
    sb.append(contract);

    /* adminproj */
    sb.append(", adminproj = ");
    sb.append(adminProject);

    /* fromdate */
    sb.append(", fromdate = ");

    if (fromDate != null) {
      sb.append(fromDate.getCalendarInStoreFormat());
    }

    /* todate */
    sb.append(", todate = ");

    if (toDate != null) {
      sb.append(toDate.getCalendarInStoreFormat());
    }

    /* tech */
    sb.append(", tech = ");
    sb.append(technique);

    /* company */
    sb.append(", companyid = ");
    sb.append(companyId);
    sb.append("]");
  }

  @Override
  public void toXML(StringBuffer xmlDoc) throws Exception {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(): Entering");
    }

    /*
     * xmlDoc.append(XML_ROW_PROJSUBID_START); xmlDoc.append(projSubId); xmlDoc.append(XML_ROW_PROJSUBID_END); xmlDoc.append(XML_ROW_PROJSUBCODE_START); xmlDoc.append(projSubCode);
     * xmlDoc.append(XML_ROW_PROJSUBCODE_END);
     */
    xmlDoc.append(XML_ROW_PROJID_START);
    xmlDoc.append(projId);
    xmlDoc.append(XML_ROW_PROJID_END);
    xmlDoc.append(XML_ROW_PROJCODE_START);
    xmlDoc.append(projCode);
    xmlDoc.append(XML_ROW_PROJCODE_END);
    xmlDoc.append(XML_ROW_PROJDESC_START);
    xmlDoc.append(projDesc);
    xmlDoc.append(XML_ROW_PROJDESC_END);
    xmlDoc.append(XML_ROW_PROJNAME_START);
    xmlDoc.append(projName);
    xmlDoc.append(XML_ROW_PROJNAME_END);
    xmlDoc.append(XML_ROW_ACTIVATED_START);
    xmlDoc.append(activated);
    xmlDoc.append(XML_ROW_ACTIVATED_END);
    xmlDoc.append(XML_ROW_PROJTECH_START);
    xmlDoc.append(technique);
    xmlDoc.append(XML_ROW_PROJTECH_END);
    xmlDoc.append(XML_ROW_FROMDATE_START);

    if (fromDate != null) {
      xmlDoc.append(fromDate.getCalendarInStoreFormat());
    }

    xmlDoc.append(XML_ROW_FROMDATE_END);
    xmlDoc.append(XML_ROW_TODATE_START);

    if (toDate != null) {
      xmlDoc.append(toDate.getCalendarInStoreFormat());
    }

    xmlDoc.append(XML_ROW_TODATE_END);
    xmlDoc.append(XML_ROW_ADMINPROJ_START);
    xmlDoc.append(adminProject);
    xmlDoc.append(XML_ROW_ADMINPROJ_END);
    xmlDoc.append(XML_ROW_CONTRACT_START);
    xmlDoc.append(contract);
    xmlDoc.append(XML_ROW_CONTRACT_END);
    xmlDoc.append(XML_ROW_COMPANY_START);
    xmlDoc.append(companyId);
    xmlDoc.append(XML_ROW_COMPANY_END);
    xmlDoc.append(XML_ASSIGNED_ACTIVITIES_START);

    if (assignedActivities != null) {
      try {
        ProjectActivity onePA;

        for (int i = 0; i < assignedActivities.size(); i++) {
          onePA = (ProjectActivity) assignedActivities.get(i);

          if (onePA != null) {
            xmlDoc.append(XML_ACTIVITY_START);
            onePA.toXML(xmlDoc);
            xmlDoc.append(XML_ACTIVITY_END);
          }
        }
      } catch (Exception e) {
        IntiroLog.error(getClass(), getClass().getName() + ".toXML(StringBuffer xmlDoc): The Available part. ERROR FROM DATABASE, exception = " + e.getMessage());
        throw new XMLBuilderException(e.getMessage());
      }
    }

    xmlDoc.append(XML_ASSIGNED_ACTIVITIES_END);
    xmlDoc.append(XML_ROW_PROJACT_START);
    this.projAct.toXML(xmlDoc);
    xmlDoc.append(XML_ROW_PROJACT_END);
  }

  public int getFixedPrice() {
    return fixedPrice;
  }

  public void setFixedPrice(int fixedPrice) {
    this.fixedPrice = fixedPrice;
  }
}
