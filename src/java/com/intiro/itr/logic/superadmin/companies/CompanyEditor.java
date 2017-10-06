/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Fredrik Bjork
 * @version       1.0
 */
package com.intiro.itr.logic.superadmin.companies;

import com.intiro.itr.logic.company.Company;
import com.intiro.itr.util.combos.CompanyCombo;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.DynamicXMLCarrier;
import com.intiro.itr.util.xml.XMLBuilder;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.log.IntiroLog;

public class CompanyEditor extends DynamicXMLCarrier {

  //~ Instance/static variables ........................................................................................

  static final String XML_COMPANYPARENT_END = "</companyparent>";
  static final String XML_COMPANYPARENT_START = "<companyparent>";
  static final String XML_MODE_END = "</mode>";
  static final String XML_MODE_START = "<mode>";
  static final String XML_TITLE_END = "</title>";
  static final String XML_TITLE_START = "<title>";
  protected String companyId = "";

  //Combos
  protected CompanyCombo companyParentCombo = null;

  //modes
  protected boolean inAddMode = false;
  protected boolean inDeleteMode = false;
  protected boolean inEditMode = false;
  protected boolean inViewMode = false;
  protected String mode = "not set";
  protected Company oneCompany = null;
  protected String title = "";

  //~ Constructors .....................................................................................................

  public CompanyEditor(UserProfile profile, String mode, String companyId) throws XMLBuilderException {
    super(profile);

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".Constructor(): Entered");
    }
    if (mode != null) {
      if (mode.indexOf("Add") != -1) {
        inAddMode = true;
        mode = "Add";
        title = "Add";
      }
      else if (mode.indexOf("Edit") != -1) {
        inEditMode = true;
        mode = "Edit";
        title = "Edit";
      }
      else if (mode.indexOf("View") != -1) {
        inViewMode = true;
        mode = "View";
        title = "View";
      }
      else { //delete mode
        inDeleteMode = true;
        mode = "Delete";
        title = "Delete";
      }
    }

    this.mode = mode;
    this.companyId = companyId;

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".Constructor(): projectId = " + companyId);
    }

    //Load the company to edit, view
    Company oneCompany = new Company();

    if (inEditMode || inViewMode || inDeleteMode) {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".Constructor(): loading project with projid = " + companyId + ", inAddMode =  " + inAddMode);
      }

      oneCompany.load(Integer.parseInt(companyId));
    }

    this.oneCompany = oneCompany;

    //Company
    if (inAddMode) {
      this.companyParentCombo = new CompanyCombo(getUserProfile(), "No parent company");
      companyParentCombo.load();
    }
    else if (inEditMode || inViewMode || inDeleteMode) {
      this.companyParentCombo = new CompanyCombo(getUserProfile(), "No parent company");
      companyParentCombo.load(Integer.toString(oneCompany.getCompanyParentId()));
    }

    companyParentCombo.setStartEndTags(XML_COMPANYPARENT_START, XML_COMPANYPARENT_END);

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".Constructor(): Leaving");
    }
  }

  //~ Methods ..........................................................................................................

  public Company getModifiedCompany() {
    return oneCompany;
  }

  /**
   * This is the method that will produce the XML.
   * It will fill the xmlDoc with XML.
   * @param    xmlDoc a StringBuffer to be filled with xml.
   */
  public void toXML(StringBuffer xmlDoc) throws Exception {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer): entered");
    }

    XMLBuilder builder = new XMLBuilder();

    //Get start of document
    builder.getStartOfDocument(xmlDoc);

    //Company combo
    companyParentCombo.toXML(xmlDoc);

    //Title
    xmlDoc.append(XML_TITLE_START);
    xmlDoc.append(title);
    xmlDoc.append(XML_TITLE_END);

    //Mode
    xmlDoc.append(XML_MODE_START);
    xmlDoc.append(mode);
    xmlDoc.append(XML_MODE_END);

    //Company
    if (getModifiedCompany() != null) {
      getModifiedCompany().toXML(xmlDoc);
    }

    //Get end of document
    builder.getEndOfDocument(xmlDoc);

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer): xmlDoc = " + xmlDoc);
    }
  }
  /**
   * @return Returns the companyId.
   */
  public String getCompanyId() {
    return companyId;
  }
  /**
   * @return Returns the companyParentCombo.
   */
  public CompanyCombo getCompanyParentCombo() {
    return companyParentCombo;
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
   * @return Returns the oneCompany.
   */
  public Company getOneCompany() {
    return oneCompany;
  }
  /**
   * @return Returns the title.
   */
  public String getTitle() {
    return title;
  }
}